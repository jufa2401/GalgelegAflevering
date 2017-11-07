package com.example.s165158.galgelegaflevering;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.s165158.galgelegaflevering.Objekter.LetterAdapter;
import com.example.s165158.galgelegaflevering.Udleveret.Galgelogik;

import java.util.Date;

public class Spillet extends Fragment {



    private int antalforkerte;private Date date;
    private String savedWord, end_game;
    private TextView the_word, status;
    private LetterAdapter ltrAdapt;
    private GridView letters;
    private Galgelogik galgelogik = new Galgelogik();
    private ImageView galge;
    private Integer[] galgeimg = {
            R.drawable.galge,
            R.drawable.forkert1,
            R.drawable.forkert2,
            R.drawable.forkert3,
            R.drawable.forkert4,
            R.drawable.forkert5,
            R.drawable.forkert6,
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_spillet);
        View rootView = inflater.inflate(R.layout.fragment_spillet, container, false);

        getActivity().setTitle(R.string.Galgen);
        letters = rootView.findViewById(R.id.letters);
        ltrAdapt = new LetterAdapter(this);
        letters.setAdapter(ltrAdapt);

        the_word = rootView.findViewById(R.id.the_word);
        status = rootView.findViewById(R.id.statusText);

        galge = rootView.findViewById(R.id.galgen);
        galge.setImageResource(R.drawable.galge);

        end_game = getResources().getString(R.string.game_end);
        play();

        return rootView;
    }

    public void play() {
        galgelogik.nulstil();
        the_word.setText("");

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    galgelogik.hentOrdFraDr();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                return null;
            }

            @Override
            protected void onPostExecute(Object result) {

            }
        }.execute();
        the_word.setText(galgelogik.getSynligtOrd());


    }

    public void letterPressed(char letterChar) {
        galgelogik.gætBogstav("" + letterChar);
        update();
        if (galgelogik.erSpilletSlut()) return;

    }

    public void update() {
        if (galgelogik.erSpilletVundet() == true) {
            status.setText(getResources().getText(R.string.winner_winner_chicken_dinner));

        }
        if (galgelogik.erSpilletTabt() == true) {
            status.setText((getResources().getString(R.string.loss) + galgelogik.getOrdet()));
            setEnd_game((getResources().getString(R.string.loss) + galgelogik.getOrdet() + "\n" + getResources().getText(R.string.game_end)));

        }

        if (galgelogik.erSpilletSlut() == true) {
            setSavedWord(galgelogik.getOrdet());
            setAntalforkerte(galgelogik.getAntalForkerteBogstaver());
            setDate(new Date());



            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder
                    .setMessage(end_game)
                    .setCancelable(false)
                    .setNegativeButton(getResources().getText(R.string.menu), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Menu menu = new Menu();
                                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, menu).commit();
                                }
                            }
                    )
                    .setPositiveButton(getResources().getText(R.string.high_score), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ListFragment score = new ListFragment();
                                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, score).commit();
                                }
                            }
                    );
            AlertDialog alert = builder.create();
            alert.show();
            return;
        }

        if (galgelogik.erSidsteBogstavKorrekt() == false) {
            if (galgelogik.getAntalForkerteBogstaver() == 1) {
                status.setText("Du har: " + galgelogik.getAntalForkerteBogstaver() + " forkert, prøv igen");
                galge.setImageResource(galgeimg[galgelogik.getAntalForkerteBogstaver()]);
            } else if (galgelogik.getAntalForkerteBogstaver() > 1) {
                status.setText("Du har: " + galgelogik.getAntalForkerteBogstaver() + " forkerte, prøv igen");
                galge.setImageResource(galgeimg[galgelogik.getAntalForkerteBogstaver()]);
            }

        } else if (galgelogik.erSidsteBogstavKorrekt() == true) {
            status.setText("Du gættede rigtigt!");
            the_word.setText(galgelogik.getSynligtOrd());


        }
    }
//    Getters and Setters
    public void setEnd_game(String end_game) {
        this.end_game = end_game;
    }

    public int getAntalforkerte() {return antalforkerte;}

    public void setAntalforkerte(int antalforkerte) {this.antalforkerte = antalforkerte;}

    public String getSavedWord() {return savedWord;}

    public void setSavedWord(String savedWord) {this.savedWord = savedWord;}

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}
}





