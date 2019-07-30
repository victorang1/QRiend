package com.example.victor_pc.qriend.login;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.victor_pc.qriend.common.APIManager;
import com.example.victor_pc.qriend.model.Session;
import com.example.victor_pc.qriend.model.User;
import com.example.victor_pc.qriend.repository.UserRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginViewModel extends ViewModel {

    private UserRepository mRepository;
    private MutableLiveData<Boolean> isSuccessful;

    public LoginViewModel() {
        mRepository = UserRepository.getInstance();
    }

    public MutableLiveData<Boolean> getUser(User user) {
        if(isSuccessful == null) {
            isSuccessful = new MutableLiveData<>();
        }
        isSuccessful = loadData(user);
        return isSuccessful;
    }

    public MutableLiveData<Boolean> loadData(final User user) {
        final MutableLiveData<Boolean> result = new MutableLiveData<>();
        mRepository.getUserLoginFromFirebase(user, new APIManager() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for(DataSnapshot item : dataSnapshot.getChildren()) {
                    User data = item.getValue(User.class);
                    if(user.getPassword().equals(data.getPassword())) {
                        Session.username = user.getUsername();
                        Session.user_key = item.getKey();
                        result.postValue(true);
                    } else {
                        result.postValue(false);
                    }
                    break;
                }
            }

            @Override
            public void onFailure(DatabaseError databaseError) {
                result.postValue(false);
            }
        });
        return result;
    }
}
