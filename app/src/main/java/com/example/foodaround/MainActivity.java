package com.example.foodaround;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<FilterHeader> data;
    private RecyclerView rvFilterHeader;
    private List<SuggestedRestaurant> data2;
    private RecyclerView rvSuggestedResto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.data = new ArrayList<FilterHeader>();

        FilterHeader a = new FilterHeader("Fast Food", R.drawable.fast_food);
        FilterHeader b = new FilterHeader("Restaurant", R.drawable.restaurant);
        FilterHeader c = new FilterHeader("Dessert", R.drawable.dessert);
        FilterHeader d = new FilterHeader("Pastry", R.drawable.pastry);
        FilterHeader e = new FilterHeader("Noodle", R.drawable.noodle);
        FilterHeader f = new FilterHeader("Coffee", R.drawable.coffee);
        FilterHeader g = new FilterHeader("Bar & Music", R.drawable.bar_and_music);

        this.data.add(a); this.data.add(b); this.data.add(c);
        this.data.add(d); this.data.add(e);this.data.add(f);
        this.data.add(g);

        this.rvFilterHeader = this.findViewById(R.id.rvfilterheader);

        FilterHeaderAdapter FilterHeaderAdapter = new FilterHeaderAdapter(MainActivity.this, this.data);

        RecyclerView.LayoutManager lm =
                new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        this.rvFilterHeader.setLayoutManager(lm);
        this.rvFilterHeader.setAdapter(FilterHeaderAdapter);

        this.data2 = new ArrayList<>();
        SuggestedRestaurant aa = new SuggestedRestaurant("Pasta Resto", "Restoran Pasta Enak Di Kota Malang", R.drawable.pastaresto);
        SuggestedRestaurant bb = new SuggestedRestaurant("Ngopi Kopi", "Coffee Shop Faforit Untuk Yang Ke Malang", R.drawable.pastaresto);

        this.data2.add(aa); this.data2.add(bb); this.data2.add(aa);
        this.data2.add(bb); this.data2.add(aa); this.data2.add(bb);
        this.data2.add(aa); this.data2.add(bb); this.data2.add(aa);
        this.data2.add(bb); this.data2.add(aa); this.data2.add(bb);

        this.rvSuggestedResto = findViewById(R.id.rvSuggestedResto);
        SuggestedRestaurantAdapter suggestedRestaurantAdapter = new SuggestedRestaurantAdapter(this, data2);
        RecyclerView.LayoutManager lm2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        this.rvSuggestedResto.setLayoutManager(lm2);
        this.rvSuggestedResto.setAdapter(suggestedRestaurantAdapter);
    }
}