package com.ingic.lmslawyer.fragments.Case;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.helpers.SimpleDividerItemDecorationWithoutLastItem;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;
import com.ingic.lmslawyer.ui.adapters.myadapters.SearchMembersAdapter;
import com.ingic.lmslawyer.ui.views.AnyEditTextView;
import com.ingic.lmslawyer.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditGroupFragment extends BaseFragment {


    @BindView(R.id.CaseImage)
    CircleImageView CaseImage;
    @BindView(R.id.tvCaseName)
    TextView tvCaseName;
    @BindView(R.id.tvMembers)
    TextView tvMembers;
    @BindView(R.id.etSearch)
    AnyEditTextView etSearch;
    @BindView(R.id.rvSearchList)
    RecyclerView rvSearchList;
    Unbinder unbinder;
    private RecyclerViewListAdapter adapter;
    private ArrayList list;

    public EditGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_group, container, false);
        unbinder = ButterKnife.bind(this, view);
        initAdapter();
        return view;
    }


    private void initAdapter() {
        adapter = new SearchMembersAdapter(getDockActivity());
        rvSearchList.setLayoutManager(new LinearLayoutManager(getDockActivity(),
                LinearLayoutManager.VERTICAL, false));
        rvSearchList.addItemDecoration(new SimpleDividerItemDecorationWithoutLastItem(getDockActivity()));
        list = new ArrayList();
        list.add("John Galin");
        list.add("Cavin Brend");
        list.add("Albert Chris");
        rvSearchList.setAdapter(adapter);
        adapter.addAll(list);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.showWhiteBackButton();
        titleBar.setSubHeading(getResources().getString(R.string.edit_group)); /*todo Add Search icon */
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.imgViewCapture, R.id.imgSearch, R.id.btnLeaveGroup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgViewCapture:
                implementedInBeta();
                break;
            case R.id.imgSearch:
                implementedInBeta();

                break;
            case R.id.btnLeaveGroup:
                implementedInBeta();

                break;
        }
    }
}
