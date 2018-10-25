package com.liuyue.hospitaltemperature.a_model;

import java.io.Serializable;

/**
 * @author kuan
 *         Created on 2017/11/14.
 * @description
 */

public class WardModel extends SqlFactory implements Serializable{
   private String wardId;
   private String wardName;
   private String departmentName;
   private String wardLocation;
   private String wardContact;
   private String wardContaceTel;

    public String getWardId() {
        return wardId == null ? "" : wardId;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

    public String getWardName() {
        return wardName == null ? "" : wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getDepartmentName() {
        return departmentName == null ? "" : departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getWardLocation() {
        return wardLocation == null ? "" : wardLocation;
    }

    public void setWardLocation(String wardLocation) {
        this.wardLocation = wardLocation;
    }

    public String getWardContact() {
        return wardContact == null ? "" : wardContact;
    }

    public void setWardContact(String wardContact) {
        this.wardContact = wardContact;
    }

    public String getWardContaceTel() {
        return wardContaceTel == null ? "" : wardContaceTel;
    }

    public void setWardContaceTel(String wardContaceTel) {
        this.wardContaceTel = wardContaceTel;
    }

    @Override
    public String toString() {
        return "WardModel{" +
                "wardId='" + wardId + '\'' +
                ", wardName='" + wardName + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", wardLocation='" + wardLocation + '\'' +
                ", wardContact='" + wardContact + '\'' +
                ", wardContaceTel='" + wardContaceTel + '\'' +
                '}';
    }
}
