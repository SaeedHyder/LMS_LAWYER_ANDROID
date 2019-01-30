package com.ingic.lmslawyer.fragments.Case;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingic.lmslawyer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterLawyerFragment extends Fragment {


    public FilterLawyerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter_lawyer, container, false);
    }

}
