package com.example.victor_pc.qriend.common;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public interface APIManager {
    void onSuccess(DataSnapshot dataSnapshot);
    void onFailure(DatabaseError databaseError);
}
