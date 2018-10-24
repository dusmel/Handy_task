package com.dusmel.handy_services;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by abd on 20/09/2017.
 */

public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.ViewHolder> {
    private List<Joblists> listItems;
    private Context context;
    private  String url;

    public JobListAdapter(List<Joblists> listItems, Context context, String url) {
        this.listItems = listItems;
        this.context = context;
        this.url = url;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.joblist, parent, false);
        ViewHolder holder = new ViewHolder(view, context, url);


        return  holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Joblists listItem = listItems.get(position);

        holder.names.setText(listItem.getNames());
        holder.time.setText(listItem.getTimestamp());
        holder.address.setText(listItem.getAddress());




    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView names, stat, time, address;

        Context context;
        String url;

        public ViewHolder(View itemView, Context context, String url) {
            super(itemView);
            itemView.setOnClickListener(this);
            names = (TextView) itemView.findViewById(R.id.hHClientNames);
            time = (TextView) itemView.findViewById(R.id.hHHiringTime);
            address = (TextView) itemView.findViewById(R.id.hHHiringadd);



            this.context = context;
            this.url  = url;



        }

        @Override
        public void onClick(View v) {

            int pos= getAdapterPosition();
            Intent intent = new Intent(context, JobDesc.class);
            intent.putExtra("pos", pos);
            intent.putExtra("url", url);
            context.startActivity(intent);




        }
    }
}
