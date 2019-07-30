package com.example.victor_pc.qriend.home;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.victor_pc.qriend.R;
import com.example.victor_pc.qriend.databinding.HomeItemLayoutBinding;
import com.example.victor_pc.qriend.model.Friend;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private Context context;
    private List<Friend> listFriend;

    public HomeAdapter(Context context, List<Friend> listFriend) {
        this.context = context;
        this.listFriend = listFriend;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private HomeItemLayoutBinding itemBinding;

        public MyViewHolder(HomeItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        HomeItemLayoutBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.home_item_layout, viewGroup, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        Friend friend = listFriend.get(position);
        myViewHolder.itemBinding.setViewModel(friend);
    }

    @Override
    public int getItemCount() {
        return listFriend.size();
    }
}
