package com.nexters.naemambo.naemambo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexters.naemambo.naemambo.adapter.MyPagerAdapter;
import com.nexters.naemambo.naemambo.fragment.MySendFragment;
import com.nexters.naemambo.naemambo.fragment.ReceiveFragment;
import com.nexters.naemambo.naemambo.util.BaseActivity;

import org.w3c.dom.Text;

public class MyboxActivity extends BaseActivity implements View.OnClickListener {
    ImageView btn_actionbar_back, btn_actionbar_write;
    private TextView action_bar_back_title;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ReceiveFragment(), "받은 메세지");
        adapter.addFragment(new MySendFragment(), "내가 쓴 메세지");
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        if (getSupportActionBar() != null) {
            actionBar = getSupportActionBar();
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setElevation(0);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.abs_write_layout);
            btn_actionbar_back = (ImageView) actionBar.getCustomView().findViewById(R.id.btn_actionbar_back);
            btn_actionbar_write = (ImageView) actionBar.getCustomView().findViewById(R.id.btn_actionbar_write);
            action_bar_back_title = (TextView) actionBar.getCustomView().findViewById(R.id.action_bar_write_title);

            action_bar_back_title.setText("My Box");
            btn_actionbar_back.setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_actionbar_back:
                finish();
                break;
            case R.id.btn_actionbar_write:
                break;
        }
    }
}
