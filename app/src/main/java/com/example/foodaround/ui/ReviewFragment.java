package com.example.foodaround.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


import com.example.foodaround.R;
import com.example.foodaround.database.Review;

import java.util.List;

public class ReviewFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayout layAddReview;
    private EditText etNama, etAlamat, etNamaresto, etReview;
    private Button btnClear, btnSubmit;

    private ReviewViewModel reviewViewModel;
    private ReviewAdapter reviewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_review, container, false);

        recyclerView = rootView.findViewById(R.id.rv_review);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        layAddReview = rootView.findViewById(R.id.layout_add);
        etNama = rootView.findViewById(R.id.et_nama);
        etAlamat = rootView.findViewById(R.id.et_alamat);
        etNamaresto = rootView.findViewById(R.id.et_namaresto);
        etReview = rootView.findViewById(R.id.et_review);
        btnClear = rootView.findViewById(R.id.btn_clear);
        btnSubmit = rootView.findViewById(R.id.btn_submit);

        reviewViewModel = new ViewModelProvider(requireActivity()).get(ReviewViewModel.class);

        btnClear.setOnClickListener(v -> clearData());

        btnSubmit.setOnClickListener(v -> {
            String nama = etNama.getText().toString().trim();
            String alamat = etAlamat.getText().toString().trim();
            String namaresto = etNamaresto.getText().toString().trim();
            String reviewText = etReview.getText().toString().trim();

            if (!nama.isEmpty() && !alamat.isEmpty() && !namaresto.isEmpty() && !reviewText.isEmpty()) {
                Review review = new Review(null, nama, alamat, namaresto, reviewText);
                reviewViewModel.insert(review);
                clearData();
            }
        });

        reviewAdapter = new ReviewAdapter();
        recyclerView.setAdapter(reviewAdapter);

        reviewAdapter.setOnItemClickListener(new ReviewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Review review) {
                Intent intent = new Intent(getActivity(), DetailReviewActivity.class);
                intent.putExtra("cnama", review.getNama());
                intent.putExtra("calamat", review.getAlamat());
                intent.putExtra("cnamaresto", review.getNamaresto());
                intent.putExtra("creview", review.getReview());
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(String reviewId) {
                reviewViewModel.delete(reviewId);
            }
        });

        reviewViewModel.getAllReviews().observe(getViewLifecycleOwner(), new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                reviewAdapter.setReviews(reviews);
            }
        });

        return rootView;
    }

    private void clearData() {
        etNama.setText("");
        etAlamat.setText("");
        etNamaresto.setText("");
        etReview.setText("");
    }
}