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
 * Created by abd on 12/09/2017.
 */

public class HiringAdapter extends RecyclerView.Adapter<HiringAdapter.ViewHolder> {

    private List<HiringListitem> listItems;
    private Context context;
    private String url;

    public HiringAdapter(List<HiringListitem> listItems, Context context, String url) {
        this.listItems = listItems;
        this.context = context;
        this.url = url;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hiringlist, parent, false);
        ViewHolder holder = new ViewHolder(v, context, url);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HiringListitem  listItem = listItems.get(position);
        String names = listItem.getNames();
        holder.names.setText(names);
        holder.field.setText(listItem.getField());
        holder.address.setText(listItem.getAddress());
        holder.time.setText(listItem.getTimestamp());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView names,field,address,time;
        public Context context;
        public String url;

        public ViewHolder(View itemView, Context context, String url) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.context = context;
            this.url = url;

            names = (TextView) itemView.findViewById(R.id.recClientNames);
            field = (TextView) itemView.findViewById(R.id.recHiringfield);
            address = (TextView) itemView.findViewById(R.id.recHiringadd);
            time = (TextView) itemView.findViewById(R.id.recHiringTime);
        }

        @Override
        public void onClick(View v) {


            int pos = getAdapterPosition();
            Intent i = new Intent(context, AdminDescr.class);
            i.putExtra("pos", pos);
            i.putExtra("url", url);
            context.startActivity(i);


        }
    }
}
