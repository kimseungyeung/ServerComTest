package com.server.servercomtest.Activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.FirebaseApp;
import com.server.servercomtest.R;
import com.server.servercomtest.databinding.LoginActivityBinding;
import com.server.servercomtest.vm.LoginViewmodel;

public class LoginActivity extends AppCompatActivity {
LoginActivityBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component();
    }
    public void component(){
        binding= DataBindingUtil.setContentView( this,R.layout.login_activity);
        binding.setLogin(new LoginViewmodel(this,binding));

    }


}
