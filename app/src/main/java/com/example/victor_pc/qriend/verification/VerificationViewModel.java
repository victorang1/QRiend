package com.example.victor_pc.qriend.verification;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.example.victor_pc.qriend.R;
import com.example.victor_pc.qriend.common.APIManager;
import com.example.victor_pc.qriend.common.QRCodeGenerator;
import com.example.victor_pc.qriend.model.Friend;
import com.example.victor_pc.qriend.model.Session;
import com.example.victor_pc.qriend.model.User;
import com.example.victor_pc.qriend.repository.UserRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VerificationViewModel extends ViewModel {

    private static final String APP_NAME = "qFriend";
    private UserRepository mRepository;
    private MutableLiveData<User> mResult;
    public static LiveData<Boolean> isDuplicate;

    public VerificationViewModel() {
        mRepository = UserRepository.getInstance();
    }

    public LiveData<Boolean> checkDuplicateFriend(Bundle bundle) {
        if(isDuplicate == null) {
            isDuplicate = new MutableLiveData<>();
        }
        isDuplicate = loadDuplicateFriend(bundle);
        return isDuplicate;
    }

    private MutableLiveData<Boolean> loadDuplicateFriend(Bundle bundle) {
        final MutableLiveData<Boolean> result = new MutableLiveData<>();
        try {
            JSONObject jsonObject = new JSONObject(getResult(bundle));
            if(jsonObject.getString("appName").equals(APP_NAME)) {
                final Friend friend = new Friend(jsonObject.getString("username"), jsonObject.getString("key"));
                mRepository.loadDuplicateListFriend(new APIManager() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        Boolean bool = new Boolean(false);
                        for(DataSnapshot item : dataSnapshot.getChildren()) {
                            Friend data = item.getValue(Friend.class);
                            Log.d("<RESULT>", "loadDuplicateFriend: " + data.getUsername() + " " + data.getKey());
                            if(friend.getUsername().equals(data.getUsername())) {
                                bool = true;
                                break;
                            }
                        }
                        result.postValue(bool);
                    }

                    @Override
                    public void onFailure(DatabaseError databaseError) {
                        result.postValue(false);
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean insertFriend(Bundle bundle) {
        try {
            JSONObject jsonObject = new JSONObject(getResult(bundle));
            if(jsonObject.getString("appName").equals(APP_NAME)) {
                Friend friend = new Friend(jsonObject.getString("username"), jsonObject.getString("key"));
                Friend user = new Friend(Session.username, Session.user_key);
                if(mRepository.insertFriendToFirebase(friend, user)) {
                    return true;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getResult(Bundle bundle) {
        String result = bundle.getString("data");
        return result;
    }

    public MutableLiveData<User> getQRCode(String user_qrCode) {
        if(mResult == null) {
            mResult = new MutableLiveData<>();
        }
        mResult = loadQRCode(user_qrCode);
        return mResult;
    }

    private MutableLiveData<User> loadQRCode(final String user_qrCode) {
        final MutableLiveData<User> result = new MutableLiveData<>();
        try {
            JSONObject jsonObject = new JSONObject(user_qrCode);
            if(jsonObject.getString("appName").equals(APP_NAME)) {
                mRepository.getUserDataFromFireBase(new APIManager() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        for(DataSnapshot item : dataSnapshot.getChildren()) {
                            User data = item.getValue(User.class);
                            if(data.getQr_code().equals(user_qrCode)) {
                                result.postValue(data);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onFailure(DatabaseError databaseError) {
                        result.postValue(null);
                    }
                });
            } else {
                result.postValue(null);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
