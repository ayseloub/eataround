package com.example.foodaround.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.foodaround.R;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    private LinearLayout headerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);

        //navbar
        headerLayout = findViewById(R.id.headerLayout);

        ImageButton btnNotif = findViewById(R.id.btn_notif);
        btnNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new ReviewFragment());
            }
        });

        ImageButton btnHome = findViewById(R.id.btn_home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new HomepageFragment());
            }
        });

        ImageButton btnBookmark = findViewById(R.id.btn_bookmark);
        btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new FavoritFragment());
            }
        });

        ImageButton btnProfil = findViewById(R.id.btn_profile);
        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new ProfilFragment());
            }
        });
    }

    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void hideHeader() {
        headerLayout.setVisibility(View.GONE);
    }

    public void showHeader() {
        headerLayout.setVisibility(View.VISIBLE);
    }
}
