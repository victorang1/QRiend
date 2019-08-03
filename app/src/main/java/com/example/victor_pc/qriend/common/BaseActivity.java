package com.example.victor_pc.qriend.common;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.victor_pc.qriend.R;

public abstract class BaseActivity<T extends ViewDataBinding, V extends ViewModel> extends AppCompatActivity {

    private V viewModel;
    private T binding;
    private Class<V> vm;
    int layout;

    public BaseActivity(Class<V> vm, int layout) {
        this.vm = vm;
        this.layout = layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(vm);
        binding = DataBindingUtil.setContentView(this, layout);
    }

    public V getViewModel() {
        return viewModel;
    }

    public T getBinding() {
        return binding;
    }

    protected void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    protected void showExitAlert(String msg, boolean negativeButton) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.app_name))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                });
        if(negativeButton) {
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    protected <TO> void showIntentMessage(String msg, final Class<TO> to, final Boolean isFinish) {
        showMessage(msg);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoActivity(to, isFinish);
            }
        }, 1000);
    }

    protected <TO> void gotoActivity(Class<TO> to, Boolean isFinish) {
        if(isFinish) {
            finish();
        }
        Intent intent = new Intent(this, to);
        startActivity(intent);
    }

    protected <TO> void gotoActivity(Class<TO> to, Boolean isFinish, String data) {
        if(isFinish) {
            finish();
        }
        Intent intent = new Intent(this, to);
        intent.putExtra("data", data);
        startActivity(intent);
    }
}
