package com.example.victor_pc.qriend.showqr;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

import com.example.victor_pc.qriend.common.APIManager;
import com.example.victor_pc.qriend.common.QRCodeGenerator;
import com.example.victor_pc.qriend.model.Session;
import com.example.victor_pc.qriend.model.User;
import com.example.victor_pc.qriend.repository.UserRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class ShowQRViewModel extends ViewModel {

    private UserRepository mRepository;
    private MutableLiveData<Bitmap> user_QRCode;
    private QRCodeGenerator qrCodeGenerator;

    public ShowQRViewModel() {
        mRepository = UserRepository.getInstance();
        qrCodeGenerator = new QRCodeGenerator();
    }

    public MutableLiveData<Bitmap> getQRCode() {
        if(user_QRCode == null) {
            user_QRCode = new MutableLiveData<>();
        }
        user_QRCode = loadUserQRCode();
        return user_QRCode;
    }

    public MutableLiveData<Bitmap> loadUserQRCode() {
        final MutableLiveData<Bitmap> result = new MutableLiveData<>();
        mRepository.getUserDataFromFireBase(new APIManager() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for(DataSnapshot item : dataSnapshot.getChildren()) {
                    User data = item.getValue(User.class);
                    if(Session.username.equals(data.getUsername())) {
                        result.postValue(qrCodeGenerator.qrgEncoder(data.getQr_code()));

                    }
                }
            }

            @Override
            public void onFailure(DatabaseError databaseError) {
                result.postValue(null);
            }
        });
        return result;
    }
}
