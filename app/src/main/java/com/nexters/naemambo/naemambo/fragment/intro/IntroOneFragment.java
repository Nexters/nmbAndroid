package com.nexters.naemambo.naemambo.fragment.intro;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nexters.naemambo.naemambo.R;


public class IntroOneFragment extends Fragment {


    public IntroOneFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_intro_one, container, false);

        ImageView tutorial_intro_01 = (ImageView) rootView.findViewById(R.id.img_tutorial);
//        TextView btn_tutorial_next = (TextView) rootView.findViewById(R.id.btn_tutorial_next);

        Glide.with(getContext()).load(R.drawable.tutorial_01).into(tutorial_intro_01);

//        viewPager = (ViewPager) getActivity().findViewById(R.id.tutorial_pager);
//        btn_tutorial_next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1,true);
//            }
//        });
        return rootView;
    }

}
