package com.liuyue.hospitaltemperature.a_presenters;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.liuyue.hospitaltemperature.a_model.PartDataSelectionModel;

import com.liuyue.hospitaltemperature.a_model.SqlFactory;
import com.liuyue.hospitaltemperature.a_model.SqlInfoCallBack;
import com.liuyue.hospitaltemperature.activity.HumitureActivity;
import com.liuyue.hospitaltemperature.activity.LoginActivity;
import com.liuyue.hospitaltemperature.db.SQLCursor;
import com.liuyue.hospitaltemperature.util.Constants;
import com.liuyue.hospitaltemperature.util.SqlStateCode;
import com.liuyue.hospitaltemperature.util.StringUtils;
import com.liuyue.hospitaltemperature.util.ToastUtil;

import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;



/**
 * Created by kys_31 on 2017/11/30.
 */

public class LoginPresenter extends BasePresenter<LoginActivity>{

    private static final String TAG = "loginpresenter";
    public LoginPresenter(LoginActivity loginActivity) {
        super(loginActivity);
    }


    /**
     * 管理员通过账号密码登录
     * @param userName 用户名
     * @param password 密码
     */
    public void fastLogin(String userName, String password){
        if (checkUserMessage(userName, password)){
            try {
                PartDataSelectionModel model = new PartDataSelectionModel();
                String[] selections = {Constants.SYSUSER_USERID, Constants.SYSUSER_PASSWORD};
                String[] hazyOrExacts = {"=", "="};
                String[] conditions = {userName, password};
                String[] parts = {"*"};
                model.setSelections(selections);
                model.setHazyOrExact(hazyOrExacts);
                model.setConditions(conditions);
                model.setParts(parts);
                model.setTableNameNo(Constants.SYSUSER_TABLE_NO);
                SQLCursor.getPartDataBySelection(mView, model, new SqlInfoCallBack() {
                    @Override
                    public void Success(ArrayList<SqlFactory> sqlFactories) {
                        mView.loginSuccess();
                    }
                    @Override
                    public void Faild(int num) {
                        com.orhanobut.logger.Logger.e("num==================="+num);
                        if (num == SqlStateCode.STSTE_NOINTERFACE){
                            mView.loginFailture(SqlStateCode.getSqlFaildInfo(num));
                        }else {
                            mView.loginFailture(StringUtils.getFailureMessage(Constants.error_usernameNoExist));
                        }
                    }
                });
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * MD5加密
     * @param key
     * @return
     */
    public  String MD5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /*检查用户名和密码填写格式*/
    private boolean checkUserMessage(String username, String password) {
        if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password)){
            mView.loginFailture(StringUtils.getFailureMessage(Constants.error_allEmpty));
            return false;
        }else if (TextUtils.isEmpty(username)){
            mView.loginFailture(StringUtils.getFailureMessage(Constants.error_usernameEmpty));
            return false;
        }else if (TextUtils.isEmpty(password)) {
            mView.loginFailture(StringUtils.getFailureMessage(Constants.error_passwordEmpty));
            return false;
        }
        return true;
    }

}
