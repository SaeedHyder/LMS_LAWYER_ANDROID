package com.ingic.lmslawyer.fragments.forgotpassword;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class NewPasswordFragment extends BaseFragment {


    @BindView(R.id.ietPassword)
    TextInputEditText ietPassword;
    @BindView(R.id.ietConfirmPassword)
    TextInputEditText ietConfirmPassword;
    Unbinder unbinder;
    private String email = "";

    public NewPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (getArguments() != null) {
            email = getArguments().getString(AppConstant.EMAIL);
        }
        return view;
    }

    public static NewPasswordFragment newInstance(String email) {

        Bundle args = new Bundle();
        args.putString(AppConstant.EMAIL, email);
        NewPasswordFragment fragment = new NewPasswordFragment();
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

    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {
        if (isValidate()) {
            serviceHelper.enqueueCall(webService.resetPassword(email, ietPassword.getText().toString(),
                    ietConfirmPassword.getText().toString()), WebServiceConstant.resetPassword);
        }
    }

    private boolean isValidate() {
        if (!(checkEmpty(ietPassword) || checkEmpty(ietConfirmPassword))) {
            if (checkPassword(ietPassword.getText().toString(), ietConfirmPassword.getText().toString())) {
                return true;
            } else
                return false;
        }
        fillAllFieldsError();
        return false;
    }


    private boolean checkPassword(String pwd, String confirmPwd) {
        if (pwd.length() > 7) {
            if (pwd.equals(confirmPwd)) {
                return true;
            } else {
                passwordNotMatched();
                return false;
            }
        }
        passwordLengthError();
        return false;
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        switch (Tag) {
            case WebServiceConstant.resetPassword:
                UIHelper.showShortToastInCenter(getDockActivity(), message);
                getDockActivity().popBackStackTillEntry(1);
                break;
            default:
                break;
        }
    }
}
