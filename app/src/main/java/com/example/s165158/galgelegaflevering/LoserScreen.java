package com.example.s165158.galgelegaflevering;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.jinatonic.confetti.ConfettiManager;

/**
 * Created by s165158 on 09-11-2017.
 */

public class LoserScreen extends Fragment {

    private static String TAG = LoserScreen.class.getName();
    private TextView loser, loser_descriptive, loser_score, endGame_TextView;
    private Button play_again, high_scores;
    private int score;
    private ConfettiManager confettiManager;
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
        getActivity().setTitle(R.string.loser_screen);
        //getActivity().setTitle(R.string.loser_screen);
        View rootView = inflater.inflate(R.layout.winnerloser_scren, container, false);
        ((MainActivity) mActivity).setDisableBack(true);
        loser = rootView.findViewById(R.id.winner_loser_text);
        loser.setText(R.string.you_lose);

        loser_descriptive = rootView.findViewById(R.id.winner_loser_score_descriptive_text);
        loser_descriptive.setText(R.string.score_text);

        loser_score = rootView.findViewById(R.id.winner_loser_score);
        Bundle args = getArguments();

        endGame_TextView = rootView.findViewById(R.id.endGame);
        String endGame = args.getString("endgame tekst");
        endGame_TextView.setText(endGame);

        score = args.getInt("score");
        String stringscore = Integer.toString(score);

        Log.d("score", "getInt score fra bundle: " + score);

        loser_score.setText(stringscore);

        ((MainActivity) mActivity).setConfettiManager(container, new int[]{Color.BLACK}, 500);

        play_again = rootView.findViewById(R.id.button_play_again_winner_loser);
        play_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Stopper confetti hvis man går til hovedmenuen.
                Menu menu = new Menu();
// Insert the fragment by replacing any existing fragment

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .addToBackStack("menu")
                        .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left, R.animator.slide_out_right, R.animator.slide_in_right)
                        .replace(R.id.fragment_container, menu)
                        .commit();
            }
        });

        high_scores = rootView.findViewById(R.id.button_high_score_winner_loser);
        high_scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListFragment highscore = new ListFragment();
                final Bundle bundle = new Bundle();
//                                    sender scoren med
                bundle.putInt("score", score);
                highscore.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .addToBackStack("High Score")
                        .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left, R.animator.slide_out_right, R.animator.slide_in_right)
                        .replace(R.id.fragment_container, highscore)

                        .commit();

            }
        });

        return rootView;
    }


}