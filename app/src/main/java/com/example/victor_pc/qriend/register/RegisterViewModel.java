package com.example.victor_pc.qriend.register;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.victor_pc.qriend.common.APIManager;
import com.example.victor_pc.qriend.common.QRCodeGenerator;
import com.example.victor_pc.qriend.model.User;
import com.example.victor_pc.qriend.repository.UserRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterViewModel extends ViewModel {

    private UserRepository mRepository;
    private QRCodeGenerator qrCodeGenerator;
    private DatabaseReference databaseReference;
    private MutableLiveData<Boolean> isDuplicate;

    public RegisterViewModel() {
        mRepository = UserRepository.getInstance();
        qrCodeGenerator = new QRCodeGenerator();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public boolean insertUser(User user) {
        String userKey = databaseReference.child("user").push().getKey();
        user.setUser_key(userKey);
        user.setQr_code(qrCodeGenerator.dataToJSON(userKey, user));
        if(mRepository.insertUserToFirebase(user)) {
            return true;
        }
        return false;
    }

    public MutableLiveData<Boolean> checkDuplicateUser(final User user) {
        if(isDuplicate == null) {
            isDuplicate = new MutableLiveData<>();
        }
        isDuplicate = loadDuplicateUser(user);
        return isDuplicate;
    }

    private MutableLiveData<Boolean> loadDuplicateUser(final User user) {
        final MutableLiveData<Boolean> result = new MutableLiveData<>();
        mRepository.getUserFromFirebase(new APIManager() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Boolean bool = new Boolean(false);
                for(DataSnapshot item : dataSnapshot.getChildren()) {
                    User data = item.getValue(User.class);
                    if(data.getUsername().equals(user.getUsername())) {
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
        return result;
    }

    public boolean checkUsernameLength(User user) {
        if(user.getUsername().length() >= 12) {
            return false;
        }
        return true;
    }
}
