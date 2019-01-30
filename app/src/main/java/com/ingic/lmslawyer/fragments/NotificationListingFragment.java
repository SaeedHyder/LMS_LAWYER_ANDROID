package com.ingic.lmslawyer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.constants.WebServiceConstant;
import com.ingic.lmslawyer.entities.NotificationEnt;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.ui.adapters.ArrayListAdapter;
import com.ingic.lmslawyer.ui.viewbinders.abstracts.NotificationListItemBinder;
import com.ingic.lmslawyer.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NotificationListingFragment extends BaseFragment {

    Unbinder unbinder;

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.alternateText)
    TextView alternateText;

    private ArrayListAdapter<NotificationEnt> adapter;
    private NotificationListItemBinder binder;

    public static NotificationListingFragment newInstance() {

        return new NotificationListingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<NotificationEnt>(getDockActivity(),
                new NotificationListItemBinder(getDockActivity()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_noti_listing, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        serviceHelper.enqueueCall(webService.getNotification(String.valueOf(prefHelper.getUser().getToken())),
                WebServiceConstant.getNotifications);

    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        if (NotificationListingFragment.this.isDetached() || NotificationListingFragment.this.isRemoving()
                || !NotificationListingFragment.this.isVisible())
            return;

        switch (Tag) {
            case WebServiceConstant.getNotifications:
                ArrayList<NotificationEnt> notificationData = (ArrayList<NotificationEnt>) result;
                if (notificationData.size() > 0) {
                    listView.setVisibility(View.VISIBLE);
                    alternateText.setVisibility(View.GONE);
                } else {
                    listView.setVisibility(View.GONE);
                    alternateText.setVisibility(View.VISIBLE);
                }
                listView.setAdapter(adapter);
                adapter.addAll(notificationData);
                break;
            default:
                break;
        }
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);

        titleBar.hideButtons();
        titleBar.showWhiteBackButton();
        titleBar.setSubHeading(getString(R.string.notification));
    }


}
