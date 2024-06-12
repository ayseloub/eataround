package com.example.foodaround.ui;

import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.foodaround.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public class EditProfile extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText edtTitle, edtStatus, edtDescription;
    private ImageView imgUploadGambar;
    private Uri imageUri;

    private AppCompatButton MaterialButton;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

    }
}