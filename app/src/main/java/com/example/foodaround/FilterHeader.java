package com.example.foodaround;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class FilterHeader {
    public String jenis;
    public int icon;

    public FilterHeader(String jenis, int icon){
        this.jenis = jenis;
        this.icon = icon;
    }

}