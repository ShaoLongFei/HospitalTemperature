package com.liuyue.hospitaltemperature.activity;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liuyue.hospitaltemperature.R;
import com.liuyue.hospitaltemperature.a_presenters.LoginPresenter;
import com.liuyue.hospitaltemperature.a_views.LoginView;
import com.liuyue.hospitaltemperature.base.BaseMvpActivity;
import com.liuyue.hospitaltemperature.util.ToastUtil;
import com.liuyue.hospitaltemperature.view.EditTextWithClear;
import com.liuyue.hospitaltemperature.view.PasswordEditText;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by kys_31 on 2017/11/30.
 */

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginView {
    @BindView(R.id.etUsername)
    EditTextWithClear etUsername;
    @BindView(R.id.etPassword)
    PasswordEditText etPassword;
    @BindView(R.id.btLogin)
    TextView btLogin;
    @BindView(R.id.input_layout_name)
    LinearLayout inputLayoutName;
    @BindView(R.id.id_line)
    View idLine;
    @BindView(R.id.input_layout_psw)
    LinearLayout inputLayoutPsw;
    @BindView(R.id.input_layout)
    LinearLayout inputLayout;
    @BindView(R.id.progressBar2)
    ProgressBar progressBar2;


    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getLayouID() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

    }


    @Override
    public void loginSuccess() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void loginFailture(String failureMessage) {
        ToastUtil.makeTextSafe(failureMessage);
    }

    @Override
    public String getUsername() {
        return etUsername.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString().trim();
    }

    @OnClick(R.id.btLogin)
    public void onLogin() {
        mPresenter.fastLogin(getUsername(), mPresenter.MD5(getPassword()));
    }



}
