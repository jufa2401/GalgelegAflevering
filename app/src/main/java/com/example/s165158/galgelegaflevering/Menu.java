package com.example.s165158.galgelegaflevering;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.s165158.galgelegaflevering.Objekter.LetterAdapter;

/**
 * Created by s165158 on 06-11-2017.
 */

public class Menu extends Fragment implements View.OnClickListener {

    Button start_game, help, high_scores;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_spillet);
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);

        getActivity().setTitle(R.string.menu);
        start_game = rootView.findViewById(R.id.button_start_game);
        start_game.setOnClickListener(this);
        help = rootView.findViewById(R.id.button_help);
        help.setOnClickListener(this);
        high_scores = rootView.findViewById(R.id.button_high_score);
        high_scores.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_start_game:
                Spillet spillet = new Spillet();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, spillet).addToBackStack("back to menu from game").commit();
                break;
            case R.id.button_high_score:
                ListFragment scores = new ListFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, scores).addToBackStack("back to menu from scores").commit();
                break;
            case R.id.button_help:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(R.string.help_message)
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Brgues
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

                break;
            default:
                break;
        }
    }
}
