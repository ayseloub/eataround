package com.example.foodaround;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.ResourceManagerInternal;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SuggestedRestaurantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<SuggestedRestaurant> filterHeaders;

    public SuggestedRestaurantAdapter(Context context, List<SuggestedRestaurant> filterHeaders) {
        this.context = context;
        this.filterHeaders = filterHeaders;
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView tvnama;
        TextView tvdeskripsi;
        ImageView ivresto;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvnama = itemView.findViewById(R.id.tvnama);
            tvdeskripsi = itemView.findViewById(R.id.tvdeskripsi);
            ivresto = itemView.findViewById(R.id.ivresto);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vh = LayoutInflater.from(context)
                .inflate(R.layout.activity_suggested_restaurant, parent, false);
        return new VH(vh);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SuggestedRestaurant SG = filterHeaders.get(position);
        VH vh = (VH) holder;
        vh.tvnama.setText(SG.nama.toString());
        vh.tvdeskripsi.setText(SG.Deskripsi.toString());
        vh.ivresto.setImageResource(SG.gambar);
    }

    @Override
    public int getItemCount() {
        return filterHeaders.size();
    }
}

