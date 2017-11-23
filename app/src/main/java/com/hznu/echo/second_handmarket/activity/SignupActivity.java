package com.hznu.echo.second_handmarket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.hznu.echo.second_handmarket.R;
import com.hznu.echo.second_handmarket.bean.User;
import com.hznu.echo.second_handmarket.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class SignupActivity extends AppCompatActivity {

    private String user_name,password,password2;
    @BindView(R.id.user_name)
    EditText userName;
    @BindView(R.id.user_password)
    EditText userPassword;
    @BindView(R.id.user_password2)
    EditText userPassword2;
    @BindView(R.id.signup)
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.signup)
    public void onViewClicked() {
        initData();
        isEmpty();
    }
    //从view获取数据
    private void initData(){
        user_name = userName.getText().toString();
        password = userPassword.getText().toString();
        password2 = userPassword2.getText().toString();
    }

    //非空判断
    private void isEmpty(){
        if(user_name.equals("")||password.equals("")||password2.equals("")){
            ToastUtil.showShort("不能为空");
        }else if(!password.equals(password2)){
            ToastUtil.showShort("两次输入的密码不一致");
        }else {
            User user = new User();
            user.setUsername(user_name);
            user.setPassword(password);
            user.signUp(new SaveListener<User>() {
                @Override
                public void done(User user, BmobException e) {
                    if(e==null){
                        ToastUtil.showShort("注册成功!");
                        startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                        finish();
                    }else{
                        if(e.getErrorCode() == 202){
                            ToastUtil.showShort("用户名已经存在");
                        }

                    }
                }
            });
        }
    }
}
