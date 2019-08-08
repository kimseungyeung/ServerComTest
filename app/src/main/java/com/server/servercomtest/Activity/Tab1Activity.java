package com.server.servercomtest.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.server.servercomtest.Data.UserData;
import com.server.servercomtest.R;
import com.server.servercomtest.Repository.FireStoreRepository;
import com.server.servercomtest.databinding.Tab1ActivityBinding;
import com.server.servercomtest.vm.Tab1ViewModel;

public class Tab1Activity extends AppCompatActivity implements View.OnClickListener {
    Tab1ActivityBinding binding;
   FireStoreRepository fr;
    UserData ud;
    Tab1ViewModel tab1v;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ud=getIntent().getParcelableExtra("data");
        fr=new FireStoreRepository();
        binding= DataBindingUtil.setContentView(this, R.layout.tab1_activity);
        tab1v=new Tab1ViewModel(this,binding);
        binding.setTab1(tab1v);
        binding.tvTop.setText(ud.getUsername()+"님");
        binding.tvBottom.setText(ud.getRealname()+"님 어서오세요");
      //  fr.ProfliePictureSet(this,ud.getEmail(),binding.ivMainImage);
        binding.ivMainImage.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_main_image:
                loadImagefromGallery();
                break;
        }
    }

    public void loadImagefromGallery() {
        //Intent 생성
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT); //ACTION_PIC과 차이점?
        intent.setType("image/*"); //이미지만 보이게
        //Intent 시작 - 갤러리앱을 열어서 원하는 이미지를 선택할 수 있다.
//        Intent i = new Intent(
//                Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
       getParent().startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }
    public Tab1ActivityBinding getBinding(){
        return this.binding;
    }

}
