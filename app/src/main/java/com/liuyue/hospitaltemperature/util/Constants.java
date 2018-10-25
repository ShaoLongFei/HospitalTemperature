package com.liuyue.hospitaltemperature.util;

/**
 * @description 常量类
 */
public class Constants {

    private Constants() {}
    // 打印日志开关
    public static final boolean DEBUG = true;

    //远程数据库账号
    public static final String SQLITEURL = "jdbc:mysql://114.115.172.62:3306/rhdb";
    //远程数据库账号
    public static final String SQLITEUSER = "newAppuser";
    //远程数据库密码
    public static final String SQLITEPW = "Wurenjie@1965";

    //默认 SharePreferences文件名
    public static final String SHARED_PATH = "soul.listener.com.humiture";

    public static final String LIKE = " like ";
    public static final String IS = " = ";

    //查询选项
    public static final int FLAG_TEM_AND_HUM=1;//温湿度
    public static final int FLAG_NO2=2;//NO2
    public static final int FLAGE_SO2=3;//SO2
    public static final int FLAG_PM10=4;//PM10

    //数据库列表序号
    public static final int SYSUSER_TABLE_NO = 1; //系统用户
    public static final int WARD_TABLE_NO = 2; //病房
    public static final int SICKROOM_TABLE_NO = 3;//对应关系
    public static final int LOG_TABLE_NO = 4;//日志表
    public static final int TEMPERATURE_TABLE_NO = 5;//温度
    //数据库表名
    public static final String SYSUSER_TABLE = "sysuser_table";
    public static final String LOG_TABLE = "log_table";
    public static final String TEMPERATURE_TABLE = "temperature_table";
    public static final String WARD_TABLE="ward_table";
    public static final String SICKROOM_TABLE="sickroom_table";
    //sysuser_table列名
    public static final String SYSUSER_ID = "id";
    public static final String SYSUSER_USERID = "userid";
    public static final String SYSUSER_USERNAME = "username";
    public static final String SYSUSER_PASSWORD = "userpassword";
    public static final String SYSUSER_USERPRIVILEGES = "userpPrivileges";//特权
    public static final String SYSUSER_USERTYPE = "userType";//用户类型
    public static final String SYSUSER_USERMAIL = "userMail";//用户邮件
    public static final String SYSUSER_USERTEL = "userTel";//用户电话

    /*病房信息列名*/
    public static final String WARD_ID="wardID";//病房ID
    public static final String WARD_NAME="wardName";//病区
    public static final String DEPARTMENT_NAME="departmentName";//科室
    public static final String WARD_LOCATION="wardLocation";//楼号
    public static final String WARD_CONTACT="wardContact";//负责人
    public static final String WARD_CONTACE_TEL="wardContaceTel";//负责人联系电话

    /*对应关系列名*/
    public static final String DEVICE_ID="deviceID";//机器号
    public static final String RESIDENT_ROOM_NO="residentRoomNo";//房间号
    public static final String DEVICE_IEEE_ADRESS="deviceIEEEAddress";//ip地址

    /*温湿度 列名*/
    public static final String HUMITURE_ID = "ID";
    public static final String HUMITURE_DEVICEID = "deviceID";
    public static final String HUMITURE_TEMPERATURE = "temperature";
    public static final String HUMITURE_HUMIDUTY = "humidity";
    public static final String NO2="NO2";
    public static final String SO2="SO2";
    public static final String PM10="PM10";
    public static final String HUMITURE_CURRENTDATE = "currentDate";
    public static final String HUMITURE_CURRENTTIME = "currentTime";
    public static final String HUMITURE_OUT = "temperatureOut";

    /*登录错误码*/
    public static final int error_allEmpty = 0;//用户名和密码全为空
    public static final int error_usernameEmpty = 1;//用户名为空
    public static final int error_passwordEmpty = 2;//密码为空
    public static final int error_usernameNoExist = 3;//用户名不存在
    public static final int error_passowordNoSure = 4;//密码不正确

    /*popWindow显示内容码*/
    public static final int SHOW_BLOCK = 0;//小区
    public static final int SHOW_BUILDINGINDEX = 1;//楼号
    public static final int SHOW_UNIT = 2;//单元
    public static final int SHOW_HOMENUMBER = 3;//房间号
    public static final int SHOW_DATACOUNT = 4;//数据量
    public static final int SHOW_PAGACOUNT = 5;//页数
    public static final int SHOW_BLOCKLOCATION = 6;//小区位置

    /*标准室内温湿度常量*/
    public static final int STAND_LOW_TEMPERATURE = 18;
    public static final int STAND_HIGHT_TEMPERATURE = 28;
    public static final int STAND_LOW_HUMIDITY = 30;
    public static final int STAND_HIGHT_HUMIDITY = 40;
    public static final int STAND_LOW_NO2 = 20;
    public static final int STAND_HIGHT_NO2 = 50;
    public static final int STAND_LOW_SO2 = 5;
    public static final int STAND_HIGHT_SO2 = 15;
    public static final int STAND_LOW_PM10 = 60;
    public static final int STAND_HIGHT_PM10 = 100;

    /*本地数据库*/
    public static final String DATEBASE_NAME = "record";
    public static final int DATEBASE_VERSION = 1;
    public static final String TABLE_NAME = "record";

    /*常量控制X坐标值*/
    public static int num = 1;

    public static final String PHONEFORMAT_NOSURE = "请正确输入手机号";

}

