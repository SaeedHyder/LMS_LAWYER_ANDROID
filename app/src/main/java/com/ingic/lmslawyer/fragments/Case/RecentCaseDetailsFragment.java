package com.ingic.lmslawyer.fragments.Case;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.constants.AppConstant;
import com.ingic.lmslawyer.constants.WebServiceConstant;
import com.ingic.lmslawyer.entities.my_cases_entities.PendingOrActiveCaseDetails;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.helpers.DateHelper;
import com.ingic.lmslawyer.helpers.SimpleDividerItemDecoration;
import com.ingic.lmslawyer.helpers.TextViewHelper;
import com.ingic.lmslawyer.helpers.UIHelper;
import com.ingic.lmslawyer.helpers.Util;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;
import com.ingic.lmslawyer.ui.adapters.myadapters.RecentCasesPaymentAdapter;
import com.ingic.lmslawyer.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecentCaseDetailsFragment extends BaseFragment {


    @BindView(R.id.rlPayment)
    RelativeLayout rlPayment;
    @BindView(R.id.rlCaseBtn)
    RelativeLayout rlCaseBtn;
    @BindView(R.id.txtPayment)
    TextView txtPayment;
    @BindView(R.id.tvLawyerName)
    TextView tvLawyerName;
    @BindView(R.id.tvDateTime)
    TextView tvDateTime;
    @BindView(R.id.tvSubject)
    TextView tvSubject;
    @BindView(R.id.tvCaseDesc)
    TextView tvCaseDesc;
    @BindView(R.id.rvPayment)
    RecyclerView rvPayment;
    Unbinder unbinder;
    @BindView(R.id.btnAccept)
    Button btnAccept;
    @BindView(R.id.btnReject)
    Button btnReject;
    @BindView(R.id.btnChargePayment)
    Button btnChargePayment;
    @BindView(R.id.llheadings)
    LinearLayout llheadings;
    @BindView(R.id.rlheadingsText)
    RelativeLayout rlheadingsText;
    @BindView(R.id.seperator)
    View seperator;
    @BindView(R.id.rlTop)
    RelativeLayout rlTop;
    @BindView(R.id.seperator1)
    View seperator1;
    @BindView(R.id.tvTotalAmount)
    TextView tvTotalAmount;
    private RecyclerViewListAdapter adapter;
    private ArrayList list;
    private boolean isFromPayment;
    TitleBar titleBar;
    private boolean isPending;
    private int sum;

    private PendingOrActiveCaseDetails pending;

    public RecentCaseDetailsFragment() {
        // Required empty public constructor
    }

    public static RecentCaseDetailsFragment newInstance(boolean isPendingCase, PendingOrActiveCaseDetails pending) {

        Bundle args = new Bundle();
        args.putBoolean(AppConstant.IS_PENDING, isPendingCase);
        //args.putSerializable(AppConstant.ACTIVE_CASES, pending);

        RecentCaseDetailsFragment fragment = new RecentCaseDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setActiveCaseDetail(PendingOrActiveCaseDetails activeCaseDetails) {
        pending = activeCaseDetails;
    }

    private void setData() {
        if (pending == null) return;
        TextViewHelper.setText(tvLawyerName, pending.getUser().getFullName());
        TextViewHelper.setText(tvSubject, pending.getSubject());
        TextViewHelper.setText(tvCaseDesc, pending.getDetail());

        Date mDate = DateHelper.stringToDate(pending.getCreatedAt(), DateHelper.DATE_TIME_FORMAT);
        TextViewHelper.setText(tvDateTime, DateHelper.getFormattedTime(mDate)
                + " | " + DateHelper.getFormattedDate(mDate, DateHelper.dateFormat));

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recent_case_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (getArguments() != null) {
            isPending = getArguments().getBoolean(AppConstant.IS_PENDING);
        }
        if (isPending) {
            rlPayment.setVisibility(View.GONE);
            btnAccept.setVisibility(View.VISIBLE);
            btnReject.setVisibility(View.VISIBLE);
        } else {
            rlPayment.setVisibility(View.VISIBLE);
            btnAccept.setVisibility(View.GONE);
            btnReject.setVisibility(View.GONE);

        }
        //pending = (PendingOrActiveCaseDetails) getArguments().getSerializable(AppConstant.ACTIVE_CASES);
//        initAdapter();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            isFromPayment = getArguments().getBoolean(AppConstant.IS_FROM_PAYMENT);
        }
        if (isFromPayment == true) {
            btnChargePayment.setVisibility(View.VISIBLE);

            txtPayment.setText("Initial Payment");
        } else {
            btnChargePayment.setVisibility(View.GONE);
            txtPayment.setText("Payments");
        }
        setData();
        initAdapter();

    }

    public static RecentCaseDetailsFragment newInstance(boolean isFromPayment) {

        Bundle args = new Bundle();
        args.putBoolean(AppConstant.IS_FROM_PAYMENT, isFromPayment);
        RecentCaseDetailsFragment fragment = new RecentCaseDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        this.titleBar = titleBar;
        titleBar.showWhiteBackButton();
        if (isFromPayment == true)
            titleBar.setSubHeading(getResources().getString(R.string.make_payment));
        else
            titleBar.setSubHeading(getResources().getString(R.string.case_details)); /*todo Add Search icon */
    }

    private void initAdapter() {

        if (RecentCaseDetailsFragment.this.isDetached()
                || RecentCaseDetailsFragment.this.isRemoving() || !RecentCaseDetailsFragment.this.isVisible()
                || pending == null)
            return;

        adapter = new RecentCasesPaymentAdapter(getDockActivity());
        rvPayment.setLayoutManager(new LinearLayoutManager(getDockActivity(),
                LinearLayoutManager.VERTICAL, false));
        rvPayment.addItemDecoration(new SimpleDividerItemDecoration(getDockActivity()));
        rvPayment.setAdapter(adapter);
        if (pending.getPayments() != null && pending.getPayments().size() > 0) {
            adapter.addAll(pending.getPayments());
            for (int i = 0; i < pending.getPayments().size(); i++) {
                sum += pending.getPayments().get(i).getCharges();
            }
            TextViewHelper.setText(tvTotalAmount, "$" + sum);
        }
    }


    @OnClick(R.id.btnChargePayment)
    public void onViewClicked() {
        implementedInBeta();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnAccept, R.id.btnReject})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAccept:
                if (Util.doubleClickCheck())
                    serviceHelper.enqueueCall(webService.acceptOrRejectCase(prefHelper.getUser().getToken(),
                            pending.getId(), AppConstant.ACCEPT), WebServiceConstant.acceptCase);
                break;
            case R.id.btnReject:
                if (Util.doubleClickCheck())
                    serviceHelper.enqueueCall(webService.acceptOrRejectCase(prefHelper.getUser().getToken(),
                            pending.getId(), AppConstant.REJECT), WebServiceConstant.acceptCase);
                break;
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        switch (Tag) {
            case WebServiceConstant.acceptCase:
                UIHelper.showShortToastInCenter(getDockActivity(), message);
                getDockActivity().popFragment();
                break;
        }
    }
}
