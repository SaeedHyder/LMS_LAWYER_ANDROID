package com.ingic.lmslawyer.fragments.NewsFeed;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Function;
import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.constants.WebServiceConstant;
import com.ingic.lmslawyer.entities.newsfeed.Categories;
import com.ingic.lmslawyer.entities.newsfeed.NewsfeedEnt;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.helpers.InternetHelper;
import com.ingic.lmslawyer.helpers.ShareIntentHelper;
import com.ingic.lmslawyer.helpers.UIHelper;
import com.ingic.lmslawyer.interfaces.CategoryFavShareListner;
import com.ingic.lmslawyer.interfaces.OnCategoryClick;
import com.ingic.lmslawyer.interfaces.OnDataChange;
import com.ingic.lmslawyer.interfaces.OnViewHolderClick;
import com.ingic.lmslawyer.interfaces.RecyclerClickListner;
import com.ingic.lmslawyer.rss_feed.Article;
import com.ingic.lmslawyer.rss_feed.Parser;
import com.ingic.lmslawyer.ui.adapters.abstracts.MatchableRVArrayAdapter;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;
import com.ingic.lmslawyer.ui.adapters.myadapters.NewsFeedCategoryAdapter;
import com.ingic.lmslawyer.ui.adapters.myadapters.NewsfeedCategoryListAdapter;
import com.ingic.lmslawyer.ui.adapters.myadapters.NewsfeedListAdapter;
import com.ingic.lmslawyer.ui.views.AnyEditTextView;
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
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ingic.lmslawyer.constants.WebServiceConstant.MARK_CATEGORY_FAVOURITE;
import static com.ingic.lmslawyer.constants.WebServiceConstant.MARK_CATEGORY_UNFAVOURITE;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFeedListFragment extends BaseFragment
        implements OnViewHolderClick,
        SearchView.OnQueryTextListener, OnDataChange, OnCategoryClick, RecyclerClickListner, CategoryFavShareListner {


    @BindView(R.id.edtSearch)
    AnyEditTextView edtSearch;
    @BindView(R.id.imgviewSearch)
    ImageView imgviewSearch;
    @BindView(R.id.rvNewsfeedCategory)
    RecyclerView rvNewsfeedCategory;
    @BindView(R.id.rvNewsfeed)
    RecyclerView rvNewsfeed;
    @BindView(R.id.mainFrameLayout)
    RelativeLayout mainFrameLayout;

    Unbinder unbinder;

    @BindView(R.id.alternateText)
    TextView alternateText;
    ArrayList<Categories> categoryData;

    Double subCategoryId;
    Dialog dialog;
    private MatchableRVArrayAdapter adapter;
    private RecyclerViewListAdapter categoryAdapter;

    private NewsfeedEnt shareEntity;
    private ArrayList<NewsfeedEnt> newsCollection;


    public NewsFeedListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_feed_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        /*swLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        swLayout.canChildScrollUp();
        swLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                    callService();
                } else {
                    swLayout.setRefreshing(false);
                }
            }
        });*/
       // categoryId = Double.valueOf(prefHelper.getUser().getSubscribeCategoryId());
        subCategoryId = Double.valueOf(prefHelper.getUser().getSubscribeCategoryId());
        initAdapter();


        if (InternetHelper.isNetworkAvailable(getDockActivity())) {
            callService();
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchListner();

    }

    private void searchListner() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bindData(getSearchedArray(s.toString()));
            }
        });
    }

    private ArrayList<NewsfeedEnt> getSearchedArray(String keyword) {

        if (newsCollection == null) {
            return new ArrayList<>();
        }
        if (newsCollection != null && newsCollection.isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<NewsfeedEnt> arrayList = new ArrayList<>();

        String UserName = "";
        for (NewsfeedEnt item : newsCollection) {

            UserName = item.getTitle();
            if (Pattern.compile(Pattern.quote(keyword.toLowerCase().trim()), Pattern.CASE_INSENSITIVE).matcher(UserName.toLowerCase().trim()).find()) {
                arrayList.add(item);
            }
        }
        return arrayList;

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void callService() {
        if (prefHelper.getUser().getSubscribeCategoryId() == 0) {
            getDockActivity().replaceDockableFragment(SelectCategoryFragment.newInstance(), "SelectCategoryFragment");
        } else {
           /* serviceHelper.enqueueCall(webService.getCategories(String.valueOf(prefHelper.getUser().getToken())),
                    WebServiceConstant.getCategories);*/
            serviceHelper.enqueueCall(webService.getNewsfeedCategories(String.valueOf(prefHelper.getUser().getToken()), subCategoryId + ""),
                    WebServiceConstant.getNewsfeed);
        }
    }



    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        if (NewsFeedListFragment.this.isDetached() || NewsFeedListFragment.this.isRemoving() || !NewsFeedListFragment.this.isVisible())
            return;

        switch (Tag) {
            case WebServiceConstant.getCategories:
                categoryData = (ArrayList<Categories>) result;
                categoryAdapter.addAll(categoryData);
                for (Categories category : categoryData
                        ) {
                    if (category.isSelected()) {
                        getSelectedCategoryData(category.getId());
                        return;
                    }
                }
                showCategoryDialog();
                break;
            case WebServiceConstant.getNewsfeed:
                newsCollection = (ArrayList<NewsfeedEnt>) result;
                bindData(newsCollection);

                //   serviceHelper.enqueueCall(webService.subscribeCategory(prefHelper.getUser().getToken(), categoryId.intValue()), WebServiceConstant.SUBSCRIBE_CATEGORY);
                break;
            case MARK_CATEGORY_FAVOURITE:
                break;

            case MARK_CATEGORY_UNFAVOURITE:
                break;
            default:
                break;
        }
    }

    private void bindData(ArrayList<NewsfeedEnt> collection) {
        if (collection != null && collection.size() > 0) {
            alternateText.setVisibility(View.GONE);
            rvNewsfeed.setVisibility(View.VISIBLE);
            adapter.addAll(collection);
        } else {
            alternateText.setVisibility(View.VISIBLE);
            rvNewsfeed.setVisibility(View.GONE);
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.showWhiteBackButton();
        titleBar.setSubHeading(getResources().getString(R.string.news_feeds)); /*todo Add Search icon */
        titleBar.showFilterButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDockActivity().replaceDockableFragment(SelectCategoryFragment.newInstance(true), "SelectCategoryFragment");
            }
        });
        titleBar.showBookmarkButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDockActivity().replaceDockableFragment(BookmarkFragment.newInstance(), "BookmarkFragment");
            }
        });
    }

    private void initAdapter() {
        adapter = new NewsfeedListAdapter(getDockActivity(), NewsFeedListFragment.this, getFunction(),
                NewsFeedListFragment.this, this);
//        rvNewsfeed.setLayoutManager(new LinearLayoutManager(getDockActivity(), LinearLayoutManager.ve, false));
        rvNewsfeed.setLayoutManager(new GridLayoutManager(getDockActivity(), 2));
        rvNewsfeed.setAdapter(adapter);


        categoryAdapter = new NewsfeedCategoryListAdapter(getDockActivity(), this, this);
        rvNewsfeedCategory.setLayoutManager(new
                LinearLayoutManager(getDockActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvNewsfeedCategory.setAdapter(categoryAdapter);
        rvNewsfeedCategory.setNestedScrollingEnabled(false);

    }


    private Function<NewsfeedEnt, String> getFunction() {
        return new Function<NewsfeedEnt, String>() {
            @Override
            public String apply(NewsfeedEnt input) {
                if (input == null || input.getTitle() == null) return "";
                else
                    return input.getTitle();
            }
        };
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.imgviewSearch)
    public void onViewClicked() {
    }



    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onCategoryClick(Double categoryId) {
        //this.categoryId = categoryId;

        serviceHelper.enqueueCall(webService.getNewsfeed(String.valueOf(prefHelper.getUser().getToken()),
                categoryId + ""),
                WebServiceConstant.getNewsfeed);
    }

    @Override
    public void share(NewsfeedEnt item, int position) {

           shareEntity=item;
           requestStoragePermission();
       /* if (item != null && item.getLink() != null && !item.getLink().equals("")) {
            ShareIntentHelper.shareTextIntent(getDockActivity(), item.getLink());
        } else {
            UIHelper.showShortToastInCenter(getDockActivity(), "Link is not avaliable");
        }*/

    }

    @Override
    public void favorite(NewsfeedEnt item, int position) {

        if (item.isFavorite()) {
            serviceHelper.enqueueCall(webService.markFavourite(prefHelper.getUser().getToken(), item.getId()), MARK_CATEGORY_FAVOURITE);
        } else {
            serviceHelper.enqueueCall(webService.markUnFavourite(prefHelper.getUser().getToken(), item.getId()), MARK_CATEGORY_UNFAVOURITE);
        }
    }

    @Override
    public void onClick(Object entity, int position) {

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

                            if (shareEntity != null && shareEntity.getLink() != null && !shareEntity.getLink().equals("")) {
                                ShareIntentHelper.shareTextIntent(getDockActivity(), shareEntity.getLink());
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

    public void showCategoryDialog() {

        dialog = new Dialog(getMainActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_news_feed_category);
        RecyclerView rvCategories = (RecyclerView) dialog.findViewById(R.id.rvCategpries);
        Button btnDone = (Button) dialog.findViewById(R.id.btnDone);
        final NewsFeedCategoryAdapter adapter = new NewsFeedCategoryAdapter(getDockActivity());
        rvCategories.setLayoutManager(new
                LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false));
        rvCategories.setAdapter(adapter);
        adapter.addAll(categoryData);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getCategoryID() == 0.0) {
                    UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.category_error));
                    return;
                }
                getSelectedCategoryData(adapter.getCategoryID());
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    private void getSelectedCategoryData(double categoryId) {
      //  this.categoryId = categoryId;
        if (categoryId == 0.0) {
            categoryId = categoryData.get(0).getId();
        }
        ((NewsfeedCategoryListAdapter) categoryAdapter).setSelectedCategoryID(categoryId);
        categoryAdapter.notifyDataSetChanged();
        new Handler().postDelayed(() -> {
            rvNewsfeedCategory.scrollToPosition(((NewsfeedCategoryListAdapter) categoryAdapter).getSelectedItemPosition());
        }, 500);
        if (categoryData != null && categoryData.size() > 0)
            serviceHelper.enqueueCall(webService.getNewsfeed(String.valueOf(prefHelper.getUser().getToken()),
                    categoryId + ""),
                    WebServiceConstant.getNewsfeed);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (adapter == null) return false;
        if (TextUtils.isEmpty(newText)) {
            adapter.getFilter().filter("");
            update(0);
        } else {
            adapter.getFilter().filter(newText);
        }
        return true;
    }

    @Override
    public void update(int count) {
        if (NewsFeedListFragment.this.isDetached() || NewsFeedListFragment.this.isRemoving()
                || !NewsFeedListFragment.this.isVisible())
            return;

        if (count > 0) {
            alternateText.setVisibility(View.GONE);
        } else
            alternateText.setVisibility(View.VISIBLE);

    }

    public void loadFeed() {


        Parser parser = new Parser();
        parser.execute(WebServiceConstant.RSS_URL);
        parser.onFinish(new Parser.OnTaskCompleted() {
            //what to do when the parsing is done
            @Override
            public void onTaskCompleted(ArrayList<Article> list) {
                getDockActivity().onLoadingFinished();
                if (NewsFeedListFragment.this.isDetached() || NewsFeedListFragment.this.isRemoving() || !NewsFeedListFragment.this.isVisible()) {
                    return;
                }
                alternateText.setVisibility(View.GONE);
                adapter.addAll(list);

            }

            //what to do in case of error
            @Override
            public void onError() {

                getDockActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getDockActivity().onLoadingFinished();
//                        progressBar.setVisibility(View.GONE);

                        if (NewsFeedListFragment.this.isDetached() || NewsFeedListFragment.this.isRemoving())
                            return;

                        Toast.makeText(getDockActivity(), "Unable to load data.",
                                Toast.LENGTH_LONG).show();
                        Log.i("Unable to load ", "articles");
                    }
                });
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
