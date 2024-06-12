package com.example.foodaround.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodaround.R;

import java.util.ArrayList;
public class RecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<RestoItem> restoItems;
    public RecyclerViewAdapter(ArrayList<RestoItem> restoItems) {

        this.restoItems = restoItems;
    }
    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_favorit, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

// Calculate positions for first and second items in each row

        int firstItemPosition = position * 2;
        int secondItemPosition = position * 2 + 1;
// Check if the first item position is within bounds

        if (firstItemPosition < restoItems.size()) {
            RestoItem firstItem = restoItems.get(firstItemPosition);
            holder.bind(firstItem, 1);
        }
// Check if the second item position is within bounds

        if (secondItemPosition < restoItems.size()) {
            RestoItem secondItem = restoItems.get(secondItemPosition);
            holder.bind(secondItem, 2);
        }
    }
    @Override
    public int getItemCount() {
// Calculate the number of rows needed
        return (int) Math.ceil((double) restoItems.size() / 2);
    }
    public static class ViewHolder extends
            RecyclerView.ViewHolder {

        ImageView ivResto1, ivResto2;
        TextView tvNama1, tvNama2;
        TextView tvDeskripsi1, tvDeskripsi2;
        RatingBar rbNilai1, rbNilai2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivResto1 = itemView.findViewById(R.id.ivResto);
            ivResto2 = itemView.findViewById(R.id.ivResto2);

            tvNama1 = itemView.findViewById(R.id.tvNama);
            tvNama2 = itemView.findViewById(R.id.tvNama2);

            tvDeskripsi1 = itemView.findViewById(R.id.tvDeskripsi);
            tvDeskripsi2 = itemView.findViewById(R.id.tvDeskripsi2);

            rbNilai1 = itemView.findViewById(R.id.rbNilai);
            rbNilai2 = itemView.findViewById(R.id.rbNilai2);

        }
        public void bind(RestoItem item, int position) {
// Bind data to views based on the position
            if (position == 1) {

                ivResto1.setImageResource(item.getImageResource());
                tvNama1.setText(item.getName());
                tvDeskripsi1.setText(item.getDescription());

                rbNilai1.setRating(item.getRating());
            } else {

                ivResto2.setImageResource(item.getImageResource());
                tvNama2.setText(item.getName());
                tvDeskripsi2.setText(item.getDescription());

                rbNilai2.setRating(item.getRating());
            }
        }

    }
}
