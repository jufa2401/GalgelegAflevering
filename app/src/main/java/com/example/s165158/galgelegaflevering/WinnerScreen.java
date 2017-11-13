package com.example.s165158.galgelegaflevering;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by s165158 on 09-11-2017.
 */

public class WinnerScreen extends Fragment {

    private TextView winner, winner_descriptive, winner_attempts;
    private Button play_again, high_scores;
    int attempts = 2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.loser_screen);
        View rootView = inflater.inflate(R.layout.winnerloser_scren, container, false);



        winner = rootView.findViewById(R.id.winner_loser_text);
        winner.setText(R.string.you_won);

        winner_descriptive = rootView.findViewById(R.id.winner_loser_descriptive_text);
        winner_descriptive.setText(R.string.used_attempts);


        winner_attempts = rootView.findViewById(R.id.winner_loser_attempts);
        winner_attempts.setText(""+attempts);


        play_again = rootView.findViewById(R.id.button_play_again_winner_loser);
        play_again.setOnClickListener(new View.OnClickListener() {
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

        high_scores = rootView.findViewById(R.id.button_high_score_winner_loser);
        high_scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListFragment score = new ListFragment();
                final Bundle bundle = new Bundle();
//                                    sender scoren med
                bundle.putInt("score",forsøg-antalforkerte);
                score.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, score).commit();

            }
        });


        return rootView;
    }

}