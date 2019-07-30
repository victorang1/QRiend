package com.example.victor_pc.qriend.repository;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.victor_pc.qriend.common.APIManager;
import com.example.victor_pc.qriend.model.Friend;
import com.example.victor_pc.qriend.model.Session;
import com.example.victor_pc.qriend.model.User;
import com.google.android.gms.dynamic.SupportFragmentWrapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class UserRepository {

    private static UserRepository instance;
    private DatabaseReference reference;

    public UserRepository() {
        reference = FirebaseDatabase.getInstance().getReference();
    }

    public static UserRepository getInstance() {
        if(instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public boolean insertUserToFirebase(User user) {
        try {
            reference.child("user").child(user.getUser_key()).setValue(user);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public boolean insertFriendToFirebase(Friend friend, Friend user) {
        try {
            reference.child("friends").child(Session.user_key).push().setValue(friend);
            reference.child("friends").child(friend.getKey()).push().setValue(user);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public MutableLiveData<Boolean> getUserFromFirebase(final APIManager listener) {
        final MutableLiveData<Boolean> result = new MutableLiveData<>();
        reference.child("user").orderByChild("username")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listener.onSuccess(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        listener.onFailure(databaseError);
                    }
                });
        return result;
    }

    public MutableLiveData<Boolean> getUserLoginFromFirebase(final User user, final APIManager listener) {
        final MutableLiveData<Boolean> result = new MutableLiveData<>();
        reference.child("user").orderByChild("username").equalTo(user.getUsername())
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailure(databaseError);
            }
        });
        return result;
    }

    public MutableLiveData<User> getUserDataFromFireBase(final APIManager listener) {
        final MutableLiveData<User> result = new MutableLiveData<>();
        reference.child("user").orderByChild("username")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailure(databaseError);
            }
        });
        return result;
    }

    public MutableLiveData<List<Friend>> getListFriend(final APIManager listener) {
        final MutableLiveData<List<Friend>> result = new MutableLiveData<>();
        reference.child("friends").child(Session.user_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailure(databaseError);
            }
        });
        return result;
    }

    public MutableLiveData<Boolean> loadDuplicateListFriend(final APIManager listener) {
        final MutableLiveData<Boolean> result = new MutableLiveData<>();
        reference.child("friends").child(Session.user_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailure(databaseError);
            }
        });
        return result;
    }
}
