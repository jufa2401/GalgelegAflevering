package com.example.s165158.galgelegaflevering;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity
{
    TextView title_top;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Menu menu = new Menu();

        getFragmentManager().beginTransaction().add(R.id.fragment_container, menu).commit();
    }
    @Override
    public void setTitle(int resid) {
        title_top = findViewById(R.id.head);
        String title = getString(resid);
        title_top.setText(title);
    }
}
