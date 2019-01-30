package com.ingic.lmslawyer.fragments.Case;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.constants.AppConstant;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatMessagesFragment extends BaseFragment {


    @BindView(R.id.rlEdit)
    RelativeLayout rlEdit;
    Unbinder unbinder;
    @BindView(R.id.tvLawyerName)
    TextView tvLawyerName;
    @BindView(R.id.tvCaseMembers)
    TextView tvCaseMembers;
    @BindView(R.id.parent)
    ScrollView parent;
    private boolean isCaseDesc;


    public ChatMessagesFragment() {
        // Required empty public constructor
    }

    public static ChatMessagesFragment newInstance(boolean isCaseDesc) {
        Bundle args = new Bundle();
        args.putBoolean(AppConstant.IS_CASE_DESC, isCaseDesc);
        ChatMessagesFragment fragment = new ChatMessagesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_messages, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (getArguments() != null) {
            isCaseDesc = getArguments().getBoolean(AppConstant.IS_CASE_DESC);
        }
        if (!isCaseDesc) {
            parent.setBackground(getResources().getDrawable(R.drawable.bg));
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isCaseDesc) {
            rlEdit.setVisibility(View.GONE);
        } else
            rlEdit.setVisibility(View.VISIBLE);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.showWhiteBackButton();
        titleBar.setSubHeading(getResources().getString(R.string.messages)); /*todo Add Search icon */
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.imgAttachment, R.id.imgSend, R.id.tvEdit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgAttachment:
                implementedInBeta();
                break;
            case R.id.imgSend:
                implementedInBeta();
                break;
            case R.id.tvEdit:
                notImplemented();
//                getDockActivity().replaceDockableFragment(new EditGroupFragment());
                break;
        }
    }
}
