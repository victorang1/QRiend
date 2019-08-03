package com.example.victor_pc.qriend.register;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;

import com.example.victor_pc.qriend.R;
import com.example.victor_pc.qriend.common.BaseActivity;
import com.example.victor_pc.qriend.databinding.ActivityRegisterBinding;
import com.example.victor_pc.qriend.login.LoginActivity;
import com.example.victor_pc.qriend.model.User;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding, RegisterViewModel> implements View.OnClickListener {

    public RegisterActivity() {
        super(RegisterViewModel.class, R.layout.activity_register);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListener();
        getBinding().setViewModel(new User());
    }

    private void initListener() {
        getBinding().btnRegister.setOnClickListener(this);
        getBinding().tvGotoSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == getBinding().btnRegister) {
            final User user = getBinding().getViewModel();
            if(getViewModel().checkUsernameLength(user)) {
                getViewModel().checkDuplicateUser(user).observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if(aBoolean) {
                            showMessage("This username has been registered");
                        } else {
                            if(getViewModel().insertUser(user)) {
                                showIntentMessage("Register Success", LoginActivity.class, true);
                            } else showMessage("Register Failed");
                        }
                    }
                });
            } else {
                showMessage("Username must less than 12 character");
            }


        } else if(v == getBinding().tvGotoSignIn) {
            gotoActivity(LoginActivity.class, true);
        }
    }
}
