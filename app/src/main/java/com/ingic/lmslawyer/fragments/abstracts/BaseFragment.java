package com.ingic.lmslawyer.fragments.abstracts;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.EditText;

import com.andreabaccega.formedittextvalidator.Validator;
import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.activities.DockActivity;
import com.ingic.lmslawyer.activities.MainActivity;
import com.ingic.lmslawyer.constants.AppConstant;
import com.ingic.lmslawyer.constants.WebServiceConstant;
import com.ingic.lmslawyer.entities.User;
import com.ingic.lmslawyer.fragments.home.MainHomeFragment;
import com.ingic.lmslawyer.global.WebServiceConstants;
import com.ingic.lmslawyer.helpers.BasePreferenceHelper;
import com.ingic.lmslawyer.helpers.GPSTracker;
import com.ingic.lmslawyer.helpers.ServiceHelper;
import com.ingic.lmslawyer.helpers.UIHelper;
import com.ingic.lmslawyer.interfaces.LoadingListener;
import com.ingic.lmslawyer.interfaces.webServiceResponseLisener;
import com.ingic.lmslawyer.retrofit.WebService;
import com.ingic.lmslawyer.retrofit.WebServiceFactory;
import com.ingic.lmslawyer.ui.views.AnyEditTextView;
import com.ingic.lmslawyer.ui.views.TitleBar;


public abstract class BaseFragment extends Fragment implements webServiceResponseLisener {

    protected Handler handler = new Handler();


    protected BasePreferenceHelper prefHelper;

    protected WebService webService;
    protected WebService headerWebService;
    protected ServiceHelper serviceHelper;

    protected GPSTracker mGpsTracker;

    protected DockActivity myDockActivity;
    //private DockActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefHelper = new BasePreferenceHelper(getContext());


        mGpsTracker = new GPSTracker(getDockActivity());

        if (webService == null) {
            webService = WebServiceFactory.getWebServiceInstanceWithCustomInterceptor(getDockActivity(),
                    WebServiceConstant.SERVICE_URL);
        }
        if (headerWebService == null) {
            headerWebService = WebServiceFactory.getWebServiceInstanceWithCustomInterceptorandheader(getDockActivity(), WebServiceConstants.SERVICE_URL);
        }
        if (serviceHelper == null) {
            serviceHelper = new ServiceHelper(this, getDockActivity(), webService);
        }

        myDockActivity = getDockActivity();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getDockActivity().getDrawerLayout() != null) {
            getDockActivity().lockDrawer();
        }
        //	setTitleBar( ((MainActivity) getDockActivity()).titleBar );

		/*if(getDockActivity().getDrawerLayout() != null){
            getDockActivity().releaseDrawer();
		}*/
    }

    public void fragmentResume() {
        setTitleBar(((MainActivity) getDockActivity()).titleBar);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    protected void createClient() {
        // webService = WebServiceFactory.getInstanceWithBasicGsonConversion();

    }

    @Override
    public void onPause() {
        super.onPause();

        if (getDockActivity().getWindow() != null)
            if (getDockActivity().getWindow().getDecorView() != null)
                UIHelper.hideSoftKeyboard(getDockActivity(), getDockActivity()
                        .getWindow().getDecorView());

    }

    protected void loadingStarted() {

        if (getParentFragment() != null)
            ((LoadingListener) getParentFragment()).onLoadingStarted();
        else
            getDockActivity().onLoadingStarted();

        isLoading = true;
    }

    protected void loadingFinished() {

        if (getParentFragment() != null)
            ((LoadingListener) getParentFragment()).onLoadingFinished();
        else if (getDockActivity() != null)
            getDockActivity().onLoadingFinished();

        isLoading = false;
        // else
        // ( (LoadingListener) super.getParentFragment() ).onLoadingFinished();
    }

    //it will gives us instance of DockActivity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myDockActivity = (DockActivity) context;
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {

    }

    @Override
    public void ResponseFailure(String tag) {

    }

    protected DockActivity getDockActivity() {

		/*DockActivity activity = (DockActivity) getActivity();
        while ( activity == null ) {
			activity = (DockActivity) getActivity();
			try {
				Thread.sleep( 50 );
			} catch ( InterruptedException e ) {
				e.printStackTrace();
			}
		}*/
        return myDockActivity;

    }

    protected MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }


    protected TitleBar getTitleBar() {
        if(getMainActivity()!=null){
            return getMainActivity().titleBar;
        }
        return null;
    }

    public String getTitleName() {
        return "";
    }

    /**
     * This is called in the end to modify titlebar. after all changes.
     *
     * @param
     */
    public void setTitleBar(TitleBar titleBar) {
        titleBar.showTitleBar();
//        titleBar.setBackgroundColor(getResources().getColor(R.color.app_maroon));
//        titleBar.setHeadingColor(getResources().getColor(R.color.white));
        // titleBar.refreshListener();
    }

    /**
     * Gets the preferred height for each item in the ListView, in pixels, after
     * accounting for screen density. ImageLoader uses this value to resize
     * thumbnail images to match the ListView item height.
     *
     * @return The preferred height in pixels, based on the current theme.
     */
    protected int getListPreferredItemHeight() {
        final TypedValue typedValue = new TypedValue();

        // Resolve list item preferred height theme attribute into typedValue
        getActivity().getTheme().resolveAttribute(
                android.R.attr.listPreferredItemHeight, typedValue, true);

        // Create a new DisplayMetrics object
        final DisplayMetrics metrics = new DisplayMetrics();

        // Populate the DisplayMetrics
        getActivity().getWindowManager().getDefaultDisplay()
                .getMetrics(metrics);

        // Return theme value based on DisplayMetrics
        return (int) typedValue.getDimension(metrics);
    }

    protected String getStringTrimed(AnyEditTextView edtView) {
        return edtView.getText().toString().trim();
    }

    /**
     * This generic method to add validator to a text view should be used
     * FormEditText
     * <p>
     * Usage : Takes Array of AnyEditTextView ;
     *
     * @return void
     */
    protected void addEmptyStringValidator(AnyEditTextView... allFields) {

        for (AnyEditTextView field : allFields) {
            field.addValidator(new EmptyStringValidator());
        }

    }

    protected void notImplemented() {
        UIHelper.showLongToastInCenter(getActivity(), getResources().getString(R.string.coming_soon));
    }

    protected void implementedInBeta() {
        UIHelper.showLongToastInCenter(getActivity(), getResources().getString(R.string.coming_soon));
//        UIHelper.showLongToastInCenter(getActivity(), getResources().getString(R.string.will_be_beta));
    }

    protected void fillAllFieldsError() {
        UIHelper.showLongToastInCenter(getActivity(), "Please fill all fields");
    }

    protected boolean checkEmpty(EditText editText) {
        return editText.getText().toString().isEmpty();
    }

    protected void phoneNumberNotValid() {
        UIHelper.showLongToastInCenter(getActivity(), getResources().getString(R.string.error_phone_not_valid));
    }


    /*protected boolean checkEmpty(AppCompatEditText editText) {
        return editText.getText().toString().isEmpty();
    }*/
    protected boolean checkEmailPattern(EditText editText) {
        if (editText.getText().toString().matches(AppConstant.EMAIL_PATTERN)) {
            return true;
        } else {
            UIHelper.showShortToastInCenter(getDockActivity(), getResources().getString(R.string.error_email_address_not_valid));
            return false;
        }
    }


    protected boolean matchPassword(EditText editText1, EditText editText2) {
        return editText1.getText().toString().equals(editText2.getText().toString());
    }

    protected void passwordNotMatched() {
        UIHelper.showLongToastInCenter(getActivity(), getResources().getString(R.string.error_passwords_do_not_match));
    }

    protected void passwordLengthError() {
        UIHelper.showLongToastInCenter(getActivity(), getResources().getString(R.string.passwords_length_error));
    }

    protected void openHomeFragment(User loginResult) {
        getDockActivity().popBackStackTillEntry(0);
        prefHelper.putUser(loginResult);
        prefHelper.setLoginStatus(true);
        getDockActivity().replaceDockableFragment(new MainHomeFragment());
    }

    protected void serverNotFound() {
        UIHelper.showLongToastInCenter(getActivity(),
                "Unable to connect to the server, "
                        + "are you connected to the internet?");
    }

    /**
     * This generic null string validator to be used FormEditText
     * <p>
     * Usage : formEditText.addValicator(new EmptyStringValidator);
     *
     * @return Boolean and setError on respective field.
     */
    protected class EmptyStringValidator extends Validator {

        public EmptyStringValidator() {
            super("The field must not be empty");
        }

        @Override
        public boolean isValid(EditText et) {
            return et.getText().toString().trim().length() >= 1;
        }

    }

    /**
     * Trigger when receives broadcasts from device to check wifi connectivity
     * using connectivity manager
     * <p>
     * Usage : registerBroadcastReceiver() on resume of activity to receive
     * notifications where needed and unregisterBroadcastReceiver() when not
     * needed.
     *
     * @return The connectivity of wifi/mobile carrier connectivity.
     */

    protected BroadcastReceiver mConnectionReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            boolean isWifiConnected = false;
            boolean isMobileConnected = false;

            ConnectivityManager connMgr = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connMgr
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (networkInfo != null)
                isWifiConnected = networkInfo.isConnected();

            networkInfo = connMgr
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (networkInfo != null)
                isMobileConnected = networkInfo.isConnected();

            Log.d("NETWORK STATUS", "wifi==" + isWifiConnected + " & mobile=="
                    + isMobileConnected);
        }
    };

    private boolean isLoading;

    protected void finishLoading() {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                loadingFinished();
            }
        });
    }

    protected boolean checkLoading() {
        if (isLoading) {
            UIHelper.showLongToastInCenter(getActivity(),
                    R.string.message_wait);
            return false;
        } else {
            return true;
        }

    }

}
