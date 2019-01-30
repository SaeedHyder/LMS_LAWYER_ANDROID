package com.ingic.lmslawyer.fragments.NewsFeed;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.constants.AppConstant;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsfeedDetailsFragment extends BaseFragment {


    @BindView(R.id.webview)
    WebView webView;
    Unbinder unbinder;
    private String url = "";

    public NewsfeedDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_newsfeed_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (getArguments() != null) {
            url = getArguments().getString(AppConstant.NEWSFEED_URL);
        }
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().getJavaScriptEnabled();
//        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new AppWebViewClients(getMainActivity().getProgressBar()));
        webView.loadUrl(url);
        return view;
    }

    public static NewsfeedDetailsFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString(AppConstant.NEWSFEED_URL, url);
        NewsfeedDetailsFragment fragment = new NewsfeedDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showWhiteBackButton();
        titleBar.setSubHeading(getString(R.string.news_feeds));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
