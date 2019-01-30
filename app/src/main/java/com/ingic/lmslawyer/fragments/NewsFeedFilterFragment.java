package com.ingic.lmslawyer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.entities.Specialization_Experience_CategoryItem;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.global.LawyerSearchConstants;
import com.ingic.lmslawyer.global.RetrofitErrorHandler;
import com.ingic.lmslawyer.global.SuccessCode;
import com.ingic.lmslawyer.helpers.UIHelper;
import com.ingic.lmslawyer.interfaces.IOnNewsfeedFilterSubmit;
import com.ingic.lmslawyer.retrofit.WebResponse;
import com.ingic.lmslawyer.ui.adapters.ArrayListAdapter;
import com.ingic.lmslawyer.ui.viewbinders.abstracts.NewsFeedFilterListItemBinder;
import com.ingic.lmslawyer.ui.views.TitleBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFeedFilterFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout parentLayout;
    private ImageView imgCross;

    private ListView listView;

    private Button btnSubmit;

    private IOnNewsfeedFilterSubmit delegate;

    private ArrayList<Specialization_Experience_CategoryItem> collection;

    private ArrayListAdapter<Specialization_Experience_CategoryItem> adapter;

    private Specialization_Experience_CategoryItem entity;

    public static NewsFeedFilterFragment newInstance() {

        return new NewsFeedFilterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sidemenu_newsfeedfilter, container, false);

        adapter = new ArrayListAdapter<Specialization_Experience_CategoryItem>(getDockActivity(), new NewsFeedFilterListItemBinder(getDockActivity()));

        return view;
    }

    public void setDelegate(IOnNewsfeedFilterSubmit delegate) {
        this.delegate = delegate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgCross = (ImageView) view.findViewById(R.id.imgCross);
        parentLayout = (LinearLayout) view.findViewById(R.id.parentLayout);

        listView = (ListView) view.findViewById(R.id.listView);

        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);

        try {
            setListener();
            getCategory();

        } catch (Exception e) {
            e.printStackTrace();
            loadingFinished();
        }
    }

    private void getCategory() {

        loadingStarted();
        Call<WebResponse<ArrayList<Specialization_Experience_CategoryItem>>> call = webService.getCategory(prefHelper.getUser().getId());

        call.enqueue(new Callback<WebResponse<ArrayList<Specialization_Experience_CategoryItem>>>() {
            @Override
            public void onResponse(Call<WebResponse<ArrayList<Specialization_Experience_CategoryItem>>> call, Response<WebResponse<ArrayList<Specialization_Experience_CategoryItem>>> response) {
                loadingFinished();

                if (response.body().getResponse().equals(SuccessCode.SUCCESS.getValue())) {

                    collection = response.body().getResult();
                    bindData(collection);


                } else {
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<WebResponse<ArrayList<Specialization_Experience_CategoryItem>>> call, Throwable t) {
                loadingFinished();
                RetrofitErrorHandler.onServiceFail(getDockActivity(), t);
            }
        });

    }

    private void bindData(ArrayList<Specialization_Experience_CategoryItem> collection) {
        listView.setAdapter(adapter);

        adapter.clearList();
        adapter.addAll(collection);
        adapter.notifyDataSetChanged();
    }

    private void bindData(Specialization_Experience_CategoryItem entity) {
        collection.add(entity);
        adapter.notifyDataSetChanged();
    }

    private void setListener() {
        parentLayout.setOnClickListener(this);
        imgCross.setOnClickListener(this);

        btnSubmit.setOnClickListener(this);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (collection != null && collection.contains(collection.get(position))) {

                    entity = collection.get(position);

                    if (entity.isChecked()) {
                        entity.setChecked(false);

                        LawyerSearchConstants.getCategory_ids().remove(entity.getId());

                    } else {
                        entity.setChecked(true);

                        LawyerSearchConstants.getCategory_ids().add(entity.getId());

                    }
                }
                bindData(entity);
                //bindData(collection);
            }
        });


    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.parentLayout:
                break;

            case R.id.imgCross:
                getDockActivity().getDrawerLayout().closeDrawers();
                break;

            case R.id.btnSubmit:
                getDockActivity().getDrawerLayout().closeDrawers();
                delegate.onSubmit();
                break;
        }
    }


}
