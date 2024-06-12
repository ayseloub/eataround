package com.example.foodaround.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foodaround.database.Review;
import com.example.foodaround.repository.ReviewRepository;

import java.util.List;

public class ReviewViewModel extends AndroidViewModel {
    private ReviewRepository repository;
    private LiveData<List<Review>> allReviews;

    public ReviewViewModel(@NonNull Application application) {
        super(application);
        repository = new ReviewRepository();
        allReviews = repository.getAllReviews();
    }

    public LiveData<List<Review>> getAllReviews() {
        return allReviews;
    }

    public void insert(Review review) {
        repository.insert(review);
    }

    public void delete(String reviewId) {
        repository.delete(reviewId);
    }
}



