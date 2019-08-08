package com.server.servercomtest.vm;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.net.Uri;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.server.servercomtest.databinding.Tab1ActivityBinding;

public class Tab1ViewModel extends ViewModel {
    Context con;
    Tab1ActivityBinding binding;
    public Tab1ViewModel(Context ctx ,Tab1ActivityBinding t){
        this.con=ctx;
        this.binding=t;

    }
    public void setd(Uri uri){
        Glide.with(con).load(uri).apply(RequestOptions.circleCropTransform()).into(binding.ivMainImage);
    }
}
