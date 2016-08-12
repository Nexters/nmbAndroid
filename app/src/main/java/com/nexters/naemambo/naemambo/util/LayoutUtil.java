package com.nexters.naemambo.naemambo.util;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nexters.naemambo.naemambo.R;


/**
 * Created by jjgod on 2016-08-12.
 */
public class LayoutUtil {
    public static void applyFontedTab(Activity activity, ViewPager viewPager, TabLayout tabLayout) {
        for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
            TextView tv = (TextView) activity.getLayoutInflater().inflate(R.layout.view_item_tablayout, null);
            if (i == viewPager.getCurrentItem()) tv.setSelected(true);

            tv.setText(viewPager.getAdapter().getPageTitle(i));
            tabLayout.getTabAt(i).setCustomView(tv);
        }
    }
}
