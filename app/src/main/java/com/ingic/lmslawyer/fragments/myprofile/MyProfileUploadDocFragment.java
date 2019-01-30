package com.ingic.lmslawyer.fragments.myprofile;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.google.gson.Gson;
import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.activities.MainActivity;
import com.ingic.lmslawyer.constants.AppConstant;
import com.ingic.lmslawyer.constants.WebServiceConstant;
import com.ingic.lmslawyer.entities.Profile.SelectedStringsEnt;
import com.ingic.lmslawyer.entities.User;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.helpers.SimpleDividerItemDecoration;
import com.ingic.lmslawyer.helpers.UIHelper;
import com.ingic.lmslawyer.helpers.Util;
import com.ingic.lmslawyer.interfaces.OnItemCancelClick;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;
import com.ingic.lmslawyer.ui.adapters.myadapters.AttachedDocsAdapter;
import com.ingic.lmslawyer.ui.views.TitleBar;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * A simple {@link Fragment} subclass.
 */
@RuntimePermissions
public class MyProfileUploadDocFragment extends BaseFragment implements MainActivity.ImageSetter {


    @BindView(R.id.tvListTitle)
    TextView tvListTitle;
    @BindView(R.id.rvDocList)
    RecyclerView rvDocList;
    Unbinder unbinder;
    private RecyclerViewListAdapter adapter;

    private String mCurrentPhotoPath;
    private File fileUrl;
    private File profilePic;

    private SelectedStringsEnt selectedData;
    private String jsonString;
    ArrayList<FileType> fileStringList = new ArrayList<>();
    ArrayList<FileType> fileStringListShow = new ArrayList<>();
//    ArrayList<String> fileExtensionList = new ArrayList<>();

    private Snackbar snackbar;
    View rootView;

    public MyProfileUploadDocFragment() {
        // Required empty public constructor
    }

    public static MyProfileUploadDocFragment newInstance(SelectedStringsEnt data) {
        Bundle args = new Bundle();
        args.putString(AppConstant.ProfileDetail, new Gson().toJson(data));
        MyProfileUploadDocFragment fragment = new MyProfileUploadDocFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_my_profile_upload_doc, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        if (getArguments() != null) {
            jsonString = getArguments().getString(AppConstant.ProfileDetail);
        }
        if (jsonString != null) {
            selectedData = new Gson().fromJson(jsonString, SelectedStringsEnt.class);
        }

        initAdapter();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getMainActivity().setImageSetter(this);

        //setData();
    }

    private void setData() {
        fileStringListShow = new ArrayList<>();

        for (String item : prefHelper.getUser().getResumeDocuments()) {

            fileUrl = new File(item);
            fileStringListShow.add(new FileType("", fileUrl, ""));
        }


        rvDocList.setLayoutManager(new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false));
        rvDocList.addItemDecoration(new SimpleDividerItemDecoration(getDockActivity()));
        adapter.addAll(fileStringListShow);

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.goneTitleBar();
    }


    private void initAdapter() {
//        adapter = new MyProfileUploadingDocAdapter(getDockActivity());
//        rvDocList.setLayoutManager(new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false));
//        rvDocList.addItemDecoration(new SimpleDividerItemDecoration(getDockActivity()));
//        rvDocList.setAdapter(adapter);
//        ArrayList<String> list = new ArrayList<>();
//        list.add("Resume.Docs");
//        list.add("Image.jpeg");
//        adapter.addAll(list);


        adapter = new AttachedDocsAdapter(getDockActivity(), new OnItemCancelClick() {
            @Override
            public void itemCrossClick(FileType item) {
                fileStringList.remove(item);
                adapter.addAll(fileStringList);
            }
        });
        rvDocList.setLayoutManager(new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false));
        rvDocList.addItemDecoration(new SimpleDividerItemDecoration(getDockActivity()));

//        rvDocList.setLayoutManager(new GridLayoutManager(getDockActivity(), 3));
        rvDocList.setAdapter(adapter);
    }


    @OnClick({R.id.imgBack, R.id.btnUploadFile, R.id.btnSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                getDockActivity().popFragment();
                break;
            case R.id.btnUploadFile:
                if (fileStringList != null && fileStringList.size() == 2) {
                    UIHelper.showShortToastInCenter(getDockActivity(), "You can't select more than 2 attachments");
                    return;
                }
                if (Util.doubleClickCheck())
                    MyProfileUploadDocFragmentPermissionsDispatcher
                            .getStoragePermissionWithPermissionCheck(MyProfileUploadDocFragment.this);


                break;
            case R.id.btnSubmit:
                callService();
                break;
        }
    }

    private void CameraOptionsSheetDialog() {
        final String[] stringItems = {getResources().getString(R.string.drive),
                getResources().getString(R.string.camera),
                getResources().getString(R.string.gallery)
        };
        final ActionSheetDialog dialog = new ActionSheetDialog(getDockActivity(), stringItems, null);
        dialog.title(getResources().getString(R.string.upload_file))
                .titleTextSize_SP(14.5f)
                .cancelText(getResources().getString(android.R.string.cancel))
                .show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        getMainActivity().pickFile();
                        break;
                    case 1:
//                        uploadFromCamera();
//                        CameraHelper.uploadFromCamera(SubmitCaseFragment.this);
                        getMainActivity().takePicture();

                        break;
                    case 2:
//                        CameraHelper.uploadFromGallery(SubmitCaseFragment.this);
                        getMainActivity().chooseImage();

                        break;
                }
                dialog.dismiss();
            }
        });
    }

    private void callService() {


        ArrayList<MultipartBody.Part> lawyer_resume = new ArrayList<>();
        if (fileStringList != null) {
            for (FileType fileObj : fileStringList) {

                lawyer_resume.add(MultipartBody.Part.createFormData("lawyer_resume[]",
                        fileObj.getFile().getName(), RequestBody.create(MediaType.parse("multipart/form-data"), fileObj.getFile())));

            }
        }

        RequestBody full_name = RequestBody.create(MediaType.parse("text/plain"), selectedData.getProfileData().getFullName());
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), selectedData.getProfileData().getEmail());
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), selectedData.getProfileData().getPhoneNumber());
        RequestBody fees = RequestBody.create(MediaType.parse("text/plain"), selectedData.getProfileData().getFees());
        RequestBody bio = RequestBody.create(MediaType.parse("text/plain"), selectedData.getProfileData().getBio());
        RequestBody academics = RequestBody.create(MediaType.parse("text/plain"), selectedData.getProfileData().getAcademics());
        RequestBody affilation_id = RequestBody.create(MediaType.parse("text/plain"), selectedData.getProfileData().getAffilations());
        RequestBody award = RequestBody.create(MediaType.parse("text/plain"), selectedData.getProfileData().getAwards());
        RequestBody experience_id = RequestBody.create(MediaType.parse("text/plain"), selectedData.getProfileData().getExperince());
        RequestBody specialization_ids = RequestBody.create(MediaType.parse("text/plain"), selectedData.getSpecializationString());
        RequestBody law_ids = RequestBody.create(MediaType.parse("text/plain"), selectedData.getLawFieldString());
        RequestBody lawyertype_ids = RequestBody.create(MediaType.parse("text/plain"), selectedData.getTypeOfLawyerString());
        RequestBody case_ids = RequestBody.create(MediaType.parse("text/plain"), selectedData.getTypeOfCaseServeString());
        RequestBody language_ids = RequestBody.create(MediaType.parse("text/plain"), selectedData.getSelectLanguageString());
        RequestBody location = RequestBody.create(MediaType.parse("text/plain"), selectedData.getProfileData().getLocation());
        RequestBody latitude = RequestBody.create(MediaType.parse("text/plain"), selectedData.getProfileData().getLatitude());
        RequestBody longitude = RequestBody.create(MediaType.parse("text/plain"), selectedData.getProfileData().getLongitude());

        MultipartBody.Part profile_image;
        if (selectedData.getProfileData().getProfile_image() != null) {

            profile_image = MultipartBody.Part.createFormData("profile_image",
                    selectedData.getProfileData().getProfile_image().getName(), RequestBody.create(MediaType.parse("image/*"), selectedData.getProfileData().getProfile_image()));
        } else {
            profile_image = MultipartBody.Part.createFormData("profile_image", "",
                    RequestBody.create(MediaType.parse("*/*"), ""));
        }


        serviceHelper.enqueueCall(webService.updateLawyerProfile(
                prefHelper.getUser().getToken(),
                full_name, email, phone, fees, bio, academics, affilation_id, award, experience_id, profile_image, lawyer_resume, specialization_ids, law_ids,
                lawyertype_ids, case_ids, language_ids, location, latitude, longitude), WebServiceConstant.updateProfile);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        switch (Tag) {
            case WebServiceConstant.updateProfile:
                //    UIHelper.showShortToastInCenter(getDockActivity(), message);
                User user = (User) result;
                prefHelper.putUser(user);
                openDialog();
//                getDockActivity().replaceDockableFragment(new MainMyCasesFragment());
                break;
            default:
                break;
        }
    }

    private void openDialog() {
        final Dialog dialog = new Dialog(getDockActivity(), R.style.DialogCustomTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_msg_okay);

        TextView tvText = (TextView) dialog.findViewById(R.id.tvText);
        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                getDockActivity().popBackStackTillEntry(1);
            }
        });
        dialog.setCancelable(false);
        dialog.show();

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_ HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "LMS User");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

/*    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && (requestCode == CameraHelper.CAMERA_REQUEST || requestCode == CameraHelper.GALLERY_REQUEST)) {

            fileUrl = CameraHelper.retrieveAndDisplayPicture(requestCode, resultCode, data, getDockActivity());
//        bitmapFile = null;
        } else if (resultCode == RESULT_OK && requestCode == CameraHelper.VIDEO_REQUEST) {
            CameraHelper.retrieveVideo(requestCode, resultCode, data, getDockActivity());
        }

//        adapter.addAll();
    }*/

    @Override
    public void setImage(String imagePath, String thumbnail) {
        if (imagePath != null) {
            fileUrl = new File(imagePath);
            fileStringList.add(new FileType(thumbnail, fileUrl, ""));
            adapter.addAll(fileStringList);
//            fileExtensionList.add("photo");
            //Toast.makeText(getDockActivity(),imagePath,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setFilePath(String filePath) {
        if (filePath != null) {
            fileUrl = new File(filePath);
            fileStringList.add(new FileType("", fileUrl, ""));
            adapter.addAll(fileStringList);
//            fileExtensionList.add("file");

        }
    }

    @Override
    public void setVideo(String filePath, String extension, String thumbnail) {
        //Do nothing
    }

/*    @Override
    public void setVideo(String thumbnailFilePath, String extension, String thumbnail) {
        if (thumbnailFilePath != null) {
            fileUrl = new File(thumbnailFilePath);
            fileStringList.add(new FileType(thumbnail, fileUrl, extension));
            adapter.addAll(fileStringList);
//            fileExtensionList.add("video");
            //Toast.makeText(getDockActivity(),imagePath,Toast.LENGTH_LONG).show();
        }
    }*/

    public class FileType {
        String fileThumbnail, extension, fileString;
        File file;

        public FileType(String fileThumbnail, File file, String extension) {
            this.fileThumbnail = fileThumbnail;
            this.file = file;
            this.extension = extension;

        }

        public FileType(String fileThumbnail, String extension) {
            this.fileThumbnail = fileThumbnail;
            this.extension = extension;
        }

        public FileType(String fileThumbnail, String File, String extension) {
            this.fileThumbnail = fileThumbnail;
            this.extension = extension;
            this.fileString = File;
        }

        public String getFileThumbnail() {
            return fileThumbnail;
        }

        public void setFileThumbnail(String fileThumbnail) {
            this.fileThumbnail = fileThumbnail;
        }

        public File getFile() {
            return file;
        }

        public String getFileString() {
            return fileString;
        }

        public void setFile(File file) {
            this.file = file;
        }

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }
    }


    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void getStoragePermission() {
        CameraOptionsSheetDialog();
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
        MyProfileUploadDocFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
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
