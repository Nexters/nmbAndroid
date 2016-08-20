package com.nexters.naemambo.naemambo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nexters.naemambo.naemambo.R;
import com.nexters.naemambo.naemambo.util.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntroTwoFragment extends BaseFragment {


    public IntroTwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_intro_two, container, false);

        ImageView tutorial_intro = (ImageView) rootView.findViewById(R.id.tutorial_intro);

        Glide.with(getContext()).load(R.drawable.tutorial_01).into(tutorial_intro);

        return rootView;
    }

}
