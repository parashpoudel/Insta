package com.example.insta.Insta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.insta.Insta.LoginForm.LoginFragment;
import com.example.insta.Insta.Register.RegisterFragment;
import com.example.insta.R;
import com.google.android.material.tabs.TabLayout;

import adapter.ViewPagerAdapter;

public class ViewPagerActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_view_pager);

        viewPager= findViewById(R.id.viewPager);
        tabLayout= findViewById(R.id.tabId);

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor( "#050404"));
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new LoginFragment(),"Login");
        viewPagerAdapter.addFragment(new RegisterFragment(),"Register");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_account_circle_black_24dp);


    }
}
