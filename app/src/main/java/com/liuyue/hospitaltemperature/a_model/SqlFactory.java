package com.liuyue.hospitaltemperature.a_model;


import com.liuyue.hospitaltemperature.util.Constants;

/**
 * @author kuan
 *         Created on 2017/11/16.
 * @description
 */

public class SqlFactory {

    /**
     * 简单工厂模式
     * 创建不同的模板
     * @param tableNameNo
     * @return
     */
    public static SqlFactory creatSqlModel(int tableNameNo) {
        SqlFactory sqlFactory = null;
        switch (tableNameNo) {
            case Constants.SYSUSER_TABLE_NO:
                sqlFactory = new SystemUserModel();
                break;
            case Constants.WARD_TABLE_NO:
                sqlFactory = new WardModel();
                break;
            case Constants.SICKROOM_TABLE_NO:
                sqlFactory=new SickRoomModel();
                break;
            case Constants.LOG_TABLE_NO:
                sqlFactory = new LogModel();
                break;
            case Constants.TEMPERATURE_TABLE_NO:
                sqlFactory = new TemperatureModel();
                break;
            default:
                break;
        }
        return sqlFactory;
    }
}
