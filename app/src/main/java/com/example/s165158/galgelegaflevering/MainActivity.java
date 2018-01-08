package com.example.s165158.galgelegaflevering;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.jinatonic.confetti.CommonConfetti;
import com.github.jinatonic.confetti.ConfettiManager;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

// Bug fundet 2. Januar, man kan gå tilbage fra win/lose -
public class MainActivity extends AppCompatActivity {
    public static boolean confettiManagerUp = FALSE;
    public boolean disableback = false;
    private TextView title_top;
    private ConfettiManager confettiManager;
    private boolean twoplayers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_menu);
        Menu menu = new Menu();

        getFragmentManager().beginTransaction().add(R.id.fragment_container, menu).commit();

    }

    //  Metode til at sætte titlen for skærmbilledet
    @Override
    public void setTitle(int resid) {
        title_top = findViewById(R.id.head);
        String title = getString(resid);
        title_top.setText(title);
    }

    public void setConfettiManager(ViewGroup container, int[] color, long durationInMillis) {
        confettiManager = CommonConfetti.rainingConfetti(container, color)
                .stream(durationInMillis);
        confettiManagerUp = TRUE;
    }

    public void terminateConfettiManager() {
        // Checker om objektet er null, da en null pointer exception blev fundet under specifikke omstændigheder
        if (confettiManager instanceof ConfettiManager) {
            confettiManager.terminate();
        }
    }

    // Hvis et spil er gået i gang, men er røget ud af hukommelsen, skal man vide hvorvidt det er et twoplayer eller single player spil
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("twoplayers", Menu.twoPlayers);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        twoplayers = savedInstanceState.getBoolean("twoplayers");
        Menu.twoPlayers = twoplayers;
    }

    @Override
    public void onBackPressed() {
        if (!disableback) super.onBackPressed();
    }

    public void setDisableBack(boolean val) {
        disableback = val;
    }
}
