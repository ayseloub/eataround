package com.example.foodaround.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodaround.R;
import com.example.foodaround.databinding.FragmentProfilBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ProfilFragment extends Fragment {
    private FragmentProfilBinding binding;
    private NoteAdapter adapter;
    private Button edit;
    private TextView tvNamaLengkap, tvStatus, tvDeskripsi, tvUserId;
    private ImageView profileImage;
    private DatabaseReference notesReference, userReference;
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 123;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfilBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new NoteAdapter(new ArrayList<>());
        binding.rvNotes.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvNotes.setHasFixedSize(true);
        binding.rvNotes.setAdapter(adapter);

        tvNamaLengkap = view.findViewById(R.id.tv_namalengkap);
        tvStatus = view.findViewById(R.id.tv_status);
        tvDeskripsi = view.findViewById(R.id.tv_deskripsi);
        profileImage = view.findViewById(R.id.profile_image);
        tvUserId = view.findViewById(R.id.tv_user_id);

        edit = view.findViewById(R.id.bt_edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), EditProfile.class);
                startActivity(i);
            }
        });

        notesReference = FirebaseDatabase.getInstance().getReference("notes");
        userReference = FirebaseDatabase.getInstance().getReference("user");
        fetchNotesFromFirebase();
        fetchUserDataFromFirebase();

        binding.fabAdd.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), InsertUpdateActivity.class);
            startActivity(intent);
        });

        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_READ_EXTERNAL_STORAGE);
        } else {
            fetchUserDataFromFirebase();
        }
    }

    private void fetchUserDataFromFirebase() {
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Retrieve user data from Firebase
                    String userId = dataSnapshot.child("user_id").getValue(String.class);
                    String title = dataSnapshot.child("title").getValue(String.class);
                    String status = dataSnapshot.child("status").getValue(String.class);
                    String description = dataSnapshot.child("description").getValue(String.class);
                    String imageUrl = dataSnapshot.child("image").getValue(String.class);

                    // Display data in TextViews
                    tvUserId.setText(userId);
                    tvNamaLengkap.setText(title);
                    tvStatus.setText(status);
                    tvDeskripsi.setText(description);

                    // Load image using Glide
                    RequestOptions requestOptions = new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_background);

                    Glide.with(ProfilFragment.this)
                            .load(imageUrl)
                            .apply(requestOptions)
                            .into(profileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }

    private void fetchNotesFromFirebase() {
        notesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Note> notes = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Note note = dataSnapshot.getValue(Note.class);
                    if (note != null) {
                        note.setId(dataSnapshot.getKey());
                        notes.add(note);
                    }
                }
                adapter.setListNotes(notes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
