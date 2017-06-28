package org.tio.http.common.http;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.http.common.HttpPacket;

import com.xiaoleilu.hutool.util.ZipUtil;

/**
 * 
 * @author tanyaowu 
 *
 */
public class HttpResponsePacket extends HttpPacket {
	private static Logger log = LoggerFactory.getLogger(HttpResponsePacket.class);

	private HttpResponseStatus status = HttpResponseStatus.C200;

	//不包含cookie的头部
	private Map<String, String> headers = new HashMap<>();
	private List<Cookie> cookies = null;
	//	private int contentLength;
//	private byte[] bodyBytes;
	private String charset = HttpConst.CHARSET_NAME;

	/**
	 * @author: tanyaowu
	 * 2017年2月22日 下午4:14:40
	 */
	public HttpResponsePacket() {
	}

	/**
	 * Content-Type: text/html; charset=utf-8
	 * @param bodyString
	 * @param charset 形如"utf-8"
	 * @return
	 * @author: tanyaowu
	 */
	public static HttpResponsePacket createHtml(String bodyString, String charset) {
		HttpResponsePacket ret = createStr(bodyString, charset, MimeType.TEXT_HTML_HTML.getType() + "; charset=" + charset);
		return ret;
	}

	/**
	 * 根据文件创建响应
	 * @param file
	 * @return
	 * @throws IOException
	 * @author: tanyaowu
	 */
	public static HttpResponsePacket createFile(File file) throws IOException {
		byte[] bodyBytes = FileUtils.readFileToByteArray(file);
		String filename = file.getName();
		return createFile(bodyBytes, filename);
	}

	/**
	 * 根据文件创建响应
	 * @param bodyBytes
	 * @param filename
	 * @return
	 * @author: tanyaowu
	 */
	public static HttpResponsePacket createFile(byte[] bodyBytes, String filename) {
		HttpResponsePacket ret = new HttpResponsePacket();
		ret.setBody(bodyBytes);

		String mimeTypeStr = null;
		String extension = FilenameUtils.getExtension(filename);
		if (StringUtils.isNoneBlank(extension)) {
			MimeType mimeType = MimeType.fromExtension(extension);
			if (mimeType != null) {
				mimeTypeStr = mimeType.getType();
			} else {
				mimeTypeStr = "application/octet-stream";
			}
		}

		ret.addHeader(HttpConst.ResponseHeaderKey.Content_Type, mimeTypeStr);
//		ret.addHeader(HttpConst.ResponseHeaderKey.Content_disposition, "attachment;filename=\"" + filename + "\"");
		return ret;
	}

	/**
	 * Content-Type: application/json; charset=utf-8
	 * @param bodyString
	 * @param charset
	 * @return
	 * @author: tanyaowu
	 */
	public static HttpResponsePacket createJson(String bodyString, String charset) {
		HttpResponsePacket ret = createStr(bodyString, charset, MimeType.TEXT_PLAIN_JSON.getType() + "; charset=" + charset);
		return ret;
	}
	
	/**
	 * Content-Type: application/javascript; charset=utf-8
	 * @param bodyString
	 * @param charset
	 * @return
	 * @author: tanyaowu
	 */
	public static HttpResponsePacket createCss(String bodyString, String charset) {
		HttpResponsePacket ret = createStr(bodyString, charset, MimeType.TEXT_CSS_CSS.getType() + "; charset=" + charset);
		return ret;
	}
	
	/**
	 * Content-Type: application/javascript; charset=utf-8
	 * @param bodyString
	 * @param charset
	 * @return
	 * @author: tanyaowu
	 */
	public static HttpResponsePacket createJs(String bodyString, String charset) {
		HttpResponsePacket ret = createStr(bodyString, charset, MimeType.APPLICATION_JAVASCRIPT_JS.getType() + "; charset=" + charset);
		return ret;
	}

	/**
	 * Content-Type: text/plain; charset=utf-8
	 * @param bodyString
	 * @param charset
	 * @return
	 * @author: tanyaowu
	 */
	public static HttpResponsePacket createTxt(String bodyString, String charset) {
		HttpResponsePacket ret = createStr(bodyString, charset, MimeType.TEXT_PLAIN_TXT.getType() + "; charset=" + charset);
		return ret;
	}
	
	public HttpResponsePacket gzip(HttpRequestPacket httpRequestPacket) {
		//Accept-Encoding
		//检查浏览器是否支持gzip
//		String Accept_Encoding = httpRequestPacket.getHeaders().get(HttpConst.RequestHeaderKey.Accept_Encoding);
//		if (StringUtils.isNoneBlank(Accept_Encoding)) {
//			String[] ss = StringUtils.split(Accept_Encoding, ",");
//			ArrayUtil.contains(ss, "gzip");
//		}
		byte[] bs = this.getBody();
		if (bs.length >= 600) {
			byte[] bs2 = ZipUtil.gzip(bs);
			if (bs2.length < bs.length) {
				this.setBody(bs);
				this.addHeader(HttpConst.ResponseHeaderKey.Content_Encoding, "gzip");
			}
		}
		return this;
	}

	/**
	 * 创建字符串输出
	 * @param bodyString
	 * @param charset
	 * @param Content_Type
	 * @return
	 * @author: tanyaowu
	 */
	private static HttpResponsePacket createStr(String bodyString, String charset, String Content_Type) {
		HttpResponsePacket ret = new HttpResponsePacket();
		if (bodyString != null) {
			try {
				ret.setBody(bodyString.getBytes(charset));
			} catch (UnsupportedEncodingException e) {
				log.error(e.toString(), e);
			}
		}
		ret.addHeader(HttpConst.ResponseHeaderKey.Content_Type, Content_Type);
		return ret;
	}

	/**
	 * @param args
	 *
	 * @author: tanyaowu
	 * 2017年2月22日 下午4:14:40
	 * 
	 */
	public static void main(String[] args) {
	}

	public boolean addCookie(Cookie cookie) {
		if (cookies == null) {
			synchronized (this) {
				if (cookies == null) {
					cookies = new ArrayList<>();
				}
			}
		}
		return cookies.add(cookie);
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @author: tanyaowu
	 */
	//	public void removeCookie(Cookie cookie) {
	//		if (cookies == null) {
	//			return;
	//		}
	//		return cookies.add(cookie);
	//	}

	public void addHeader(String key, String value) {
		headers.put(key, value);
	}

	public void removeHeader(String key, String value) {
		headers.remove(key);
	}

	/**
	 * @return the headers
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}

	/**
	 * @param headers the headers to set
	 */
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	//	/**
	//	 * @return the bodyLength
	//	 */
	//	public int getContentLength()
	//	{
	//		return contentLength;
	//	}
	//
	//	/**
	//	 * @param bodyLength the bodyLength to set
	//	 */
	//	public void setContentLength(int contentLength)
	//	{
	//		this.contentLength = contentLength;
	//	}

	/**
	 * @return the status
	 */
	public HttpResponseStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(HttpResponseStatus status) {
		this.status = status;
	}

//	/**
//	 * @return the bodyBytes
//	 */
//	public byte[] getBodyBytes() {
//		return bodyBytes;
//	}
//
//	/**
//	 * @param bodyBytes the bodyBytes to set
//	 */
//	public void setBody(byte[] bodyBytes) {
//		this.bodyBytes = bodyBytes;
//	}

	/**
	 * @return the cookies
	 */
	public List<Cookie> getCookies() {
		return cookies;
	}

	/**
	 * @param cookies the cookies to set
	 */
	public void setCookies(List<Cookie> cookies) {
		this.cookies = cookies;
	}

	/**
	 * @return the charset
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * @param charset the charset to set
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

}
