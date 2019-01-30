package com.ingic.lmslawyer.fragments.forgotpassword;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingic.lmslawyer.R;
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
public class ForgotPasswordEmailFragment extends BaseFragment {


    @BindView(R.id.ietEmail)
    TextInputEditText ietEmail;
    Unbinder unbinder;

    public ForgotPasswordEmailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_password_email, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showWhiteBackButton();
//        titleBar.setBackgroundColor(getResources().getColor(R.color.transparent));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {
        if (isValidate()) {
            serviceHelper.enqueueCall(webService.forgotPassword(ietEmail.getText().toString()),
                    WebServiceConstant.forgotPassword);
        }

    }

    private boolean isValidate() {
        if (!checkEmpty(ietEmail)) {
            return checkEmailPattern(ietEmail);
        }
        UIHelper.showShortToastInCenter(getDockActivity(), getResources().getString(R.string.enter_email));
        return false;
    }


    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        switch (Tag) {
            case WebServiceConstant.forgotPassword:
                getDockActivity().replaceDockableFragment(EnterCodeFragment.newInstance(ietEmail.getText().toString()));
                break;
            default:
                break;
        }
    }

}
