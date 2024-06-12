package com.example.foodaround.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodaround.R;
import com.example.foodaround.database.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<Review> reviews = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Review review);
        void onDeleteClick(String reviewId);
        void onEditClick(Review review);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.tvNama.setText(review.getNama());
        holder.tvAlamat.setText(review.getAlamat());
        holder.tvNamaresto.setText(review.getNamaresto());
        holder.tvReview.setText(review.getReview());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(review);
            }
        });
        holder.tvDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(review.getId());
            }
        });
        holder.tvEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(review);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNama;
        private TextView tvAlamat;
        private TextView tvNamaresto;
        private TextView tvReview;
        private TextView tvDelete;
        private TextView tvEdit;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvAlamat = itemView.findViewById(R.id.tv_alamat);
            tvNamaresto = itemView.findViewById(R.id.tv_namaresto);
            tvReview = itemView.findViewById(R.id.tv_review);
            tvDelete = itemView.findViewById(R.id.tv_delete);
            tvEdit = itemView.findViewById(R.id.tv_edit);
        }
    }
}



