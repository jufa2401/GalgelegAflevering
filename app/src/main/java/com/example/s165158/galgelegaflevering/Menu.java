package com.example.s165158.galgelegaflevering;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static java.lang.Boolean.TRUE;

/**
 * Created by s165158 on 06-11-2017.
 */

public class Menu extends Fragment implements View.OnClickListener {

    public static boolean twoPlayers;
    private Button start_game_1player, start_game_2player, help, high_scores;
    private AppCompatActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity)
            mActivity = (AppCompatActivity) context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_spillet);
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        twoPlayers = false;
        getActivity().setTitle(R.string.menu);
        start_game_1player = rootView.findViewById(R.id.button_start_game_1player);
        start_game_1player.setOnClickListener(this);

        start_game_2player = rootView.findViewById(R.id.button_start_game_2player);
        start_game_2player.setOnClickListener(this);

        help = rootView.findViewById(R.id.button_help);
        help.setOnClickListener(this);
        high_scores = rootView.findViewById(R.id.button_high_score);
        high_scores.setOnClickListener(this);

        if (MainActivity.confettiManagerUp == TRUE) {
            ((MainActivity) mActivity).terminateConfettiManager();
        }

        return rootView;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.button_start_game_1player:
                Spillet spil_1 = new Spillet();
                getFragmentManager().beginTransaction().setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left, R.animator.slide_out_right, R.animator.slide_in_right).replace(R.id.fragment_container, spil_1).addToBackStack("back to menu from game").commit();
                break;
            case R.id.button_start_game_2player:
                twoPlayers = true;
                Spillet spil_2 = new Spillet();
                getFragmentManager().beginTransaction().setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left, R.animator.slide_out_right, R.animator.slide_in_right).replace(R.id.fragment_container, spil_2).addToBackStack("back to menu from game").commit();
                break;

            case R.id.button_high_score:
                ListFragment scores = new ListFragment();

                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left, R.animator.slide_out_right, R.animator.slide_in_right)
                        .replace(R.id.fragment_container, scores).addToBackStack("back to menu from scores").commit();
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

    @Override
    public Context getContext() {
        Context context;
        int ver = Build.VERSION.SDK_INT;
        if (ver > 22) {
            context = super.getContext();
        }
        // Til gamle APIer
        else {
            context = getActivity();
        }
        return context;
    }
}
