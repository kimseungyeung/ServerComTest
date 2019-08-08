package com.server.servercomtest.Activity;

import android.app.LocalActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.server.servercomtest.Constants;
import com.server.servercomtest.Data.UserData;
import com.server.servercomtest.Network.FireStoreNetwork;
import com.server.servercomtest.R;
import com.server.servercomtest.Repository.FireStoreRepository;
import com.server.servercomtest.databinding.ActivityMainBinding;
import com.server.servercomtest.databinding.Tab1ActivityBinding;
import com.server.servercomtest.vm.MainViewModel;
import com.server.servercomtest.vm.Tab1ViewModel;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActivityMainBinding binding;
    Context con;

    String email;
    String phone;
    String realname;
    String username;
    FirebaseFirestore db;
    String uid;
    FireStoreRepository fr;
    ImageView iv_profile;
    TabHost tabHost;
    Tab1Activity t1;
    LocalActivityManager mLocalActivityManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db=FirebaseFirestore.getInstance();
        uid=getIntent().getStringExtra("uid");
        con=this;
        fr=new FireStoreRepository();
       new LoadingTask(savedInstanceState).execute();
    }

    public void component(Bundle bundle, UserData ud) {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMain(new MainViewModel(this, binding));
        binding.toolbar.setTitle("");
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_icon);
       View v=binding.navLeftMenu.getHeaderView(0);
       iv_profile=(ImageView)v.findViewById(R.id.iv_profile_picture);
       //iv_profile.setBackground(getDrawable(R.drawable.menu_icon));
        binding.navLeftMenu.setNavigationItemSelectedListener(this);
        tabHost =binding.tabhost;
       final Intent i=   new Intent(this, new Tab3Activity().getClass());
        mLocalActivityManager = new LocalActivityManager(this, false);
       mLocalActivityManager.dispatchCreate(bundle);
        tabHost.setup(mLocalActivityManager);

        /** 새로운 탭을 추가하기 위한 TabSpect */
        TabHost.TabSpec TabSpec = tabHost.newTabSpec("tid1");
        TabHost.TabSpec Tab2Spec = tabHost.newTabSpec("tid2");
        TabHost.TabSpec Tab3Spec = tabHost.newTabSpec("tid3");
        TabHost.TabSpec Tab4Spec = tabHost.newTabSpec("tid4");
        TabHost.TabSpec Tab5Spec = tabHost.newTabSpec("tid5");

        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View bv1 = li.inflate(R.layout.tabbutton1, null);
        View bv2 = li.inflate(R.layout.tabbutton2, null);
        View bv3 = li.inflate(R.layout.tabbutton3, null);
        View bv4 = li.inflate(R.layout.tabbutton4, null);
        View bv5 = li.inflate(R.layout.tabbutton5, null);
        TabSpec.setIndicator(bv1);
        Intent ii= new Intent(getApplicationContext(),
                Tab3Activity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         t1= new Tab1Activity();
       Intent in1= new Intent(this,t1.getClass());
       in1.putExtra("data",ud);
        TabSpec.setContent(in1);
        Tab2Spec.setIndicator(bv2);
        Tab2Spec.setContent(new Intent(this, new Tab2Activity().getClass()));
        Tab3Spec.setIndicator(bv3);
        Tab3Spec.setContent(new Intent(this, new Tab3Activity().getClass()));
        Tab4Spec.setIndicator(bv4);
        Tab4Spec.setContent(new Intent(this, new Tab4Activity().getClass()));
        Tab5Spec.setIndicator(bv5);
        Tab5Spec.setContent(new Intent(this, new Tab5Activity().getClass()));


        /** 탭을 TabHost 에 추가한다 */
        tabHost.addTab(TabSpec);
        tabHost.addTab(Tab2Spec);
        tabHost.addTab(Tab3Spec);
        tabHost.addTab(Tab4Spec);
        tabHost.addTab(Tab5Spec);



        tabHost.getTabWidget().getChildTabViewAt(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(i,100);
            }
        });
        // 탭의 선택
        tabHost.getTabWidget().setCurrentTab(0);
        //tabHost.setOnTabChangedListener(dd);
      //  fr.ProfliePictureSet(con,ud.getEmail(),iv_profile);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_right:
                Toast.makeText(this,"오른쪽",Toast.LENGTH_LONG).show();
                return true;
            case android.R.id.home:
                Toast.makeText(this,"왼쪽",Toast.LENGTH_LONG).show();
                binding.drawerLeft.openDrawer(Gravity.LEFT);
                return true;
        }


    return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.menu_1:
                Toast.makeText(this,"메뉴1",Toast.LENGTH_LONG).show();
                break;
        }
        binding.drawerLeft.openDrawer(GravityCompat.START);
        return false;
    }


    public class LoadingTask extends AsyncTask<Integer, Integer, Boolean> {
        ProgressDialog asyncDialog = new ProgressDialog(con);
       Bundle bundle;

        boolean result = false;

        public LoadingTask(Bundle b) {
            this.bundle=b;
        }

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("로딩 중 입니다...");
            asyncDialog.setCancelable(false);
            asyncDialog.show();

            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Integer... integers) {
            DocumentReference docRef2 = db.collection("userlist").document(uid);
            docRef2.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    email = documentSnapshot.get("email").toString();
                    phone = documentSnapshot.get("phone").toString();
                    realname = documentSnapshot.get("realname").toString();
                    username = documentSnapshot.get("username").toString();
                    UserData ud=new UserData(email,"",realname,email,phone);
                    component(bundle,ud);
                }
            });

            return result;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
//           super.onPostExecute(aBoolean);

            asyncDialog.dismiss();



        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @android.support.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            //이미지를 하나 골랐을때
            if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
                //data에서 절대경로로 이미지를 가져옴
                Uri uri = data.getData();
                fr.pictureupload(email,"test.jpg",uri);
                Glide.with(this).load(uri).apply(RequestOptions.circleCropTransform()).into(iv_profile);
              Tab1Activity t1=  (Tab1Activity) mLocalActivityManager.getActivity("tid1");
              t1.getBinding().getTab1().settab1pimage(uri);
            }

    }
}
