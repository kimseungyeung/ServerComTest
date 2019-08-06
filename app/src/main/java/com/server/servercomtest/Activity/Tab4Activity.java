package com.server.servercomtest.Activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.server.servercomtest.R;
import com.server.servercomtest.databinding.Tab1ActivityBinding;
import com.server.servercomtest.databinding.Tab4ActivityBinding;

public class Tab4Activity extends AppCompatActivity {
    Tab4ActivityBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.tab4_activity);

    }
}
