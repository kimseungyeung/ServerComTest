package com.server.servercomtest.Activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.server.servercomtest.R;
import com.server.servercomtest.databinding.Tab1ActivityBinding;

public class Tab1Activity extends AppCompatActivity {
    Tab1ActivityBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.tab1_activity);
        Glide.with(this).load(R.drawable.ic_launcher_background).apply(RequestOptions.circleCropTransform()).into(binding.ivMainImage);
    }
}
