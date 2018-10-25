package com.liuyue.hospitaltemperature.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.liuyue.hospitaltemperature.R;
import com.liuyue.hospitaltemperature.a_model.TemperatureModel;
import com.liuyue.hospitaltemperature.a_presenters.HumiturePresenter;
import com.liuyue.hospitaltemperature.a_presenters.WarnPresenter;
import com.liuyue.hospitaltemperature.a_views.HumitureView;
import com.liuyue.hospitaltemperature.base.BaseMvpActivity;

import java.util.ArrayList;
import java.util.List;

import static com.liuyue.hospitaltemperature.util.ViewUtil.setStatusBarColor;

/**
 * Created by 流月 on 2018/6/27.
 *
 * @description
 */

public class WarnActivity extends BaseMvpActivity<WarnPresenter> {

    @Override
    public WarnPresenter createPresenter() {
        return new WarnPresenter(this);
    }

    @Override
    protected int getLayouID() {
        return R.layout.activity_warn;
    }

    @Override
    protected void initView() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarColor(WarnActivity.this,0XFF7adfb8);
        }
        findViewById(R.id.bt_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        findViewById(R.id.bt_temperature).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WarnActivity.this,WarnListActivity.class);
                intent.putExtra("flag",1);
                startActivity(intent);
            }
        });
        findViewById(R.id.bt_humiture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WarnActivity.this,WarnListActivity.class);
                intent.putExtra("flag",2);
                startActivity(intent);
            }
        });
        findViewById(R.id.bt_so2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WarnActivity.this,WarnListActivity.class);
                intent.putExtra("flag",3);
                startActivity(intent);
            }
        });
        findViewById(R.id.bt_no2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WarnActivity.this,WarnListActivity.class);
                intent.putExtra("flag",4);
                startActivity(intent);
            }
        });
        findViewById(R.id.bt_pm10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WarnActivity.this,WarnListActivity.class);
                intent.putExtra("flag",5);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.initTemperatureData();
    }
}
