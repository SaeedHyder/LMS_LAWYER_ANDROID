package com.ingic.lmslawyer.fragments.Case;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.constants.AppConstant;
import com.ingic.lmslawyer.constants.WebServiceConstant;
import com.ingic.lmslawyer.entities.library_entities.Case;
import com.ingic.lmslawyer.entities.library_entities.SortLibrary;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.helpers.SimpleDividerItemDecoration;
import com.ingic.lmslawyer.interfaces.OnViewHolderClick;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;
import com.ingic.lmslawyer.ui.adapters.myadapters.MyCaseLibraryAdapter;
import com.ingic.lmslawyer.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class LibraryFragment extends BaseFragment implements OnViewHolderClick {


    @BindView(R.id.rvLibrary)
    RecyclerView rvLibrary;
    Unbinder unbinder;
    @BindView(R.id.flParent)
    FrameLayout flParent;
    @BindView(R.id.alternateText)
    TextView alternateText;
    private RecyclerViewListAdapter adapter;
    private ArrayList list;

    private static boolean mIsCaseDesc;
    private Case mCaseLibrary;
    private String mCaseId;

    public LibraryFragment() {
        // Required empty public constructor
    }

    public static LibraryFragment newInstance(boolean isCaseDesc) {

        Bundle args = new Bundle();
        args.putBoolean(AppConstant.IS_CASE_DESC, isCaseDesc);
        LibraryFragment fragment = new LibraryFragment();
        fragment.setArguments(args);
        return fragment;


    }

    public static LibraryFragment newInstance(boolean isCaseDesc, Case caseLibrary, String case_id) {

       Bundle args = new Bundle();
        args.putBoolean(AppConstant.IS_CASE_DESC, isCaseDesc);
        args.putSerializable(AppConstant.CASE_LIBRARY, caseLibrary);
        args.putString(AppConstant.CASE_ID, case_id);
        LibraryFragment fragment = new LibraryFragment();
        fragment.setArguments(args);

       return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        unbinder = ButterKnife.bind(this, view);

      /*  if (getArguments() != null) {
            mIsCaseDesc = getArguments().getBoolean(AppConstant.IS_CASE_DESC);
        }
        if (!mIsCaseDesc) {
            flParent.setBackground(getResources().getDrawable(R.drawable.bg));
        }

        initAdapter();*/

        if (getArguments() != null) {
            mIsCaseDesc = getArguments().getBoolean(AppConstant.IS_CASE_DESC);
            mCaseLibrary = (Case) getArguments().getSerializable(AppConstant.CASE_LIBRARY);
            mCaseId = getArguments().getString(AppConstant.CASE_ID);

        }
        if (!mIsCaseDesc) {
            flParent.setBackground(getResources().getDrawable(R.drawable.bg));
        }

        initAdapter();
        if (mIsCaseDesc) {
            //        calling service
            serviceHelper.enqueueCall(webService.getCaseLibrary(prefHelper.getUser().getToken(), mCaseId),
                    WebServiceConstant.getCaseLibrary);
        } else
            setAdapterData(mCaseLibrary);
        return view;
    }

    private void setAdapterData(Case caseLibrary) {
        if (caseLibrary == null) return;
        List<SortLibrary> seperateLibraryList = new ArrayList<>();
//        seperateLibraryList.add(new SortLibrary(mCaseLibrary.getPhotosCount(), mCaseLibrary.getPhotos().get(0).getDocUrl(), mCaseLibrary.getPhotos()));
        String imageUrl = "";
        if (caseLibrary.getPhotos() != null &&
                caseLibrary.getPhotos().size() > 0
                && caseLibrary.getPhotos().get(0) != null
                && caseLibrary.getPhotos().get(0).getDocUrl() != null) {
            imageUrl = caseLibrary.getPhotos().get(0).getDocUrl();
        }

        seperateLibraryList.add(new SortLibrary("Photos", caseLibrary.getPhotosCount(),
                imageUrl, caseLibrary.getPhotos()));

        seperateLibraryList.add(new SortLibrary("Videos", caseLibrary.getVideosCount(), "",
                caseLibrary.getVideos()));
        seperateLibraryList.add(new SortLibrary("Documents", caseLibrary.getFilesCount(),
                "", caseLibrary.getFiles()));

        adapter.addAll(seperateLibraryList);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.showWhiteBackButton();
        titleBar.setSubHeading(getResources().getString(R.string.library)); /*todo Add Search icon */
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        switch (Tag) {
            case WebServiceConstant.getCaseLibrary: //For new cases
                if (LibraryFragment.this.isDetached() || LibraryFragment.this.isRemoving() || !LibraryFragment.this.isVisible())
                    return;
                if (result != null) {
                    mCaseLibrary = (Case) result;
                    if (mCaseLibrary != null && !(mCaseLibrary.getPhotos() != null && mCaseLibrary.getPhotosCount() == 0
                            && mCaseLibrary.getVideos() != null && mCaseLibrary.getVideosCount() == 0
                            && mCaseLibrary.getFiles() != null && mCaseLibrary.getFilesCount() == 0
                    )) {
                        if (alternateText != null)
                            alternateText.setVisibility(View.GONE);
                        setAdapterData(mCaseLibrary);

                    } else if (alternateText != null) {
                        alternateText.setVisibility(View.VISIBLE);
                    }
                }
                break;
        }
    }


    private void initAdapter() {
        adapter = new MyCaseLibraryAdapter(getDockActivity(), this);
        rvLibrary.setLayoutManager(new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false));
        rvLibrary.addItemDecoration(new SimpleDividerItemDecoration(getDockActivity()));
        rvLibrary.setAdapter(adapter);
    }


    @Override
    public void onItemClick(View view, int position) {

    }
}
