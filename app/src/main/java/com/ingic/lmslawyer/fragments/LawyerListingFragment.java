/*
package com.ingic.lmslawyer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.entities.LawyerSearchItem;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.global.FilterSearchConstants;
import com.ingic.lmslawyer.global.LawyerSearchConstants;
import com.ingic.lmslawyer.global.RetrofitErrorHandler;
import com.ingic.lmslawyer.global.SideMenuChooser;
import com.ingic.lmslawyer.global.SideMenuDirection;
import com.ingic.lmslawyer.global.SuccessCode;
import com.ingic.lmslawyer.helpers.UIHelper;
import com.ingic.lmslawyer.interfaces.IOnLawyerFilterSubmit;
import com.ingic.lmslawyer.retrofit.WebResponse;
import com.ingic.lmslawyer.ui.adapters.ArrayListAdapter;
import com.ingic.lmslawyer.ui.viewbinders.abstracts.LawyerListItemBinder;
import com.ingic.lmslawyer.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LawyerListingFragment extends BaseFragment implements IOnLawyerFilterSubmit {

    Unbinder unbinder;

    @BindView(R.id.listView)
    ListView listView;

    private ArrayListAdapter<LawyerSearchItem> adapter;
    private ArrayList<LawyerSearchItem> searchCollection;
    private LawyerListItemBinder binder;

    public static LawyerListingFragment newInstance() {

        return new LawyerListingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lawyerlisting, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        adapter = new ArrayListAdapter<LawyerSearchItem>(getDockActivity(), new LawyerListItemBinder(getDockActivity()));

//        getData();
        ArrayList<LawyerSearchItem> list = new ArrayList<>();
        list.add(new LawyerSearchItem());
        list.add(new LawyerSearchItem());
        list.add(new LawyerSearchItem());
        listView.setAdapter(adapter);
        adapter.addAll(list);
        setListener();
    }

    private void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                */
/*todo commit for alpha*//*

             */
/*   LawyersProfileFragment lawyersProfileFragment = LawyersProfileFragment.newInstance();
                lawyersProfileFragment.setListItem(searchCollection.get(position));
*//*

                getDockActivity().addDockableFragment(new LawyersProfileFragment(), "LawyersProfileFragment");
            }
        });
    }

    private void getData() {

        loadingStarted();
        Call<WebResponse<ArrayList<LawyerSearchItem>>> call = webService.getLawyerRecord(
                prefHelper.getUser().getId(),
                LawyerSearchConstants.getBudget(),
                LawyerSearchConstants.getLatitude(),
                LawyerSearchConstants.getLongitude(),
                android.text.TextUtils.join(",", LawyerSearchConstants.getType_ids()),
                android.text.TextUtils.join(",", LawyerSearchConstants.getCase_ids()));

        call.enqueue(new Callback<WebResponse<ArrayList<LawyerSearchItem>>>() {
            @Override
            public void onResponse(Call<WebResponse<ArrayList<LawyerSearchItem>>> call, Response<WebResponse<ArrayList<LawyerSearchItem>>> response) {
                loadingFinished();

                if (response.body().getResponse().equals(SuccessCode.SUCCESS.getValue())) {

                    if (response.body().getResult().size() == 0) {
                        UIHelper.showLongToastInCenter(getDockActivity(), "No record found");
                    } else {
                        searchCollection = response.body().getResult();
                        bindData(response.body().getResult());
                    }

                } else {
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<WebResponse<ArrayList<LawyerSearchItem>>> call, Throwable t) {
                loadingFinished();
                RetrofitErrorHandler.onServiceFail(getDockActivity(), t);
            }
        });


    }

    private void bindData(ArrayList<LawyerSearchItem> collection) {
        adapter.clearList();
        listView.setAdapter(adapter);
        adapter.addAll(collection);
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);

        titleBar.hideButtons();
        titleBar.showWhiteBackButton();
        titleBar.setSubHeading(getString(R.string.lawyer_heading));
        titleBar.showFilterButton();

        getMainActivity().settingSideMenu(false, SideMenuChooser.DRAWER.getValue(), SideMenuDirection.RIGHT.getValue());
        getDockActivity().getLawyerFilterSideMenu().setDelegate(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        getDockActivity().releaseDrawer();

    }

    @Override
    public void onSubmit() {
        if (!FilterSearchConstants.checkIfAllFieldsAreEmpty()) {

            loadingStarted();
            Call<WebResponse<ArrayList<LawyerSearchItem>>> call = webService.getLawyerFilteredRecord(
                    prefHelper.getUser().getId(),
                    FilterSearchConstants.getName(),
                    FilterSearchConstants.getEducation(),
                    FilterSearchConstants.getSuccessRate(),
                    FilterSearchConstants.getExperienceIds(),
                    (FilterSearchConstants.getSpecializationIds() != null ? android.text.TextUtils.join(",", FilterSearchConstants.getSpecializationIds()) : null),
                    LawyerSearchConstants.getBudget(),
                    LawyerSearchConstants.getLatitude(),
                    LawyerSearchConstants.getLongitude(),
                    android.text.TextUtils.join(",", LawyerSearchConstants.getType_ids()),
                    android.text.TextUtils.join(",", LawyerSearchConstants.getCase_ids()));

            call.enqueue(new Callback<WebResponse<ArrayList<LawyerSearchItem>>>() {
                @Override
                public void onResponse(Call<WebResponse<ArrayList<LawyerSearchItem>>> call, Response<WebResponse<ArrayList<LawyerSearchItem>>> response) {
                    loadingFinished();

                    FilterSearchConstants filterSearchConstants = new FilterSearchConstants();

                    FilterSearchConstants.isFilter = false;
                    FilterSearchConstants.emptyAllFields(filterSearchConstants);

                    if (response.body().getResponse().equals(SuccessCode.SUCCESS.getValue())) {

                        if (response.body().getResult().size() == 0) {
                            UIHelper.showLongToastInCenter(getDockActivity(), "No record found");
                        } else {
                            searchCollection = response.body().getResult();
                            bindData(response.body().getResult());
                        }


                    } else {
                        UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<WebResponse<ArrayList<LawyerSearchItem>>> call, Throwable t) {
                    loadingFinished();
                    RetrofitErrorHandler.onServiceFail(getDockActivity(), t);

                    FilterSearchConstants filterSearchConstants = new FilterSearchConstants();

                    FilterSearchConstants.isFilter = false;
                    FilterSearchConstants.emptyAllFields(filterSearchConstants);
                }
            });

        } else {
            UIHelper.showLongToastInCenter(getDockActivity(), "No Filter Found");
        }
    }


}
*/
