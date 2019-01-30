package com.ingic.lmslawyer.fragments.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.iid.FirebaseInstanceId;
import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.constants.AppConstant;
import com.ingic.lmslawyer.constants.WebServiceConstant;
import com.ingic.lmslawyer.entities.User;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.fragments.login.MainLoginFragment;
import com.ingic.lmslawyer.helpers.UIHelper;
import com.ingic.lmslawyer.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SettingFragment extends BaseFragment implements View.OnClickListener {

    Unbinder unbinder;

    @BindView(R.id.mainLayout)
    LinearLayout mainLayout;

    @BindView(R.id.togglePushNotification)
    ToggleButton togglePushNotification;
    @BindView(R.id.changePass)
    RelativeLayout changePass;
    @BindView(R.id.tvLanguageText)
    TextView tvLanguageText;
    @BindView(R.id.rlLogout)
    RelativeLayout rlLogout;
    @BindView(R.id.tvLanguage)
    TextView tvLanguage;

    public static SettingFragment newInstance() {

        return new SettingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListener();
        togglePushNotification.setChecked(prefHelper.getUser().getIsNotify() == 1 ? true : false);
    }

    private void setListener() {

        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        togglePushNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                serviceHelper.enqueueCall(webService.pushOnOff(prefHelper.getUser().getToken(),
                        b == true ? "1" : "0"
                        ),
                        WebServiceConstant.pushOnOff);
//                UIHelper.showLongToastInCenter(getDockActivity(), "Will be implemented with push notification");
            }
        });
        changePass.setOnClickListener(this);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showWhiteBackButton();
        titleBar.setSubHeading(getString(R.string.settings));
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.changePass:
                getDockActivity().addDockableFragment(new ChangePsswordFragment(), "ChangePsswordFragment");
//                getDockActivity().addDockableFragment(ChangePasswordFragment.newInstance(), "ChangePasswordFragment");
                break;
        }
    }

    String refreshToken = FirebaseInstanceId.getInstance().getToken();

    @OnClick(R.id.rlLogout)
    public void onViewClicked() {


        serviceHelper.enqueueCall(webService.logout(prefHelper.getUser().getToken(),
                refreshToken == null ? "3333" : refreshToken,
                AppConstant.DEVICE_TYPE,
                "211"), WebServiceConstant.logout);


    }

    private void SearchLawyer(String txtSearch) {

    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        switch (Tag) {
            case WebServiceConstant.pushOnOff:
                User newUser = (User) result;

                User oldUser = prefHelper.getUser();
                oldUser.setIsNotify(newUser.getIsNotify());

                prefHelper.putUser(oldUser);

                UIHelper.showShortToastInCenter(getDockActivity(), message);

                break;
            case WebServiceConstant.logout:
                prefHelper.setLoginStatus(false);
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(new MainLoginFragment());

                break;
            default:
                break;
        }
    }

}
