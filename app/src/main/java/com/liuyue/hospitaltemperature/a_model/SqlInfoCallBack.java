package com.liuyue.hospitaltemperature.a_model;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author kuan
 * Created on 2017/11/17.
 * @description  数据库请求回调
 */

public interface SqlInfoCallBack {

    //请求成功回调
    void Success(ArrayList<SqlFactory> sqlFactories) throws SQLException;
    //请求失败回调
    void Faild(int num);


}
