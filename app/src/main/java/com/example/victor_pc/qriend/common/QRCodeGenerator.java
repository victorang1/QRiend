package com.example.victor_pc.qriend.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.WindowManager;

import com.example.victor_pc.qriend.R;
import com.example.victor_pc.qriend.model.User;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QRCodeGenerator {

    public byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }

    public Bitmap convertByteToBitmap(byte[] qrCode) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(qrCode, 0, qrCode.length);
        return bitmap;
    }

    public Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    500, 500, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        R.color.colorBlack:R.color.colorWhite;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

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
