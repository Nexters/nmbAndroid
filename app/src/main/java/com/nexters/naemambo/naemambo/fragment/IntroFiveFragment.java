package com.nexters.naemambo.naemambo.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nexters.naemambo.naemambo.LoginActivity;
import com.nexters.naemambo.naemambo.R;
import com.nexters.naemambo.naemambo.util.BaseFragment;
import com.nexters.naemambo.naemambo.util.Const;
import com.nexters.naemambo.naemambo.util.SPreference;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntroFiveFragment extends BaseFragment {


    private SPreference pref;

    public IntroFiveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_intro_last, container, false);
        pref = new SPreference(getContext());

        ImageView tutorial_intro = (ImageView) rootView.findViewById(R.id.img_tutorial);
        Button btn_tutorial_last = (Button) rootView.findViewById(R.id.btn_tutorial_last);

        Glide.with(getContext()).load(R.drawable.tutorial_05).into(tutorial_intro);
        btn_tutorial_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pref.getBool(Const.IS_TUTORIAL,false)){//튜토리얼 처음일때만
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    pref.put(Const.IS_TUTORIAL, false);
                    getActivity().finish();
                }else{
                    getActivity().finish();
                }
            }
        });
        return rootView;
    }

}
