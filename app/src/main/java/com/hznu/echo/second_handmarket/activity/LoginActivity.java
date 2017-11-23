package com.hznu.echo.second_handmarket.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hznu.echo.second_handmarket.R;
import com.hznu.echo.second_handmarket.bean.User;
import com.hznu.echo.second_handmarket.utils.PreferenceUtils;
import com.hznu.echo.second_handmarket.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by echo on 17/4/12.
 */


public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    @BindView(R.id.forget)
    TextView forget;
    @BindView(R.id.signup)
    TextView signup;
    private EditText username, password;
    private Button bt_username_clear;
    private Button bt_pwd_clear;
    private Button bt_pwd_eye;
    private Button login;
    private boolean isOpen = false;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isUserLogined = PreferenceUtils.getBoolean(LoginActivity.this,
                "is_user_logined", false);
        if (isUserLogined) {
            //进入登录界面
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //隐藏标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        username = (EditText) findViewById(R.id.username);
        // 监听文本框内容变化
        username.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // 获得文本框中的用户
                String user = username.getText().toString().trim();
                if ("".equals(user)) {
                    // 用户名为空,设置按钮不可见
                    bt_username_clear.setVisibility(View.INVISIBLE);
                } else {
                    // 用户名不为空，设置按钮可见
                    bt_username_clear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password = (EditText) findViewById(R.id.password);
        // 监听文本框内容变化
        password.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // 获得文本框中的用户
                String pwd = password.getText().toString().trim();
                if ("".equals(pwd)) {
                    // 用户名为空,设置按钮不可见
                    bt_pwd_clear.setVisibility(View.INVISIBLE);
                } else {
                    // 用户名不为空，设置按钮可见
                    bt_pwd_clear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        bt_username_clear = (Button) findViewById(R.id.bt_username_clear);
        bt_username_clear.setOnClickListener(this);

        bt_pwd_clear = (Button) findViewById(R.id.bt_pwd_clear);
        bt_pwd_clear.setOnClickListener(this);

        bt_pwd_eye = (Button) findViewById(R.id.bt_pwd_eye);
        bt_pwd_eye.setOnClickListener(this);

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_username_clear:
                // 清除登录名
                username.setText("");
                break;
            case R.id.bt_pwd_clear:
                // 清除密码
                password.setText("");
                break;
            case R.id.bt_pwd_eye:
                // 密码可见与不可见的切换
                if (isOpen) {
                    isOpen = false;
                } else {
                    isOpen = true;
                }

                // 默认isOpen是false,密码不可见
                changePwdOpenOrClose(isOpen);

                break;
            case R.id.login:
                // TODO 登录按钮
                String user = username.getText().toString();
                String pass = password.getText().toString();
                //非空验证
                if (user.equals("") || pass.equals("")) {
                    Toast.makeText(LoginActivity.this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    User user1 = new User();
                    user1.setUsername(user);
                    user1.setPassword(pass);
                    user1.login(new SaveListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if(e==null){
                                ToastUtil.showShort("登录成功");
                                Log.d(TAG, "done: " + user.toString());
                                saveState(user);
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                finish();
                            }else{
                                ToastUtil.showShort(e.toString());
                            }
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    /**
     * 密码可见与不可见的切换
     *
     * @param flag
     */
    private void changePwdOpenOrClose(boolean flag) {
        // 第一次过来是false，密码不可见
        if (flag) {
            // 密码可见
            bt_pwd_eye.setBackgroundResource(R.drawable.password_open);
            // 设置EditText的密码可见
            password.setTransformationMethod(HideReturnsTransformationMethod
                    .getInstance());
        } else {
            // 密码不可见
            bt_pwd_eye.setBackgroundResource(R.drawable.password_close);
            // 设置EditText的密码隐藏
            password.setTransformationMethod(PasswordTransformationMethod
                    .getInstance());
        }

    }


    @OnClick({R.id.forget, R.id.signup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.forget:
                break;
            case R.id.signup:
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
                finish();
                break;
        }
    }

    private void saveState(User user){
        PreferenceUtils.setBoolean(LoginActivity.this,"is_user_logined", true);
        PreferenceUtils.setString(LoginActivity.this,"USER_NAME",user.getNickname());
        PreferenceUtils.setString(LoginActivity.this,"USER_SEX",user.getSex());
        PreferenceUtils.setString(LoginActivity.this,"USER_EMAIL",user.getE_mail());
        PreferenceUtils.setString(LoginActivity.this,"USER_SCHOOL",user.getSchool());
    }
}

