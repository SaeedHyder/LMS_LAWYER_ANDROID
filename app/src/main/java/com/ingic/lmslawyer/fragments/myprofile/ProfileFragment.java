package com.ingic.lmslawyer.fragments.myprofile;


import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.activities.MainActivity;
import com.ingic.lmslawyer.constants.AppConstant;
import com.ingic.lmslawyer.constants.WebServiceConstant;
import com.ingic.lmslawyer.entities.LawyerRateResponse;
import com.ingic.lmslawyer.entities.Profile.ProfileData;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.helpers.CameraHelper;
import com.ingic.lmslawyer.helpers.UIHelper;
import com.ingic.lmslawyer.helpers.Util;
import com.ingic.lmslawyer.ui.adapters.ProfileSpinnerAdapter;
import com.ingic.lmslawyer.ui.views.AnyTextView;
import com.ingic.lmslawyer.ui.views.AutoCompleteLocation;
import com.ingic.lmslawyer.ui.views.TagEditText;
import com.ingic.lmslawyer.ui.views.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class ProfileFragment extends BaseFragment implements MainActivity.ImageSetter, AdapterView.OnItemSelectedListener/*, AdapterView.OnItemSelectedListener */ {


    private static final String TAG = ProfileFragment.class.getSimpleName();
    @BindView(R.id.imgviewProfile)
    CircleImageView imgviewProfile;

    @BindView(R.id.ietFees)
    TextInputEditText ietFees;

    @BindView(R.id.ietFulName)
    TextInputEditText ietFulName;

    @BindView(R.id.ietEmail)
    TextInputEditText ietEmail;

    @BindView(R.id.ietMobile)
    TextInputEditText ietMobile;

    @BindView(R.id.ietLocation)
    TextInputEditText ietLocation;

    /*@BindView(R.id.ietAcademics)
    TextInputEditText ietAcademics;*/

    @BindView(R.id.edtAcademics)
    TagEditText edtAcademics;

    @BindView(R.id.rateLayout)
    RelativeLayout rateLayout;
    @BindView(R.id.spinnerRates)
    Spinner spinnerRates;

    @BindView(R.id.affiliationLayout)
    RelativeLayout affiliationLayout;
    @BindView(R.id.spinnerAffiliation)
    Spinner spinnerAffiliation;

    /*@BindView(R.id.ietAffiliation)
    TextInputEditText ietAffiliation;*/

    @BindView(R.id.expLayout)
    RelativeLayout expLayout;
    @BindView(R.id.spinnerExp)
    Spinner spinnerExp;

    @BindView(R.id.etBioData)
    EditText etBioData;
    Unbinder unbinder;
    @BindView(R.id.cameraIcon)
    ImageView cameraIcon;
    @BindView(R.id.flProfilePicture)
    FrameLayout flProfilePicture;
    @BindView(R.id.txtAcademics)
    AnyTextView txtAcademics;
    @BindView(R.id.academicsLayout)
    RelativeLayout academicsLayout;
    //    @BindView(R.id.txtFees)
//    AnyTextView txtFees;
    @BindView(R.id.txtAffiliation)
    AnyTextView txtAffiliation;
    @BindView(R.id.ietAward)
    TextInputEditText ietAward;
    @BindView(R.id.txtExp)
    AnyTextView txtExp;
    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.txt_autoCompleteLocation)
    AutoCompleteLocation txtAutoCompleteLocation;
    @BindView(R.id.txt_win_count)
    AnyTextView txtWinCount;
    @BindView(R.id.txt_lose_count)
    AnyTextView txtLoseCount;
    @BindView(R.id.tilRate)
    TextInputLayout tilRate;


    //For lawyer rate
    private ArrayList<String> rateIds;
    private ArrayList<String> ratePromptTexts;
    private String selectedRateId;

    //For lawyer affiliation
    private ArrayList<String> affiliationIds;
    private ArrayList<String> affiliationPromptTexts;
    private String selectedAffiliationId;

    //For lawyer experience
    private ArrayList<String> experienceIds;
    private ArrayList<String> experiencePromptTexts;
    private String selectedExperienceId;

    private File profilePic;
    private String profilePath;
    private ImageLoader imageLoader;
    private ProfileData profileData;
    private String locationName;
    private LatLng locationLatLng;
    private ArrayList<LawyerRateResponse> lawyerRatesResponse = new ArrayList<>();
    private ArrayList<LawyerRateResponse> lawyerAffiliationResponse = new ArrayList<>();
    private ArrayList<LawyerRateResponse> lawyerExperienceResponse = new ArrayList<>();

    int rateCheck = 0;

    private Snackbar snackbar;
    View rootView;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getMainActivity().setImageSetter(this);
        autoCompleteLocationListner();

        setProfileData();
        getLawyerRates();
        getLawyerAffiliation();
        getLawyerExperience();

        spinnerListner();

        ietFees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerRates.performClick();

            }
        });


    }


    private void spinnerListner() {

     /*   spinnerAffiliation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedAffiliationId = String.valueOf(lawyerAffiliationResponse.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/
        spinnerExp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedExperienceId = String.valueOf(lawyerExperienceResponse.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setProfileData() {

        if (prefHelper.getUser() != null) {
            if (prefHelper.getUser().getImageUrl() != null && !prefHelper.getUser().getImageUrl().equals("")) {
                //Picasso.with(getDockActivity()).load(prefHelper.getUser().getImageUrl()).into(imgviewProfile);
                imageLoader.displayImage(prefHelper.getUser().getImageUrl(), imgviewProfile);
                cameraIcon.setVisibility(View.GONE);
                profilePath = prefHelper.getUser().getImageUrl();

              /*  try {
                    profilePic = new Compressor(getDockActivity()).compressToFile(new File(prefHelper.getUser().getImageUrl()));
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
            ietFulName.setText(prefHelper.getUser().getFullName());
            txtWinCount.setText(prefHelper.getUser().getWin_case()+"");
            txtLoseCount.setText(prefHelper.getUser().getLossCase()+"");
            ietEmail.setText(prefHelper.getUser().getEmail());
            ietAward.setText(prefHelper.getUser().getAward());
            ietMobile.setText(prefHelper.getUser().getPhone());
            etBioData.setText(prefHelper.getUser().getBio());
            edtAcademics.setText(prefHelper.getUser().getAcademics());
            txtAutoCompleteLocation.setText(prefHelper.getUser().getLocation());
            locationName = prefHelper.getUser().getLocation();
            locationLatLng = new LatLng(Util.getParsedDouble((prefHelper.getUser().getLatitude()))
                    , Util.getParsedDouble((prefHelper.getUser().getLongitude())));

            setSpinnersData();
        }
    }

    private void setSpinnersData() {


    }

    private void autoCompleteLocationListner() {

        txtAutoCompleteLocation.setAutoCompleteTextListener(new AutoCompleteLocation.AutoCompleteLocationListener() {
            @Override
            public void onTextClear() {

            }

            @Override
            public void onItemSelected(Place selectedPlace) {
                locationName = selectedPlace.getName().toString();
                locationLatLng = selectedPlace.getLatLng();
            }
        });
    }

    private void getLawyerRates() {
        serviceHelper.enqueueCall(webService.getLawyerRates(prefHelper.getUser().getToken()), WebServiceConstant.LawyerRates);
    }

    private void getLawyerAffiliation() {
        serviceHelper.enqueueCall(webService.getLawyerAffiliation(prefHelper.getUser().getToken()), WebServiceConstant.LawyerAffiliation);
    }

    private void getLawyerExperience() {
        serviceHelper.enqueueCall(webService.getLawyerExperience(prefHelper.getUser().getToken()), WebServiceConstant.LawyerExperience);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.showWhiteBackButton();
        titleBar.setSubHeading(getResources().getString(R.string.profile)); /*todo Add Search icon */
    }


    @OnClick({R.id.flProfilePicture, R.id.btnNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.flProfilePicture:
                if (Util.doubleClickCheck())
                    ProfileFragmentPermissionsDispatcher.getStoragePermissionWithPermissionCheck(ProfileFragment.this);
                break;


            case R.id.btnNext:
                if (isValidate(ietFulName.getText().toString(), ietEmail.getText().toString()
                        , ietMobile.getText().toString(), ietLocation.getText().toString()
                        , edtAcademics.getText().toString().replace(" ", ","), selectedRateId,
                        selectedAffiliationId, ietAward.getText().toString(),
                        selectedExperienceId, etBioData.getText().toString()
                )) {
                    profileData = new ProfileData(ietFulName.getText().toString(), ietEmail.getText().toString(), ietMobile.getText().toString(),
                            edtAcademics.getText().toString().replace(" ", ","), selectedRateId, selectedAffiliationId, ietAward.getText().toString(),
                            selectedExperienceId, etBioData.getText().toString(), locationName, String.valueOf(locationLatLng.latitude), String.valueOf(locationLatLng.longitude), profilePic);
                    getDockActivity().addDockableFragment(MyProfileListingFragment.newInstance(profileData)
                            , MyProfileListingFragment.class.getSimpleName());
//                    getDockActivity().replaceDockableFragment(MyProfileListingFragment.newInstance(profileData));
                }
                break;
        }
    }


    private boolean isValidate(String fullName, String email, String phone, String location, String academics,
                               String fees, String affiliation, String awards, String experience, String bioData
    ) {
        if (!(fullName == null || fullName.isEmpty() || email == null || email.isEmpty()
                || phone == null || phone.isEmpty() || academics == null || academics.isEmpty()
                || fees == null || fees.isEmpty() || affiliation == null || affiliation.isEmpty() || awards == null || awards.isEmpty()
                || experience == null || experience.isEmpty() || bioData == null || bioData.isEmpty() || txtAutoCompleteLocation.getText() == null || txtAutoCompleteLocation.getText().toString().isEmpty()

        )) {
            if (email.matches(AppConstant.EMAIL_PATTERN)) {

                if (checkPhoneLength(phone.length())) {

                    if (profilePath == null || profilePath.isEmpty()) {
                        UIHelper.showShortToastInCenter(getDockActivity(), "Upload profile picture");
                        return false;
                    }

                    return true;
                } else {
                    phoneNumberNotValid();
                    return false;
                }
            } else {
                UIHelper.showShortToastInCenter(getDockActivity(), getResources().getString(R.string.error_email_address_not_valid));
                return false;
            }
        }
        fillAllFieldsError();
        return false;
    }

    private boolean checkPhoneLength(int phone) {
        return phone > 6;
    }

    ProfileSpinnerAdapter adapterRates;

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {

        if (ProfileFragment.this.isDetached() || ProfileFragment.this.isRemoving()
                || !ProfileFragment.this.isVisible())
            return;
        switch (Tag) {
            case WebServiceConstant.LawyerRates:


                lawyerRatesResponse = (ArrayList<LawyerRateResponse>) result;

                /*
                *    ArrayList<LawyerRateResponse>  mResultList = (ArrayList<LawyerRateResponse>) result;
                lawyerRatesResponse =mResultList;
                lawyerRatesResponse.add()*/

                adapterRates = new ProfileSpinnerAdapter(getDockActivity(),
                        R.layout.spinner_item_activities, R.id.txt,
                        lawyerRatesResponse, prefHelper, getMainActivity(), WebServiceConstant.LawyerRates
//                        ,
//                        getResources().getString(R.string.select_fees)
                );

                spinnerRates.setAdapter(adapterRates);
                spinnerRates.setOnItemSelectedListener(this);

//                spinnerRates.setPrompt("Select Fees");
                for (int i = 0; i < lawyerRatesResponse.size(); i++) {
                    if (lawyerRatesResponse.get(i).getId() == prefHelper.getUser().getFees()) {
                        spinnerRates.setSelection(i);
                    }
                }
                break;

            case WebServiceConstant.LawyerAffiliation:

                lawyerAffiliationResponse = (ArrayList<LawyerRateResponse>) result;

                ProfileSpinnerAdapter adapterAffiliation = new ProfileSpinnerAdapter(getDockActivity(),
                        R.layout.spinner_item_activities, R.id.txt, lawyerAffiliationResponse, prefHelper,
                        getMainActivity(), WebServiceConstant.LawyerAffiliation);
                spinnerAffiliation.setAdapter(adapterAffiliation);

                spinnerAffiliation.setOnItemSelectedListener(this);

                for (int i = 0; i < lawyerAffiliationResponse.size(); i++) {
                    if (lawyerAffiliationResponse.get(i).getId() == prefHelper.getUser().getAffilationId()) {
                        spinnerAffiliation.setSelection(i);
                    }
                }

                break;

            case WebServiceConstant.LawyerExperience:

                lawyerExperienceResponse = (ArrayList<LawyerRateResponse>) result;

                ProfileSpinnerAdapter adapterExperince = new ProfileSpinnerAdapter(getDockActivity(),
                        R.layout.spinner_item_activities, R.id.txt, lawyerExperienceResponse, prefHelper, getMainActivity(), WebServiceConstant.LawyerExperience);
                spinnerExp.setAdapter(adapterExperince);
                spinnerExp.setOnItemSelectedListener(this);


                for (int i = 0; i < lawyerExperienceResponse.size(); i++) {
                    if (lawyerExperienceResponse.get(i).getId() == prefHelper.getUser().getExperienceId()) {
                        spinnerExp.setSelection(i);
                    }
                }

                break;
        }
    }


    @Override
    public void setImage(String imagePath, String thumbnail) {

        if (imagePath != null) {

            try {
                profilePic = new Compressor(getDockActivity()).compressToFile(new File(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            profilePath = imagePath;

            imageLoader.displayImage("file:///" + thumbnail, imgviewProfile);
            cameraIcon.setVisibility(View.GONE);
        }

    }

    @Override
    public void setFilePath(String filePath) {

    }

    @Override
    public void setVideo(String filePath, String extension, String thumbnail) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (profilePath != null && !profilePath.isEmpty() && prefHelper.getUser().getImageUrl() == null) {
            imageLoader.displayImage("file:///" + profilePath, imgviewProfile);
            cameraIcon.setVisibility(View.GONE);
        } else {
            imageLoader.displayImage(profilePath, imgviewProfile);
            cameraIcon.setVisibility(View.GONE);
        }

       /* spinnerRates.setSelection(spinnerRates.getSelectedItemPosition());
        spinnerAffiliation.setSelection(spinnerAffiliation.getSelectedItemPosition());
        spinnerExp.setSelection(spinnerExp.getSelectedItemPosition());*/
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if (adapterView == spinnerRates) {
            Log.e(TAG, "onItemSelected: spinnerRates pos:" + position);

            if (lawyerRatesResponse.get(position).getId() != null)
                selectedRateId = String.valueOf(lawyerRatesResponse.get(position).getId());
            else
                selectedRateId = null;


        } else if (adapterView == spinnerAffiliation) {
            Log.e(TAG, "onItemSelected: spinnerAffiliation pos:" + position);
            if (lawyerAffiliationResponse.get(position).getId() != null)
                selectedAffiliationId = String.valueOf(lawyerAffiliationResponse.get(position).getId());
            else
                selectedAffiliationId = null;

        } else if (adapterView == spinnerExp) {
            Log.e(TAG, "onItemSelected: spinnerExp pos:" + position);
            if (lawyerExperienceResponse.get(position).getId() != null)

                selectedExperienceId = String.valueOf(lawyerExperienceResponse.get(position).getId());
            else
                selectedExperienceId = null;


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.e(TAG, "onNothingSelected");

    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void getStoragePermission() {
        CameraHelper.uploadPhotoDialog(getMainActivity());
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showRationaleForStorage(final PermissionRequest request) {
        new AlertDialog.Builder(getDockActivity())
                .setMessage(R.string.permission_storage_rationale)
                .setPositiveButton(R.string.button_allow, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        request.proceed();
                    }
                })
                .setNegativeButton(R.string.button_deny, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        request.cancel();
                    }
                })
                .show();
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showDeniedForStorage() {
        showSnackBar(getResources().getString(R.string.permission_storage_denied));
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showNeverAskForStorage() {
        showSnackBar(getResources().getString(R.string.permission_storage_neverask));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        ProfileFragmentPermissionsDispatcher.onRequestPermissionsResult(ProfileFragment.this, requestCode, grantResults);
    }

    private void showSnackBar(String msg) {
        snackbar = Snackbar.make(rootView, msg, Snackbar.LENGTH_LONG);
        snackbar.setAction("Settings", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startInstalledAppDetailsActivity(getDockActivity());
            }
        });
        snackbar.show();
    }

    private void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }


}
