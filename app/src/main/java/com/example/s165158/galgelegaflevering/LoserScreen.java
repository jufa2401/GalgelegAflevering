package com.example.s165158.galgelegaflevering;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by s165158 on 09-11-2017.
 */

public class LoserScreen extends Fragment {

    private TextView loser, loser_descriptive, loser_score, endGame_TextView;
    private Button play_again, high_scores;
    private int score;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.loser_screen);
        View rootView = inflater.inflate(R.layout.winnerloser_scren, container, false);


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


        play_again = rootView.findViewById(R.id.button_play_again_winner_loser);
        play_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Menu menu = new Menu();
// Insert the fragment by replacing any existing fragment

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, menu)
                        .disallowAddToBackStack()
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
                        .replace(R.id.fragment_container, highscore)
                        .disallowAddToBackStack()
                        .commit();

            }
        });


        return rootView;
    }

}