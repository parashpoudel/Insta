package com.example.insta.Insta;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.insta.R;
import com.google.android.material.tabs.TabLayout;

import adapter.ViewPagerAdapter;
import fragments.AccountFragment;
import fragments.FeedsFragment;
import fragments.UploadFragment;
import url.Global;


public class MainActivity extends AppCompatActivity {
    private ViewPager hpviewPager;
    private TabLayout hptabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);

        hpviewPager = findViewById(R.id.hpviewPager);
        hptabLayout = findViewById(R.id.hptabId);

        hptabLayout.setSelectedTabIndicatorColor(Color.parseColor( "#050404"));
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new FeedsFragment(),"");
        viewPagerAdapter.addFragment(new UploadFragment(),"");
        viewPagerAdapter.addFragment(new AccountFragment(),"");

        hpviewPager.setAdapter(viewPagerAdapter);
        hptabLayout.setupWithViewPager(hpviewPager);
        hptabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp);
        hptabLayout.getTabAt(1).setIcon(R.drawable.ic_camera_alt_black_24dp);
        hptabLayout.getTabAt(2).setIcon(R.drawable.ic_account_circle_black_24dp);

        Toast.makeText(this, Global.username,Toast.LENGTH_LONG).show();
        Toast.makeText(this,Global.userImage,Toast.LENGTH_LONG).show();



}}
