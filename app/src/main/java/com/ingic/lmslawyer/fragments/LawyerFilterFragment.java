/*
package com.ingic.lmslawyer.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.entities.Specialization_Experience_CategoryItem;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.global.FilterSearchConstants;
import com.ingic.lmslawyer.global.RetrofitErrorHandler;
import com.ingic.lmslawyer.global.SuccessCode;
import com.ingic.lmslawyer.helpers.UIHelper;
import com.ingic.lmslawyer.interfaces.IOnLawyerFilterSubmit;
import com.ingic.lmslawyer.retrofit.WebResponse;
import com.ingic.lmslawyer.ui.views.AnyEditTextView;
import com.ingic.lmslawyer.ui.views.AnyTextView;
import com.ingic.lmslawyer.ui.views.MultiSpinner;
import com.ingic.lmslawyer.ui.views.TitleBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LawyerFilterFragment extends BaseFragment implements View.OnClickListener, MultiSpinner.MultiSpinnerListener {

    private LinearLayout parentLayout;
    private ImageView imgCross;

    private AnyEditTextView edtName;
    private AnyTextView txtSpecialization;
    private AnyTextView txtExp;
    private AnyTextView txtRate;
    private AnyEditTextView edtEducation;
    private Button btnSubmit;

    private ArrayList<String> specializationNameCollection = new ArrayList<>();
    private ArrayList<String> specializationIdCollection = new ArrayList<>();
    private ArrayList<String> selectedSpecializationId = new ArrayList<>();

    private ArrayList<String> experienceNameCollection = new ArrayList<>();
    private ArrayList<String> experienceIdCollection = new ArrayList<>();
    private String selectedExperienceId;

    private ArrayList<String> successRateIdCollection = new ArrayList<>();
    private String selectedSuccessRateId;

    private MultiSpinner specSpinner;
    private MultiSpinner expSpinner;
    private MultiSpinner successSpinner;

    private IOnLawyerFilterSubmit delegate;

    public static LawyerFilterFragment newInstance() {
        return new LawyerFilterFragment();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        specSpinner = new MultiSpinner(getDockActivity());
        expSpinner = new MultiSpinner(getDockActivity());
        successSpinner = new MultiSpinner(getDockActivity());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sidemenu_lawyerfilter, container, false);


        return view;

    }

    public void setDelegate(IOnLawyerFilterSubmit delegate) {
        this.delegate = delegate;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        imgCross = (ImageView) view.findViewById(R.id.imgCross);
        parentLayout = (LinearLayout) view.findViewById(R.id.parentLayout);

        edtName = (AnyEditTextView) view.findViewById(R.id.edtName);
        txtSpecialization = (AnyTextView) view.findViewById(R.id.txtSpecialization);
        txtExp = (AnyTextView) view.findViewById(R.id.txtExp);
        txtRate = (AnyTextView) view.findViewById(R.id.txtRate);
        edtEducation = (AnyEditTextView) view.findViewById(R.id.edtEducation);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);

        try {
            setListener();
            getSpecialization();
            getExperience();
            makeSuccessRateCollection();
        } catch (Exception e) {
            e.printStackTrace();
            loadingFinished();
        }

    }

    private void setListener() {
        parentLayout.setOnClickListener(this);
        imgCross.setOnClickListener(this);

        txtSpecialization.setOnClickListener(this);
        txtExp.setOnClickListener(this);
        txtRate.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    private void getSpecialization() {

        loadingStarted();
        Call<WebResponse<ArrayList<Specialization_Experience_CategoryItem>>> call = webService.getSpecialization(prefHelper.getUser().getId());

        call.enqueue(new Callback<WebResponse<ArrayList<Specialization_Experience_CategoryItem>>>() {
            @Override
            public void onResponse(Call<WebResponse<ArrayList<Specialization_Experience_CategoryItem>>> call, Response<WebResponse<ArrayList<Specialization_Experience_CategoryItem>>> response) {
                loadingFinished();

                if (response.body().getResponse().equals(SuccessCode.SUCCESS.getValue())) {

                    for (int i = 0; i < response.body().getResult().size(); i++) {

                        specializationIdCollection.add(response.body().getResult().get(i).getId());
                        specializationNameCollection.add(response.body().getResult().get(i).getTitle());


                    }

                    specSpinner.setTag("spec");
                    specSpinner.setItems(specializationNameCollection, specializationIdCollection, "", LawyerFilterFragment.this);


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


    private void getExperience() {
        loadingStarted();
        Call<WebResponse<ArrayList<Specialization_Experience_CategoryItem>>> call = webService.getExperience(prefHelper.getUser().getId());

        call.enqueue(new Callback<WebResponse<ArrayList<Specialization_Experience_CategoryItem>>>() {
            @Override
            public void onResponse(Call<WebResponse<ArrayList<Specialization_Experience_CategoryItem>>> call, Response<WebResponse<ArrayList<Specialization_Experience_CategoryItem>>> response) {
                loadingFinished();

                if (response.body().getResponse().equals(SuccessCode.SUCCESS.getValue())) {

                    for (int i = 0; i < response.body().getResult().size(); i++) {
                        experienceIdCollection.add(response.body().getResult().get(i).getId());
                        experienceNameCollection.add(response.body().getResult().get(i).getTitle());

                    }

                    expSpinner.setTag("exp");
                    expSpinner.setItems(experienceNameCollection, experienceIdCollection, "", LawyerFilterFragment.this);


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

    private void makeSuccessRateCollection() {
        for (int i = 0; i < 4; i++) {
            successRateIdCollection.add(String.valueOf(i));
        }

        successSpinner.setTag("rate");
        successSpinner.setItems(successRateIdCollection, successRateIdCollection, "", LawyerFilterFragment.this);
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.parentLayout:
                break;

            case R.id.imgCross:
                getDockActivity().getDrawerLayout().closeDrawers();
                break;

            case R.id.txtSpecialization:
                specSpinner.performClick();
                break;

            case R.id.txtExp:

                expSpinner.performClick();

                break;

            case R.id.txtRate:
                successSpinner.performClick();
                break;

            case R.id.btnSubmit:

                FilterSearchConstants.setName(edtName.getText().toString().length() > 0 ? edtName.getText().toString() : null);
                FilterSearchConstants.setEducation(edtEducation.getText().toString().length() > 0 ? edtEducation.getText().toString() : null);

                FilterSearchConstants.isFilter = true;

                getDockActivity().getDrawerLayout().closeDrawers();

                delegate.onSubmit();

                break;


        }
    }

    @Override
    public void onItemsSelected(String tag, String ids) {
        if (tag.equals("spec")) {

            selectedSpecializationId = Lists.newArrayList(Splitter.on(" , ").split(ids));
            FilterSearchConstants.setSpecializationIds(selectedSpecializationId);

        } else if (tag.equals("exp")) {
            selectedExperienceId = ids.trim();
            FilterSearchConstants.setExperienceIds(selectedExperienceId);
        } else if (tag.equals("rate")) {
            selectedSuccessRateId = ids.trim();
            FilterSearchConstants.setSuccessRate(selectedSuccessRateId);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);


    }
}
*/
