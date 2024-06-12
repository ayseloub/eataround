package com.example.foodaround.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodaround.R;

public class DetailReviewActivity extends AppCompatActivity {
    TextView tvNama, tvAlamat, tvNamaresto, tvReview, back_link;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_review);
        back_link = findViewById(R.id.back_link);
        tvNama = findViewById(R.id.tv_nama);
        tvAlamat = findViewById(R.id.tv_alamat);
        tvNamaresto = findViewById(R.id.tv_namaresto);
        tvReview = findViewById(R.id.tv_des);

        if (getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();
            String getCNama = bundle.getString("cnama");
            String getCAlamat = bundle.getString("calamat");
            String getCNamaresto = bundle.getString("cnamaresto");
            String getCReview = bundle.getString("creview");
            tvNama.setText(getCNama);
            tvAlamat.setText(getCAlamat);
            tvNamaresto.setText(getCNamaresto);
            tvReview.setText(getCReview);
        }
        back_link.setOnClickListener(v-> {
            setResult(RESULT_OK, null);

            finish();
        });
    }
}

