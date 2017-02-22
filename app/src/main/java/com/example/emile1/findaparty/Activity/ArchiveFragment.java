package com.example.emile1.findaparty.Activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.emile1.findaparty.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArchiveFragment extends Fragment {


    public ArchiveFragment() {
        // Required empty public constructor
    }

    public static ArchiveFragment newInstance() {
        ArchiveFragment fragment = new ArchiveFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_archive, container, false);
    }

}
