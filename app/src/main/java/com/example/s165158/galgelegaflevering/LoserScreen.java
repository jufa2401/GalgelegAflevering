package com.example.s165158.galgelegaflevering;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by s165158 on 09-11-2017.
 */

public class LoserScreen extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.winnerloser_scren, container, false);

        getActivity().setTitle(R.string.loser_screen);

        return rootView;
    }

}