package com.example.victor_pc.qriend.login;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.victor_pc.qriend.R;
import com.example.victor_pc.qriend.common.BaseActivity;
import com.example.victor_pc.qriend.databinding.ActivityLoginBinding;
import com.example.victor_pc.qriend.home.HomeActivity;
import com.example.victor_pc.qriend.model.User;
import com.example.victor_pc.qriend.register.RegisterActivity;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements View.OnClickListener {

    public LoginActivity() {
        super(LoginViewModel.class, R.layout.activity_login);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListener();
        getBinding().setViewModel(new User());
    }

    private void initListener() {
        getBinding().btnLogin.setOnClickListener(this);
        getBinding().tvGotoSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == getBinding().btnLogin) {
            User user = getBinding().getViewModel();
            getViewModel().getUser(user).observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean aBoolean) {
                    if(aBoolean) {
                        gotoActivity(HomeActivity.class, true);
                    } else {
                        showMessage("Incorrect email and password");
                    }
                }
            });
        } else if(v == getBinding().tvGotoSignUp) {
            gotoActivity(RegisterActivity.class, true);
        }
    }
}
