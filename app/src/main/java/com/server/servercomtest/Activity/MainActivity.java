package com.server.servercomtest.Activity;

import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;

import com.server.servercomtest.R;
import com.server.servercomtest.databinding.ActivityMainBinding;
import com.server.servercomtest.vm.MainViewModel;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActivityMainBinding binding;
    Context con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        component(savedInstanceState);
    }

    public void component(Bundle bundle) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMain(new MainViewModel(this, binding));
        binding.toolbar.setTitle("");
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_icon);
       View v=binding.navLeftMenu.getHeaderView(0);
       ImageView iv=(ImageView)v.findViewById(R.id.iv_profile_picture);
       iv.setBackground(getDrawable(R.drawable.menu_icon));
        binding.navLeftMenu.setNavigationItemSelectedListener(this);
        TabHost tabHost =binding.tabhost;
       final Intent i=   new Intent(this, new Tab3Activity().getClass());
        LocalActivityManager mLocalActivityManager = new LocalActivityManager(this, false);
       mLocalActivityManager.dispatchCreate(bundle);
        tabHost.setup(mLocalActivityManager);
        con=this;
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

        TabSpec.setContent(new Intent(this, new Tab1Activity().getClass()));
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
}
