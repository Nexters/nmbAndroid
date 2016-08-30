package com.nexters.naemambo.naemambo.fragment.intro;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nexters.naemambo.naemambo.R;
import com.nexters.naemambo.naemambo.util.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntroFourFragment extends BaseFragment {


    public IntroFourFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_intro_one, container, false);

        ImageView tutorial_intro = (ImageView) rootView.findViewById(R.id.img_tutorial);

        Glide.with(getContext()).load(R.drawable.tutorial_04).into(tutorial_intro);

        return rootView;
    }

}
