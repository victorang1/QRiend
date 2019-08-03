package com.example.victor_pc.qriend.scanqr;

import android.arch.lifecycle.ViewModel;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

public class ScanQRViewModel extends ViewModel {

    private static final String APP_NAME = "qFriend";

    public ScanQRViewModel() {

    }

    public boolean checkResult(Result result) {
        try{
            JSONObject jsonObject = new JSONObject(result.getText());
            if(jsonObject.getString("appName").equals(APP_NAME)) {
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
