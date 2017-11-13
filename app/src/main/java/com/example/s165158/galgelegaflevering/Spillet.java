package com.example.s165158.galgelegaflevering;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s165158.galgelegaflevering.Objekter.LetterAdapter;
import com.example.s165158.galgelegaflevering.Udleveret.Galgelogik;
import com.example.s165158.galgelegaflevering.database.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Spillet extends Fragment {
    private String date;
    private SimpleDateFormat dateformatter;
    private Date date2;
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
//        Async task til at opdatere ord fra DR.
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
            //            Efer udførelsen af at hente ordet fra DR.
            @Override
            protected void onPostExecute(Object result) {
                the_word.setText(galgelogik.getSynligtOrd());
                letters.setAdapter(ltrAdapt);
                status.setText(getResources().getText(R.string.welcome));

            }
        }.execute();
    }
    // Når man trykker på tastaturet
    public void letterPressed(char letterChar) {

        galgelogik.gætBogstav("" + letterChar);
        forsøg++;
        update();
        if (galgelogik.erSpilletSlut()) return;

    }

//    Opdateringer og gennemtjek efter der bliver trykket
    public void update() {
//        Når spillet er vundet
        if (galgelogik.erSpilletVundet() == true) {
            status.setText(getResources().getText(R.string.winner_winner_chicken_dinner));
            end_game = getResources().getString(R.string.winner) + getForsøg() + getResources().getString(R.string.attempts)
                    + getResources().getString(R.string.game_end);

//            Mediaplayer til 3. Aflevering
            final MediaPlayer mp = MediaPlayer.create(getContext(),R.raw.yababy);
            mp.start();

        }
//        Når spillet er tabt
        if (galgelogik.erSpilletTabt() == true) {
            status.setText((getResources().getString(R.string.loss)+" " + galgelogik.getOrdet()));
            end_game = getResources().getString(R.string.loss)+" " + galgelogik.getOrdet() + getResources().getText(R.string.game_end);
//            Mediapleyer til 3. Aflevering
            final MediaPlayer mp = MediaPlayer.create(getContext(),R.raw.dumbass);
            mp.start();

        }
//        Når spillet er slut. Altså ting der er generelle både for når der er tabt og vundet.
        if (galgelogik.erSpilletSlut() == true) {
            savedWord = galgelogik.getOrdet();
            the_word.setText(savedWord);
            antalforkerte = galgelogik.getAntalForkerteBogstaver();
            date2 = new Date();
            dateformatter = new SimpleDateFormat("H:mm,EEE, MMM d, ''yy");
            date = dateformatter.format(date2);
            Log.d("transferred data", "Følgende gemmes i databasen: "+ "noname" + forsøg + date);
            databaseHelper.addData("noname",savedWord,forsøg-antalforkerte,date);


//            Dialog boks for at brugeren skal vide at der sker noget vigtigt.
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder
                    .setMessage(R.string.game_has_ended)
                    .setCancelable(false)
                    .setNegativeButton(getResources().getText(R.string.ok), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    WinnerScreen winnerScreen = new WinnerScreen();
                                    LoserScreen loserScreen = new LoserScreen();
                                    final Bundle bundle = new Bundle();
//                                    sender scoren med
                                    bundle.putInt("score",forsøg-antalforkerte);
                                    bundle.putString("endgame tekst", end_game);

                                    if (galgelogik.erSpilletVundet() == true) {
                                        winnerScreen.setArguments(bundle);

                                        getFragmentManager().beginTransaction()
                                                .replace(R.id.fragment_container, winnerScreen)
                                                .addToBackStack(null)
                                                .commit();
                                    }
                                    if (galgelogik.erSpilletTabt() == true) {
                                        loserScreen.setArguments(bundle);
                                        getFragmentManager().beginTransaction()
                                                .replace(R.id.fragment_container, loserScreen)
                                                .addToBackStack(null)
                                                .commit();

                                    }
                                }
                            }
                    );
            AlertDialog alert = builder.create();
            alert.show();
            return;
        }

//        Hvis sidste bogstav er gættet forkert.
        if (galgelogik.erSidsteBogstavKorrekt() == false) {
            if (galgelogik.getAntalForkerteBogstaver() == 1) {
                status.setText("Du har: " + galgelogik.getAntalForkerteBogstaver() + " forkert, prøv igen");
                galge.setImageResource(galgeimg[galgelogik.getAntalForkerteBogstaver()]);
            } else if (galgelogik.getAntalForkerteBogstaver() > 1) {
                status.setText("Du har: " + galgelogik.getAntalForkerteBogstaver() + " forkerte, prøv igen");
                galge.setImageResource(galgeimg[galgelogik.getAntalForkerteBogstaver()]);
            }
//          Hvis sidste bogstav er gættet rigtigt
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

    public String getDate() {return date;}

    public void setDate(String date) {this.date = date;}

    public int getForsøg() {return forsøg;}

}





