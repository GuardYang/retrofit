package com.ysr.rf.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */
public class RequestShipperName {

    /**
     * EBusinessID : 1293600
     * Success : true
     * LogisticCode : 1000745320654
     * Shippers : [{"ShipperCode":"YD","ShipperName":"韵达快递"},{"ShipperCode":"EMS","ShipperName":"EMS"},{"ShipperCode":"HTKY","ShipperName":"百世汇通"}]
     */

    private String EBusinessID;
    private boolean Success;
    private String LogisticCode;
    private List<ShippersBean> Shippers;

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public String getLogisticCode() {
        return LogisticCode;
    }

    public void setLogisticCode(String LogisticCode) {
        this.LogisticCode = LogisticCode;
    }

    public List<ShippersBean> getShippers() {
        return Shippers;
    }

    public void setShippers(List<ShippersBean> Shippers) {
        this.Shippers = Shippers;
    }

    public static class ShippersBean {
        /**
         * ShipperCode : YD
         * ShipperName : 韵达快递
         */

        private String ShipperCode;
        private String ShipperName;

        public String getShipperCode() {
            return ShipperCode;
        }

        public void setShipperCode(String ShipperCode) {
            this.ShipperCode = ShipperCode;
        }

        public String getShipperName() {
            return ShipperName;
        }

        public void setShipperName(String ShipperName) {
            this.ShipperName = ShipperName;
        }
    }
}
