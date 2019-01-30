package com.ingic.lmslawyer.fragments.login;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.iid.FirebaseInstanceId;
import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.constants.AppConstant;
import com.ingic.lmslawyer.constants.WebServiceConstant;
import com.ingic.lmslawyer.entities.User;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.helpers.UIHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class LMSSignUpFragment extends BaseFragment {


    @BindView(R.id.ietFulName)
    TextInputEditText ietFulName;
    @BindView(R.id.ietEmail)
    TextInputEditText ietEmail;
    @BindView(R.id.ietMobile)
    TextInputEditText ietMobile;
    @BindView(R.id.ietPassword)
    TextInputEditText ietPassword;
    @BindView(R.id.ietConfirmPassword)
    TextInputEditText ietConfirmPassword;
    @BindView(R.id.btnSignUp)
    Button btnSignUp;
    Unbinder unbinder;
    private final String TAG = LMSSignUpFragment.class.getSimpleName();
    private String refreshToken = "656";


    public LMSSignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lmssign_up, container, false);
        unbinder = ButterKnife.bind(this, view);
        refreshToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "onCreateView: refreshToken = " + refreshToken);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnSignUp)
    public void onViewClicked() {
        if (isValidate(ietFulName.getText().toString(), ietEmail.getText().toString(), ietMobile.getText().toString(),
                ietPassword.getText().toString(), ietConfirmPassword.getText().toString())) {
            refreshToken = FirebaseInstanceId.getInstance().getToken();
            Log.d(TAG, "onCreateView: refreshToken = " + refreshToken);

            serviceHelper.enqueueCall(webService.register(ietFulName.getText().toString(), ietEmail.getText().toString(),
                    ietMobile.getText().toString(), ietPassword.getText().toString(), ietConfirmPassword.getText().toString(),
                    AppConstant.DEVICE_TYPE, refreshToken, AppConstant.ROLE_ID), WebServiceConstant.register);
        }
    }

    private boolean isValidate(String fullName, String email, String phone, String pwd, String confirmPwd) {
        if (!(fullName.isEmpty() || email.isEmpty() || phone.isEmpty() || pwd.isEmpty() || confirmPwd.isEmpty())) {
            if (email.matches(AppConstant.EMAIL_PATTERN)) {
                if (checkPassword(pwd, confirmPwd)) {
                    if (checkPhoneLength(phone.length()))
                        return true;
                    else {
                        phoneNumberNotValid();
                        return false;
                    }
                } else return false;
            } else {
                UIHelper.showShortToastInCenter(getDockActivity(), getResources().getString(R.string.error_email_address_not_valid));
                return false;
            }
        }

        fillAllFieldsError();
        return false;
    }

    private boolean checkPhoneLength(int phone) {
        return phone > 7;
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
            case WebServiceConstant.register:
                UIHelper.showShortToastInCenter(getDockActivity(), message);
                openHomeFragment((User) result);
                break;
            default:
                break;
        }
    }

}
