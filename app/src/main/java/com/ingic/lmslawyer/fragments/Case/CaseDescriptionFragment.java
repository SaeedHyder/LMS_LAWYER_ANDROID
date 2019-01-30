package com.ingic.lmslawyer.fragments.Case;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.constants.WebServiceConstant;
import com.ingic.lmslawyer.entities.event_entities.EventMember;
import com.ingic.lmslawyer.entities.event_entities.MemberDetail;
import com.ingic.lmslawyer.entities.my_cases_entities.PendingOrActiveCaseDetails;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.helpers.DateHelper;
import com.ingic.lmslawyer.helpers.TextViewHelper;
import com.ingic.lmslawyer.helpers.UIHelper;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;
import com.ingic.lmslawyer.ui.adapters.myadapters.AddMoreMembersAdapter;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CaseDescriptionFragment extends BaseFragment {


    @BindView(R.id.tvLawyerName)
    TextView tvLawyerName;
    @BindView(R.id.tvDateTime)
    TextView tvDateTime;
    @BindView(R.id.tvSubject)
    TextView tvSubject;
    @BindView(R.id.tvCaseDesc)
    TextView tvCaseDesc;
    @BindView(R.id.rvCaseMembers)
    RecyclerView rvCaseMembers;
    @BindView(R.id.btnPayment)
    Button btnPayment;
    @BindView(R.id.btnAcknowledge)
    Button btnAcknowledge;

    Unbinder unbinder;
    private RecyclerViewListAdapter adapter;
    private ArrayList list;
    private PendingOrActiveCaseDetails mActiveCaseDetails;
    boolean mIsWaitingAcknowledge;


    public static CaseDescriptionFragment newInstance() {
        return new CaseDescriptionFragment();
    }

    public void setActiveCaseDetail(PendingOrActiveCaseDetails activeCaseDetails, boolean isWaitingAcknowledge) {
        mActiveCaseDetails = activeCaseDetails;
        mIsWaitingAcknowledge = isWaitingAcknowledge;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_case_description, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (mIsWaitingAcknowledge) {
            btnPayment.setVisibility(View.GONE);
            btnAcknowledge.setVisibility(View.VISIBLE);
        } else {
            btnAcknowledge.setVisibility(View.GONE);
            btnPayment.setVisibility(View.VISIBLE);
        }
        /*if (getArguments() != null) {
            mActiveCaseDetails = (PendingOrActiveCaseDetails) getArguments().getSerializable(AppConstant.ACTIVE_CASES);
        }*/

//        setData();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        setData();
    }

    private void setData() {
        if (CaseDescriptionFragment.this.isDetached() || CaseDescriptionFragment.this.isRemoving()
                || !CaseDescriptionFragment.this.isVisible() || mActiveCaseDetails == null)
            return;
        TextViewHelper.setText(tvLawyerName, mActiveCaseDetails.getUser().getFullName());
        TextViewHelper.setText(tvSubject, mActiveCaseDetails.getSubject());
        TextViewHelper.setText(tvCaseDesc, mActiveCaseDetails.getDetail());

        Date mDate = DateHelper.stringToDate(mActiveCaseDetails.getCreatedAt(), DateHelper.DATE_TIME_FORMAT);
        TextViewHelper.setText(tvDateTime, DateHelper.getFormattedTime(mDate)
                + " | " + DateHelper.getFormattedDate(mDate, DateHelper.dateFormat));
    }

    private void initAdapter() {
        if (CaseDescriptionFragment.this.isDetached() || CaseDescriptionFragment.this.isRemoving()
                || !CaseDescriptionFragment.this.isVisible() || mActiveCaseDetails == null)
            return;
//        if (mActiveCaseDetails == null) return;

        adapter = new AddMoreMembersAdapter(getDockActivity());
        rvCaseMembers.setLayoutManager(new LinearLayoutManager(getDockActivity(),
                LinearLayoutManager.VERTICAL, false));
        list = new ArrayList();
        list.add(new EventMember(new MemberDetail(mActiveCaseDetails.getUser().getFullName())));
        list.add(new EventMember(new MemberDetail(mActiveCaseDetails.getLawyer().getFullName())));
        rvCaseMembers.setAdapter(adapter);
        adapter.addAll(list);
    }


    @OnClick({R.id.tvAddMore, R.id.btnPayment, R.id.btnAcknowledge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvAddMore:
                implementedInBeta();
                break;
            case R.id.btnPayment:
                implementedInBeta();

                break;
            case R.id.btnAcknowledge:
                openDialog();
                break;
        }
    }

    private void openDialog() {
        final String[] mList = {getResources().getString(R.string.acknowledge), getResources().getString(R.string.reject)};
        final ActionSheetDialog dialog = new ActionSheetDialog(getDockActivity(), mList, null);
        dialog.title(getResources().getString(R.string.case_mark_complete))
                .titleTextSize_SP(14.5f)
                .cancelText(getResources().getString(R.string.cancel))
                .cancelText(getResources().getColor(R.color.red))
                .show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                String mText = "";
                if (position == 0) {
                    mText = "yes";
                } else
                    mText = "no";

                callService(mText);
                dialog.dismiss();
            }
        });
    }

    private void callService(String mText) {
        serviceHelper.enqueueCall(webService.acknowledgeCase(prefHelper.getUser().getToken(),
                mActiveCaseDetails.getId(), mText),
                WebServiceConstant.acknowledgeCase);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        switch (Tag) {
            case WebServiceConstant.acknowledgeCase:
                UIHelper.showShortToastInCenter(getDockActivity(), message);
                getDockActivity().popFragment();
                getDockActivity().popFragment();
                break;
            default:
                break;
        }
    }
}
