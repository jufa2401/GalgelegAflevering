package com.example.s165158.galgelegaflevering.Objekter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.s165158.galgelegaflevering.R;

/**
 * Created by s165158 on 08-11-2017.
 */

public class RecyclerListAdapter  extends RecyclerView.Adapter<RecyclerListAdapter.ViewHolder> {

    public RecyclerListAdapter(String[] idarray, String[] namearray, String[] wordarray, String[] scorearray, String[] datearray) {
        this.idarray = idarray;
        this.namearray = namearray;
        this.wordarray = wordarray;
        this.scorearray = scorearray;
        this.datearray = datearray;
    }

    private String[] idarray, namearray, wordarray, scorearray, datearray;
    Listener mListener;


//    On click listener for position
    public interface Listener{
        void onClick(int position);
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view =  itemView;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_item,parent,false);

        return new ViewHolder(view);
    }
//    Bemærk jeg bruger ikke name endnu, det er et parameter jeg lagde ind i klassen fordi jeg kunne forestille mig at implementere det senere enten sammen eller i stedet for ID.
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        View view = holder.view;
        TextView id = (TextView) view.findViewById(R.id.list_item_ID);
        id.setText(idarray[position]);
        TextView word = (TextView) view.findViewById(R.id.list_item_Word);
        word.setText(wordarray[position]);
        TextView date = (TextView) view.findViewById(R.id.list_item_Date);
        date.setText(datearray[position]);
        TextView score = (TextView) view.findViewById(R.id.list_item_Score);
        score.setText(scorearray[position]);
        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(mListener != null){
                    mListener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return scorearray.length;
    }
}