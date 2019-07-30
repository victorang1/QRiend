package com.example.victor_pc.qriend.home;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.victor_pc.qriend.R;
import com.example.victor_pc.qriend.common.BaseActivity;
import com.example.victor_pc.qriend.databinding.ActivityHomeBinding;
import com.example.victor_pc.qriend.model.Friend;
import com.example.victor_pc.qriend.model.User;
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
        getViewModel().getListFriend().observe(this, new Observer<List<Friend>>() {
            @Override
            public void onChanged(@Nullable List<Friend> friends) {
                list.clear();
                list.addAll(friends);
                mAdapter.notifyDataSetChanged();
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
}
