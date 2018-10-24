package com.dusmel.handy_services;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by abd on 28/08/2017.
 */

public class HandyAdapter extends RecyclerView.Adapter<HandyAdapter.ViewHolder> {

    private List<ListHandyman> listHandymen;
    private Context context;
    private String Url;



public HandyAdapter(List<ListHandyman> listHandymen, Context context, String Url){
    this.listHandymen = listHandymen;
    this.context = context;
    this.Url = Url;


        }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_handyman, parent, false);
        ViewHolder holder=new ViewHolder(v, context, Url);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListHandyman listHandyman = listHandymen.get(position);

        String handyNames = listHandyman.getHandyFname()+" "+listHandyman.getHandyLname();

        holder.HandyNames.setText(handyNames);
        holder.HandyField.setText(listHandyman.getHandyField());

        String zone = "Zone: "+listHandyman.getHandyAdd();
        holder.HandyAdd.setText(zone);

        String price = "price: "+listHandyman.getHandyPrice();
        holder.HandyPrice.setText(price);


        String success = "success rate:: "+listHandyman.getHandySuccess();
        holder.HandySuccess.setText(success);


        Picasso.with(context)
                .load(listHandyman.getHandypfl())
                .into(holder.Handypfl);

    }

    @Override
    public int getItemCount() {
        return listHandymen.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView HandyNames;
        public TextView HandyField;
        public TextView HandyAdd;
        public TextView HandyPrice;
        public TextView HandySuccess;
        public CircleImageView Handypfl;
        Context context;
        String url;

        public ViewHolder(View itemView, Context context, String Url) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.context = context;

            HandyAdd = (TextView) itemView.findViewById(R.id.handyAdd);
            HandyField = (TextView) itemView.findViewById(R.id.handyField);
            HandyNames = (TextView) itemView.findViewById(R.id.handyNames);
            HandyPrice = (TextView) itemView.findViewById(R.id.handyprice);
            HandySuccess = (TextView) itemView.findViewById(R.id.handysuccess);
            Handypfl = (CircleImageView) itemView.findViewById(R.id.Handypfl);


        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            int ids = listHandymen.get(pos).getId();
            Intent i = new Intent(context, HandymanDesc.class);
            i.putExtra("ids", ids);
            context.startActivity(i);





        }
    }
}
