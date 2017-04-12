package com.runyuan.scan.network.packets;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kleen
 * Date: 2017-04-11
 * Time: 14:19
 */
public class ScanInReqBody extends BaseBody{
    @JSONField(name="ActionType")
    private String actionType;
    @JSONField(name="Id")
    private String id;
    @JSONField(name="Qrcode")
    private String qrcode;
    @JSONField(name="Result")
    private String result;
    @JSONField(name="Scount")
    private String scount;
    @JSONField(name="Sum")
    private String sum;
    @JSONField(name="Tid")
    private String tid;
    @JSONField(name="Wcount")
    private String wcount;

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getScount() {
        return scount;
    }

    public void setScount(String scount) {
        this.scount = scount;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getWcount() {
        return wcount;
    }

    public void setWcount(String wcount) {
        this.wcount = wcount;
    }
}
