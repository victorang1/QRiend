package com.example.victor_pc.qriend.home;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.victor_pc.qriend.common.APIManager;
import com.example.victor_pc.qriend.model.Friend;
import com.example.victor_pc.qriend.model.Session;
import com.example.victor_pc.qriend.repository.UserRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private UserRepository mRepository;
    private MutableLiveData<List<Friend>> mFriendList;

    public HomeViewModel() {
        this.mRepository = UserRepository.getInstance();
    }

    public MutableLiveData<List<Friend>> getListFriend() {
        if(mFriendList == null) {
            mFriendList = new MutableLiveData<>();
        }
        mFriendList = loadListFriend();
        return mFriendList;
    }

    public MutableLiveData<List<Friend>> loadListFriend() {
        final MutableLiveData<List<Friend>> result = new MutableLiveData<>();
        mRepository.getListFriend(new APIManager() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                List<Friend> list = new ArrayList<>();
                for(DataSnapshot item : dataSnapshot.getChildren()) {
                    Friend friend = item.getValue(Friend.class);
                    list.add(friend);
                }
                Session.friendList = list;
                result.postValue(list);
            }

            @Override
            public void onFailure(DatabaseError databaseError) {
                result.postValue(null);
            }
        });
        return result;
    }
}
