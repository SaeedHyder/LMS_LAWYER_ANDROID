package com.ingic.lmslawyer.fragments.settings;


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
 */public class ChangePsswordFragment extends BaseFragment {


    @BindView(R.id.ietOldPassword)
    TextInputEditText ietOldPassword;
    @BindView(R.id.ietNewPassword)
    TextInputEditText ietNewPassword;
    @BindView(R.id.ietConfirmPassword)
    TextInputEditText ietConfirmPassword;
    Unbinder unbinder;

    public ChangePsswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_pssword, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
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
                serviceHelper.enqueueCall(webService.changePassword(prefHelper.getUser().getToken(),
                        ietOldPassword.getText().toString(),
                        ietNewPassword.getText().toString(),
                        ietConfirmPassword.getText().toString()), WebServiceConstant.changePassword);

//            implementedInBeta();
//            getDockActivity().popFragment();
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        switch (Tag) {
            case WebServiceConstant.changePassword:
                UIHelper.showShortToastInCenter(getDockActivity(), message);
                getDockActivity().popFragment();
                break;
            default:
                break;
        }
    }

    private boolean isValidate() {
        if (!(checkEmpty(ietOldPassword) || checkEmpty(ietNewPassword) || checkEmpty(ietConfirmPassword))) {
            if (checkPassword(ietNewPassword.getText().toString(), ietConfirmPassword.getText().toString())) {
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
}
