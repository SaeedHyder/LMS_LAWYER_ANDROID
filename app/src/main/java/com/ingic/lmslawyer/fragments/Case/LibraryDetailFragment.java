package com.ingic.lmslawyer.fragments.Case;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.constants.AppConstant;
import com.ingic.lmslawyer.entities.library_entities.Photo;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.interfaces.OnViewHolderClick;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;
import com.ingic.lmslawyer.ui.adapters.myadapters.LibraryDetailAdapter;
import com.ingic.lmslawyer.ui.views.TitleBar;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class LibraryDetailFragment extends BaseFragment implements OnViewHolderClick {


    @BindView(R.id.rvDocs)
    RecyclerView rvDocs;
    @BindView(R.id.alternateText)
    TextView alternateText;
    Unbinder unbinder;
    private RecyclerViewListAdapter adapter;
    List<Photo> docList;
    private int docType;

    public LibraryDetailFragment() {
        // Required empty public constructor
    }

    public static LibraryDetailFragment newInstance(List<Photo> docList, int pos) {

        try {
            Bundle args = new Bundle();
            args.putSerializable(AppConstant.DOC_LIST, (Serializable) docList);
            args.putInt(AppConstant.DOC_TYPE, pos);
            LibraryDetailFragment fragment = new LibraryDetailFragment();
            fragment.setArguments(args);
            return fragment;
        }
        catch (Exception e){
            e.printStackTrace();
            return new LibraryDetailFragment();

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (getArguments() != null) {
            docList = (List<Photo>) getArguments().getSerializable(AppConstant.DOC_LIST);
            docType = getArguments().getInt(AppConstant.DOC_TYPE);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.showWhiteBackButton();
        titleBar.setSubHeading(getResources().getString(R.string.library)); /*todo Add Search icon */
    }


    private void initAdapter() {
//        seperate library
        adapter = new LibraryDetailAdapter(getDockActivity(), this, docList, docType);
        rvDocs.setLayoutManager(new GridLayoutManager(getDockActivity(), 3, LinearLayoutManager.VERTICAL, false));
        rvDocs.setAdapter(adapter);

        if (docList != null)
            adapter.addAll(docList);

    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
