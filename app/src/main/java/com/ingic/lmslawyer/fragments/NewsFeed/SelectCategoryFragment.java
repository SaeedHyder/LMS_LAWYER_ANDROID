package com.ingic.lmslawyer.fragments.NewsFeed;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.entities.User;
import com.ingic.lmslawyer.entities.newsfeed.AllCategoriesEnt;
import com.ingic.lmslawyer.entities.newsfeed.SubCategory;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.helpers.UIHelper;
import com.ingic.lmslawyer.interfaces.CaterogyClickListner;
import com.ingic.lmslawyer.interfaces.RecyclerClickListner;
import com.ingic.lmslawyer.ui.binder.CategoryListBinder;
import com.ingic.lmslawyer.ui.binder.SearchBinder;
import com.ingic.lmslawyer.ui.binder.SuggestionBinder;
import com.ingic.lmslawyer.ui.views.AnyEditTextView;
import com.ingic.lmslawyer.ui.views.AnyTextView;
import com.ingic.lmslawyer.ui.views.CustomRecyclerView;
import com.ingic.lmslawyer.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ingic.lmslawyer.constants.WebServiceConstant.AllCategories;
import static com.ingic.lmslawyer.constants.WebServiceConstant.SUBSCRIBE_CATEGORY;
import static com.ingic.lmslawyer.constants.WebServiceConstant.SUGGEST_CATEGORIES;


public class SelectCategoryFragment extends BaseFragment implements RecyclerClickListner, CaterogyClickListner {
    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.edtSearch)
    AnyEditTextView edtSearch;
    @BindView(R.id.imgviewSearch)
    ImageView imgviewSearch;
    @BindView(R.id.llSearchBar)
    LinearLayout llSearchBar;
    @BindView(R.id.edtSelectCategory)
    AnyEditTextView edtSelectCategory;
    @BindView(R.id.AddCategory)
    ImageView AddCategory;
    @BindView(R.id.rv_categories)
    CustomRecyclerView rvCategories;
    Unbinder unbinder;

    @BindView(R.id.btn_submit)
    TextView btnSubmit;
    @BindView(R.id.rv_categories_list)
    CustomRecyclerView rvCategoriesList;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.viewSearch)
    View viewSearch;
    @BindView(R.id.txt_search_result)
    AnyTextView txtSearchResult;
    @BindView(R.id.txt_search_result_not_found)
    AnyTextView txtSearchResultNotFound;
    @BindView(R.id.rv_search_list)
    CustomRecyclerView rvSearchList;
    @BindView(R.id.ll_search_result)
    LinearLayout llSearchResult;

    private ArrayList<String> collection;
    private Integer categoryId;
    private static Boolean isSubscribed = false;
    private ArrayList<AllCategoriesEnt> categoriesCollection;

    public static SelectCategoryFragment newInstance() {
        Bundle args = new Bundle();
        isSubscribed = false;
        SelectCategoryFragment fragment = new SelectCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static SelectCategoryFragment newInstance(boolean isSubscribedKey) {
        Bundle args = new Bundle();
        isSubscribed = isSubscribedKey;
        SelectCategoryFragment fragment = new SelectCategoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_select_category, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        serviceHelper.enqueueCall(webService.getAllCategories(prefHelper.getUser().getToken()), AllCategories);
        setData();
        categoryListner();

        if (isSubscribed) {
            btnBack.setVisibility(View.VISIBLE);
        } else {
            btnBack.setVisibility(View.GONE);
        }
    }


    public ArrayList<SubCategory> getSearchedArray(String keyword) {
        if (categoriesCollection == null) {
            return new ArrayList<>();
        }
        if (categoriesCollection != null && categoriesCollection.isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<SubCategory> arrayList = new ArrayList<>();

        String UserName = "";
        for (AllCategoriesEnt item : categoriesCollection) {

            for (SubCategory subCategory : item.getSubCategories()) {
                UserName = subCategory.getTitle();
                if (Pattern.compile(Pattern.quote(keyword.toLowerCase().trim()), Pattern.CASE_INSENSITIVE).matcher(UserName.toLowerCase().trim()).find()) {
                    arrayList.add(subCategory);
                }
            }

        }
        return arrayList;

    }


    private void categoryListner() {
        edtSelectCategory.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        // Identifier of the action. This will be either the identifier you supplied,
                        // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            collection.add(edtSelectCategory.getText().toString());
                            rvCategories.notifyDataSetChanged();
                            rvCategories.scrollToPosition(collection.size() - 1);
                            edtSelectCategory.getText().clear();
                            UIHelper.hideSoftKeyboard(getDockActivity(), edtSelectCategory);
                            return true;
                        }
                        return false;
                    }
                });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    llSearchResult.setVisibility(View.VISIBLE);
                    rvCategoriesList.setVisibility(View.GONE);
                    bindData(getSearchedArray(s.toString()));
                } else {
                    llSearchResult.setVisibility(View.GONE);
                    rvCategoriesList.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    private void bindData(ArrayList<SubCategory> searchedArray) {

        if (searchedArray != null && searchedArray.size() > 0) {
            txtSearchResult.setVisibility(View.VISIBLE);
            txtSearchResultNotFound.setVisibility(View.GONE);
            rvSearchList.setVisibility(View.VISIBLE);
            rvSearchList.BindRecyclerView(new SearchBinder(getDockActivity(), prefHelper, this), searchedArray,
                   new GridLayoutManager(getDockActivity(),2)
                    , new DefaultItemAnimator());

        } else {
            txtSearchResult.setVisibility(View.GONE);
            txtSearchResultNotFound.setVisibility(View.VISIBLE);
            rvSearchList.setVisibility(View.GONE);
        }
    }


    private void setData() {

        collection = new ArrayList<>();

        rvCategories.BindRecyclerView(new SuggestionBinder(getDockActivity(), prefHelper, this), collection,
                new LinearLayoutManager(getDockActivity(), LinearLayoutManager.HORIZONTAL, false)
                , new DefaultItemAnimator());


    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {
            case AllCategories:
                categoriesCollection = new ArrayList<>();
                categoriesCollection.addAll((ArrayList<AllCategoriesEnt>) result);

                if (categoriesCollection != null && categoriesCollection.size() > 0) {

                    rvCategoriesList.setVisibility(View.VISIBLE);
                    rvCategoriesList.BindRecyclerView(new CategoryListBinder(getDockActivity(), prefHelper, this), categoriesCollection,
                            new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false)
                            , new DefaultItemAnimator());
                } else {
                    rvCategoriesList.setVisibility(View.GONE);
                }
                break;

            case SUGGEST_CATEGORIES:
                rvCategories.clearList();
                rvCategories.notifyDataSetChanged();
                edtSelectCategory.getText().clear();
                UIHelper.hideSoftKeyboard(getDockActivity(), edtSelectCategory);
                UIHelper.showShortToastInCenter(getDockActivity(), getDockActivity().getResources().getString(R.string.posted_successfully));
                break;

            case SUBSCRIBE_CATEGORY:
                User user = prefHelper.getUser();
                user.setSubscribeCategoryId(categoryId);
                prefHelper.putUser(user);

                llSearchResult.setVisibility(View.GONE);
                rvCategoriesList.setVisibility(View.VISIBLE);

                serviceHelper.enqueueCall(webService.getAllCategories(prefHelper.getUser().getToken()), AllCategories);
                break;
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBarComplete();
    }


    @Override
    public void onClick(Object entity, int position) {

        if (position <= collection.size() - 1) {
            collection.remove(position);
        }
        rvCategories.notifyItemRemoved(position);
    }


    @OnClick({R.id.btnBack, R.id.btn_submit, R.id.AddCategory})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                getDockActivity().popFragment();
                break;
            case R.id.btn_submit:
                getDockActivity().popFragment();
                break;
            case R.id.AddCategory:
                if (collection.size() > 0) {
                    String suggestions = android.text.TextUtils.join(",", collection);
                    serviceHelper.enqueueCall(webService.suggestCategories(suggestions, prefHelper.getUser().getToken()), SUGGEST_CATEGORIES);
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), getDockActivity().getResources().getString(R.string.suggest_news_category));
                }
                break;
        }
    }

    @Override
    public void categoryClick(Object entity, int position) {
        SubCategory data = (SubCategory) entity;
        categoryId = data.getId();
        serviceHelper.enqueueCall(webService.subscribeCategory(prefHelper.getUser().getToken(), data.getId()), SUBSCRIBE_CATEGORY);
    }
}
