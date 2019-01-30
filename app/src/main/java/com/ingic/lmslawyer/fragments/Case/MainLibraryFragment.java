package com.ingic.lmslawyer.fragments.Case;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.constants.WebServiceConstant;
import com.ingic.lmslawyer.entities.library_entities.LibraryEnt;
import com.ingic.lmslawyer.entities.library_entities.SortLibrary;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.helpers.SimpleDividerItemDecoration;
import com.ingic.lmslawyer.interfaces.OnViewHolderClick;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;
import com.ingic.lmslawyer.ui.adapters.myadapters.PerCaseLibraryAdapter;
import com.ingic.lmslawyer.ui.adapters.myadapters.SeperateLibraryAdapter;
import com.ingic.lmslawyer.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainLibraryFragment extends BaseFragment implements OnViewHolderClick {


    @BindView(R.id.rlTop)
    RelativeLayout rlTop;
    @BindView(R.id.alternateText)
    TextView alternateText;
    /*==================*/
    @BindView(R.id.rvSeperateLibrary)
    RecyclerView rvSeperateLibrary;
    @BindView(R.id.rvCaseLibrary)
    RecyclerView rvCaseLibrary;
    Unbinder unbinder;
    private RecyclerViewListAdapter seperateLibraryAdapter;
    private RecyclerViewListAdapter caseLibraryAdapter;
    private LibraryEnt libraryEnt;

    public MainLibraryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_library, container, false);
        unbinder = ButterKnife.bind(this, view);
        initAdapter();
//        calling service
        serviceHelper.enqueueCall(webService.getUserLibrary(prefHelper.getUser().getToken()),
                WebServiceConstant.getUserLibrary);
        return view;
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.showWhiteBackButton();
        titleBar.setSubHeading(getResources().getString(R.string.library)); /*todo Add Search icon */
    }


    private void initAdapter() {
//        seperate library
        seperateLibraryAdapter = new SeperateLibraryAdapter(getDockActivity(), this);
        rvSeperateLibrary.setLayoutManager(new LinearLayoutManager(getDockActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvSeperateLibrary.setAdapter(seperateLibraryAdapter);

        //Case library
        caseLibraryAdapter = new PerCaseLibraryAdapter(getDockActivity(), this);
        rvCaseLibrary.setLayoutManager(new LinearLayoutManager(getDockActivity(),
                LinearLayoutManager.VERTICAL, false));
        rvCaseLibrary.addItemDecoration(new SimpleDividerItemDecoration(getDockActivity()));
        rvCaseLibrary.setAdapter(caseLibraryAdapter);
//        caseLibraryAdapter.addAll(list);
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        if (MainLibraryFragment.this.isDetached() ||
                MainLibraryFragment.this.isRemoving() || !MainLibraryFragment.this.isVisible())
            return;
        switch (Tag) {
            case WebServiceConstant.getUserLibrary: //For new cases
                if (result != null) {
                    libraryEnt = (LibraryEnt) result;
                    if (libraryEnt != null && !(libraryEnt.getPhotos() != null && libraryEnt.getPhotosCount() == 0
                            && libraryEnt.getVideos() != null && libraryEnt.getVideosCount() == 0
                            && libraryEnt.getFiles() != null && libraryEnt.getFilesCount() == 0
                    )) {
                        alternateText.setVisibility(View.GONE);
                        rlTop.setVisibility(View.VISIBLE);
                        setAdapterData();

                    } else {
                        rlTop.setVisibility(View.GONE);
                        alternateText.setVisibility(View.VISIBLE);

                    }
                }
                break;
        }
    }

    private void setAdapterData() {
        if (libraryEnt == null) return;
        List<SortLibrary> seperateLibraryList = new ArrayList<>();
        if (libraryEnt.getPhotos().get(0) != null && libraryEnt.getPhotos().get(0).getDocUrl() != null)
            seperateLibraryList.add(new SortLibrary("All Photos", libraryEnt.getPhotosCount(),
                    libraryEnt.getPhotos().get(0).getDocUrl(), libraryEnt.getPhotos()));
        seperateLibraryList.add(new SortLibrary("All Videos", libraryEnt.getVideosCount(),
                "", libraryEnt.getVideos()));
        seperateLibraryList.add(new SortLibrary("All Documents", libraryEnt.getFilesCount(),
                "", libraryEnt.getFiles()));
        seperateLibraryAdapter.addAll(seperateLibraryList);

        //Case library
        if (libraryEnt.getCases() != null && libraryEnt.getCases().size() > 0) {
            caseLibraryAdapter.addAll(libraryEnt.getCases());
        }
    }

}
