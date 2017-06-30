package org.tio.http.common.http;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.exception.AioDecodeException;
import org.tio.http.common.http.HttpConst.RequestBodyFormat;
import org.tio.http.common.utils.HttpUtil;
import org.tio.json.Json;

/**
 * 
 * @author tanyaowu 
 *
 */
public class HttpRequestDecoder {
	private static Logger log = LoggerFactory.getLogger(HttpRequestDecoder.class);

	/**
	 * 
	 *
	 * @author: tanyaowu
	 * 2017年2月22日 下午4:06:42
	 * 
	 */
	public HttpRequestDecoder() {

	}

	public static final int MAX_HEADER_LENGTH = 20480;

	public static HttpRequestPacket decode(ByteBuffer buffer) throws AioDecodeException {
		int count = 0;
		Step step = Step.firstline;
		StringBuilder currLine = new StringBuilder();
		Map<String, String> headers = new HashMap<>();
		int contentLength = 0;
		byte[] bodyBytes = null;
		RequestLine firstLine = null;
		while (buffer.hasRemaining()) {
			count++;
			if (count > MAX_HEADER_LENGTH) {
				throw new AioDecodeException("max http header length " + MAX_HEADER_LENGTH);
			}

			byte b = buffer.get();

			if (b == '\n') {
				if (currLine.length() == 0) {
					String contentLengthStr = headers.get("Content-Length");
					if (StringUtils.isBlank(contentLengthStr)) {
						contentLength = 0;
					} else {
						contentLength = Integer.parseInt(contentLengthStr);
					}

					int readableLength = buffer.limit() - buffer.position();
					if (readableLength >= contentLength) {
						step = Step.body;
						break;
					} else {
						return null;
					}
				} else {
					if (step == Step.firstline) {
						firstLine = parseRequestLine(currLine.toString());
						step = Step.header;
					} else if (step == Step.header) {
						KeyValue keyValue = parseHeaderLine(currLine.toString());
						headers.put(keyValue.getKey(), keyValue.getValue());
					}

					currLine.setLength(0);
				}
				continue;
			} else if (b == '\r') {
				continue;
			} else {
				currLine.append((char) b);
			}
		}

		if (step != Step.body) {
			return null;
		}

		if (contentLength > 0) {
			bodyBytes = new byte[contentLength];
			buffer.get(bodyBytes);
		}

		if (!headers.containsKey(HttpConst.RequestHeaderKey.Host)) {
			throw new AioDecodeException("there is no host header");
		}

		HttpRequestPacket httpRequestPacket = new HttpRequestPacket();
		

		httpRequestPacket.setRequestLine(firstLine);
		httpRequestPacket.setHeaders(headers);
		httpRequestPacket.setContentLength(contentLength);

		//解析消息体
		parseBody(httpRequestPacket, firstLine, bodyBytes);

		//解析User_Agent(浏览器操作系统等信息)
//		String User_Agent = headers.get(HttpConst.RequestHeaderKey.User_Agent);
//		if (StringUtils.isNotBlank(User_Agent)) {
//			//			long start = System.currentTimeMillis();
//			UserAgentAnalyzer userAgentAnalyzer = UserAgentAnalyzerFactory.getUserAgentAnalyzer();
//			UserAgent userAgent = userAgentAnalyzer.parse(User_Agent);
//			httpRequestPacket.setUserAgent(userAgent);
//		}

		
//		StringBuilder logstr = new StringBuilder();
//		logstr.append("\r\n------------------ websocket header start ------------------------\r\n");
//		logstr.append(firstLine.getInitStr()).append("\r\n");
//		Set<Entry<String, String>> entrySet = headers.entrySet();
//		for (Entry<String, String> entry : entrySet) {
//			logstr.append(StringUtils.leftPad(entry.getKey(), 30)).append(" : ").append(entry.getValue()).append("\r\n");
//		}
//		logstr.append("------------------ websocket header start ------------------------\r\n");
//		log.error(logstr.toString());

		return httpRequestPacket;

	}
	/**
	 * Content-Type : application/x-www-form-urlencoded; charset=UTF-8
	 * Content-Type : application/x-www-form-urlencoded; charset=UTF-8
	 * @param httpRequestPacket
	 * @param headers
	 * @author: tanyaowu
	 */
	public static void parseBodyFormat(HttpRequestPacket httpRequestPacket, Map<String, String> headers) {
		String Content_Type = headers.get(HttpConst.RequestHeaderKey.Content_Type);
		RequestBodyFormat bodyFormat = null;
		if (StringUtils.contains(Content_Type, HttpConst.RequestHeaderValue.Content_Type.application_x_www_form_urlencoded)) {
			bodyFormat = RequestBodyFormat.URLENCODED;
		} else if (StringUtils.contains(Content_Type, HttpConst.RequestHeaderValue.Content_Type.multipart_form_data)) {
			bodyFormat = RequestBodyFormat.MULTIPART;
		} else {
			bodyFormat = RequestBodyFormat.TEXT;
		}
		httpRequestPacket.setBodyFormat(bodyFormat);

		if (StringUtils.isNoneBlank(Content_Type)) {
			String[] ss = StringUtils.split(Content_Type, ";");
			if (ss.length > 1) {
				for (String str : ss) {
					String[] ss1 = StringUtils.split(str, "=");
					if (ss1.length > 1) {
						String key = ss1[0];
						String value = ss1[1];
						if (StringUtils.endsWithIgnoreCase(key, "charset")) {
							httpRequestPacket.setCharset(value);
							log.info("解析到charset:{}", value);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 解析消息体 TODO: MULTIPART待完成
	 * @param httpRequestPacket
	 * @param firstLine
	 * @param bodyBytes
	 * @author: tanyaowu
	 */
	private static void parseBody(HttpRequestPacket httpRequestPacket, RequestLine firstLine, byte[] bodyBytes) {
		parseBodyFormat(httpRequestPacket, httpRequestPacket.getHeaders());
		RequestBodyFormat bodyFormat = httpRequestPacket.getBodyFormat();
		
		String bodyString = null;
		if (bodyBytes != null) {
			httpRequestPacket.setBody(bodyBytes);
			try {
				bodyString = new String(bodyBytes, httpRequestPacket.getCharset());
				httpRequestPacket.setBodyString(bodyString);
				log.error("bodyString:{}",bodyString);
			} catch (UnsupportedEncodingException e) {
				log.error(e.toString(), e);
			}
		}
		
		if (bodyFormat == RequestBodyFormat.URLENCODED) {
			parseUrlencoded(httpRequestPacket, firstLine, bodyBytes, bodyString);
		}
	}

	/**
	 * 解析URLENCODED格式的消息体
	 * 形如： 【Content-Type : application/x-www-form-urlencoded; charset=UTF-8】
	 * @author: tanyaowu
	 */
	private static void parseUrlencoded(HttpRequestPacket httpRequestPacket, RequestLine firstLine, byte[] bodyBytes, String bodyString) {
		String paramStr = "";
		if (StringUtils.isNotBlank(firstLine.getQueryStr())) {
			paramStr += firstLine.getQueryStr();
		}
		if (bodyString != null) {
			if (paramStr != null) {
				paramStr += "&";
			}
			paramStr += bodyString;
		}

		if (paramStr != null) {
			Map<String, List<String>> params = HttpUtil.decodeParams(paramStr, httpRequestPacket.getCharset());
			httpRequestPacket.setParams(params);
			log.error("paramStr:{}", paramStr);
			log.error("param:{}", Json.toJson(params));
		}
	}
	
//	private static void parseText(HttpRequestPacket httpRequestPacket, RequestLine firstLine, byte[] bodyBytes, String bodyString) {
//		String paramStr = "";
//		if (StringUtils.isNotBlank(firstLine.getQueryStr())) {
//			paramStr += firstLine.getQueryStr();
//		}
//		if (bodyString != null) {
//			if (paramStr != null) {
//				paramStr += "&";
//			}
//			paramStr += bodyString;
//		}
//
//		if (paramStr != null) {
//			Map<String, List<String>> params = HttpUtil.decodeParams(paramStr, httpRequestPacket.getCharset());
//			httpRequestPacket.setParams(params);
//			log.error("paramStr:{}", paramStr);
//			log.error("param:{}", Json.toJson(params));
//		}
//	}



	/**
	 * 解析第一行(请求行)
	 * @param line
	 * @return
	 *
	 * @author: tanyaowu
	 * 2017年2月23日 下午1:37:51
	 *
	 */
	public static RequestLine parseRequestLine(String line) {
		int index1 = line.indexOf(' ');
		String method = StringUtils.upperCase(line.substring(0, index1));
		int index2 = line.indexOf(' ', index1 + 1);
		String requestUrl = line.substring(index1 + 1, index2);
		String queryStr = null;
		int indexOfQuestionmark = requestUrl.indexOf("?");
		if (indexOfQuestionmark != -1) {
			queryStr = StringUtils.substring(requestUrl, indexOfQuestionmark + 1);
		}

		String version = line.substring(index2 + 1);

		RequestLine requestLine = new RequestLine();
		requestLine.setMethod(method);
		requestLine.setPath(requestUrl);
		requestLine.setQueryStr(queryStr);
		requestLine.setVersion(version);
		requestLine.setInitStr(line);
		return requestLine;
	}

	/**
	 * 解析请求头的每一行
	 * @param line
	 * @return
	 *
	 * @author: tanyaowu
	 * 2017年2月23日 下午1:37:58
	 *
	 */
	public static KeyValue parseHeaderLine(String line) {
		KeyValue keyValue = new KeyValue();
		int p = line.indexOf(":");
		if (p == -1) {
			keyValue.setKey(line);
			return keyValue;
		}

		String name = line.substring(0, p).trim();
		String value = line.substring(p + 1).trim();

		keyValue.setKey(name);
		keyValue.setValue(value);

		return keyValue;
	}

	public static enum Step {
		firstline, header, body
	}

	/**
	 * @param args
	 *
	 * @author: tanyaowu
	 * 2017年2月22日 下午4:06:42
	 * 
	 */
	public static void main(String[] args) {

	}

}
