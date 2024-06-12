package com.example.foodaround;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SuggestedRestaurantAdapter extends RecyclerView.Adapter<SuggestedRestaurantAdapter.VH> {
    private final Context context;
    private final List<SuggestedRestaurant> filterHeaders;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(SuggestedRestaurant suggestedRestaurant);
    }

    public SuggestedRestaurantAdapter(Context context, List<SuggestedRestaurant> filterHeaders) {
        this.context = context;
        this.filterHeaders = filterHeaders;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(filterHeaders.get(position));
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vh = LayoutInflater.from(context)
                .inflate(R.layout.activity_suggested_restaurant, parent, false); // Changed layout name
        return new VH(vh);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        SuggestedRestaurant SG = filterHeaders.get(position);
        holder.tvnama.setText(SG.nama.toString());
        holder.tvdeskripsi.setText(SG.Deskripsi.toString());
        holder.ivresto.setImageResource(SG.gambar);
    }

    @Override
    public int getItemCount() {
        return filterHeaders.size();
    }
}
