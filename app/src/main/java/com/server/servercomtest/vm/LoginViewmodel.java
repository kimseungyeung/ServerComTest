package com.server.servercomtest.vm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.view.menu.MenuView;
import android.text.InputType;
import android.util.JsonWriter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.server.servercomtest.Data.UserData;
import com.server.servercomtest.Network.FireStoreNetwork;
import com.server.servercomtest.Network.ServerNetwork;
import com.server.servercomtest.R;
import com.server.servercomtest.databinding.AcountCreateDialogBinding;
import com.server.servercomtest.databinding.LoginActivityBinding;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginViewmodel  extends ViewModel {
    String login="";
    Context con;
    LoginActivityBinding binding;
    FireStoreNetwork fn;
    ServerNetwork sn;
    public LoginViewmodel(Context ctx, LoginActivityBinding b){
        con=ctx;
        binding=b;
        fn=new FireStoreNetwork(con);
        sn=new ServerNetwork();
    }

 public void onclick(View v){
    switch (v.getId()){
        case R.id.btn_create:
            acountdialog(con);
            break;
        case R.id.btn_login:
            Toast.makeText(con,binding.edtPassword.getText(),Toast.LENGTH_LONG).show();
            String e=binding.edtEmail.getText().toString().trim();
            String d=binding.edtPassword.getText().toString().trim();
            new LoginTask(e,d).execute();
            break;
    }
 }
    JSONObject users=null;
 public void acountdialog(final Context ctx){
     android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(ctx);
     LayoutInflater inflater = ((Activity)ctx).getLayoutInflater();
     View view = inflater.inflate(R.layout.acount_create_dialog, null);
     final AcountCreateDialogBinding acountbinding=DataBindingUtil.inflate(LayoutInflater.from(ctx),R.layout.acount_create_dialog,null,false);

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
             Map<String, Object> user = new HashMap<>();
             user.put("username", ud.getUsername()); // 아이디
             user.put("password", ud.getPassword()); // 암호
             user.put("realname", ud.getRealname()); // 실명
             user.put("email", ud.getEmail()); // 이메일
             user.put("mobile", ud.getMobile()); // 휴대폰번호

              users = new JSONObject();
             try {
                 for (Map.Entry<String, Object> entry : user.entrySet()) {
                     String key = entry.getKey();
                     Object value = entry.getValue();
                     users.put(key, value);
                 }
             }catch (Exception e){

             }

             new AccoutCreteTask(ud).execute();

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

    public class AccoutCreteTask extends AsyncTask<Integer, Integer, Boolean> {
        ProgressDialog asyncDialog = new ProgressDialog(con);
        String email = null;
        String password = null;
        boolean result = false;
        UserData userData;
        public AccoutCreteTask(UserData ud) {
            this.userData=ud;
        }

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("계정생성 중 입니다...");
            asyncDialog.setCancelable(false);
            asyncDialog.show();

            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Integer... integers) {

            try {
                String token = sn.gettoken("hidayz", "hidayz");

                //ca.signup(token,users);
              //  sn.signup(token,users);
                fn.signup(userData);
            }catch (Exception e){
                Log.e("e",e.getMessage().toString());
            //    Toast.makeText(con,e.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
            try {
            //    Thread.sleep(1000);
            } catch (Exception e) {
                Log.e("error",e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
//           super.onPostExecute(aBoolean);

            asyncDialog.dismiss();


//           asyncDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
    public class LoginTask extends AsyncTask<Integer, Integer, Boolean> {
        ProgressDialog asyncDialog = new ProgressDialog(con);
        String username = null;
        String password = null;
        boolean result = false;

        public LoginTask(String un,String pw) {
            this.username=un.trim();
            this.password=pw;
        }

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("계정생성 중 입니다...");
            asyncDialog.setCancelable(false);
            asyncDialog.show();

            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Integer... integers) {

            try {
                String token = sn.gettoken("hidayz", "hidayz");

                //ca.signup(token,users);
                //  sn.signup(token,users);
                fn.signin(username,password);
            }catch (Exception e){
                Log.e("e",e.getMessage().toString());
                //    Toast.makeText(con,e.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
            try {
                //    Thread.sleep(1000);
            } catch (Exception e) {
                Log.e("error",e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
//           super.onPostExecute(aBoolean);

            asyncDialog.dismiss();


//           asyncDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
