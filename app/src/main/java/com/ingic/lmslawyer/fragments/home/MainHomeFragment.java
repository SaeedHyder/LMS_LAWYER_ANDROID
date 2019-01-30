package com.ingic.lmslawyer.fragments.home;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.constants.AppConstant;
import com.ingic.lmslawyer.constants.WebServiceConstant;
import com.ingic.lmslawyer.entities.User;
import com.ingic.lmslawyer.entities.newsfeed.Categories;
import com.ingic.lmslawyer.entities.newsfeed.NewsfeedEnt;
import com.ingic.lmslawyer.fragments.Case.CalenderFragment;
import com.ingic.lmslawyer.fragments.Case.MainLibraryFragment;
import com.ingic.lmslawyer.fragments.Case.MainMyCasesFragment;
import com.ingic.lmslawyer.fragments.NewsFeed.NewsFeedListFragment;
import com.ingic.lmslawyer.fragments.NotificationListingFragment;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.fragments.messages.MessagesListFragment;
import com.ingic.lmslawyer.fragments.myprofile.ProfileFragment;
import com.ingic.lmslawyer.fragments.settings.SettingFragment;
import com.ingic.lmslawyer.helpers.InternetHelper;
import com.ingic.lmslawyer.helpers.UIHelper;
import com.ingic.lmslawyer.interfaces.OnViewHolderClick;
import com.ingic.lmslawyer.rss_feed.Article;
import com.ingic.lmslawyer.rss_feed.Parser;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;
import com.ingic.lmslawyer.ui.adapters.myadapters.HomeNewsfeedListAdapter;
import com.ingic.lmslawyer.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainHomeFragment extends BaseFragment implements OnViewHolderClick {


    @BindView(R.id.imgHomeUserProfile)
    ImageView imgHomeUserProfile;
    @BindView(R.id.imgHomeNotification)
    ImageView imgHomeNotification;
    @BindView(R.id.rvHomeNewsfeed)
    RecyclerView rvHomeNewsfeed;
    Unbinder unbinder;
    private RecyclerViewListAdapter adapter;
    private ArrayList list;
    ArrayList<Categories> categoryData;

    public MainHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        initAdapter();
        //Rss Feed Work
        if (!InternetHelper.isNetworkAvailable(getDockActivity())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getDockActivity());
            builder.setMessage(R.string.alert_message)
                    .setTitle(R.string.alert_title)
                    .setCancelable(false)
                    .setPositiveButton(R.string.alert_positive,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    getDockActivity().finish();
                                }
                            });

            AlertDialog alert = builder.create();
            alert.show();

        } else if (InternetHelper.isNetworkAvailable(getDockActivity())) {
            callService();
//            loadFeed();
        }

//        getLawyerProfile();
        return view;
    }

    private void callService() {
       /* serviceHelper.enqueueCall(webService.getCategories(String.valueOf(prefHelper.getUser().getToken())),
                WebServiceConstant.getCategories);*/
        serviceHelper.enqueueCall(webService.getNewsfeed(String.valueOf(prefHelper.getUser().getToken()),
                prefHelper.getUser().getSubscribeCategoryId()+""),
                WebServiceConstant.getNewsfeed);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        switch (Tag) {
            case WebServiceConstant.getUserProfile:
                prefHelper.putUser((User) result);
                break;

            case WebServiceConstant.getCategories:
                categoryData = (ArrayList<Categories>) result;
               /* if (categoryData != null && categoryData.size() > 0)
                    serviceHelper.enqueueCall(webService.getNewsfeed(String.valueOf(prefHelper.getUser().getToken()),
                            categoryData.get(0).getId()),
                            WebServiceConstant.getNewsfeed);*/
                break;
            case WebServiceConstant.getNewsfeed:
                ArrayList<NewsfeedEnt> newsfeedResult = (ArrayList<NewsfeedEnt>) result;
                if (newsfeedResult != null && newsfeedResult.size() > 0) {
                    adapter.addAll(newsfeedResult);
                }

                break;

        }
    }

    public void loadFeed() {

//        if (!mSwipeRefreshLayout.isRefreshing())
//            progressBar.setVisibility(View.VISIBLE);
        getDockActivity().onLoadingStarted();


        Parser parser = new Parser();
        parser.execute(WebServiceConstant.RSS_URL);
        parser.onFinish(new Parser.OnTaskCompleted() {
            //what to do when the parsing is done
            @Override
            public void onTaskCompleted(ArrayList<Article> list) {
                adapter.addAll(list);
                getDockActivity().onLoadingFinished();
            }

            //what to do in case of error
            @Override
            public void onError() {

                getDockActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getDockActivity().onLoadingFinished();
//                        progressBar.setVisibility(View.GONE);
//                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getDockActivity(), "Unable to load data.",
                                Toast.LENGTH_LONG).show();
                        Log.i("Unable to load ", "articles");
                    }
                });
            }
        });
    }

    private void initAdapter() {
        adapter = new HomeNewsfeedListAdapter(getDockActivity(), this);
        rvHomeNewsfeed.setLayoutManager(new LinearLayoutManager(getDockActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvHomeNewsfeed.setAdapter(adapter);
//        adapter.addAll(list);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.goneTitleBar();
    }


    @OnClick({R.id.imgHomeUserProfile, R.id.imgHomeNotification, R.id.myCaseTv, R.id.myLibraryTv, R.id.settingsTv, R.id.myCalenderTv, R.id.messagesTv, R.id.newfeedTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgHomeUserProfile:
                getDockActivity().replaceDockableFragment(new ProfileFragment());
                break;
            case R.id.imgHomeNotification:
                getDockActivity().replaceDockableFragment(new NotificationListingFragment());
                break;

            case R.id.myCaseTv:
                if (prefHelper.getUser().getBio() != null && !prefHelper.getUser().getBio().isEmpty() &&
                        prefHelper.getUser().getIsActive() != null && prefHelper.getUser().getIsActive() != AppConstant.ACCEPT) {
                    UIHelper.showShortToastInCenter(getDockActivity(),
                            getResources().getString(R.string.pending_approval));
                } else if (prefHelper.getUser().getIsActive() != null && prefHelper.getUser().getIsActive() == AppConstant.ACCEPT)
                    getDockActivity().replaceDockableFragment(new MainMyCasesFragment());
                else
                    UIHelper.showShortToastInCenter(getDockActivity(), getResources().getString(R.string.profile_is_not_approved));
                break;
            case R.id.myLibraryTv:
                if (prefHelper.getUser().getBio() != null && !prefHelper.getUser().getBio().isEmpty() &&
                        prefHelper.getUser().getIsActive() != null && prefHelper.getUser().getIsActive() != AppConstant.ACCEPT) {
                    UIHelper.showShortToastInCenter(getDockActivity(),
                            getResources().getString(R.string.pending_approval));
                } else if (prefHelper.getUser().getIsActive() != null && prefHelper.getUser().getIsActive() == AppConstant.ACCEPT)
                    getDockActivity().replaceDockableFragment(new MainLibraryFragment(), MainLibraryFragment.class.getSimpleName());
                else
                    UIHelper.showShortToastInCenter(getDockActivity(), getResources().getString(R.string.profile_is_not_approved));

                break;
            case R.id.settingsTv:
                getDockActivity().replaceDockableFragment(new SettingFragment());
                break;
            case R.id.myCalenderTv:
                if (prefHelper.getUser().getBio() != null && !prefHelper.getUser().getBio().isEmpty() &&
                        prefHelper.getUser().getIsActive() != null && prefHelper.getUser().getIsActive() != AppConstant.ACCEPT) {
                    UIHelper.showShortToastInCenter(getDockActivity(),
                            getResources().getString(R.string.pending_approval));
                } else if (prefHelper.getUser().getIsActive() != null && prefHelper.getUser().getIsActive() == AppConstant.ACCEPT)
                    getDockActivity().replaceDockableFragment(CalenderFragment.newInstance(false, ""));
                else
                    UIHelper.showShortToastInCenter(getDockActivity(), getResources().getString(R.string.profile_is_not_approved));

                break;
            case R.id.messagesTv:
                getDockActivity().replaceDockableFragment(new MessagesListFragment());

                break;
            case R.id.newfeedTv:
                getDockActivity().replaceDockableFragment(new NewsFeedListFragment(), NewsFeedListFragment.class.getSimpleName());
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @OnClick(R.id.imgviewSearch)
    public void onViewClicked() {
        implementedInBeta();

    }
}
