package com.ingic.lmslawyer.fragments.login;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.constants.AppConstant;
import com.ingic.lmslawyer.constants.WebServiceConstant;
import com.ingic.lmslawyer.entities.User;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.fragments.forgotpassword.ForgotPasswordEmailFragment;
import com.ingic.lmslawyer.helpers.UIHelper;
import com.ingic.lmslawyer.singleton.BusProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class LMSLoginFragment extends BaseFragment {


    private final String TAG = LMSLoginFragment.class.getSimpleName();
    @BindView(R.id.ietEmail)
    TextInputEditText ietEmail;
    @BindView(R.id.ietPassword)
    TextInputEditText ietPassword;
    @BindView(R.id.btnSingIn)
    Button btnSingIn;
    @BindView(R.id.tvForgotPassword)
    TextView tvForgotPassword;
    @BindView(R.id.tvSignUp)
    TextView tvSignUp;
    Unbinder unbinder;
    private String refreshToken = "dsdsdsfdf";

    public LMSLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lmslogin, container, false);
        unbinder = ButterKnife.bind(this, view);
        //        refreshToken = FirebaseInstanceId.getInstance().getToken();

        ietEmail.setText("");
        ietPassword.setText("");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnSingIn, R.id.tvForgotPassword, R.id.tvSignUp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSingIn:
                if (isValidate(ietEmail.getText().toString(), ietPassword.getText().toString())) {
                    refreshToken = FirebaseInstanceId.getInstance().getToken();
                    serviceHelper.enqueueCall(webService.login(ietEmail.getText().toString(), ietPassword.getText().toString(),
                            AppConstant.DEVICE_TYPE, refreshToken, AppConstant.ROLE_ID), WebServiceConstant.login);
                }
                break;
            case R.id.tvForgotPassword:
                getDockActivity().replaceDockableFragment(new ForgotPasswordEmailFragment(),
                        ForgotPasswordEmailFragment.class.getSimpleName());
                break;
            case R.id.tvSignUp:
//                BusProvider.getUIBusInstance().post(new User("abs"));
                Integer i = 1;
                BusProvider.getUIBusInstance().post(1);
                break;
        }
    }

    private boolean isValidate(String email, String pwd) {
        if (!(email.isEmpty() || pwd.isEmpty())) {
            if (email.matches(AppConstant.EMAIL_PATTERN)) {
                return true;
               /* if (checkLength(pwd)) {
                    return true;
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), getResources().getString(R.string.passwords_length_error));
                    return false;
                }*/
            } else {
                UIHelper.showShortToastInCenter(getDockActivity(), getResources().getString(R.string.error_email_address_not_valid));
                return false;
            }
        }
        fillAllFieldsError();
        return false;
    }

    private boolean checkLength(String pwd) {
        return pwd.length() > 7;
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

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        switch (Tag) {
            case WebServiceConstant.login:
                if (((User) result).getRoleId() != WebServiceConstant.LAWYER_ROLE_ID) {
                    UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.roleIDError));
                    return;
                }
                UIHelper.showShortToastInCenter(getDockActivity(), message);
                openHomeFragment((User) result);
                break;
            default:
                break;
        }
    }

}
