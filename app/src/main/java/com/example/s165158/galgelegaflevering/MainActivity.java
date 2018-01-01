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


public class MainActivity extends AppCompatActivity {
    public static boolean confettiManagerUp = FALSE;
    private TextView title_top;
    private ConfettiManager confettiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Tvinger aktiviteten til at køre i portrait mode.
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

    public void setConfettiManager(ViewGroup container, int color, long durationInMillis) {
        confettiManager = CommonConfetti.rainingConfetti(container, new int[]{color})
                .stream(durationInMillis);
        confettiManagerUp = TRUE;
    }

    public void terminateConfettiManager() {
        confettiManager.terminate();
    }

    //    Metode til at stoppe brugeren for at gå tilbage til tidligere skærme, hvor han ikke skal.
    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        switch (count) {
            case 0:
                super.onBackPressed();
                break;
            case 1:
                super.onBackPressed();
                break;
            case 2:
//
                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;
            default:
                getFragmentManager().popBackStack();
                break;
        }

    }
}
