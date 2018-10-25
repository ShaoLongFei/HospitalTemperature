package com.liuyue.hospitaltemperature.a_presenters;

import android.app.Activity;

import com.liuyue.hospitaltemperature.a_model.RecordModel;
import com.liuyue.hospitaltemperature.a_model.SickRoomModel;
import com.liuyue.hospitaltemperature.a_model.SqlFactory;
import com.liuyue.hospitaltemperature.a_model.SqlInfoCallBack;
import com.liuyue.hospitaltemperature.a_model.TemperatureModel;
import com.liuyue.hospitaltemperature.a_model.WardModel;
import com.liuyue.hospitaltemperature.activity.WarnActivity;
import com.liuyue.hospitaltemperature.activity.WheatherActivity;
import com.liuyue.hospitaltemperature.db.SQLCursor;
import com.liuyue.hospitaltemperature.util.Constants;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 流月 on 2018/6/27.
 *
 * @description
 */

public class WarnPresenter extends BasePresenter<WarnActivity>{
    private  List<TemperatureModel> mTemperatureModelList=new ArrayList<>();
    private int mIntTemperatureCount=0;
    private  List<SickRoomModel> mSickRoomModelList=new ArrayList<>();
    private int mIntSickRoomCount=0;
    private List<WardModel> mWardModelList=new ArrayList<>();
    private int mIntWardCount=0;
    public static List<RecordModel> sWarnTemperatureList=new ArrayList<>();
    public static List<RecordModel> sWarnHumitureList=new ArrayList<>();
    public static List<RecordModel> sWarnSO2List=new ArrayList<>();
    public static List<RecordModel> sWarnNO2List=new ArrayList<>();
    public static List<RecordModel> sWarnPM10List=new ArrayList<>();

    public WarnPresenter(WarnActivity warnActivity) {
        super(warnActivity);
    }

    public void initTemperatureData(){
        try {
            SQLCursor.getData(mView, Constants.TEMPERATURE_TABLE_NO, new SqlInfoCallBack() {
                @Override
                public void Success(ArrayList<SqlFactory> sqlFactories) throws SQLException {
                    mIntTemperatureCount = sqlFactories.size();
                    mTemperatureModelList.clear();
                    for (int i=0;i<mIntTemperatureCount;i++){
                        mTemperatureModelList.add((TemperatureModel)sqlFactories.get(i));
                    }

                    SQLCursor.getData(mView, Constants.SICKROOM_TABLE_NO, new SqlInfoCallBack() {

                        @Override
                        public void Success(ArrayList<SqlFactory> sqlFactories) throws SQLException {
                            mIntSickRoomCount = sqlFactories.size();
                            mSickRoomModelList.clear();
                            for (int i=0;i<mIntSickRoomCount;i++){
                                mSickRoomModelList.add((SickRoomModel) sqlFactories.get(i));
                            }
                            SQLCursor.getData(mView, Constants.WARD_TABLE_NO, new SqlInfoCallBack() {
                                @Override
                                public void Success(ArrayList<SqlFactory> sqlFactories) throws SQLException {
                                    mIntWardCount = sqlFactories.size();
                                    mWardModelList.clear();
                                    for (int i=0;i<mIntWardCount;i++){
                                        mWardModelList.add((WardModel) sqlFactories.get(i));
                                    }
                                    mView.hideDialog();
                                    distributeData();
                                }

                                @Override
                                public void Faild(int num) {

                                }
                            });
                        }

                        @Override
                        public void Faild(int num) {

                        }
                    });
                }

                @Override
                public void Faild(int num) {

                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    private void distributeData() {
        for (int i=0;i<mIntTemperatureCount;i++){
            int temp=getIntegerValue(mTemperatureModelList.get(i).getTemperature());
            if (temp>Constants.STAND_HIGHT_TEMPERATURE){
                addData(1,i,1,temp);
            }else if (temp<Constants.STAND_LOW_TEMPERATURE){
                addData(1,i,0,temp);
            }
            int humiture=getIntegerValue(mTemperatureModelList.get(i).getHumidity());
            if (humiture>Constants.STAND_HIGHT_HUMIDITY){
                addData(2,i,1,humiture);
            }else if (humiture<Constants.STAND_LOW_HUMIDITY){
                addData(2,i,0,humiture);
            }
            int SO2=getIntegerValue(mTemperatureModelList.get(i).getSo2());
            if (SO2>Constants.STAND_HIGHT_SO2){
                addData(3,i,1,SO2);
            }else if (SO2<Constants.STAND_LOW_SO2){
                addData(3,i,0,SO2);
            }
            int NO2=getIntegerValue(mTemperatureModelList.get(i).getNo2());
            if (NO2>Constants.STAND_HIGHT_NO2){
                addData(4,i,1,NO2);
            }else if (NO2<Constants.STAND_LOW_NO2){
                addData(4,i,0,NO2);
            }
            int PM10=getIntegerValue(mTemperatureModelList.get(i).getPm10());
            if (PM10>Constants.STAND_HIGHT_PM10){
                addData(5,i,1,PM10);
            }else if (PM10<Constants.STAND_LOW_PM10){
                addData(5,i,0,PM10);
            }
        }
    }

    private void addData(int flag, int i,int state,int value) {
        RecordModel recordModel=new RecordModel();
        recordModel.setState(state);
        recordModel.setDate(mTemperatureModelList.get(i).getCurrentDate());
        recordModel.setTime(mTemperatureModelList.get(i).getCurrentTime());
        recordModel.setLocation(getLocation(mTemperatureModelList.get(i).getId()));
        switch (flag){
            case 1://温度
                recordModel.setValue(value+"℃");
            sWarnTemperatureList.add(recordModel);
                break;
            case 2://湿度
                recordModel.setValue(value+"%");
            sWarnHumitureList.add(recordModel);
                break;
            case 3://SO2
                recordModel.setValue(value+"mg/m³");
            sWarnSO2List.add(recordModel);
                break;
            case 4://NO2
                recordModel.setValue(value+"mg/m³");
            sWarnNO2List.add(recordModel);
                break;
            case 5://PM10
                recordModel.setValue(value+"mg/m³");
            sWarnPM10List.add(recordModel);
                break;
            default:

                break;
        }
    }

    private String room="未知的房间";
    private String getLocation(String id) {
        String location="未知的地点";
        String wardID=getWardID(id);
        location=getDepartment(wardID)+room;
        return location;
    }

    private String getDepartment(String wardID) {
        String department="未知的科室";
        for (int i=0;i<mIntWardCount;i++){
            if (mWardModelList.get(i).getWardId().equals(wardID)){
                department=mWardModelList.get(i).getDepartmentName()+"-"+mWardModelList.get(i).getWardName()
                        +"-"+mWardModelList.get(i).getWardLocation()+"-";
            }
        }
        return department;
    }

    private String getWardID(String id) {
        String wardID="未知的wardID";
        for (int i=0;i<mIntSickRoomCount;i++){
            if (mSickRoomModelList.get(i).getDeviceID().equals(id)){
                wardID=mSickRoomModelList.get(i).getWardID();
                room=mSickRoomModelList.get(i).getResidentRoomNo()+"室";
                break;
            }
        }
        return wardID;
    }

    private int getIntegerValue(String string){
        return (int)Double.parseDouble(string);
    }

}
