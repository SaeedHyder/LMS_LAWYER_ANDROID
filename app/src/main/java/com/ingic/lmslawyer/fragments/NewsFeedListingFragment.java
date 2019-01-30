/*
package com.ingic.lmslawyer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.common.base.Function;
import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.entities.NewsFeedItem;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.global.LawyerSearchConstants;
import com.ingic.lmslawyer.global.RetrofitErrorHandler;
import com.ingic.lmslawyer.global.SideMenuChooser;
import com.ingic.lmslawyer.global.SideMenuDirection;
import com.ingic.lmslawyer.global.SuccessCode;
import com.ingic.lmslawyer.helpers.UIHelper;
import com.ingic.lmslawyer.interfaces.IOnLawyerFilterSubmit;
import com.ingic.lmslawyer.interfaces.IOnNewsfeedFilterSubmit;
import com.ingic.lmslawyer.retrofit.WebResponse;
import com.ingic.lmslawyer.ui.adapters.FilterableListAdapter;
import com.ingic.lmslawyer.ui.viewbinders.abstracts.NewsFeedListItemBinder;
import com.ingic.lmslawyer.ui.views.AnyEditTextView;
import com.ingic.lmslawyer.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFeedListingFragment extends BaseFragment implements IOnNewsfeedFilterSubmit {

    Unbinder unbinder;

    @BindView(R.id.edtSearch)
    AnyEditTextView edtSearch;
    @BindView(R.id.listView)
    ListView listView;

    private ArrayList<NewsFeedItem> newsFeedCollection;

    private IOnLawyerFilterSubmit delegate;

    private FilterableListAdapter<NewsFeedItem> adapter;
    private NewsFeedListItemBinder binder;

    public static NewsFeedListingFragment newInstance() {

        return new NewsFeedListingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newsfeed, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getData();

        setEditSearch();
        setListener();
    }

    private void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                NewsfeedDetailFragment newsfeedDetailFragment = NewsfeedDetailFragment.newInstance();

                NewsFeedItem newsFeedItem = (NewsFeedItem) adapterView.getAdapter().getItem(position);

                newsfeedDetailFragment.setNewsFeedItem(newsFeedItem);

                getDockActivity().addDockableFragment(newsfeedDetailFragment, "newsfeedDetailFragment");
            }
        });
    }

    private void setEditSearch() {
        edtSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    filterList(s.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void filterList(String string) {
        adapter.getFilter().filter(string);
    }

    private void getData() {
        loadingStarted();
        Call<WebResponse<ArrayList<NewsFeedItem>>> call = webService.getNewsFeed(
                prefHelper.getUser().getId(),
                "0");
        call.enqueue(new Callback<WebResponse<ArrayList<NewsFeedItem>>>() {
            @Override
            public void onResponse(Call<WebResponse<ArrayList<NewsFeedItem>>> call, Response<WebResponse<ArrayList<NewsFeedItem>>> response) {
                try {
                    if (response.body().getResponse().equals(SuccessCode.SUCCESS.getValue())) {

                        loadingFinished();
                        if (response.body().getResult().size() == 0) {
                            UIHelper.showLongToastInCenter(getDockActivity(), "No record found");
                        } else {
                            bindData(response.body().getResult());
                            newsFeedCollection = response.body().getResult();
                        }

                    } else {
                        UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<WebResponse<ArrayList<NewsFeedItem>>> call, Throwable t) {
                loadingFinished();
                RetrofitErrorHandler.onServiceFail(getDockActivity(), t);
            }
        });
    }

    private void bindData(ArrayList<NewsFeedItem> collection) {

        adapter = new FilterableListAdapter<NewsFeedItem>(
                getDockActivity(),
                new ArrayList<NewsFeedItem>(),
                new NewsFeedListItemBinder(getDockActivity()),
                new Function<NewsFeedItem, String>() {

                    @Override
                    @Nullable
                    public String apply(
                            @Nullable NewsFeedItem arg0) {
                        // TODO Auto-generated
                        // method
                        // stub
                        return arg0.getTitle();
                    }
                });


        listView.setAdapter(adapter);
        adapter.addAll(collection);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);

        titleBar.hideButtons();
        titleBar.showWhiteBackButton();
        titleBar.setSubHeading(getString(R.string.news_feed_heading));
        titleBar.showFilterButton();

        LawyerSearchConstants.setCategory_ids(new ArrayList<String>());
        getMainActivity().settingSideMenu(true, SideMenuChooser.DRAWER.getValue(), SideMenuDirection.RIGHT.getValue());
        getDockActivity().getNewsFeedFilterSideMenu().setDelegate(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSubmit() {


        if (LawyerSearchConstants.getCategory_ids().size() != 0) {

            loadingStarted();
            Call<WebResponse<ArrayList<NewsFeedItem>>> call = webService.getNewsFeed(
                    prefHelper.getUser().getId(),
                    android.text.TextUtils.join(",", LawyerSearchConstants.getCategory_ids()));
            call.enqueue(new Callback<WebResponse<ArrayList<NewsFeedItem>>>() {
                @Override
                public void onResponse(Call<WebResponse<ArrayList<NewsFeedItem>>> call, Response<WebResponse<ArrayList<NewsFeedItem>>> response) {
                    try {
                        if (response.body().getResponse().equals(SuccessCode.SUCCESS.getValue())) {

                            loadingFinished();

                            if (response.body().getResult().size() == 0) {
                                UIHelper.showLongToastInCenter(getDockActivity(), "No record found");
                            } else {
                                bindData(response.body().getResult());
                                newsFeedCollection = response.body().getResult();
                            }

                        } else {
                            UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<WebResponse<ArrayList<NewsFeedItem>>> call, Throwable t) {
                    loadingFinished();
                    RetrofitErrorHandler.onServiceFail(getDockActivity(), t);
                }
            });
        } else {
            UIHelper.showLongToastInCenter(getDockActivity(), "No Filter Found");
        }


    }


}
*/
