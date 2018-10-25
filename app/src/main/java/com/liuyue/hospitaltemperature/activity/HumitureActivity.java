package com.liuyue.hospitaltemperature.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.liuyue.hospitaltemperature.R;
import com.liuyue.hospitaltemperature.a_model.WardModel;
import com.liuyue.hospitaltemperature.a_presenters.HumiturePresenter;
import com.liuyue.hospitaltemperature.a_views.HumitureView;
import com.liuyue.hospitaltemperature.base.BaseMvpActivity;
import com.liuyue.hospitaltemperature.util.ToastUtil;

import butterknife.BindView;

import static com.liuyue.hospitaltemperature.util.ViewUtil.setStatusBarColor;


/**
 * Created by kys_31 on 2017/12/5.
 */

public class HumitureActivity extends BaseMvpActivity<HumiturePresenter> implements HumitureView {
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.iv_filter)
    ImageView ivFilter;
    @BindView(R.id.lv_lineChat)
    public LineChart mDoubleLineChar;
    @BindView(R.id.bt_upPage)
    Button btUpPage;
    @BindView(R.id.bt_nextPage)
    Button btNextPage;
    @BindView(R.id.tv_address)
    TextView tvAddress;


    @Override
    public HumiturePresenter createPresenter() {
        return new HumiturePresenter(this);
    }

    @Override
    protected int getLayouID() {
        return R.layout.layout_fullscreen;
    }

    @Override
    protected void initView() {
         /*设置状态栏颜色*/
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.text_black));
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarColor(HumitureActivity.this,0XFF58ae8d);
        }
    }

    @Override
    protected void initData(){

        mPresenter.initHospitalData(getIntent().getIntExtra("flag",1));
    }


    @Override
    public void setAddress(String str) {
        tvAddress.setText(str);
    }

    @Override
    public void setNextClick(boolean click) {
        btNextPage.setEnabled(click);
        if (!click){
            btNextPage.setText("尾页");
            btNextPage.setTextColor(Color.GRAY);
        }else {
            btNextPage.setText("下一页");
            btNextPage.setTextColor(Color.WHITE);
        }
    }

    @Override
    public void setUpClick(boolean click) {
        btUpPage.setEnabled(click);
        if (!click){
            btUpPage.setText("首页");
            btUpPage.setTextColor(Color.GRAY);
        }else {
            btUpPage.setText("上一页");
            btUpPage.setTextColor(Color.WHITE);
        }
    }

    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.iv_close:
                finish();
                break;
            case R.id.iv_filter:
                try{
                    if (!mPresenter.filterDialog.isShowing()){
                        mPresenter.showFilterDialog();
                    }
                }catch (NullPointerException e){
                    ToastUtil.showShortToastSafe("正在初始化数据，请稍后重试");
                }

                break;
            case R.id.bt_upPage:
                mPresenter.upPage();
                break;
            case R.id.bt_nextPage:
                mPresenter.nextPage();
                break;
                default:
                    break;
        }
    }
}
