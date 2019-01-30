package com.ingic.lmslawyer.fragments.NewsFeed;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.entities.BookmarkEnt;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.helpers.ShareIntentHelper;
import com.ingic.lmslawyer.helpers.UIHelper;
import com.ingic.lmslawyer.interfaces.BookmarkInterface;
import com.ingic.lmslawyer.interfaces.RecyclerClickListner;
import com.ingic.lmslawyer.ui.binder.BookmarkBinder;
import com.ingic.lmslawyer.ui.views.CustomRecyclerView;
import com.ingic.lmslawyer.ui.views.TitleBar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.ingic.lmslawyer.constants.WebServiceConstant.BookmarkKey;
import static com.ingic.lmslawyer.constants.WebServiceConstant.MARK_CATEGORY_UNFAVOURITE;

public class BookmarkFragment extends BaseFragment implements BookmarkInterface {
    @BindView(R.id.alternateText)
    TextView alternateText;
    @BindView(R.id.rv_bookmark)
    CustomRecyclerView rvBookmark;
    Unbinder unbinder;

    private  BookmarkEnt shareEnt;

    public static BookmarkFragment newInstance() {
        Bundle args = new Bundle();

        BookmarkFragment fragment = new BookmarkFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        serviceHelper.enqueueCall(webService.getMarkFeeds(prefHelper.getUser().getToken()), BookmarkKey);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag){
            case BookmarkKey:
                ArrayList<BookmarkEnt> entity=(ArrayList<BookmarkEnt>)result;
                setData(entity);
                break;

            case MARK_CATEGORY_UNFAVOURITE:
                serviceHelper.enqueueCall(webService.getMarkFeeds(prefHelper.getUser().getToken()), BookmarkKey);
                break;
        }
    }


    private void setData(ArrayList<BookmarkEnt> entity) {

        if(entity!=null && entity.size()>0){
            rvBookmark.setVisibility(View.VISIBLE);
            alternateText.setVisibility(View.GONE);

            rvBookmark.BindRecyclerView(new BookmarkBinder(getDockActivity(), prefHelper, this), entity,
                    new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false)
                    , new DefaultItemAnimator());
        }else {
            rvBookmark.setVisibility(View.GONE);
            alternateText.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showWhiteBackButton();
        titleBar.setSubHeading(getDockActivity().getResources().getString(R.string.my_bookmarks));
    }



    @Override
    public void share(Object item, int position) {
        shareEnt=(BookmarkEnt)item;

        requestStoragePermission();

    }

    @Override
    public void delete(Object item, int position) {

        BookmarkEnt deleteEnt=(BookmarkEnt)item;

        serviceHelper.enqueueCall(webService.markUnFavourite(prefHelper.getUser().getToken(), deleteEnt.getId()), MARK_CATEGORY_UNFAVOURITE);

    }

    private void requestStoragePermission() {
        Dexter.withActivity(getDockActivity())
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        if (report.areAllPermissionsGranted()) {

                            if (shareEnt != null) {
                                ShareIntentHelper.shareImageAndTextResultIntent(getDockActivity(), shareEnt.getImageUrl(),shareEnt.getTitle());
                            } else {
                                UIHelper.showShortToastInCenter(getDockActivity(), "Link is not avaliable");
                            }

                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            requestStoragePermission();

                        } else if (report.getDeniedPermissionResponses().size() > 0) {
                            requestStoragePermission();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        UIHelper.showShortToastInCenter(getDockActivity(), "Grant Storage Permission to processed");
                        openSettings();
                    }
                })

                .onSameThread()
                .check();


    }

    private void openSettings() {

        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        Uri uri = Uri.fromParts("package", getDockActivity().getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }
}
