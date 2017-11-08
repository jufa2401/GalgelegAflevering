//package com.example.s165158.galgelegaflevering;
//
//import android.app.Activity;
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.s165158.galgelegaflevering.database.DatabaseHelper;
//
//
///**
// * Created by s165158 on 04-11-2017.
// */
//
//public class ListAdapter extends RecyclerView.Adapter {
////    TODO: Check if you need context
//
//
//    private String[] id, name, word, date, scoretext;
//
//    public ListAdapter (String[] id, String[] scoretext, String[] date, String[] word,
//                        String[] name)
//    {
//        this.id = id;
//        this.scoretext = scoretext;
//        this.date = date;
//        this.word = word;
//        this.name = name;
//    }
//
//
//
//
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_item,parent,false);
//
//        return new ListViewHolder(view);
//
//
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        ((ListViewHolder)holder).bindView(position);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return id.length;
//    }
//
//    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        private String[] name, word, date, scoretext, id;
//
//        private DatabaseHelper databaseHelper;
//        private TextView itemText;
//        private ImageView itemImage;
//
//        public void setCol0(String[] col0) {
//            this.id = col0;
//        }
//
//        public void setCol1(String[] col1) {
//            this.name = col1;
//        }
//
//        public void setCol2(String[] col2) {
//            this.word = col2;
//        }
//
//        public void setCol3(String[] col3) {
//            this.scoretext = col3;
//        }
//
//        public void setCol4(String[] col4) {
//            this.date = col4;
//        }
//
//
//        public ListViewHolder(View itemView) {
//            super(itemView);
//            itemView.setOnClickListener(this);
//            itemText = itemView.findViewById(R.id.itemText);
//
//        }
//
//        public void bindView(int position) {
//            itemText.setText(scoretext[position]);
//        }
//
//        @Override
//        public void onClick(View view) {
//
//        }
//    }
//
//}
