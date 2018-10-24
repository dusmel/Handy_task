package com.dusmel.handy_services;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by abd on 26/08/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<ListItem> listItems;
    private Context context;
    private  String url;

    public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        ViewHolder holder = new ViewHolder(v, context, url);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ListItem listItem = listItems.get(position);

        holder.fieldTit.setText(listItem.getTitle());

        Picasso.with(context)
                .load(listItem.getImage())
                .into(holder.img);
        Picasso.with(context)
                .load(listItem.getLogo())
                .into(holder.fieldLogo);

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView img;
        public TextView fieldTit;
        public CircleImageView fieldLogo;
        Context context;
        String url;



        public ViewHolder(View itemView, Context context, String url) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.context = context;
            this.url = url;

            img = (ImageView) itemView.findViewById(R.id.imgfield);
            fieldTit =  (TextView) itemView.findViewById(R.id.fieldtitle);
            fieldLogo = (CircleImageView) itemView.findViewById(R.id.fieldLogo);


        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Intent openHandyman = new Intent(context, HandymanField.class);
            openHandyman.putExtra("position", pos);
            context.startActivity(openHandyman);
//            Toast.makeText(context, ""+pos , Toast.LENGTH_SHORT).show();

        }
    }
}
