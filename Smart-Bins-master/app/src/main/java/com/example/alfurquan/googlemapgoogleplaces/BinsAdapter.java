package com.example.alfurquan.googlemapgoogleplaces;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BinsAdapter extends RecyclerView.Adapter<BinsAdapter.MyViewHolder> {
    private Context context;
    private View view;
    private List<Bins> binsList;

    public BinsAdapter(Context context, List<Bins> binsList) {
        this.context = context;
        this.binsList = binsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_bins, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Bins bins = binsList.get(position);
        holder.title.setText(bins.getLabel());
        holder.imageView.setImageResource(R.drawable.bin2);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,BinsDetail.class);
                intent.putExtra("name",bins.getLabel());
                intent.putExtra("id",bins.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return binsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView imageView;


        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.event_type);
            imageView = (ImageView)itemView.findViewById(R.id.bin_img);
        }
    }
}
