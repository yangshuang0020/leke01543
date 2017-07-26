package org.tio.http.common.http;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.exception.AioDecodeException;



/**
 * @author tanyaowu 
 * 2017年7月26日 下午2:20:43
 */
public class HttpMultiBodyDecoder
{
    private static Logger log = LoggerFactory.getLogger(HttpMultiBodyDecoder.class);

    /**
     * 
     */
    public HttpMultiBodyDecoder()
    {
    	
    }

    public static enum ParseStep
    {
        BOUNDARY, HEADER, BODY, END
    }

    public static void decode(HttpRequestPacket httpRequestPacket, RequestLine firstLine, byte[] bodyBytes, String initboundary) throws AioDecodeException
    {
    	ByteBuffer buffer = ByteBuffer.wrap(bodyBytes);
    	buffer.position(0);
    	
		String boundary = "--" + initboundary;
        String endBoundary = boundary + "--";
        
//        int boundaryLength = boundary.getBytes().length;
        ParseStep parseStep = ParseStep.BOUNDARY;
//        int bufferLength = buffer.capacity();
        try
        {
            label1 : while (true)
            {
                if (parseStep == ParseStep.BOUNDARY)
                {
                    String line = getLine(buffer, httpRequestPacket.getCharset());
//                    int offset = HttpMultiBodyDecoder.processReadIndex(buffer);
                    if (boundary.equals(line))
                    {
                        parseStep = ParseStep.HEADER;
                    } else if (endBoundary.equals(line)) // 结束了
                    {
//                        int ss = buffer.readerIndex() + 2 - offset;
                        break;
                    } else
                    {
                        throw new AioDecodeException("line is:" + line + ", but need: " + boundary + "");
                    }
                }

                MultiBodyHeader multiBodyHeader = new MultiBodyHeader();
                if (parseStep == ParseStep.HEADER)
                {
                    List<String> lines = new ArrayList<>(2);
                    label2:
                    while (true)
                    {
                        String line = getLine(buffer, httpRequestPacket.getCharset());
//                        HttpMultiBodyDecoder.processReadIndex(buffer);
                        if ("".equals(line))
                        {
                            parseStep = ParseStep.BODY;
                            parseMultiBodyHeader(lines, multiBodyHeader);
                            break label2;
                        } else
                        {
                            lines.add(line);
                        }
                    }
                }

                if (parseStep == ParseStep.BODY)
                {
                    ParseStep newParseStep = parseMultiBody(multiBodyHeader, httpRequestPacket, buffer, boundary, endBoundary);
                    parseStep = newParseStep;
                    
                    if (parseStep == ParseStep.END) {
						break label1;
					}
                }

            }
        } catch (UnsupportedEncodingException e)
        {
            log.error(e.getMessage(), e);
        }

    }

//    public static int processReadIndex(ByteBuffer buffer)
//    {
//        int newReaderIndex = buffer.readerIndex();
//        if (newReaderIndex < buffer.capacity())
//        {
//            buffer.readerIndex(newReaderIndex + 1);
//            return 1;
//        }
//        return 0;
//    }

    /**
     * 返回值不包括最后的\r\n
     * @param buffer
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getLine(ByteBuffer buffer, String charset) throws UnsupportedEncodingException
    {
    	char lastByte = 0; // 上一个字节
        int initPosition = buffer.position();

        while (buffer.hasRemaining())
        {
            char b = (char)buffer.get();

            if (b == '\n')
            {
                if (lastByte == '\r')
                {
                	int startIndex = initPosition;
                	int endIndex = buffer.position() - 2;
                	int length = endIndex - startIndex;
                	byte[] dst = new byte[length];
                	
                	System.arraycopy(buffer.array(), startIndex, dst, 0, length);
                    String line = new String(dst, charset);
                    return line;
                }
            }
            lastByte = b;
        }
        return null;
    }

    /**
     * 
     * @param lines
     * @param multiBodyHeader
     * @author: tanyaowu
     */
    public static void parseMultiBodyHeader(List<String> lines, MultiBodyHeader multiBodyHeader)
    {
        for (int i = 0; i < lines.size(); i++)
        {
            String line = lines.get(i);
            if (i == 0)
            {
                String[] mapStrings = StringUtils.split(line, ";");
                String s = mapStrings[0];//

                String[] namekeyvalue = StringUtils.split(mapStrings[1], "=");
                multiBodyHeader.setName(namekeyvalue[1].substring(1, namekeyvalue[1].length() - 1));

                if (mapStrings.length == 3)
                {
                    String[] finenamekeyvalue = StringUtils.split(mapStrings[2], "=");
                    String filename = finenamekeyvalue[1].substring(1, finenamekeyvalue[1].length() - 1);
                    multiBodyHeader.setFilename(FilenameUtils.getName(filename));
                }
            } else if (i == 1)
            {
                String[] map = StringUtils.split(line, ":");
                String contentType = map[0].trim();//
                multiBodyHeader.setContentType(contentType);
            }
        }
    }

    /**
     * 
     * @param multiBodyHeader
     * @param httpRequestPacket
     * @param buffer
     * @param boundary
     * @param endBoundary
     * @return
     * @throws UnsupportedEncodingException
     * @author: tanyaowu
     */
    public static ParseStep parseMultiBody(MultiBodyHeader multiBodyHeader, HttpRequestPacket httpRequestPacket, ByteBuffer buffer, String boundary, String endBoundary) throws UnsupportedEncodingException
    {
        int initPosition = buffer.position();

        while (buffer.hasRemaining())
        {
            String line = getLine(buffer, httpRequestPacket.getCharset());
            boolean isEndBoundary = endBoundary.equals(line);
            boolean isBoundary = boundary.equals(line);
            if (isBoundary || isEndBoundary)
            {
                int startIndex = initPosition;
            	int endIndex = buffer.position() - line.getBytes().length - 2;
            	int length = endIndex - startIndex;
            	byte[] dst = new byte[length];
            	
            	System.arraycopy(buffer.array(), startIndex, dst, 0, length);
            	
            	if (!StringUtils.isBlank(multiBodyHeader.getFilename())) // 是文件
                {
                    UploadFile uploadFile = new UploadFile();
                    uploadFile.setName(multiBodyHeader.getFilename());
                    uploadFile.setData(dst);
                    uploadFile.setSize(dst.length);

                    httpRequestPacket.addParam(multiBodyHeader.getName(), uploadFile);
                } else
                {
                    httpRequestPacket.addParam(multiBodyHeader.getName(),  new String(dst, httpRequestPacket.getCharset()));
                }
            	if (isEndBoundary) {
            		return ParseStep.END;
				} else {
					return ParseStep.HEADER;
				}
            }
        }
        return null;
    }

    /**
     * @param args
     * @throws UnsupportedEncodingException
     */
    public static void main(String[] args) throws UnsupportedEncodingException
    {
        String testString = "hello\r\nddd\r\n";
        ByteBuffer buffer = ByteBuffer.wrap(testString.getBytes());

        String xString = getLine(buffer, "utf-8");
        System.out.println(xString);
        xString = getLine(buffer, "utf-8");
        System.out.println(xString);
    }

    public static class MultiBodyHeader
    {
        private String contentDisposition = "form-data";
        private String name = null;
        private String filename = null;
        private String contentType = null;

        public String getContentDisposition()
        {
            return contentDisposition;
        }

        public void setContentDisposition(String contentDisposition)
        {
            this.contentDisposition = contentDisposition;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getFilename()
        {
            return filename;
        }

        public void setFilename(String filename)
        {
            this.filename = filename;
        }

        public String getContentType()
        {
            return contentType;
        }

        public void setContentType(String contentType)
        {
            this.contentType = contentType;
        }
    }

}
