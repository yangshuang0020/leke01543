package org.tio.http.server.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.http.common.http.HttpConst;
import org.tio.http.common.http.HttpRequestPacket;
import org.tio.http.common.http.HttpResponsePacket;
import org.tio.http.common.http.MimeType;

/**
 * @author tanyaowu 
 * 2017年6月29日 下午4:17:24
 */
public class Resps {
	private static Logger log = LoggerFactory.getLogger(Resps.class);

	/**
	 * 
	 * @author: tanyaowu
	 */
	public Resps() {
	}

	/**
	 * Content-Type: text/html; charset=utf-8
	 * @param httpRequestPacket
	 * @param bodyString
	 * @param charset
	 * @return
	 * @author: tanyaowu
	 */
	public static HttpResponsePacket html(HttpRequestPacket httpRequestPacket, String bodyString, String charset) {
		HttpResponsePacket ret = string(httpRequestPacket, bodyString, charset, MimeType.TEXT_HTML_HTML.getType() + "; charset=" + charset);
		return ret;
	}

	/**
	 * 根据文件创建响应
	 * @param httpRequestPacket
	 * @param fileOnServer
	 * @return
	 * @throws IOException
	 * @author: tanyaowu
	 */
	public static HttpResponsePacket file(HttpRequestPacket httpRequestPacket, File fileOnServer) throws IOException {
		byte[] bodyBytes = com.xiaoleilu.hutool.io.FileUtil.readBytes(fileOnServer);
		String filename = fileOnServer.getName();
		return file(httpRequestPacket, bodyBytes, filename);
	}

	/**
	 * 根据文件创建响应
	 * @param httpRequestPacket
	 * @param bodyBytes
	 * @param filename
	 * @return
	 * @author: tanyaowu
	 */
	public static HttpResponsePacket file(HttpRequestPacket httpRequestPacket, byte[] bodyBytes, String filename) {
		HttpResponsePacket ret = new HttpResponsePacket(httpRequestPacket);
		ret.setBody(bodyBytes, httpRequestPacket);

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
	 * @param httpRequestPacket
	 * @param bodyString
	 * @param charset
	 * @return
	 * @author: tanyaowu
	 */
	public static HttpResponsePacket json(HttpRequestPacket httpRequestPacket, String bodyString, String charset) {
		HttpResponsePacket ret = string(httpRequestPacket, bodyString, charset, MimeType.TEXT_PLAIN_JSON.getType() + "; charset=" + charset);
		return ret;
	}

	/**
	 * Content-Type: text/css; charset=utf-8
	 * @param httpRequestPacket
	 * @param bodyString
	 * @param charset
	 * @return
	 * @author: tanyaowu
	 */
	public static HttpResponsePacket css(HttpRequestPacket httpRequestPacket, String bodyString, String charset) {
		HttpResponsePacket ret = string(httpRequestPacket, bodyString, charset, MimeType.TEXT_CSS_CSS.getType() + "; charset=" + charset);
		return ret;
	}

	/**
	 * Content-Type: application/javascript; charset=utf-8
	 * @param bodyString
	 * @param charset
	 * @return
	 * @author: tanyaowu
	 */
	public static HttpResponsePacket js(HttpRequestPacket httpRequestPacket, String bodyString, String charset) {
		HttpResponsePacket ret = string(httpRequestPacket, bodyString, charset, MimeType.APPLICATION_JAVASCRIPT_JS.getType() + "; charset=" + charset);
		return ret;
	}

	/**
	 * Content-Type: text/plain; charset=utf-8
	 * @param bodyString
	 * @param charset
	 * @return
	 * @author: tanyaowu
	 */
	public static HttpResponsePacket txt(HttpRequestPacket httpRequestPacket, String bodyString, String charset) {
		HttpResponsePacket ret = string(httpRequestPacket, bodyString, charset, MimeType.TEXT_PLAIN_TXT.getType() + "; charset=" + charset);
		return ret;
	}

	/**
	 * 创建字符串输出
	 * @param bodyString
	 * @param charset
	 * @param Content_Type
	 * @return
	 * @author: tanyaowu
	 */
	private static HttpResponsePacket string(HttpRequestPacket httpRequestPacket, String bodyString, String charset, String Content_Type) {
		HttpResponsePacket ret = new HttpResponsePacket(httpRequestPacket);
		if (bodyString != null) {
			try {
				ret.setBody(bodyString.getBytes(charset), httpRequestPacket);
			} catch (UnsupportedEncodingException e) {
				log.error(e.toString(), e);
			}
		}
		ret.addHeader(HttpConst.ResponseHeaderKey.Content_Type, Content_Type);
		return ret;
	}

	/**
	 * @param args
	 * @author: tanyaowu
	 */
	public static void main(String[] args) {

	}
}
