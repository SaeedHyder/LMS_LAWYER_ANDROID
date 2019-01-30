package com.ingic.lmslawyer.activities;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.google.firebase.iid.FirebaseInstanceId;
import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.constants.AppConstant;
import com.ingic.lmslawyer.constants.WebServiceConstant;
import com.ingic.lmslawyer.entities.ResponseWrapper;
import com.ingic.lmslawyer.entities.User;
import com.ingic.lmslawyer.fragments.NotificationListingFragment;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.fragments.home.MainHomeFragment;
import com.ingic.lmslawyer.fragments.login.MainLoginFragment;
import com.ingic.lmslawyer.global.SideMenuChooser;
import com.ingic.lmslawyer.global.SideMenuDirection;
import com.ingic.lmslawyer.helpers.InternetHelper;
import com.ingic.lmslawyer.helpers.ScreenHelper;
import com.ingic.lmslawyer.helpers.ServiceHelper;
import com.ingic.lmslawyer.helpers.UIHelper;
import com.ingic.lmslawyer.interfaces.webServiceResponseLisener;
import com.ingic.lmslawyer.residemenu.ResideMenu;
import com.ingic.lmslawyer.retrofit.WebService;
import com.ingic.lmslawyer.retrofit.WebServiceFactory;
import com.ingic.lmslawyer.ui.views.TitleBar;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenFile;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ChosenImages;
import com.kbeanie.imagechooser.api.ChosenVideo;
import com.kbeanie.imagechooser.api.ChosenVideos;
import com.kbeanie.imagechooser.api.FileChooserListener;
import com.kbeanie.imagechooser.api.FileChooserManager;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;
import com.kbeanie.imagechooser.api.VideoChooserListener;
import com.kbeanie.imagechooser.api.VideoChooserManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ingic.lmslawyer.constants.WebServiceConstant.GetProfile;

//import com.ingic.lmslawyer.fragments.LawyerFilterFragment;


public class MainActivity extends DockActivity implements OnClickListener, ImageChooserListener, FileChooserListener, VideoChooserListener ,webServiceResponseLisener {
    private final static String TAG = "ICA";
    public TitleBar titleBar;
    @BindView(R.id.sideMneuFragmentContainer)
    public FrameLayout sideMneuFragmentContainer;
    @BindView(R.id.header_main)
    TitleBar header_main;
    @BindView(R.id.mainFrameLayout)
    FrameLayout mainFrameLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    ImageSetter imageSetter;
    private MainActivity mContext;
    private boolean loading;
    private ResideMenu resideMenu;
    private float lastTranslate = 0.0f;
    private String sideMenuType;
    private String sideMenuDirection;
    private ImageChooserManager imageChooserManager;
    private String filePath;
    private int chooserType;
    private boolean isActivityResultOver = false;
    private String originalFilePath;
    private String thumbnailFilePath;
    private String thumbnailSmallFilePath;
    private FileChooserManager fm;
    private VideoChooserManager videoManager;
    private BroadcastReceiver blockUserBroadcast, updateProfileBroadCast;
    private String refreshToken = "";
    private boolean activityVisible = false;

    public boolean wasPaused = false;

    private ServiceHelper serviceHelper;
    private WebService webService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dock);
        ButterKnife.bind(this);
        titleBar = header_main;
        // setBehindContentView(R.layout.fragment_frame);
        mContext = this;
        Log.i("Screen Density", ScreenHelper.getDensity(this) + "");

        sideMenuType = SideMenuChooser.DRAWER.getValue();
        sideMenuDirection = SideMenuDirection.RIGHT.getValue();

        settingSideMenu(false, sideMenuType, sideMenuDirection);

        if (webService == null) {
            webService = WebServiceFactory.getWebServiceInstanceWithCustomInterceptor(getDockActivity(),
                    WebServiceConstant.SERVICE_URL);
        }

        if (serviceHelper == null) {
            serviceHelper = new ServiceHelper(this, getDockActivity(), webService);
        }

        titleBar.setMenuButtonListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                implementedInBeta();
               /* if (sideMenuType.equals(SideMenuChooser.DRAWER.getValue()) && getDrawerLayout() != null) {
                    if (sideMenuDirection.equals(SideMenuDirection.LEFT.getValue())) {
                        drawerLayout.openDrawer(Gravity.LEFT);
                    } else {
                        drawerLayout.openDrawer(Gravity.RIGHT);
                    }
                } else {
                    resideMenu.openMenu(sideMenuDirection);
                }*/

            }
        });

        titleBar.setBackButtonListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (loading) {
                    UIHelper.showLongToastInCenter(getApplicationContext(),
                            R.string.message_wait);
                } else {
                    Fragment frag = getSupportFragmentManager().findFragmentById(getDockFrameLayoutId());
                   /* if (frag instanceof MainMyCaseDetailsFragment) {
                        popFragment();
                    }*/
                    popFragment();
                    UIHelper.hideSoftKeyboard(getApplicationContext(),
                            titleBar);
                }
            }
        });

        titleBar.setNotificationButtonListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (loading) {
                    UIHelper.showLongToastInCenter(getApplicationContext(),
                            R.string.message_wait);
                } else {
                    addDockableFragment(NotificationListingFragment.newInstance(), "NotificationListingFragment");
                }
            }
        });

        if (savedInstanceState == null) {
            //initFragment();
        }
        setBlockUserBroadCast();
        setUpdateProfileBroadCast();
       /* if (getIntent() != null && getIntent().getStringExtra(AppConstant.ACTION_TYPE) != null) {
            if (getIntent().getStringExtra(AppConstant.ACTION_TYPE).equals(AppConstant.ACCOUNT_REJECT)) {
                logoutApi();
            } else
                if (getIntent().getStringExtra(AppConstant.ACTION_TYPE).equals(AppConstant.ACCOUNT_APPROVED)) {
                getProfileApi();
            }
        }*/
        getProfileApi();
    }

    private void setBlockUserBroadCast() {
        blockUserBroadcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e(TAG, "blockUserBroadcast: ***  blockUserBroadcast Call ***");
                if (InternetHelper.CheckInternetConectivityandShowToast(MainActivity.this)) {
                    logoutApi();
                }
            }
        };
    }

    private void setUpdateProfileBroadCast() {
        updateProfileBroadCast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e(TAG, "updateProfileBroadCast: ***  updateProfileBroadCast Call ***");
                if (InternetHelper.CheckInternetConectivityandShowToast(MainActivity.this)) {
                    getProfileApi();
                }
            }
        };
    }

    private void getProfileApi() {

        if (prefHelper.getUser() == null) return;
        WebService webService = WebServiceFactory
                .getWebServiceInstanceWithDefaultInterceptor(MainActivity.this, WebServiceConstant.SERVICE_URL);

        webService.getUserProfile(prefHelper.getUser().getToken()).enqueue(new Callback<ResponseWrapper<User>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<User>> call, Response<ResponseWrapper<User>> response) {
                if (MainActivity.this.isDestroyed() || MainActivity.this.isFinishing()) {
                    //fragment is no longer available here, ignore response
                    return;
                }
                if (response == null || response.body() == null || response.body().getResponse() == null) {
                } else if (response.body().getResponse().equals(WebServiceConstant.SUCCESS_RESPONSE_CODE)) {
                    prefHelper.putUser(response.body().getResult());
                }

            }

            @Override
            public void onFailure(Call<ResponseWrapper<User>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void logoutApi() {
        refreshToken = FirebaseInstanceId.getInstance().getToken();

        WebService webService = WebServiceFactory
                .getWebServiceInstanceWithDefaultInterceptor(MainActivity.this, WebServiceConstant.SERVICE_URL);

        webService.logout(prefHelper.getUser().getToken(),
                refreshToken,
                AppConstant.DEVICE_TYPE,
                "211")
                .enqueue(new Callback<ResponseWrapper>() {
                    @Override
                    public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {
                        if (MainActivity.this.isDestroyed() || MainActivity.this.isFinishing()) {
                            //fragment is no longer available here, ignore response
                            return;
                        }

                        if (response == null || response.body() == null || response.body().getResponse() == null) {
                            UIHelper.showLongToastInCenter(MainActivity.this, getResources().getString(R.string.null_response));
                        } else if (response.body().getResponse().equals(WebServiceConstant.SUCCESS_RESPONSE_CODE)) {
//                            ShortcutBadger.removeCount(MainActivity.this);
                            UIHelper.showShortToastInCenter(MainActivity.this, getString(R.string.block_msg));
                            prefHelper.setLoginStatus(false);
//                            MainActivity.this.popBackStackTillEntry(0);
//                            MainActivity.this.replaceDockableFragment(new MainLoginFragment(), MainLoginFragment.class.getSimpleName());

                            if (isActivityVisible()) {
                                MainActivity.this.popBackStackTillEntry(0);
                                MainActivity.this.replaceDockableFragment(new MainLoginFragment(), MainLoginFragment.class.getSimpleName());
//                                UIHelper.showLongToastInCenter(MainActivity.this, response.body().getMessage());
                            }
                        } else if (response.body().getResponse().equals(WebServiceConstant.FAILURE_RESPONSE_CODE)) {
                            UIHelper.showLongToastInCenter(MainActivity.this, response.body().getMessage());
                        } else {
                            //UIHelper.showLongToastInCenter(MainActivity.this, getResources().getString(R.string.failure_response));
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                        UIHelper.showLongToastInCenter(MainActivity.this, t.getMessage());
                    }
                });
    }

    public View getDrawerView() {
        return getLayoutInflater().inflate(getSideMenuFrameLayoutId(), null);
    }

    public void settingSideMenu(boolean isNewsFeed, String type, String direction) {

        if (type.equals(SideMenuChooser.DRAWER.getValue())) {


            DrawerLayout.LayoutParams params = new DrawerLayout.LayoutParams((int) getResources().getDimension(R.dimen.x300), (int) DrawerLayout.LayoutParams.MATCH_PARENT);


            if (direction.equals(SideMenuDirection.LEFT.getValue())) {
                params.gravity = Gravity.LEFT;
                sideMneuFragmentContainer.setLayoutParams(params);
            } else {
                params.gravity = Gravity.RIGHT;
                sideMneuFragmentContainer.setLayoutParams(params);
            }
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
          /*  if (!isNewsFeed) {
                lawyerFilterFragment = LawyerFilterFragment.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager()
                        .beginTransaction();
                transaction.replace(getSideMenuFrameLayoutId(), lawyerFilterFragment).commit();
            } else {
                newsFeedFilterFragment = NewsFeedFilterFragment.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager()
                        .beginTransaction();
                transaction.replace(getSideMenuFrameLayoutId(), newsFeedFilterFragment).commit();
            }*/

            drawerLayout.closeDrawers();
        } else {
            resideMenu = new ResideMenu(this);
            resideMenu.attachToActivity(this);
            resideMenu.setMenuListener(getMenuListener());
            resideMenu.setScaleValue(0.52f);

            setResideMenuItemDirection(direction);
        }
    }

    private boolean isApplicationInBackground() {
        final ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningTaskInfo> tasks = manager.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            final ComponentName topActivity = tasks.get(0).topActivity;
            return !topActivity.getPackageName().equals(getPackageName());
        }
        return false;
    }


    @Override
    protected void onPause() {
        super.onPause();
        activityPaused();

        wasPaused = true;
        if (isApplicationInBackground()) {

        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        /*if(wasPaused) {
            wasPaused = false;
        }*/

        activityResumed();
        try {
            this.registerReceiver(blockUserBroadcast, new IntentFilter(AppConstant.BLOCK_USER_BROADCAST));
            this.registerReceiver(updateProfileBroadCast, new IntentFilter(AppConstant.UPDATE_PROFILE_BROADCAST));

        /*    if (savedInstanceState == null)
                initFragment();
            else {
                //resume last running fragment
                resumeLastFragment();
            }*/
            initFragment();

        } catch (Exception ex) {
        }
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(blockUserBroadcast);
        this.unregisterReceiver(updateProfileBroadCast);
        super.onDestroy();
    }

    private void setResideMenuItemDirection(String direction) {

      /*  if (direction.equals(SideMenuDirection.LEFT.getValue())) {

            LawyerFilterFragment leftSideMenuFragment = LawyerFilterFragment.newInstance();
            resideMenu.addMenuItem(leftSideMenuFragment, "LeftSideMenuFragment", direction);

        } else if (direction.equals(SideMenuDirection.RIGHT.getValue())) {

            LawyerFilterFragment rightSideMenuFragment = LawyerFilterFragment.newInstance();
            resideMenu.addMenuItem(rightSideMenuFragment, "RightSideMenuFragment", direction);

        }*/

    }

    private int getSideMenuFrameLayoutId() {
        return R.id.sideMneuFragmentContainer;

    }


    public void initFragment() {
        getSupportFragmentManager().addOnBackStackChangedListener(getListener());
        if (prefHelper.isLogin() && !wasPaused) {
           // serviceHelper.enqueueCall(webService.getProfile(prefHelper.getUser().getToken()+""), GetProfile);
            replaceDockableFragment(new MainHomeFragment(), "HomeFragment");
        } else if (!prefHelper.isLogin()) {
//            replaceDockableFragment(new BudgetFragment(), "BudgetFragment");
            replaceDockableFragment(new MainLoginFragment(), "MainLoginFragment");
        }

        wasPaused = false;
    }

    private FragmentManager.OnBackStackChangedListener getListener() {
        FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                FragmentManager manager = getSupportFragmentManager();

                if (manager != null) {
                    BaseFragment currFrag = (BaseFragment) manager.findFragmentById(getDockFrameLayoutId());
                    if (currFrag != null) {
                        currFrag.fragmentResume();
                    }
                }
            }
        };

        return result;
    }

    @Override
    public void onLoadingStarted() {

        if (mainFrameLayout != null) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            mainFrameLayout.setVisibility(View.VISIBLE);
            if (progressBar != null) {
                progressBar.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
            loading = true;
        }
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    @Override
    public void onLoadingFinished() {
        mainFrameLayout.setVisibility(View.VISIBLE);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        if (progressBar != null) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
        loading = false;

    }

    @Override
    public void onProgressUpdated(int percentLoaded) {

    }

    @Override
    public int getDockFrameLayoutId() {
        return R.id.mainFrameLayout;
    }

    @Override
    public void onMenuItemActionCalled(int actionId, String data) {

    }

    @Override
    public void setSubHeading(String subHeadText) {

    }

    @Override
    public boolean isLoggedIn() {
        return false;
    }

    @Override
    public void hideHeaderButtons(boolean leftBtn, boolean rightBtn) {
    }

    @Override
    public void onBackPressed() {
        if (loading) {
            UIHelper.showLongToastInCenter(getApplicationContext(),
                    R.string.message_wait);
        } else
            super.onBackPressed();

    }
/*

    @Override
    public LawyerFilterFragment getLawyerFilterSideMenu() {
        return lawyerFilterFragment;
    }

    @Override
    public NewsFeedFilterFragment getNewsFeedFilterSideMenu() {
        return newsFeedFilterFragment;
    }
*/

    @Override
    public void onClick(View view) {

    }

    private void notImplemented() {
        UIHelper.showLongToastInCenter(this, "Coming Soon");
    }

    protected void implementedInBeta() {
        UIHelper.showLongToastInCenter(this, getResources().getString(R.string.will_be_beta));
    }

    private void reinitializeImageChooser() {
        imageChooserManager = new ImageChooserManager(this, chooserType, "Test");
        Bundle bundle = new Bundle();
        bundle.putBoolean(Intent.EXTRA_ALLOW_MULTIPLE, true);
        imageChooserManager.setExtras(bundle);
        imageChooserManager.setImageChooserListener(this);
        imageChooserManager.reinitialize(filePath);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, "Saving Stuff");
        Log.i(TAG, "File Path: " + filePath);
        Log.i(TAG, "Chooser Type: " + chooserType);
        outState.putBoolean("activity_result_over", isActivityResultOver);
        outState.putInt("chooser_type", chooserType);
        outState.putString("media_path", filePath);
        outState.putString("orig", originalFilePath);
        outState.putString("thumb", thumbnailFilePath);
        outState.putString("thumbs", thumbnailSmallFilePath);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("chooser_type")) {
                chooserType = savedInstanceState.getInt("chooser_type");
            }
            if (savedInstanceState.containsKey("media_path")) {
                filePath = savedInstanceState.getString("media_path");
            }
            if (savedInstanceState.containsKey("activity_result_over")) {
                isActivityResultOver = savedInstanceState.getBoolean("activity_result_over");
                originalFilePath = savedInstanceState.getString("orig");
                thumbnailFilePath = savedInstanceState.getString("thumb");
                thumbnailSmallFilePath = savedInstanceState.getString("thumbs");
            }
        }
        Log.i(TAG, "Restoring Stuff");
        Log.i(TAG, "File Path: " + filePath);
        Log.i(TAG, "Chooser Type: " + chooserType);
        Log.i(TAG, "Activity Result Over: " + isActivityResultOver);
        if (isActivityResultOver) {
            //populateData();
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void chooseImage() {
        chooserType = ChooserType.REQUEST_PICK_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_PICK_PICTURE, "Test");
        Bundle bundle = new Bundle();
        bundle.putBoolean(Intent.EXTRA_ALLOW_MULTIPLE, true);
        imageChooserManager.setExtras(bundle);
        imageChooserManager.setImageChooserListener(this);
        imageChooserManager.clearOldFiles();
        try {
            //pbar.setVisibility(View.VISIBLE);
            filePath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void takePicture() {
        chooserType = ChooserType.REQUEST_CAPTURE_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_CAPTURE_PICTURE, "Test");
        imageChooserManager.setImageChooserListener(this);
        try {
            //pbar.setVisibility(View.VISIBLE);
            filePath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pickFile() {
        chooserType = ChooserType.REQUEST_PICK_FILE;
        fm = new FileChooserManager(this, getResources().getString(R.string.app_name));
        fm.setMimeType("application/pdf");
        fm.setFileChooserListener(this);
        try {
            fm.choose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void chooseVideo() {
        chooserType = ChooserType.REQUEST_PICK_VIDEO;
        videoManager = new VideoChooserManager(this,
                ChooserType.REQUEST_PICK_VIDEO, getResources().getString(R.string.app_name));
        Bundle bundle = new Bundle();
        bundle.putBoolean(Intent.EXTRA_ALLOW_MULTIPLE, true);
        videoManager.setExtras(bundle);
        videoManager.setVideoChooserListener(this);
        videoManager.clearOldFiles();
        try {
            //pbar.setVisibility(View.VISIBLE);
            filePath = videoManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "OnActivityResult");
        Log.i(TAG, "File Path : " + filePath);
        Log.i(TAG, "Chooser Type: " + chooserType);

        if (requestCode == ChooserType.REQUEST_PICK_FILE && resultCode == RESULT_OK) {
            if (fm == null) {
                fm = new FileChooserManager(this);
                fm.setFileChooserListener(this);
            }
            Log.i(TAG, "Probable file size: " + fm.queryProbableFileSize(data.getData(), this));
            fm.submit(requestCode, data);
        }


        if (resultCode == RESULT_OK
                && (requestCode == ChooserType.REQUEST_PICK_PICTURE || requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)) {
            if (imageChooserManager == null) {
                reinitializeImageChooser();
            }
            imageChooserManager.submit(requestCode, data);
        } else if (resultCode == RESULT_OK && requestCode == ChooserType.REQUEST_PICK_VIDEO) {
            if (videoManager == null) {
                reinitializeVideoChooser();
            }
            videoManager.submit(requestCode, data);
        }
    }

    private void reinitializeVideoChooser() {
        videoManager = new VideoChooserManager(this, chooserType, getResources().getString(R.string.app_name));
        Bundle bundle = new Bundle();
        bundle.putBoolean(Intent.EXTRA_ALLOW_MULTIPLE, true);
        videoManager.setExtras(bundle);
        videoManager.setVideoChooserListener(this);
        videoManager.reinitialize(filePath);
    }


    @Override
    public void onImageChosen(final ChosenImage image) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Log.i(TAG, "Chosen Image: O - " + image.getFilePathOriginal());
                Log.i(TAG, "Chosen Image: T - " + image.getFileThumbnail());
                Log.i(TAG, "Chosen Image: Ts - " + image.getFileThumbnailSmall());
                isActivityResultOver = true;
                originalFilePath = image.getFilePathOriginal();
                thumbnailFilePath = image.getFileThumbnail();
                thumbnailSmallFilePath = image.getFileThumbnailSmall();
                //pbar.setVisibility(View.GONE);
                if (image != null) {
                    Log.i(TAG, "Chosen Image: Is not null");

                    imageSetter.setImage(originalFilePath, thumbnailFilePath);
                    //loadImage(imageViewThumbnail, image.getFileThumbnail());
                } else {
                    Log.i(TAG, "Chosen Image: Is null");
                }
            }
        });
    }

    @Override
    public void onFileChosen(final ChosenFile file) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // progressBar.setVisibility(View.INVISIBLE);
                imageSetter.setFilePath(file.getFilePath());
                populateFileDetails(file);
            }
        });
    }

    private void populateFileDetails(ChosenFile file) {
        StringBuffer text = new StringBuffer();
        text.append("File name: " + file.getFileName() + "\n\n");
        text.append("File path: " + file.getFilePath() + "\n\n");
        text.append("Mime type: " + file.getMimeType() + "\n\n");
        text.append("File extn: " + file.getExtension() + "\n\n");
        text.append("File size: " + file.getFileSize() / 1024 + "KB");

        //Toast.makeText(this,text.toString(),Toast.LENGTH_LONG).show();
    }


    @Override
    public void onVideoChosen(final ChosenVideo chosenVideo) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "Chosen Image: O - " + chosenVideo.getVideoFilePath());
                Log.i(TAG, "Chosen Image: T - " + chosenVideo.getThumbnailPath());
                Log.i(TAG, "Chosen Image: Ts - " + chosenVideo.getThumbnailSmallPath());
                isActivityResultOver = true;
                originalFilePath = chosenVideo.getVideoFilePath();
                thumbnailFilePath = chosenVideo.getThumbnailPath();
                thumbnailSmallFilePath = chosenVideo.getThumbnailSmallPath();
                //pbar.setVisibility(View.GONE);
                if (chosenVideo != null) {
                    Log.i(TAG, "Chosen Image: Is not null");


                    imageSetter.setVideo(originalFilePath, chosenVideo.getExtension(), thumbnailFilePath);
                    //loadImage(imageViewThumbnail, image.getFileThumbnail());
                } else {
                    Log.i(TAG, "Chosen Video: Is null");
                }
            }
        });
    }

    @Override
    public void onError(final String reason) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Log.i(TAG, "OnError: " + reason);
                // pbar.setVisibility(View.GONE);
                UIHelper.showLongToastInCenter(MainActivity.this, reason);

            }
        });
    }

    @Override
    public void onVideosChosen(final ChosenVideos chosenVideos) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "On Images Chosen: " + chosenVideos.size());
                onVideoChosen(chosenVideos.getImage(0));
            }
        });
    }

    @Override
    public void onImagesChosen(final ChosenImages images) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "On Images Chosen: " + images.size());
                onImageChosen(images.getImage(0));
            }
        });
    }

    public void setImageSetter(ImageSetter imageSetter) {
        this.imageSetter = imageSetter;
    }

    public interface ImageSetter {

        public void setImage(String imagePath, String thumbnail);

        public void setFilePath(String filePath);

        public void setVideo(String filePath, String extension, String thumbnail);

    }

    public boolean isActivityVisible() {
        return activityVisible;
    }

    public void activityResumed() {
        activityVisible = true;
    }

    public void activityPaused() {
        activityVisible = false;
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        switch (Tag){
            case GetProfile:
                User user=(User)result;
                prefHelper.putUser(user);
            //    replaceDockableFragment(new MainHomeFragment(), "HomeFragment");
                break;
        }
    }

    @Override
    public void ResponseFailure(String tag) {

    }


}
