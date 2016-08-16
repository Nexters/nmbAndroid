package com.nexters.naemambo.naemambo;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class CustomAdapter extends PagerAdapter {

    LayoutInflater inflater;

    public CustomAdapter(LayoutInflater inflater) {
        // TODO Auto-generated constructor stub


        this.inflater=inflater;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 5;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub

        View view=null;


        view= inflater.inflate(R.layout.activity_tutorial_adapter, null);


        ImageView img= (ImageView)view.findViewById(R.id.img_viewpager_childimage);


        //img.setImageResource(R.drawable.tutorial_01+position);
        //img.setImageResource(R.drawable.gametitle_01+position);
        //ViewPager에 만들어 낸 View 추가
        container.addView(view);

        //Image가 세팅된 View를 리턴
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub


        container.removeView((View)object);

    }

    @Override
    public boolean isViewFromObject(View v, Object obj) {
        // TODO Auto-generated method stub
        return v==obj;
    }

}