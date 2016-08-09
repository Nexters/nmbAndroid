package com.nexters.naemambo.naemambo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.nexters.naemambo.naemambo.adapter.MyPagerAdapter;
import com.nexters.naemambo.naemambo.fragment.MySendFragment;
import com.nexters.naemambo.naemambo.fragment.ReceiveFragment;
import com.nexters.naemambo.naemambo.util.BaseActivity;

public class MyboxActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        setActionBarConfig("My Box",0);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ReceiveFragment(), "받은 메세지");
        adapter.addFragment(new MySendFragment(), "내가 쓴 메세지");
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
