package com.ingic.lmslawyer.fragments.myprofile;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.activities.DockActivity;
import com.ingic.lmslawyer.constants.AppConstant;
import com.ingic.lmslawyer.entities.LawyerStepsEnt;
import com.ingic.lmslawyer.entities.Profile.ProfileData;
import com.ingic.lmslawyer.entities.Profile.ProfileServicesEnt;
import com.ingic.lmslawyer.entities.Profile.SelectedStringsEnt;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.helpers.TextViewHelper;
import com.ingic.lmslawyer.helpers.UIHelper;
import com.ingic.lmslawyer.interfaces.ProfileCheckBoxInterface;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;
import com.ingic.lmslawyer.ui.adapters.myadapters.MyProfileListingAdapter;
import com.ingic.lmslawyer.ui.views.AnyEditTextView;
import com.ingic.lmslawyer.ui.views.AnyTextView;
import com.ingic.lmslawyer.ui.views.TitleBar;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfileListingFragment extends BaseFragment implements ProfileCheckBoxInterface {


    @BindView(R.id.tvListTitle)
    TextView tvListTitle;
    @BindView(R.id.rvProfileList)
    RecyclerView rvProfileList;
    Unbinder unbinder;
    @BindView(R.id.edt_search)
    AnyEditTextView edtSearch;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    private RecyclerViewListAdapter<ProfileServicesEnt> adapter;
    private ArrayList<ProfileServicesEnt> list = new ArrayList<>();
    String[] titleNames = {"Select Specialization", "Select Law Field", "Choose Type of lawyer", "Choose type of case you serve", "Select Language"};

    ArrayList<ProfileServicesEnt> specializationList = new ArrayList<>();
    ArrayList<ProfileServicesEnt> lawFieldList = new ArrayList<>();
    ArrayList<ProfileServicesEnt> typeOfLawyerList = new ArrayList<>();
    ArrayList<ProfileServicesEnt> typeOfCaseServeList = new ArrayList<>();
    ArrayList<ProfileServicesEnt> selectLanguageList = new ArrayList<>();


    String[] specs = {"Business Law", "Constitution Law", "Immigration", "Government", "Intellectual Property", "Abuse"};
    String[] lawFields = {"Corps LLC", "Criminal Law", "Education Law", "Family and Juvenile Law", "Health Law", "Civil Rights"};
    String[] lawyerTypes = {"Personal Injury Lawyer", "State Planning Lawyer", "Bank Cruptcy Lawyer", "Property Lawyer"};
    String[] yourServeCases = {"Civil Case", "Criminal Case", "Drugs Crime", "Income Tax", "Workplace Gender Driscrimination", "Divorce"};
    String[] langs = {"English", "Arabic"};
    private int i;
    private ProfileData profileData;
    private String jsonString;
    private HashMap<String, Integer> profileHashMap = new HashMap<String, Integer>();
    private String specializationString;
    private String lawFieldString;
    private String typeOfLawyerString;
    private String typeOfCaseServeString;
    private String selectLanguageString;


    public MyProfileListingFragment() {
        // Required empty public constructor
    }

    public static MyProfileListingFragment newInstance(ProfileData profileData) {
        Bundle args = new Bundle();
        args.putString(AppConstant.ProfileDetail, new Gson().toJson(profileData));
        MyProfileListingFragment fragment = new MyProfileListingFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_profile_listing, container, false);
        unbinder = ButterKnife.bind(this, view);
        i = 0;


        if (getArguments() != null) {
            jsonString = getArguments().getString(AppConstant.ProfileDetail);
        }
        if (jsonString != null) {
            profileData = new Gson().fromJson(jsonString, ProfileData.class);
        }

        getDockActivity().setFragmentRefreshListener(new DockActivity.FragmentRefreshListener() {
            @Override
            public void onRefresh() {
                onMyCustomBack();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        serviceHelper.enqueueCall(webService.getLawyerSteps(prefHelper.getUser().getToken()), AppConstant.getLawyerSteps);
        TextViewHelper.setText(tvListTitle, titleNames[i]);
        setTextWatecher();
    }

    private void setTextWatecher() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                bindData(getSearchedArray(s.toString()));
            }
        });
    }

    public ArrayList<ProfileServicesEnt> getSearchedArray(String keyword) {
        if (list.isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<ProfileServicesEnt> arrayList = new ArrayList<>();

        String UserName = "";
        for (ProfileServicesEnt item : list) {
            UserName = item.getTitle();

            if (Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE).matcher(UserName).find()) {
                arrayList.add(item);
            }
        }
        return arrayList;

    }

    private void bindData(ArrayList<ProfileServicesEnt> collection) {
        if (MyProfileListingFragment.this.isDetached() || MyProfileListingFragment.this.isRemoving()
                || !MyProfileListingFragment.this.isVisible())
            return;


        if (collection.size() > 0) {
            rvProfileList.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);
        } else {
            rvProfileList.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
        }

        if (collection != null && collection.size() > 0)
            adapter.addAll(collection);


    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.goneTitleBar();
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        if (MyProfileListingFragment.this.isDetached() || MyProfileListingFragment.this.isRemoving()
                || !MyProfileListingFragment.this.isVisible())
            return;
        switch (Tag) {
            case AppConstant.getLawyerSteps:
                LawyerStepsEnt steps = (LawyerStepsEnt) result;
                specializationList.addAll(steps.getSpecialization());
                lawFieldList.addAll(steps.getLaw());
                typeOfLawyerList.addAll(steps.getLawyerType());
                selectLanguageList.addAll(steps.getLanguage());
                typeOfCaseServeList.addAll(steps.get_case());
                initAdapter(specializationList);

                //   serviceHelper.enqueueCall(webService.getCaseTypes(prefHelper.getUser().getToken()), AppConstant.getCaseTypes);
                break;

            case AppConstant.getCaseTypes:
                ArrayList<ProfileServicesEnt> CaseTypes = (ArrayList<ProfileServicesEnt>) result;
                typeOfCaseServeList.addAll(CaseTypes);

                break;


        }
    }


    private void initAdapter(ArrayList<ProfileServicesEnt> specialization) {
        adapter = new MyProfileListingAdapter(getDockActivity(), this);
        rvProfileList.setLayoutManager(new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false));
        // rvProfileList.addItemDecoration(new SimpleDividerItemDecoration(getDockActivity()));

        rvProfileList.setAdapter(adapter);

        list.addAll(specialization);
        bindData(list);
        //adapter.addAll(specialization);

    }


    @OnClick({R.id.imgBack, R.id.imgviewSearch, R.id.btnNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                onMyCustomBack();

                break;
            case R.id.imgviewSearch:

                break;
            case R.id.btnNext:
                if (!checkValidation()) {
                    UIHelper.showShortToastInCenter(getDockActivity(), "Please select atleast one option");
                    return;
                }
                if (i == 4) {
                    selectLanguageString = StringUtils.join(profileHashMap.values(), ',');

                    profileHashMap.clear();
                    SelectedStringsEnt selectedStringsEnt = new SelectedStringsEnt(specializationString, lawFieldString, typeOfLawyerString, typeOfCaseServeString, selectLanguageString, profileData);
                    getDockActivity().addDockableFragment(MyProfileUploadDocFragment.newInstance(selectedStringsEnt), "MyProfileUploadDocFragment");
                }

                if (i < 4)
                    i++;
                setAccordinglyData(i);

                break;
        }
    }

    private boolean checkValidation() {
        return profileHashMap.size() > 0;
    }

    private void onMyCustomBack() {
        if (profileHashMap.size() > 0)
            profileHashMap.clear();

        if (i == 0) {
            getDockActivity().popFragment();
        }
        if (i > 0)
            i--;

        setAccordinglyData(i);

    }

    private void setAccordinglyData(int i) {

        TextViewHelper.setText(tvListTitle, titleNames[i]);
        switch (i) {
            case 0:
                list.clear();
                list.addAll(specializationList);
                bindData(list);

                break;
            case 1:
                specializationString = StringUtils.join(profileHashMap.values(), ',');
                profileHashMap.clear();

                list.clear();
                list.addAll(lawFieldList);
                bindData(list);
                //  adapter.addAll(lawFieldList);

                break;
            case 2:
                lawFieldString = StringUtils.join(profileHashMap.values(), ',');
                profileHashMap.clear();

                list.clear();
                list.addAll(typeOfLawyerList);
                bindData(list);

                break;
            case 3:
                typeOfLawyerString = StringUtils.join(profileHashMap.values(), ',');
                profileHashMap.clear();

                list.clear();
                list.addAll(typeOfCaseServeList);
                bindData(list);
                //     adapter.addAll(typeOfCaseServeList);


                break;
            case 4:
                typeOfCaseServeString = StringUtils.join(profileHashMap.values(), ',');
                profileHashMap.clear();
                list.clear();
                list.addAll(selectLanguageList);
                bindData(list);
                //    adapter.addAll(selectLanguageList);


                break;


        }
    }

    @Override
    public void setHashMap(ProfileServicesEnt item, Boolean value) {
        if (value) {
            profileHashMap.put(item.getTitle(), item.getId());
        } else {
            profileHashMap.remove(item.getTitle());
        }
    }


}
