package com.example.victor_pc.qriend.common;



import android.graphics.Bitmap;
import android.util.Log;

import com.example.victor_pc.qriend.model.User;
import com.google.zxing.WriterException;

import org.json.JSONException;
import org.json.JSONObject;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QRCodeGenerator {

    public String dataToJSON(String key, User user) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("appName", "qFriend");
            jsonObject.put("username", user.getUsername());
            jsonObject.put("key", key);
            return jsonObject.toString();
        } catch (JSONException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public Bitmap qrgEncoder(String inputValue) {
        QRGEncoder qrgEncoder = new QRGEncoder(
                inputValue, null,
                QRGContents.Type.TEXT,
                200);
        try{
            Bitmap bitmap = qrgEncoder.encodeAsBitmap();
            return bitmap;
        } catch (WriterException ex) {
            Log.d("<ERROR>", "Fail to generate qr");
        }
        return null;
    }
}
