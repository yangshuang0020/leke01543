package com.runyuan.scan.network.server.handler;

import com.runyuan.scan.network.common.ScanPacket;
import com.runyuan.scan.network.common.ScanSessionContext;
import com.runyuan.scan.network.common.intf.AbsScanBsHandler;
import com.runyuan.scan.network.common.json.Json;
import com.runyuan.scan.network.packets.ScanInReqBody;
import com.runyuan.scan.network.packets.ScanInRespBody;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kleen
 * Date: 2017-04-11
 * Time: 14:18
 */
public class ScanInHandler extends AbsScanBsHandler<ScanInReqBody> {
    @Override
    public Class<ScanInReqBody> bodyClass() {
        return ScanInReqBody.class;
    }

    @Override
    public Object handler(ScanPacket packet, ScanInReqBody bsBody, ChannelContext<ScanSessionContext, ScanPacket, Object> channelContext) throws Exception {

        ScanPacket scanPacket = new ScanPacket();

        System.out.println(bsBody.getTid());

        ScanInRespBody respBody = new ScanInRespBody();
        respBody.setActionType("01");
        respBody.setDataType("01");
        respBody.setId(bsBody.getId());
        respBody.setMethodIndex("0000");
        respBody.setQrcode("20152525");
        respBody.setScount("852");
        respBody.setSum("1000");
        respBody.setTid("In201704949");
        respBody.setWcount("122");
        String s = Json.toJson(respBody);
        scanPacket.setBody(s.getBytes());
        Aio.send(channelContext,scanPacket);
        return null;
    }
}
