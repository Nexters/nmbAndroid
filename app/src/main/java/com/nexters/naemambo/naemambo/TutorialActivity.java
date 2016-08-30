package com.nexters.naemambo.naemambo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.nexters.naemambo.naemambo.adapter.MyPagerAdapter;
import com.nexters.naemambo.naemambo.fragment.intro.IntroFiveFragment;
import com.nexters.naemambo.naemambo.fragment.intro.IntroFourFragment;
import com.nexters.naemambo.naemambo.fragment.intro.IntroOneFragment;
import com.nexters.naemambo.naemambo.fragment.intro.IntroThreeFragment;
import com.nexters.naemambo.naemambo.fragment.intro.IntroTwoFragment;
import com.nexters.naemambo.naemambo.util.BaseActivity;

/**
 * Created by Jeon on 2016-08-13.
 */
public class TutorialActivity extends BaseActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_main);

        viewPager = (ViewPager) findViewById(R.id.tutorial_pager);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new IntroOneFragment());
        adapter.addFragment(new IntroTwoFragment());
        adapter.addFragment(new IntroThreeFragment());
        adapter.addFragment(new IntroFourFragment());
        adapter.addFragment(new IntroFiveFragment());
        viewPager.setAdapter(adapter);

    }

/*    //onClick속성이 지정된 View를 클릭했을때 자동으로 호출되는 메소드
    public void mOnClick(View v){

        int position;

        switch( v.getId() ){
            case R.id.btn_previous://이전버튼 클릭

                position=pager.getCurrentItem();//현재 보여지는 아이템의 위치를 리턴

                //현재 위치(position)에서 -1 을 해서 이전 position으로 변경
                //이전 Item으로 현재의 아이템 변경 설정(가장 처음이면 더이상 이동하지 않음)
                //첫번째 파라미터: 설정할 현재 위치
                //두번째 파라미터: 변경할 때 부드럽게 이동하는가? false면 팍팍 바뀜
                pager.setCurrentItem(position-1,true);

                break;

            case R.id.btn_next://다음버튼 클릭

                position=pager.getCurrentItem();//현재 보여지는 아이템의 위치를 리턴

                //현재 위치(position)에서 +1 을 해서 다음 position으로 변경
                //다음 Item으로 현재의 아이템 변경 설정(가장 마지막이면 더이상 이동하지 않음)
                //첫번째 파라미터: 설정할 현재 위치
                //두번째 파라미터: 변경할 때 부드럽게 이동하는가? false면 팍팍 바뀜
                pager.setCurrentItem(position+1,true);

                break;
        }

    }*/
}
