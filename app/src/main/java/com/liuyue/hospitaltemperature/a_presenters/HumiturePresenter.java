package com.liuyue.hospitaltemperature.a_presenters;

import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.liuyue.hospitaltemperature.R;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;


import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import com.liuyue.hospitaltemperature.a_model.PartDataSelectionModel;
import com.liuyue.hospitaltemperature.a_model.SickRoomModel;
import com.liuyue.hospitaltemperature.a_model.SqlFactory;
import com.liuyue.hospitaltemperature.a_model.SqlInfoCallBack;
import com.liuyue.hospitaltemperature.a_model.TemperatureModel;
import com.liuyue.hospitaltemperature.a_model.WardModel;
import com.liuyue.hospitaltemperature.activity.HumitureActivity;
import com.liuyue.hospitaltemperature.db.SQLCursor;
import com.liuyue.hospitaltemperature.util.Constants;
import com.liuyue.hospitaltemperature.util.SizeUtil;
import com.liuyue.hospitaltemperature.util.SqlStateCode;
import com.liuyue.hospitaltemperature.util.TimeUtil;
import com.liuyue.hospitaltemperature.util.ToastUtil;
import com.liuyue.hospitaltemperature.util.ValueFormatterUtil;
import com.liuyue.hospitaltemperature.util.ViewUtil;
import com.liuyue.hospitaltemperature.view.BrokenLineView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kys_31 on 2017/12/5.
 */

public class HumiturePresenter extends BasePresenter<HumitureActivity> {

    private int mIntWardCount;
    private int mIntSickRoomCount;
    private int mIntTemperatureCount;
    private List<WardModel> mWardModelList = new ArrayList<>();
    private List<SickRoomModel> mSickRoomModelList = new ArrayList<>();
    private List<TemperatureModel> mTemperatureModelList = new ArrayList<>();
    private PopupWindow mPopupWindow;
    private List<String> mWardLocationList = new ArrayList<>();
    private List<String> mWardNameList = new ArrayList<>();
    private List<String> mDepartmentNameList = new ArrayList<>();
    private List<String> mWardIDList = new ArrayList<>();
    private List<String> mResidentRoomNoList = new ArrayList<>();
    private int mIntDataCount = 12;
    private int mIntPageCount = 1;
    private int index = 1;
    private TextView mTvWardLocation;
    private TextView mTvWardName;
    private TextView mTvDepartmentName;
    private TextView mTvResidentRoomNo;
    private TextView mTvDate;
    private BrokenLineView brokenLineView;
    public Dialog filterDialog;
    private int flag = Constants.FLAG_TEM_AND_HUM;


    public HumiturePresenter(HumitureActivity humitureActivity) {
        super(humitureActivity);
    }

    public void initHospitalData( int flag) {
        this.flag=flag;
        try {
            SQLCursor.getData(mView, Constants.WARD_TABLE_NO, new SqlInfoCallBack() {
                @Override
                public void Success(ArrayList<SqlFactory> sqlFactories) throws SQLException {
                    mIntWardCount = sqlFactories.size();
                    mWardModelList.clear();
                    for (int i = 0; i < mIntWardCount; i++) {
                        mWardModelList.add((WardModel) sqlFactories.get(i));
                        mWardLocationList.add(mWardModelList.get(i).getWardLocation());
                        mWardNameList.add(mWardModelList.get(i).getWardName());
                        mDepartmentNameList.add(mWardModelList.get(i).getDepartmentName());
                        mWardIDList.add(mWardModelList.get(i).getWardId());
                    }
                    Log.e("飞", "成功");
                    SQLCursor.getData(mView, Constants.SICKROOM_TABLE_NO, new SqlInfoCallBack() {
                        @Override
                        public void Success(ArrayList<SqlFactory> sqlFactories) {
                            mIntSickRoomCount = sqlFactories.size();
                            mSickRoomModelList.clear();
                            for (int i = 0; i < mIntSickRoomCount; i++) {
                                mSickRoomModelList.add((SickRoomModel) sqlFactories.get(i));
                                mResidentRoomNoList.add(mSickRoomModelList.get(i).getResidentRoomNo());
                            }
                            mWardLocationList = removeDuplicate(mWardLocationList);
                            mWardNameList = removeDuplicate(mWardNameList);
                            mDepartmentNameList = removeDuplicate(mDepartmentNameList);
                            mWardIDList = removeDuplicate(mWardIDList);
                            mResidentRoomNoList = removeDuplicate(mResidentRoomNoList);
                            mView.hideDialog();
                            showFilterDialog();
                        }

                        @Override
                        public void Faild(int num) {
                            ToastUtil.makeText(SqlStateCode.getSqlFaildInfo(num));
                            mView.hideDialog();
                        }
                    });
                }

                @Override
                public void Faild(int num) {
                    ToastUtil.makeText(SqlStateCode.getSqlFaildInfo(num));
                    mView.hideDialog();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<String> removeDuplicate(List<String> list) {
        Set set = new LinkedHashSet<String>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }


    /*显示需要过滤的数据*/
    public void showFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mView);
        filterDialog = builder.create();
        final View viewDialog = LayoutInflater.from(mView).inflate(R.layout.dialog_selectuselocation, null);
        filterDialog.show();
        filterDialog.getWindow().setContentView(viewDialog);
        filterDialog.setCanceledOnTouchOutside(false);

        mTvWardLocation = viewDialog.findViewById(R.id.tv_wardLocation);
        mTvWardName = viewDialog.findViewById(R.id.tv_wardName);
        mTvDepartmentName = viewDialog.findViewById(R.id.tv_departmentName);
        mTvResidentRoomNo = viewDialog.findViewById(R.id.tv_residentRoomNo);
        mTvDate = viewDialog.findViewById(R.id.tv_date);
        mTvDate.setText(TimeUtil.getSystemTime());
        LinearLayout llDate = viewDialog.findViewById(R.id.ll_date);

        Button btQuery = viewDialog.findViewById(R.id.bt_query);
        Button btCancle = viewDialog.findViewById(R.id.bt_cancle);
        btCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterDialog.dismiss();
            }
        });
        btQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mView.setAddress(mTvWardLocation.getText().toString() + " " +
                        mTvWardName.getText().toString() + " " + mTvDepartmentName.getText().toString() + "-"
                        + mTvResidentRoomNo.getText().toString());
                startQuery();
            }
        });

        mTvDepartmentName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop(mDepartmentNameList, mTvDepartmentName);
            }
        });

        mTvWardName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPop(mWardNameList, mTvWardName);
            }
        });

        mTvWardLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPop(mWardLocationList, mTvWardLocation);
            }
        });
        mTvResidentRoomNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPop(mResidentRoomNoList, mTvResidentRoomNo);
            }
        });
        llDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeUtil.selectDate(mView, mTvDate);
            }
        });

    }

    private void changeTextView(int number) {
        switch (number){
            case 1:
                mWardNameList.clear();
                for (int i = 0; i < mIntWardCount; i++) {
                    if (mTvDepartmentName.getText().toString().equals(mWardModelList.get(i).getDepartmentName())) {
                        mWardNameList.add(mWardModelList.get(i).getWardName());
                    }
                }
                mWardNameList = removeDuplicate(mWardNameList);
                if (mWardNameList.size()!=0){
                    mTvWardName.setText(mWardNameList.get(0));
                }else {
                    mTvWardName.setText("   ");
                }
            case 2:
                mWardLocationList.clear();
                for (int i = 0; i < mIntWardCount; i++) {
                    if (mTvWardName.getText().toString().equals(mWardModelList.get(i).getWardName())) {
                        mWardLocationList.add(mWardModelList.get(i).getWardLocation());
                    }
                }
                mWardLocationList = removeDuplicate(mWardLocationList);
                if (mWardLocationList.size()!=0){
                    mTvWardLocation.setText(mWardLocationList.get(0));
                }else {
                    mTvWardLocation.setText("   ");
                }
            case 3:
                String wardID = getWardID();
                mResidentRoomNoList.clear();
                for (int i = 0; i < mIntSickRoomCount; i++) {
                    if (wardID.equals(mSickRoomModelList.get(i).getWardID())) {
                        mResidentRoomNoList.add(mSickRoomModelList.get(i).getResidentRoomNo());
                    }
                }
                mResidentRoomNoList = removeDuplicate(mResidentRoomNoList);
                if (mResidentRoomNoList.size()!=0){
                    mTvResidentRoomNo.setText(mResidentRoomNoList.get(0));
                }else {
                    mTvResidentRoomNo.setText("   ");
                }
                break;

        }

    }

    private String getWardID() {
        String wardID = null;
        for (int i = 0; i < mIntWardCount; i++) {
            if (mWardModelList.get(i).getWardLocation().equals(mTvWardLocation.getText().toString()) &&
                    mWardModelList.get(i).getDepartmentName().equals(mTvDepartmentName.getText().toString()) &&
                    mWardModelList.get(i).getWardName().equals(mTvWardName.getText().toString())) {
                wardID = mWardModelList.get(i).getWardId();
                Log.e("wardID", wardID);
            }
        }
        return wardID;
    }

    private void startQuery() {
        mTemperatureModelList.clear();

        String wardID = getWardID();

        String deviceID = null;
        boolean existence = false;
        for (int i = 0; i < mIntSickRoomCount; i++) {
            if (mSickRoomModelList.get(i).getResidentRoomNo().equals(mTvResidentRoomNo.getText().toString()) &&
                    mSickRoomModelList.get(i).getWardID().equals(wardID)) {
                deviceID = mSickRoomModelList.get(i).getDeviceID();
                Log.e("deviceID", deviceID);
                existence = true;
            }
        }
        if (!existence) {
            ToastUtil.showShortToastSafe("未找到对应位置的病房！");
            return;
        }
        try {
            PartDataSelectionModel model = new PartDataSelectionModel();
            String[] parts = new String[]{Constants.HUMITURE_TEMPERATURE, Constants.HUMITURE_HUMIDUTY,
                    Constants.NO2,Constants.SO2,Constants.PM10, Constants.HUMITURE_CURRENTTIME,
                    Constants.DEVICE_ID,Constants.HUMITURE_CURRENTDATE};
            String[] selections = {Constants.HUMITURE_DEVICEID, Constants.HUMITURE_CURRENTDATE};
            String[] hazyOrExact = {"=", "="};
//            String[] conditions = {deviceID, TimeUtil.getSystemTime()};
            String[] conditions = {deviceID, mTvDate.getText().toString()};
            model.setTableNameNo(Constants.TEMPERATURE_TABLE_NO);
            model.setParts(parts);
            model.setSelections(selections);
            model.setHazyOrExact(hazyOrExact);
            model.setConditions(conditions);
            SQLCursor.getPartDataBySelection(mView, model, new SqlInfoCallBack() {
                @Override
                public void Success(ArrayList<SqlFactory> sqlFactories) {
                    filterDialog.dismiss();
                    mIntTemperatureCount = sqlFactories.size();
                    mTemperatureModelList.clear();
                    for (int i = 0; i < mIntTemperatureCount; i++) {
                        mTemperatureModelList.add((TemperatureModel) (sqlFactories.get(i)));
                    }
                    startDraw();
                }

                @Override
                public void Faild(int num) {
                    filterDialog.dismiss();
                    startDraw();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void startDraw() {
        Constants.num = 1;
        brokenLineView = new BrokenLineView(mView.mDoubleLineChar, mView);
        mIntPageCount = 1;
        index = 1;
        mView.setUpClick(false);
        mView.mDoubleLineChar.clear();
        mIntDataCount = mTemperatureModelList.size();
        if (mIntDataCount > 12) {
            mIntPageCount = 2;
            mView.setNextClick(true);
        }
        brokenLineView.setDate(TimeUtil.getSystemTime());
        if (mIntDataCount < 5) {
            brokenLineView.clear();
            return;
        }

        if (flag==Constants.FLAG_TEM_AND_HUM){
            brokenLineView.showRightYAxisLine();
        }
        fillData(mIntDataCount > 12 ? 12 : mIntDataCount);
        brokenLineView.showXAxisLine(ValueFormatterUtil.getXAxisValueFormat(mTemperatureModelList));
        brokenLineView.showLeftYAxisLine();
        brokenLineView.showLegend();
    }

    /*填充数据*/
    private void fillData(int count) {
        if (flag == Constants.FLAG_TEM_AND_HUM) {
            /*初始化数据*/
            ArrayList<Entry> humidityList = new ArrayList<>();
            for (int i = 0 + (index - 1) * 12; i < count; i++) {
                humidityList.add(new Entry(i - (index - 1) * 12, Float.valueOf(mTemperatureModelList.get(i).getHumidity())));
            }
            ArrayList<Entry> temperatureList = new ArrayList<>();
            for (int i = 0 + (index - 1) * 12; i < count; i++) {
                temperatureList.add(new Entry(i - (index - 1) * 12, Float.valueOf(mTemperatureModelList.get(i).getTemperature())));
            }
        /*初始化LineDataSet*/
            LineDataSet set1 = brokenLineView.getLineDataSet(humidityList, R.string.humidity, YAxis.AxisDependency.LEFT, Color.BLUE, new PercentFormatter());
            LineDataSet set2 = brokenLineView.getLineDataSet(temperatureList, R.string.temperature, YAxis.AxisDependency.RIGHT, Color.YELLOW, ValueFormatterUtil.getTemperatureFormat());
            brokenLineView.setCirclePoint(set1, Constants.STAND_LOW_HUMIDITY, Constants.STAND_HIGHT_HUMIDITY);
            brokenLineView.setCirclePoint(set2, Constants.STAND_LOW_TEMPERATURE, Constants.STAND_HIGHT_TEMPERATURE);
            ArrayList<ILineDataSet> lineDataSetsList = new ArrayList<>();
            lineDataSetsList.add(set1);
            lineDataSetsList.add(set2);
            brokenLineView.setData(lineDataSetsList);
        } else {
            ArrayList<Entry> AirList = new ArrayList<>();
            LineDataSet set = null;
            for (int i = 0 + (index - 1) * 12; i < count; i++) {
                switch (flag) {
                    case Constants.FLAG_NO2:
                        AirList.add(new Entry(i - (index - 1) * 12, Float.valueOf(mTemperatureModelList.get(i).getNo2())));
                        set = brokenLineView.getLineDataSet(AirList, R.string.no2, YAxis.AxisDependency.LEFT, Color.BLUE, ValueFormatterUtil.getGasFormat());
                        brokenLineView.setCirclePoint(set, Constants.STAND_LOW_HUMIDITY, Constants.STAND_HIGHT_HUMIDITY);
                        brokenLineView.setyLeftAxisMaximum(70f);
                        break;
                    case Constants.FLAGE_SO2:
                        AirList.add(new Entry(i - (index - 1) * 12, Float.valueOf(mTemperatureModelList.get(i).getSo2())));
                        set = brokenLineView.getLineDataSet(AirList, R.string.so2, YAxis.AxisDependency.LEFT, Color.BLUE, new PercentFormatter());
                        brokenLineView.setCirclePoint(set, Constants.STAND_LOW_SO2, Constants.STAND_HIGHT_SO2);
                        brokenLineView.setyLeftAxisMaximum(20f);
                        break;
                    case Constants.FLAG_PM10:
                        AirList.add(new Entry(i - (index - 1) * 12, Float.valueOf(mTemperatureModelList.get(i).getPm10())));
                        set = brokenLineView.getLineDataSet(AirList, R.string.pm10, YAxis.AxisDependency.LEFT, Color.BLUE, new PercentFormatter());
                        brokenLineView.setCirclePoint(set, Constants.STAND_LOW_PM10, Constants.STAND_HIGHT_PM10);
                        brokenLineView.setyLeftAxisMaximum(130f);
                        break;
                    default:
                        AirList.add(new Entry(i - (index - 1) * 12, Float.valueOf(mTemperatureModelList.get(i).getSo2())));
                        set = brokenLineView.getLineDataSet(AirList, R.string.no2, YAxis.AxisDependency.LEFT, Color.BLUE, new PercentFormatter());
                        brokenLineView.setCirclePoint(set, Constants.STAND_LOW_NO2, Constants.STAND_HIGHT_NO2);
                        brokenLineView.setyLeftAxisMaximum(70f);
                        break;
                }

            }



            ArrayList<ILineDataSet> lineDataSetsList = new ArrayList<>();
            lineDataSetsList.add(set);
            brokenLineView.setData(lineDataSetsList);


        }

    }

    /*上一页*/
    public void upPage() {
        index--;
        mView.setNextClick(true);
        if (index == 1) {
            mView.setUpClick(false);
        }
        Constants.num = 1;
        fillData(12);
    }

    /*下一页*/
    public void nextPage() {
        index++;
        mView.setUpClick(true);
        if (index == mIntPageCount) {
            mView.setNextClick(false);
        }
        Constants.num = 2;
        fillData(mIntDataCount);
    }


    /*显示PopWindow过滤数据*/
    private void showPop(final List<String> list, final TextView tvText) {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            View viewPop = LayoutInflater.from(mView).inflate(R.layout.pop_view, null);
            mPopupWindow = new PopupWindow(viewPop, SizeUtil.getViewWidth(mTvWardLocation), ViewGroup.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setFocusable(true);
            mPopupWindow.showAsDropDown(tvText);
            mPopupWindow.update();
            ListView listView = viewPop.findViewById(R.id.lv_view);
            listView.setAdapter(new ArrayAdapter<>(mView, R.layout.item_select_view, list));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    tvText.setText(list.get(i));
                    if (tvText.equals(mTvDepartmentName)){
                        changeTextView(1);
                    }else if (tvText.equals(mTvWardName)){
                        changeTextView(2);
                    }else if (tvText.equals(mTvWardLocation)){
                        changeTextView(3);
                    }
                    mPopupWindow.dismiss();
                }
            });

        }
    }


}
