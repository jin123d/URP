package com.jin123d.urp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jin123d.app.BaseActivity;
import com.jin123d.fragment.CjBjgFragment;
import com.jin123d.fragment.CjJgFragment;


public class CjActivity extends BaseActivity {

    private CjJgFragment jgFragment;
    private CjBjgFragment bjgFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cj);
    }

    @Override
    protected void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new MyPagerAdapter(getFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initData() {

    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        private MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private final String[] titles = {"及格成绩", "不及格成绩"};

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (jgFragment == null) {
                        jgFragment = new CjJgFragment();
                    }
                    return jgFragment;

                case 1:
                    if (bjgFragment == null) {
                        bjgFragment = new CjBjgFragment();
                    }
                    return bjgFragment;
                default:
                    return null;
            }
        }
    }

}
