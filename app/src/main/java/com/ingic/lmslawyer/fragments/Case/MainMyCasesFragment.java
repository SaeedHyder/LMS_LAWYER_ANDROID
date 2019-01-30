package com.ingic.lmslawyer.fragments.Case;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.constants.AppConstant;
import com.ingic.lmslawyer.entities.my_cases_entities.PendingOrActiveCaseDetails;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainMyCasesFragment extends BaseFragment {


    @BindView(R.id.pager)
    ViewPager pager;
    Unbinder unbinder;
    @BindView(R.id.tabs)
    TabLayout tabs;

    public MainMyCasesFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_my_cases, container, false);
        unbinder = ButterKnife.bind(this, view);
        pager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        tabs.setupWithViewPager(pager);
        return view;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.showWhiteBackButton();
        titleBar.setSubHeading(getResources().getString(R.string.my_case)); /*todo Add Search icon */
    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return (position == 0) ? getResources().getString(R.string.tab1) : getResources().getString(R.string.tab2);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            return (position == 0) ? MyCasesListFragment.newInstance(true) : MyCasesListFragment.newInstance(false);
        }
    }
}