package com.example.s165158.galgelegaflevering;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.s165158.galgelegaflevering.database.DatabaseHelper;

import org.w3c.dom.Text;

import java.io.PrintStream;
import java.util.ArrayList;
//import android.widget.ListAdapter;


/**
 * Created by s165158 on 04-11-2017.
 */

public class ListFragment extends Fragment {

    private Button button_playagain;
    private int score;
    private DatabaseHelper databaseHelper;
    private TextView score_text_TextView, scoreint_TextView;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInsanceState){
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.listRecyclerView);
        getActivity().setTitle(R.string.high_score);
        databaseHelper = new DatabaseHelper(getActivity());

        score_text_TextView = rootView.findViewById(R.id.score_text);
        scoreint_TextView = rootView.findViewById(R.id.score);


        Bundle args = getArguments();
        if (args  != null && args.containsKey("score")) {
            score = args.getInt("score");
            String stringscore = Integer.toString(score);
            score_text_TextView.setVisibility(View.VISIBLE);
            scoreint_TextView.setVisibility(View.VISIBLE);
            Log.d("score","getInt score fra bundle: " + score);

           scoreint_TextView.setText(stringscore);

        }



        button_playagain = (Button)rootView.findViewById(R.id.button_play_again);
        button_playagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Menu menu = new Menu();
// Insert the fragment by replacing any existing fragment

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, menu)
                        .commit();

            }
        });

//        ListAdapter listAdapter = new ListAdapter(databaseHelper.getColumn(0),
//                databaseHelper.getColumn(1), databaseHelper.getColumn(2),
//                databaseHelper.getColumn(3),databaseHelper.getColumn(4));

        RecyclerListAdapter recyclerListAdapter = new RecyclerListAdapter(databaseHelper.getColumn(0),
                databaseHelper.getColumn(1), databaseHelper.getColumn(2),
                databaseHelper.getColumn(3),databaseHelper.getColumn(4));

        recyclerView.setAdapter(recyclerListAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return rootView;
    }


    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(getContext(),message, Toast.LENGTH_SHORT).show();
    }



}



