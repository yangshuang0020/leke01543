package com.runyuan.scan.network.common;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.AioHandler;

import java.nio.ByteBuffer;

/**
 *
 * @author tanyaowu
 * 2017年3月27日 上午12:14:12
 */
public abstract class ScanAbsAioHandler implements AioHandler<ScanSessionContext, ScanPacket, Object>
{
    private static Logger log = LoggerFactory.getLogger(ScanAbsAioHandler.class);
    /**
     * 编码：把业务消息包编码为可以发送的ByteBuffer
     * 消息头：type + bodyLength
     * 消息体：byte[]
     */
    @Override
    public ByteBuffer encode(ScanPacket packet, GroupContext<ScanSessionContext, ScanPacket, Object> groupContext, ChannelContext<ScanSessionContext, ScanPacket, Object> channelContext)
    {
        byte[] body = packet.getBody();
        int bodyLen = 0;
        if (body != null)
        {
            bodyLen = body.length;
        }

        //总长度是消息头的长度+消息体的长度
        int allLen = ScanPacket.HEADER_LENGHT + bodyLen;

        ByteBuffer buffer = ByteBuffer.allocate(allLen);
        buffer.order(groupContext.getByteOrder());

        //写入消息体长度
        String bodyLenStr = String.format("%04d", bodyLen);
        buffer.put(bodyLenStr.getBytes());
        

        //写入消息体
        if (body != null)
        {
            buffer.put(body);
        }
        return buffer;
    }

    /**
     * 解码：把接收到的ByteBuffer，解码成应用可以识别的业务消息包
     * 消息头：type + bodyLength
     * 消息体：byte[]
     */
    @Override
    public ScanPacket decode(ByteBuffer buffer, ChannelContext<ScanSessionContext, ScanPacket, Object> channelContext) throws AioDecodeException
    {



        int readableLength = buffer.limit() - buffer.position();

        if (readableLength < ScanPacket.HEADER_LENGHT)
        {
            return null;
        }

        byte[] bs = new byte[4];
        buffer.get(bs);

        String bodyLengthStr = new String(bs);

        Integer bodyLength = Integer.parseInt(bodyLengthStr);

        if (bodyLength < 0)
        {
            throw new AioDecodeException("bodyLength [" + bodyLength + "] is not right, remote:" + channelContext.getClientNode());
        }

        int neededLength = ScanPacket.HEADER_LENGHT + bodyLength;
        int test = readableLength - neededLength;
        if (test < 0) // 不够消息体长度(剩下的buffe组不了消息体)
        {
            return null;
        } else
        {
            ScanPacket imPacket = new ScanPacket();

            if (bodyLength > 0)
            {
                byte[] dst = new byte[bodyLength];
                buffer.get(dst);
                imPacket.setBody(dst);
                JSONObject parse = (JSONObject)JSONObject.parse(imPacket.getBody());
                imPacket.setType(parse.getByte("ActionType"));
            }
            return imPacket;
        }
    }
}
