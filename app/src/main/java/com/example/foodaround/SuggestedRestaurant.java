package com.example.foodaround;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SuggestedRestaurant  {

    public String nama;
    public String Deskripsi;
    public int gambar;

    public SuggestedRestaurant(String nama, String Deskripsi, int gambar){
        this.nama = nama;
        this.Deskripsi = Deskripsi;
        this.gambar = gambar;
    }
}