package com.example.foodaround.ui;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodaround.R;
import com.example.foodaround.databinding.ItemNoteBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private final List<Note> listNotes;

    public NoteAdapter(List<Note> listNotes) {
        this.listNotes = listNotes;
    }


    void setListNotes(List<Note> listNotes) {
        final NoteDiffCallback diffCallback = new NoteDiffCallback(this.listNotes, listNotes);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.listNotes.clear();
        this.listNotes.addAll(listNotes);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNoteBinding binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NoteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final NoteViewHolder holder, int position) {
        holder.bind(listNotes.get(position));

        Note currentNote = listNotes.get(position);

        // Load gambar menggunakan Glide jika imagePath tidak kosong
        String imagePath = currentNote.getImagePath();
        if (imagePath != null && !imagePath.isEmpty()) {
            holder.loadImage(imagePath);
        } else {
            holder.binding.imageGambar.setImageResource(R.drawable.ic_launcher_background);
        }

        // Ambil gambar profil pengguna dari Firebase dan tampilkan di ImageView
        holder.loadUserProfileImage();

        // Set listener untuk membuka InsertUpdateActivity saat item diklik
        holder.binding.cvItemNote.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), InsertUpdateActivity.class);
            intent.putExtra(InsertUpdateActivity.EXTRA_NOTE, currentNote);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listNotes.size();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        final ItemNoteBinding binding;
        ImageView imageProfile; // Tambahkan ImageView untuk gambar profil pengguna
        DatabaseReference userReference; // Tambahkan referensi ke user

        NoteViewHolder(ItemNoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.userReference = FirebaseDatabase.getInstance().getReference("user");

            // Inisialisasi ImageView untuk gambar profil pengguna
            imageProfile = binding.imageProfile;
        }

        void bind(Note note) {
            binding.tvItemTitle.setText(note.getTitle()); // Menetapkan judul dari data Note
            binding.tvItemDate.setText(note.getDate());
            binding.tvItemDescription.setText(note.getDescription());
        }

        void loadImage(String imagePath) {
            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background);

            Glide.with(binding.getRoot().getContext())
                    .load(Uri.parse(imagePath))
                    .apply(requestOptions)
                    .into(binding.imageGambar);
        }

        // Metode untuk memuat gambar profil pengguna dari Firebase
        void loadUserProfileImage() {
            userReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        // Ambil URL gambar profil pengguna dari Firebase
                        String imageUrl = dataSnapshot.child("image").getValue(String.class);

                        // Load gambar profil pengguna menggunakan Glide
                        RequestOptions requestOptions = new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.ic_launcher_background)
                                .error(R.drawable.ic_launcher_background);

                        Glide.with(binding.getRoot().getContext())
                                .load(imageUrl)
                                .apply(requestOptions)
                                .into(imageProfile);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle database error
                }
            });
        }
    }
}

