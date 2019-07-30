package com.example.victor_pc.qriend.verification;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.victor_pc.qriend.R;
import com.example.victor_pc.qriend.common.BaseActivity;
import com.example.victor_pc.qriend.common.QRCodeGenerator;
import com.example.victor_pc.qriend.databinding.ActivityVerificationBinding;
import com.example.victor_pc.qriend.home.HomeActivity;
import com.example.victor_pc.qriend.model.Friend;
import com.example.victor_pc.qriend.model.User;
import com.example.victor_pc.qriend.scanqr.ScanQRActivity;

import java.util.ArrayList;
import java.util.List;

public class VerificationActivity extends BaseActivity<ActivityVerificationBinding, VerificationViewModel> implements View.OnClickListener{

    private QRCodeGenerator qrCodeGenerator;

    public VerificationActivity() {
        super(VerificationViewModel.class, R.layout.activity_verification);
        qrCodeGenerator = new QRCodeGenerator();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getResultFromIntent();
        initListener();
    }

    @Override
    public void onClick(View v) {
        if(v == getBinding().llAccept) {
            getViewModel().checkDuplicateFriend(getIntent().getExtras()).observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean aBoolean) {
                    
                }
            });
        } else if(v == getBinding().llCancel) {
            gotoActivity(ScanQRActivity.class, true);
        }
    }

    private void toastMessage(String msg, final Class to) {
        disableButton();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoActivity(to, true);
            }
        }, 2000);
    }

    private void disableButton() {
        getBinding().llAccept.setEnabled(false);
        getBinding().llCancel.setEnabled(false);
    }

    private void initListener() {
        getBinding().llAccept.setOnClickListener(this);
        getBinding().llCancel.setOnClickListener(this);
    }

    private void getResultFromIntent() {
        getViewModel().getQRCode(getViewModel().getResult(getIntent().getExtras())).observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                getBinding().setViewModel(user);
                getBinding().qrCode.setImageBitmap(qrCodeGenerator.qrgEncoder(user.getQr_code()));
            }
        });
    }
}
