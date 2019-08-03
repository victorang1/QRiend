package com.example.victor_pc.qriend.verification;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;

import com.example.victor_pc.qriend.common.APIManager;
import com.example.victor_pc.qriend.model.Friend;
import com.example.victor_pc.qriend.model.Session;
import com.example.victor_pc.qriend.model.User;
import com.example.victor_pc.qriend.repository.UserRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import org.json.JSONException;
import org.json.JSONObject;

public class VerificationViewModel extends ViewModel {

    private static final String APP_NAME = "qFriend";
    private UserRepository mRepository;
    private MutableLiveData<User> mResult;
    private User userModel;

    public VerificationViewModel() {
        mRepository = UserRepository.getInstance();
        userModel = new User();
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
                if(Session.username.equals(jsonObject.getString("username"))) {
                    result.postValue(new User(Session.username));
                    userModel.setEventId(User.SIMILAR_DATA);
                } else {
                    mRepository.getUserDataFromFireBase(new APIManager() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            for(DataSnapshot item : dataSnapshot.getChildren()) {
                                User data = item.getValue(User.class);
                                if(data.getQr_code().equals(user_qrCode)) {
                                    userModel.setEventId(User.SHOW_DATA);
                                    result.postValue(data);
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onFailure(DatabaseError databaseError) {
                            userModel.setEventId(User.NO_DATA);
                            result.postValue(null);
                        }
                    });
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Boolean checkDuplicateFriend(Bundle bundle) {
        try {
            JSONObject jsonObject = new JSONObject(getResult(bundle));
            if(jsonObject.getString("appName").equals(APP_NAME)) {
                final Friend friend = new Friend(jsonObject.getString("username"), jsonObject.getString("key"));
                for(Friend data : Session.friendList) {
                    if(friend.getUsername().equals(data.getUsername())) {
                        return true;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getEventId() {
        return userModel.getEventId();
    }
}
