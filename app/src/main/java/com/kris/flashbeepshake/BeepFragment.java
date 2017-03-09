package com.kris.flashbeepshake;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class BeepFragment extends Fragment {


    public BeepFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_beep, container, false);

        ButterKnife.bind(this, v);

        return v;
    }

}
