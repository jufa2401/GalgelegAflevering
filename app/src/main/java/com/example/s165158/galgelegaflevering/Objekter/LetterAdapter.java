package com.example.s165158.galgelegaflevering.Objekter;
/*
   Largely influenced by https://github.com/yoshuawuyts/hangDroid/tree/master/src/com/yoshuawuyts/hangdroid
   amendments have been made.
 */


import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s165158.galgelegaflevering.R;
import com.example.s165158.galgelegaflevering.Spillet;

public class LetterAdapter extends BaseAdapter {
    private final Spillet spillet;
    private String[] letters;
    private LayoutInflater letterInf;

    public LetterAdapter(Spillet c) {
        letters=new String[29];
//       tæller alfabetet op
        for (int a = 0; a < letters.length; a++) {
            letters[a] = "" + (char)(a+'a');
        }
//        sætter de danske bogstaver
        letters[26] = "æ";
        letters[27] = "ø";
        letters[28] = "å";

        //specify the context in which we want to inflate the layout
        // will be passed from the main activity
        letterInf = LayoutInflater.from(c.getContext());
        spillet = c;
    }

    @Override
    public int getCount() {

        return letters.length;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        //create a button for the letter at this position in the alphabet
        final Button letterBtn;
        if (convertView == null) {
            //inflate the button layout
            letterBtn = (Button)letterInf.inflate(R.layout.letter, parent, false);
        } else {
            letterBtn = (Button) convertView;
        }
        //set the text to this letter
        letterBtn.setText(letters[position]);


        letterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("OnItemClickListener", "Item clicked (meant for keyboard)");
                String ltr = ((TextView) v).getText().toString();
                char letterChar = ltr.charAt(0);

                // button is no longer available for press
                v.setEnabled(false);
//                rydder tekst for knappen
                ((TextView) v).setText("");
                // Kode til at sætte knap usynlig, valgt ikke at bruge for at beholde knaptryksanimation. Kunne beholdt animationen og få knappenn til at forsvinde ved at lave trådprogrammering, det er fravalgt.
                //                letterBtn.setVisibility(convertView.INVISIBLE);
                spillet.letterPressed(letterChar);
            }
        });
        return letterBtn;

    }



    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
