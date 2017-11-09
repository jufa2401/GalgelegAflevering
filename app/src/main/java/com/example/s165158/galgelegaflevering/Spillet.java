package com.example.s165158.galgelegaflevering;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s165158.galgelegaflevering.Objekter.LetterAdapter;
import com.example.s165158.galgelegaflevering.Udleveret.Galgelogik;
import com.example.s165158.galgelegaflevering.database.DatabaseHelper;
import com.github.jinatonic.confetti.CommonConfetti;


import java.util.Date;

public class Spillet extends Fragment {

//    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
//    String[] savedScores;
    private Date date;
    private int antalforkerte;


    private DatabaseHelper databaseHelper;
    private int forsøg = 0;
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
        databaseHelper = new DatabaseHelper(getActivity());
        getActivity().setTitle(R.string.Galgen);
        letters = rootView.findViewById(R.id.letters);
        ltrAdapt = new LetterAdapter(this);
//        letters.setAdapter(ltrAdapt);

        the_word = rootView.findViewById(R.id.the_word);
        status = rootView.findViewById(R.id.statusText);
        status.setText(getResources().getText(R.string.waiting));

        galge = rootView.findViewById(R.id.galgen);
        galge.setImageResource(R.drawable.galge);


        play();

        return rootView;
    }

    public void play() {
        galgelogik.nulstil();
//        the_word.setText("");

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {

                    galgelogik.hentOrdFraDr();
                    Log.e("ord fra DR", "DR Ord hentet, eller fejl i Galgelogik");
                } catch (InterruptedException e) {
                    Thread.interrupted();
                    Log.e("ord fra DR", "Kunne ikke hente ord fra DR");
                    e.printStackTrace();
//                   Luk programmet
                    getActivity().finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                return Log.d("hent fra dr afsluttet","Ordene fra DR er enten hentet eller fejlet");
            }

            @Override
            protected void onPostExecute(Object result) {
                the_word.setText(galgelogik.getSynligtOrd());
                letters.setAdapter(ltrAdapt);
                status.setText(getResources().getText(R.string.welcome));

            }
        }.execute();
    }

    public void letterPressed(char letterChar) {

        galgelogik.gætBogstav("" + letterChar);
        forsøg++;
        update();
        if (galgelogik.erSpilletSlut()) return;

    }

    public void update() {
        if (galgelogik.erSpilletVundet() == true) {
            status.setText(getResources().getText(R.string.winner_winner_chicken_dinner));
            setEnd_game(getResources().getString(R.string.winner) + getForsøg() + getResources().getString(R.string.attempts)
                    + getResources().getString(R.string.game_end));

//            Mediaplayer til 3. Aflevering
            final MediaPlayer mp = MediaPlayer.create(getContext(),R.raw.yababy);
            mp.start();



        }
        if (galgelogik.erSpilletTabt() == true) {
            status.setText((getResources().getString(R.string.loss) + galgelogik.getOrdet()));
            setEnd_game((getResources().getString(R.string.loss) + galgelogik.getOrdet() + "\n" + getResources().getText(R.string.game_end)));
//            Mediapleyer til 3. Aflevering
            final MediaPlayer mp = MediaPlayer.create(getContext(),R.raw.dumbass);
            mp.start();

        }

        if (galgelogik.erSpilletSlut() == true) {
            setSavedWord(galgelogik.getOrdet());
            setAntalforkerte(galgelogik.getAntalForkerteBogstaver());
            setDate(new Date());
            Log.d("transferred data", "Følgende gemmes i databasen: "+ "noname" + forsøg + date.toString());
            databaseHelper.addData("noname",savedWord,forsøg-antalforkerte,date.toString());



            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder
                    .setMessage(end_game)
                    .setCancelable(false)
                    .setNegativeButton(getResources().getText(R.string.menu), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Menu menu = new Menu();
                                    getFragmentManager().beginTransaction()
                                            .replace(R.id.fragment_container, menu)
                                            .commit();

                                }
                            }
                    )
                    .setPositiveButton(getResources().getText(R.string.high_score), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ListFragment score = new ListFragment();
                                    final Bundle bundle = new Bundle();
//                                    sender scoren med
                                    bundle.putInt("score",forsøg-antalforkerte);
                                    score.setArguments(bundle);
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


    /**
     * Custom toast
     * @param message
     */
    public void toastMessage(String message) {
        Toast.makeText(getContext(),message, Toast.LENGTH_SHORT).show();
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

    public int getForsøg() {return forsøg;}
}





