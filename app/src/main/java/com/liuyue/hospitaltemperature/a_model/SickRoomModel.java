package com.liuyue.hospitaltemperature.a_model;

import java.io.Serializable;

/**
 * Created by 流月 on 2018/6/2.
 *
 * @description
 */

public class SickRoomModel extends SqlFactory implements Serializable {
    private String deviceID;
    private String WardID;
    private String ResidentRoomNo;
    private String deviceIEEEAdress;

    public String getDeviceID() {
        return deviceID == null ? "" : deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getWardID() {
        return WardID == null ? "" : WardID;
    }

    public void setWardID(String wardID) {
        WardID = wardID;
    }

    public String getResidentRoomNo() {
        return ResidentRoomNo == null ? "" : ResidentRoomNo;
    }

    public void setResidentRoomNo(String residentRoomNo) {
        ResidentRoomNo = residentRoomNo;
    }

    public String getDeviceIEEEAdress() {
        return deviceIEEEAdress == null ? "" : deviceIEEEAdress;
    }

    public void setDeviceIEEEAdress(String deviceIEEEAdress) {
        this.deviceIEEEAdress = deviceIEEEAdress;
    }

    @Override
    public String toString() {
        return "SickRoomModel{" +
                "deviceID='" + deviceID + '\'' +
                ", WardID='" + WardID + '\'' +
                ", ResidentRoomNo='" + ResidentRoomNo + '\'' +
                ", deviceIEEEAdress='" + deviceIEEEAdress + '\'' +
                '}';
    }
}
