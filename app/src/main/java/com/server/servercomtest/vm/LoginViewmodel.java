package com.server.servercomtest.vm;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.view.menu.MenuView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.server.servercomtest.Data.UserData;
import com.server.servercomtest.R;
import com.server.servercomtest.databinding.AcountDialogBinding;
import com.server.servercomtest.databinding.LoginActivityBinding;


public class LoginViewmodel  extends ViewModel {
    String login="";
    Context con;
    LoginActivityBinding binding;
    //CreateAcount ca;
    public LoginViewmodel(Context ctx, LoginActivityBinding b){
        con=ctx;
        binding=b;
        //ca=new CreateAcount();
    }

 public void onclick(View v){
    switch (v.getId()){
        case R.id.btn_create:
            acountdialog(con);
            break;
        case R.id.btn_login:
            Toast.makeText(con,binding.edtPassword.getText(),Toast.LENGTH_LONG).show();
            break;
    }
 }

 public void acountdialog(final Context ctx){
     android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(ctx);
     LayoutInflater inflater = ((Activity)ctx).getLayoutInflater();
     View view = inflater.inflate(R.layout.acount_dialog, null);
     final AcountDialogBinding acountbinding= DataBindingUtil.inflate(LayoutInflater.from(ctx),R.layout.acount_dialog,null,false);

     dialogBuilder.setView(acountbinding.getRoot());

     final android.support.v7.app.AlertDialog alertDialog = dialogBuilder.create();
     alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
     alertDialog.setCancelable(false);
     alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    acountbinding.btnAcountCreate.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

            String id=acountbinding.edtCreateId.getText().toString().trim();
             String password = acountbinding.edtCreatePassword.getText().toString().trim();
             String realname = acountbinding.edtCreateRealname.getText().toString().trim();
             String email = acountbinding.edtCreateEmail.getText().toString().trim();
             String phonenum = acountbinding.edtCreatePhonenum.getText().toString().trim();

             if (id.equals("")) {

                 return;
             } if (password.equals("")) {
                 Toast.makeText(ctx,ctx.getString(R.string.insert_password),Toast.LENGTH_LONG).show();
                 return;
             }if (password.equals("")) {
                 Toast.makeText(ctx,ctx.getString(R.string.insert_password),Toast.LENGTH_LONG).show();
                 return;
             }if (realname.equals("")) {
                 Toast.makeText(ctx,ctx.getString(R.string.insert_password),Toast.LENGTH_LONG).show();
                 return;
             } if (email.equals("")) {
                 Toast.makeText(ctx,ctx.getString(R.string.insert_password),Toast.LENGTH_LONG).show();
                 return;
             }
             if (phonenum.equals("")) {
                 Toast.makeText(ctx,ctx.getString(R.string.insert_password),Toast.LENGTH_LONG).show();
                 return;
             }
             if (password.length() < 6) {
                 Toast.makeText(ctx,ctx.getString(R.string.insert_password_length),Toast.LENGTH_LONG).show();
                 return;
             }
            // create_acount(email, password, nickname, phonenum, alertDialog);
             UserData ud = new UserData(id,password,realname,email,phonenum);
            // ca.signup(con,ud);
             alertDialog.dismiss();

         }
     });
     acountbinding.btnCreateCancel.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             alertDialog.cancel();
         }
     });
     alertDialog.show();
 }
}
