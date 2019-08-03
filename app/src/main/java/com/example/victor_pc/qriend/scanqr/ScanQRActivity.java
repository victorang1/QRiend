package com.example.victor_pc.qriend.scanqr;

import android.os.Bundle;

import com.example.victor_pc.qriend.R;
import com.example.victor_pc.qriend.common.BaseActivity;
import com.example.victor_pc.qriend.databinding.ActivityScanQrBinding;
import com.example.victor_pc.qriend.home.HomeActivity;
import com.example.victor_pc.qriend.verification.VerificationActivity;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanQRActivity extends BaseActivity<ActivityScanQrBinding, ScanQRViewModel> {

    private ZXingScannerView mScannerView;

    public ScanQRActivity() {
        super(ScanQRViewModel.class, R.layout.activity_scan_qr);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setResult() {
        mScannerView.setResultHandler(new ZXingScannerView.ResultHandler() {
            @Override
            public void handleResult(Result result) {
                if(getViewModel().checkResult(result)) {
                    gotoActivity(VerificationActivity.class, true, result.getText());
                } else {
                    showMessage("This QR Code is not from our application");
                    gotoActivity(HomeActivity.class, true);
                }
            }
        });
    }

    private void initScanner() {
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        setResult();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initScanner();
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void onBackPressed() {
        gotoActivity(HomeActivity.class, true);
    }
}
