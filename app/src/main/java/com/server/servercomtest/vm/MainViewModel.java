package com.server.servercomtest.vm;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.BindingAdapter;

import com.server.servercomtest.databinding.ActivityMainBinding;

public class MainViewModel extends ViewModel {
    Context con;
    ActivityMainBinding binding;
    public MainViewModel(Context ctx ,ActivityMainBinding b){
        con=ctx;
        binding=b;

    }



}
