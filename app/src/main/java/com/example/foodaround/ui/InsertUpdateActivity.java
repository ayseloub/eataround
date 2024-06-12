package com.example.foodaround.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.foodaround.R;
import com.example.foodaround.databinding.ActivityInsertUpdateBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class InsertUpdateActivity extends AppCompatActivity {

    public static final String EXTRA_NOTE = "extra_note";
    private final int ALERT_DIALOG_CLOSE = 10;
    private final int ALERT_DIALOG_DELETE = 20;
    private boolean isEdit = false;
    private final int PICK_IMAGE_REQUEST = 1;

    private Note note;
    private NoteInsertUpdateViewModel noteInsertUpdateViewModel;
    private ActivityInsertUpdateBinding binding;
    private DatabaseReference databaseReference;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inisialisasi Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("notes");

        noteInsertUpdateViewModel = obtainViewModel(InsertUpdateActivity.this);
        note = getIntent().getParcelableExtra(EXTRA_NOTE);
        if (note != null) {
            isEdit = true;
        } else {
            note = new Note();
        }
        String actionBarTitle;
        String btnTitle;
        if (isEdit) {
            actionBarTitle = getString(R.string.change);
            btnTitle = getString(R.string.update);
            if (note != null) {
                binding.edtTitle.setText(note.getTitle());
                binding.edtDescription.setText(note.getDescription());
                // Set the image if exists
                if (note.getImagePath() != null && !note.getImagePath().isEmpty()) {
                    Glide.with(this).load(note.getImagePath()).into(binding.imgUploadgambar);
                }
            }
        } else {
            actionBarTitle = getString(R.string.add);
            btnTitle = getString(R.string.save);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.btnSubmit.setText(btnTitle);
        binding.btnSubmit.setOnClickListener(view -> {
            String title = binding.edtTitle.getText().toString().trim();
            String description = binding.edtDescription.getText().toString().trim();
            if (title.isEmpty()) {
                binding.edtTitle.setError(getString(R.string.empty));
            } else if (description.isEmpty()) {
                binding.edtDescription.setError(getString(R.string.empty));
            } else {
                note.setTitle(title);
                note.setDescription(description);
                if (isEdit) {
                    if (imageUri != null) {
                        uploadImageAndSaveNote();
                    } else {
                        updateNoteInFirebase(note);
                    }
                } else {
                    note.setDate(DateHelper.getCurrentDate());
                    if (imageUri != null) {
                        uploadImageAndSaveNote();
                    } else {
                        addNoteToFirebase(note);
                    }
                }
            }
        });

        binding.imgUploadgambar.setOnClickListener(view -> pickImage());

        binding.btnDelete.setOnClickListener(view -> {
            showAlertDialog(ALERT_DIALOG_DELETE);
        });
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                binding.imgUploadgambar.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageAndSaveNote() {
        if (imageUri != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference("images/" + imageUri.getLastPathSegment());
            UploadTask uploadTask = storageReference.putFile(imageUri);

            uploadTask.addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                String imageUrl = uri.toString();
                note.setImagePath(imageUrl);
                if (isEdit) {
                    updateNoteInFirebase(note);
                } else {
                    addNoteToFirebase(note);
                }
            })).addOnFailureListener(e -> {
                showToast("Image upload failed: " + e.getMessage());
                Log.e("FirebaseError", "Image upload failed", e);
            });
        }
    }

    private void addNoteToFirebase(Note note) {
        String key = databaseReference.push().getKey();
        if (key != null) {
            note.setId(key);
            databaseReference.child(key).setValue(note)
                    .addOnSuccessListener(aVoid -> {
                        showToast(getString(R.string.added));
                        navigateToMainActivityWithMessage("File tersimpan");
                    })
                    .addOnFailureListener(e -> showToast("Error adding note: " + e.getMessage()));
        }
    }

    private void updateNoteInFirebase(Note note) {
        databaseReference.child(note.getId()).setValue(note)
                .addOnSuccessListener(aVoid -> {
                    showToast(getString(R.string.changed));
                    navigateToMainActivityWithMessage("File tersimpan");
                })
                .addOnFailureListener(e -> showToast("Error updating note: " + e.getMessage()));
    }

    private void navigateToMainActivityWithMessage(String message) {
        Intent intent = new Intent(InsertUpdateActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        Toast.makeText(InsertUpdateActivity.this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isEdit) {
            getMenuInflater().inflate(R.menu.menu_form, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            showAlertDialog(ALERT_DIALOG_DELETE);
        } else if (item.getItemId() == android.R.id.home) {
            showAlertDialog(ALERT_DIALOG_CLOSE);
        } else {
            return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showAlertDialog(ALERT_DIALOG_CLOSE);
    }

    private void showAlertDialog(int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel);
            dialogMessage = getString(R.string.message_cancel);
        } else {
            dialogMessage = getString(R.string.message_delete);
            dialogTitle = getString(R.string.delete);
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes),
                        (dialog, id) -> {
                            if (!isDialogClose) {
                                deleteNoteFromFirebase(note);
                            }
                            finish();
                        })
                .setNegativeButton(getString(R.string.no),
                        (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void deleteNoteFromFirebase(Note note) {
        databaseReference.child(note.getId()).removeValue()
                .addOnSuccessListener(aVoid -> showToast(getString(R.string.deleted)))
                .addOnFailureListener(e -> showToast("Error deleting note: " + e.getMessage()));
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @NonNull
    private static NoteInsertUpdateViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, factory).get(NoteInsertUpdateViewModel.class);
    }
}
