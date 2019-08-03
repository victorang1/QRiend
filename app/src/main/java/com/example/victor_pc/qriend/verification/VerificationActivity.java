package com.example.victor_pc.qriend.verification;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;

import com.example.victor_pc.qriend.R;
import com.example.victor_pc.qriend.common.BaseActivity;
import com.example.victor_pc.qriend.common.QRCodeGenerator;
import com.example.victor_pc.qriend.databinding.ActivityVerificationBinding;
import com.example.victor_pc.qriend.home.HomeActivity;
import com.example.victor_pc.qriend.model.User;
import com.example.victor_pc.qriend.scanqr.ScanQRActivity;

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
            if(!getViewModel().checkDuplicateFriend(getIntent().getExtras())) {
                if(getViewModel().insertFriend(getIntent().getExtras())) {
                    showMessage("Friend has been added to the friendlist");
                    gotoActivity(HomeActivity.class, true);
                }
            } else {
                showMessage("You cannot add this friend");
                gotoActivity(HomeActivity.class, true);
            }
        } else if(v == getBinding().llCancel) {
            gotoActivity(ScanQRActivity.class, true);
        }
    }

    private void initListener() {
        getBinding().llAccept.setOnClickListener(this);
        getBinding().llCancel.setOnClickListener(this);
    }

    private void getResultFromIntent() {
        getViewModel().getQRCode(getViewModel().getResult(getIntent().getExtras())).observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if(getViewModel().getEventId() == User.NO_DATA) {
                    showLoadingProgress();
                }
                else if(getViewModel().getEventId() == User.SIMILAR_DATA) {
                    showMessage("You cannot add yourself");
                    gotoActivity(HomeActivity.class, true);
                }
                else if(getViewModel().getEventId() == User.SHOW_DATA) {
                    stopLoadingProgress();
                    getBinding().setViewModel(user);
                    getBinding().qrCode.setImageBitmap(qrCodeGenerator.qrgEncoder(user.getQr_code()));
                }
            }
        });
    }

    private void showLoadingProgress() {
        getBinding().progressLoading.setVisibility(View.VISIBLE);
        getBinding().rlContent.setVisibility(View.GONE);
    }

    private void stopLoadingProgress() {
        getBinding().progressLoading.setVisibility(View.GONE);
        getBinding().rlContent.setVisibility(View.VISIBLE);
    }
}
