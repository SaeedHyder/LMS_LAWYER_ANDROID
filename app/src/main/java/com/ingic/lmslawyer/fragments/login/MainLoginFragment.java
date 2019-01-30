package com.ingic.lmslawyer.fragments.login;


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
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.singleton.BusProvider;
import com.ingic.lmslawyer.ui.views.TitleBar;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainLoginFragment extends BaseFragment {


    @BindView(R.id.pager)
    ViewPager pager;
    Unbinder unbinder;
    @BindView(R.id.tabs)
    TabLayout tabs;

    public MainLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        pager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        tabs.setupWithViewPager(pager);
        return view;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Subscribe
    public void activitySelected(Integer itemSelected) {
//        UIHelper.showShortToastInCenter(getDockActivity(),"main login called");
        pager.setCurrentItem(itemSelected);
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getUIBusInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        // Always unregister when an object no longer should be on the bus.
        BusProvider.getUIBusInstance().unregister(this);
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return (position == 0) ? getResources().getString(R.string.sign_in) : getResources().getString(R.string.sign_up);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            return (position == 0) ? new LMSLoginFragment() : new LMSSignUpFragment();
        }
    }
}
