package com.server.servercomtest.Activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.server.servercomtest.R;
import com.server.servercomtest.databinding.ActivityMainBinding;
import com.server.servercomtest.vm.MainViewModel;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        component();
    }

    public void component() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMain(new MainViewModel(this, binding));
        binding.toolbar.setTitle("");
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
                return true;
        }


    return super.onOptionsItemSelected(item);
    }
}
