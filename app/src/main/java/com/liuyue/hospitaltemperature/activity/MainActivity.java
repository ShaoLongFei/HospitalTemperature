package com.liuyue.hospitaltemperature.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.liuyue.hospitaltemperature.R;
import com.liuyue.hospitaltemperature.base.BaseActivity;
import com.liuyue.hospitaltemperature.util.Constants;

import static com.liuyue.hospitaltemperature.util.ViewUtil.setStatusBarColor;


/**
 * Created by kys_31 on 2017/12/18.
 */

public class MainActivity extends BaseActivity {


    @Override
    protected int getLayouID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        findViewById(R.id.indoor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, HumitureActivity.class);
                intent.putExtra("flag", Constants.FLAG_TEM_AND_HUM);
                startActivity(intent );
            }
        });
        findViewById(R.id.outdoor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WheatherActivity.class));
            }
        });
        findViewById(R.id.bt_no2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, HumitureActivity.class);
                intent.putExtra("flag", Constants.FLAG_NO2);
                startActivity(intent );
            }
        });
        findViewById(R.id.bt_so2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, HumitureActivity.class);
                intent.putExtra("flag", Constants.FLAGE_SO2);
                startActivity(intent );
            }
        });
        findViewById(R.id.bt_pm10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, HumitureActivity.class);
                intent.putExtra("flag", Constants.FLAG_PM10);
                startActivity(intent );
            }
        });
        findViewById(R.id.bt_warn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, WarnActivity.class);
                startActivity(intent );
            }
        });


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarColor(MainActivity.this,0XFF7adfb8);
        }
    }

}
