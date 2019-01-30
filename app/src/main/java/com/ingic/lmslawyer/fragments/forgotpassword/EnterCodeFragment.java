package com.ingic.lmslawyer.fragments.forgotpassword;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.constants.AppConstant;
import com.ingic.lmslawyer.constants.WebServiceConstant;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.helpers.UIHelper;
import com.ingic.lmslawyer.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnterCodeFragment extends BaseFragment {


    @BindView(R.id.et_enterPin)
    PinEntryEditText etEnterPin;
    Unbinder unbinder;
    private String email = "";

    public EnterCodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_enter_code, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (getArguments() != null) {
            email = getArguments().getString(AppConstant.EMAIL);
        }
        return view;
    }

    public static EnterCodeFragment newInstance(String email) {

        Bundle args = new Bundle();
        args.putString(AppConstant.EMAIL, email);
        EnterCodeFragment fragment = new EnterCodeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showWhiteBackButton();
        titleBar.setBackgroundColor(getResources().getColor(R.color.transparent));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnSubmit, R.id.tvResentCode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                if (etEnterPin.getText().length() < 4) {
                    UIHelper.showShortToastInCenter(getDockActivity(), "Please enter pin");
                } else
                    serviceHelper.enqueueCall(webService.codeVerification(email, etEnterPin.getText().toString()),
                            WebServiceConstant.codeVerification);
                break;
            case R.id.tvResentCode:
                serviceHelper.enqueueCall(webService.forgotPassword(email),
                        WebServiceConstant.forgotPassword);
                break;
        }
    }


    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        switch (Tag) {
            case WebServiceConstant.codeVerification:
                getDockActivity().replaceDockableFragment(NewPasswordFragment.newInstance(email));
                break;
            case WebServiceConstant.forgotPassword:
                UIHelper.showShortToastInCenter(getDockActivity(), message);
                break;
            default:
                break;
        }
    }
}
