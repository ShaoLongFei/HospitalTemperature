package com.liuyue.hospitaltemperature.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuyue.hospitaltemperature.Adapter.RecordAdapter;
import com.liuyue.hospitaltemperature.R;
import com.liuyue.hospitaltemperature.a_model.RecordModel;
import com.liuyue.hospitaltemperature.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import static com.liuyue.hospitaltemperature.a_presenters.WarnPresenter.sWarnHumitureList;
import static com.liuyue.hospitaltemperature.a_presenters.WarnPresenter.sWarnNO2List;
import static com.liuyue.hospitaltemperature.a_presenters.WarnPresenter.sWarnPM10List;
import static com.liuyue.hospitaltemperature.a_presenters.WarnPresenter.sWarnTemperatureList;
import static com.liuyue.hospitaltemperature.util.ViewUtil.setStatusBarColor;

/**
 * Created by 流月 on 2018/6/27.
 *
 * @description
 */

public class WarnListActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    private RecyclerView mRecycleListView;
    private RecordAdapter recordAdapter;
    private List<RecordModel> mRecordModelList=new ArrayList<>();
    private ImageView iv_back;
    private TextView tv_date;
    private ImageView iv_date;
    private TextView tv_noData;
    private int flag=0;
    private String date;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar=getSupportActionBar();
        bar.hide();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarColor(WarnListActivity.this,0XFF7adfb8);
        }
        setContentView(R.layout.activity_record);
        flag=getIntent().getIntExtra("flag",1);
        initControl();

    }

    private void initControl() {
        iv_back=(ImageView)findViewById(R.id.iv_back);
        tv_date=(TextView)findViewById(R.id.tv_date);
        iv_date=(ImageView) findViewById(R.id.iv_date);
        tv_noData=(TextView)findViewById(R.id.tv_noData);
        mRecycleListView=(RecyclerView)findViewById(R.id.rv_view);
        iv_back.setOnClickListener(this);
        iv_date.setOnClickListener(this);
        tv_date.addTextChangedListener(this);
        date=TimeUtil.getSystemTime();
        tv_date.setText(date);
    }

    private void fillData() {
        switch (flag){
            case 1://温度
                for (RecordModel recordModel:sWarnTemperatureList) {
                    if (recordModel.getDate().equals(date)){
                        mRecordModelList.add(recordModel);
                    }
                }
                break;
            case 2://湿度
                for (RecordModel recordModel:sWarnHumitureList) {
                    if (recordModel.getDate().equals(date)){
                        mRecordModelList.add(recordModel);
                    }
                }
                break;
            case 3://SO2
                for (RecordModel recordModel:sWarnNO2List) {
                    if (recordModel.getDate().equals(date)){
                        mRecordModelList.add(recordModel);
                    }
                }
                break;
            case 4://NO2
                for (RecordModel recordModel:sWarnNO2List) {
                    if (recordModel.getDate().equals(date)){
                        mRecordModelList.add(recordModel);
                    }
                }
                break;
            case 5://PM10
                for (RecordModel recordModel:sWarnPM10List) {
                    if (recordModel.getDate().equals(date)){
                        mRecordModelList.add(recordModel);
                    }
                }
                break;
                default:
                    break;
        }
        if (mRecordModelList.size()!=0){
            tv_noData.setVisibility(View.GONE);
            recordAdapter=new RecordAdapter(WarnListActivity.this,mRecordModelList);
            mRecycleListView.setLayoutManager(new LinearLayoutManager(this));
            mRecycleListView.setAdapter(recordAdapter);
        }else {
            tv_noData.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_date:
                TimeUtil.selectDate(WarnListActivity.this, tv_date);

                break;
                default:
                    break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        date=tv_date.getText().toString();
        fillData();
    }
}
