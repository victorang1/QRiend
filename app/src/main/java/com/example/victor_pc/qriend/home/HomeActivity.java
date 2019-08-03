package com.example.victor_pc.qriend.home;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.victor_pc.qriend.R;
import com.example.victor_pc.qriend.common.BaseActivity;
import com.example.victor_pc.qriend.databinding.ActivityHomeBinding;
import com.example.victor_pc.qriend.model.Friend;
import com.example.victor_pc.qriend.scanqr.ScanQRActivity;
import com.example.victor_pc.qriend.showqr.ShowQRActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity<ActivityHomeBinding, HomeViewModel> implements View.OnClickListener {

    private HomeAdapter mAdapter;
    private List<Friend> list = new ArrayList<>();

    public HomeActivity() {
        super(HomeViewModel.class, R.layout.activity_home);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListener();
        showLoading();
        getViewModel().getListFriend().observe(this, new Observer<List<Friend>>() {
            @Override
            public void onChanged(@Nullable List<Friend> friends) {
                if(!friends.isEmpty()) {
                    hideDefaultListFriend();
                    list.clear();
                    list.addAll(friends);
                    mAdapter.notifyDataSetChanged();
                } else {
                    showDefaultListFriend();
                }
            }
        });
        initAdapter();
    }

    private void initAdapter() {
        mAdapter = new HomeAdapter(this, list);
        getBinding().recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getBinding().recyclerView.setHasFixedSize(true);
        getBinding().recyclerView.setAdapter(mAdapter);
    }

    private void initListener() {
        getBinding().llShowQR.setOnClickListener(this);
        getBinding().llScanQR.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == getBinding().llScanQR) {
            gotoActivity(ScanQRActivity.class, true);
        }
        else if(v == getBinding().llShowQR) {
            gotoActivity(ShowQRActivity.class, true);
        }
    }

    private void showLoading() {
        disableButton();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getBinding().rlLoading.setVisibility(View.GONE);
                enableButton();
            }
        }, 2000);
    }

    private void disableButton() {
        getBinding().llScanQR.setEnabled(false);
        getBinding().llShowQR.setEnabled(false);
    }

    private void enableButton() {
        getBinding().llScanQR.setEnabled(true);
        getBinding().llShowQR.setEnabled(true);
    }

    private void showDefaultListFriend() {
        getBinding().rlDefaultFriendList.setVisibility(View.VISIBLE);
        getBinding().rlFriendList.setVisibility(View.GONE);
    }

    private void hideDefaultListFriend() {
        getBinding().rlDefaultFriendList.setVisibility(View.GONE);
        getBinding().rlFriendList.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        showExitAlert("Are you sure you want to quit this application?", true);
    }
}
