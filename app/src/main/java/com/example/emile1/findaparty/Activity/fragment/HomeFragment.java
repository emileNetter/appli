package com.example.emile1.findaparty.Activity.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.emile1.findaparty.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private FragmentTabHost host;
    private int limit;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        final ViewPager viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        limit = (myPagerAdapter.getCount() > 1 ? myPagerAdapter.getCount() - 1 : 1);

        //TabLayout definition
        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setAdapter(myPagerAdapter);
        viewPager.setOffscreenPageLimit(limit);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return v;
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 2;
        private String[] tabTitles= new String[] {getString(R.string.recent_tab),getString(R.string.archive_tab)};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return RecentFragment.newInstance();
                case 1: return ArchiveFragment.newInstance();
                default: return RecentFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
    }

}
