package com.example.foodaround.repository;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodaround.database.Review;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReviewRepository {
    private DatabaseReference databaseReference;

    public ReviewRepository() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("reviews");
    }

    public void insert(Review review) {
        String id = "review" + new Date().getTime();
        review.setId(id);
        databaseReference.child(id).setValue(review);
    }

    public void delete(String reviewId) {
        databaseReference.child(reviewId).removeValue();
    }

    public void update(Review review) {
        databaseReference.child(review.getId()).setValue(review);
    }

    public LiveData<List<Review>> getAllReviews() {
        MutableLiveData<List<Review>> reviewsLiveData = new MutableLiveData<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Review> reviews = new ArrayList<>();
                for (DataSnapshot reviewSnapshot : snapshot.getChildren()) {
                    Review review = reviewSnapshot.getValue(Review.class);
                    reviews.add(review);
                }
                reviewsLiveData.setValue(reviews);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return reviewsLiveData;
    }
}