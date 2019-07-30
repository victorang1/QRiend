package com.example.victor_pc.qriend.showqr;

import android.arch.lifecycle.Observer;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.victor_pc.qriend.R;
import com.example.victor_pc.qriend.common.BaseActivity;
import com.example.victor_pc.qriend.databinding.ActivityShowQrBinding;
import com.example.victor_pc.qriend.home.HomeActivity;
import com.example.victor_pc.qriend.model.Session;
import com.example.victor_pc.qriend.model.User;

public class ShowQRActivity extends BaseActivity<ActivityShowQrBinding, ShowQRViewModel> {

    public ShowQRActivity() {
        super(ShowQRViewModel.class, R.layout.activity_show_qr);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewModel().getQRCode().observe(this, new Observer<Bitmap>() {
            @Override
            public void onChanged(@Nullable Bitmap bitmap) {
                getBinding().qrCode.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public void onBackPressed() {
        gotoActivity(HomeActivity.class, true);
    }
}
