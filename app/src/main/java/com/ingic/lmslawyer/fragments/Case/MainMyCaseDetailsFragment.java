package com.ingic.lmslawyer.fragments.Case;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.constants.AppConstant;
import com.ingic.lmslawyer.entities.my_cases_entities.PendingOrActiveCaseDetails;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.helpers.Util;
import com.ingic.lmslawyer.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainMyCaseDetailsFragment extends BaseFragment {


    @BindView(R.id.flDetail)
    FrameLayout flDetail;
    Unbinder unbinder;
    @BindView(R.id.tvDesc)
    TextView tvDesc;
    @BindView(R.id.tvMsg)
    TextView tvMsg;
    @BindView(R.id.tvLib)
    TextView tvLib;
    @BindView(R.id.tvCal)
    TextView tvCal;
    @BindView(R.id.imgDesc)
    ImageView imgDesc;
    @BindView(R.id.imgMsg)
    ImageView imgMsg;
    @BindView(R.id.imgLib)
    ImageView imgLib;
    @BindView(R.id.imgCal)
    ImageView imgCal;

    private boolean mIsCaseDesc ,mIsWaitingAcknowledge;
    private PendingOrActiveCaseDetails mActiveCaseDetails;

    private CalenderFragment calenderFragment;

    public MainMyCaseDetailsFragment() {
        // Required empty public constructor
    }

    public static MainMyCaseDetailsFragment newInstance(boolean isCaseDesc, boolean waitingForAcknowledgement) {

        try {
            Bundle args = new Bundle();
            args.putBoolean(AppConstant.IS_CASE_DESC, isCaseDesc);
            args.putBoolean(AppConstant.IS_WAITING_ACKNOWLEDGE, waitingForAcknowledgement);
            MainMyCaseDetailsFragment fragment = new MainMyCaseDetailsFragment();
            fragment.setArguments(args);
            return fragment;
        }
        catch (Exception e){
            e.printStackTrace();
            return new MainMyCaseDetailsFragment();
        }


    }

   /* public static MainMyCaseDetailsFragment newInstance(boolean isCaseDesc, PendingOrActiveCaseDetails activeCaseDetails) {
        try {
            Bundle args = new Bundle();
            args.putBoolean(AppConstant.IS_CASE_DESC, isCaseDesc);
            //args.putSerializable(AppConstant.ACTIVE_CASES, activeCaseDetails);
            MainMyCaseDetailsFragment fragment = new MainMyCaseDetailsFragment();
            fragment.setArguments(args);
            return fragment;
        } catch (Exception e) {
            e.printStackTrace();
            return new MainMyCaseDetailsFragment();
        }
    }*/

    public void setActiveCaseDetail(PendingOrActiveCaseDetails activeCaseDetails){
        mActiveCaseDetails = activeCaseDetails;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.showWhiteBackButton();
        titleBar.setSubHeading(getResources().getString(R.string.my_case)); /*todo Add Search icon */
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_my_case_details, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (getArguments() != null) {
            mIsCaseDesc = getArguments().getBoolean(AppConstant.IS_CASE_DESC);
            mIsWaitingAcknowledge = getArguments().getBoolean(AppConstant.IS_WAITING_ACKNOWLEDGE);
            //mActiveCaseDetails = (PendingOrActiveCaseDetails) getArguments().getSerializable(AppConstant.ACTIVE_CASES);
        }

        CaseDescriptionFragment caseDescriptionFragment = CaseDescriptionFragment.newInstance();
        caseDescriptionFragment.setActiveCaseDetail(mActiveCaseDetails, mIsWaitingAcknowledge);

        loadFragment(caseDescriptionFragment, CaseDescriptionFragment.class.getSimpleName());

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

/*
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getDockActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flDetail, fragment);
        transaction.addToBackStack(null);
        transaction.commitNowAllowingStateLoss();
    }*/

    private void loadFragment(BaseFragment fragment, String tag) {

      getChildFragmentManager()
                .beginTransaction()
                .replace(getFrameLayoutId(), fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    @Override
    public void fragmentResume() {
        super.fragmentResume();

        if(calenderFragment != null) {
            if (calenderFragment.isVisible()) {
                calenderFragment.fragmentResume();
            }
        }

    }

    private int getFrameLayoutId() {
        return R.id.flDetail;
    }


    @OnClick({R.id.imgDesc, R.id.imgMsg, R.id.imgLib, R.id.imgCal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgDesc:
                if (Util.doubleClickCheck()) {

                    setVisibilitySelected(View.VISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);
                    setBackgroundViewGone(View.INVISIBLE, View.VISIBLE, View.VISIBLE, View.VISIBLE);

                    CaseDescriptionFragment caseDescriptionFragment = CaseDescriptionFragment.newInstance();
                    caseDescriptionFragment.setActiveCaseDetail(mActiveCaseDetails, mIsWaitingAcknowledge);

                    //CaseDescriptionFragment fragment = CaseDescriptionFragment.newInstance(mActiveCaseDetails);
                    if (!caseDescriptionFragment.isVisible()) {
//                    getDockActivity().getSupportFragmentManager().popBackStack();
                  /*  Bundle args = new Bundle();
                    args.putSerializable(AppConstant.DESCRIPTION, mActiveCaseDetails);
                    fragment.setArguments(args);*/

                        loadFragment(caseDescriptionFragment, CaseDescriptionFragment.class.getSimpleName());
                    }
                }
                break;
            case R.id.imgMsg:
                if (Util.doubleClickCheck()) {

                    setVisibilitySelected(View.INVISIBLE, View.VISIBLE, View.INVISIBLE, View.INVISIBLE);
                    setBackgroundViewGone(View.VISIBLE, View.INVISIBLE, View.VISIBLE, View.VISIBLE);
                    ChatMessagesFragment fragment2 = ChatMessagesFragment.newInstance(true);
                    if (!fragment2.isVisible()) {
//                    getDockActivity().getSupportFragmentManager().popBackStack();
                        loadFragment(fragment2, ChatMessagesFragment.class.getSimpleName());
                    }
                }
                break;
            case R.id.imgLib:
                if (Util.doubleClickCheck()) {

                    setVisibilitySelected(View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.INVISIBLE);
                    setBackgroundViewGone(View.VISIBLE, View.VISIBLE, View.INVISIBLE, View.VISIBLE);
                    LibraryFragment fragment3 = LibraryFragment.newInstance(true, null, String.valueOf(mActiveCaseDetails.getId()));
                    if (!fragment3.isVisible()) {
//                    getDockActivity().getSupportFragmentManager().popBackStack();
                        loadFragment(fragment3, LibraryFragment.class.getSimpleName());
                    }
                }
                break;
            case R.id.imgCal:
                if (Util.doubleClickCheck()) {

                    setVisibilitySelected(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.VISIBLE);
                    setBackgroundViewGone(View.VISIBLE, View.VISIBLE, View.VISIBLE, View.INVISIBLE);

                    calenderFragment = CalenderFragment.newInstance(true, String.valueOf(mActiveCaseDetails.getId()));
                    if (!calenderFragment.isVisible()) {
//                    getDockActivity().getSupportFragmentManager().popBackStack();
                        loadFragment(calenderFragment, CalenderFragment.class.getSimpleName());
                    }
                }
                break;
        }
    }

    private void setVisibilitySelected(int desc, int msg, int lib, int cal) {
        tvDesc.setVisibility(desc);
        tvMsg.setVisibility(msg);
        tvLib.setVisibility(lib);
        tvCal.setVisibility(cal);
    }

    private void setBackgroundViewGone(int desc, int msg, int lib, int cal) {
        imgDesc.setVisibility(desc);
        imgMsg.setVisibility(msg);
        imgLib.setVisibility(lib);
        imgCal.setVisibility(cal);
    }

    public boolean ismIsCaseDesc() {
        return mIsCaseDesc;
    }

    public void setmIsCaseDesc(boolean mIsCaseDesc) {
        this.mIsCaseDesc = mIsCaseDesc;
    }


}
