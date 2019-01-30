package com.ingic.lmslawyer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.entities.NewsFeedItem;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.helpers.UIHelper;
import com.ingic.lmslawyer.ui.views.AnyTextView;
import com.ingic.lmslawyer.ui.views.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.commons.lang3.text.WordUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NewsfeedDetailFragment extends BaseFragment {

    @BindView(R.id.txtFeed)
    AnyTextView txtFeed;
    @BindView(R.id.txtTime)
    AnyTextView txtTime;
    @BindView(R.id.txtDate)
    AnyTextView txtDate;
    @BindView(R.id.txtLocation)
    AnyTextView txtLocation;
    @BindView(R.id.imgFeed)
    ImageView imgFeed;
    @BindView(R.id.txtDetail)
    AnyTextView txtDetail;
    Unbinder unbinder;
    private NewsFeedItem newsfeedListingItem;
    private ImageLoader imageLoader;

    public static NewsfeedDetailFragment newInstance() {

        return new NewsfeedDetailFragment();
    }

    public void setNewsFeedItem(NewsFeedItem newsfeedListingItem) {
        this.newsfeedListingItem = newsfeedListingItem;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageLoader = ImageLoader.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newsfeed_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setData();
    }

    private void setData() {
        txtFeed.setText(newsfeedListingItem.getTitle());
        txtTime.setText(newsfeedListingItem.getUpdated_at().subSequence(newsfeedListingItem.getUpdated_at().indexOf(" "), newsfeedListingItem.getUpdated_at().length()));
        txtDate.setText(newsfeedListingItem.getUpdated_at().subSequence(0, newsfeedListingItem.getUpdated_at().indexOf(" ")));
        txtLocation.setText(WordUtils.capitalize(newsfeedListingItem.getLocation()));
        imageLoader.displayImage(newsfeedListingItem.getNewsfeed_image(), imgFeed);
        txtDetail.setText(newsfeedListingItem.getDescription());
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);

        titleBar.hideButtons();
        titleBar.showWhiteBackButton();
        titleBar.setSubHeading(getString(R.string.news_feed_heading));
        titleBar.showNotificationButton();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();

        UIHelper.hideSoftKeyboard(getDockActivity(), this.getView());

    }
}
