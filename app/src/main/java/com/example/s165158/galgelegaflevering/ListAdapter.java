package com.example.s165158.galgelegaflevering;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by s165158 on 04-11-2017.
 */

public class ListAdapter extends RecyclerView.Adapter {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_item,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder)holder).bindView(position);

    }

    @Override
    public int getItemCount() {
        return Ourdata.examples.length;
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView itemText;
        private ImageView itemImage;

        public ListViewHolder(View itemView) {
            super(itemView);

            itemText = itemView.findViewById(R.id.itemText);
            itemView.setOnClickListener(this);
        }

        public void bindView(int position) {
            itemText.setText(Ourdata.examples[position]);
        }

        @Override
        public void onClick(View view) {

        }
    }

}
