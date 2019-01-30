package com.ingic.lmslawyer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.helpers.UIHelper;
import com.ingic.lmslawyer.ui.views.AnyEditTextView;
import com.ingic.lmslawyer.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChangePasswordFragment extends BaseFragment {

    Unbinder unbinder;

    @BindView(R.id.mainLayout)
    LinearLayout mainLayout;

    @BindView(R.id.txtOldPass)
    AnyEditTextView txtOldPass;

    @BindView(R.id.txtNewPass)
    AnyEditTextView txtNewPass;

    @BindView(R.id.txtConfirmPass)
    AnyEditTextView txtConfirmPass;

    @BindView(R.id.btnChangePass)
    Button btnChangePass;

    public static ChangePasswordFragment newInstance() {

        return new ChangePasswordFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_changepass, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListener();

    }

    private void setListener() {

        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    implementedInBeta();
                   /* loadingStarted();
                    Call<WebResponse<User>> call = webService.changePassword(
                            prefHelper.getUser().getId(),
                            txtOldPass.getText().toString(),
                            txtNewPass.getText().toString(),
                            txtConfirmPass.getText().toString());
                    call.enqueue(new Callback<WebResponse<User>>() {
                        @Override
                        public void onResponse(Call<WebResponse<User>> call, Response<WebResponse<User>> response) {
                            loadingFinished();

                            if (response.body().getResponse().equals(SuccessCode.SUCCESS.getValue())) {

                                UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                                getDockActivity().popFragment();

                            } else {
                                UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<WebResponse<User>> call, Throwable t) {
                            loadingFinished();
                            RetrofitErrorHandler.onServiceFail(getDockActivity(), t);
                        }
                    });*/
                }
            }
        });
    }


    private boolean validate() {
        return txtOldPass.testValidity() && txtNewPass.testValidity() && txtConfirmPass.testValidity() && passLength() && matchPassword();
    }

    private boolean passLength() {
        if (txtOldPass.getText().toString().length() < 5) {
            UIHelper.showLongToastInCenter(getDockActivity(), getResources().getString(R.string.passwords_length_error));
            return false;
        } else {
            return true;
        }
    }

    private boolean matchPassword() {
        if (!(txtNewPass.getText().toString().equals(txtConfirmPass.getText().toString()))) {
            UIHelper.showLongToastInCenter(getDockActivity(), getResources().getString(R.string.error_passwords_do_not_match));
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);

        titleBar.hideButtons();
        titleBar.showWhiteBackButton();
        titleBar.setSubHeading(getString(R.string.change_password));
    }


}
