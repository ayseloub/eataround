package com.example.foodaround.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodaround.FilterHeader;
import com.example.foodaround.FilterHeaderAdapter;
import com.example.foodaround.R;
import com.example.foodaround.SuggestedRestaurant;
import com.example.foodaround.SuggestedRestaurantAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomepageFragment extends Fragment {

    private List<FilterHeader> data;
    private RecyclerView rvFilterHeader;
    private List<SuggestedRestaurant> data2;
    private RecyclerView rvSuggestedResto;

    public HomepageFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        this.data = new ArrayList<>();

        FilterHeader a = new FilterHeader("Fast Food", R.drawable.fast_food);
        FilterHeader b = new FilterHeader("Restaurant", R.drawable.restaurant);
        FilterHeader c = new FilterHeader("Dessert", R.drawable.dessert);
        FilterHeader d = new FilterHeader("Pastry", R.drawable.pastry);
        FilterHeader e = new FilterHeader("Noodle", R.drawable.noodle);
        FilterHeader f = new FilterHeader("Coffee", R.drawable.coffee);
        FilterHeader g = new FilterHeader("Bar & Music", R.drawable.bar_and_music);

        this.data.add(a);
        this.data.add(b);
        this.data.add(c);
        this.data.add(d);
        this.data.add(e);
        this.data.add(f);
        this.data.add(g);

        this.rvFilterHeader = view.findViewById(R.id.rvfilterheader);

        FilterHeaderAdapter filterHeaderAdapter = new FilterHeaderAdapter(getActivity(), this.data);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        this.rvFilterHeader.setLayoutManager(lm);
        this.rvFilterHeader.setAdapter(filterHeaderAdapter);

        this.data2 = new ArrayList<>();
        SuggestedRestaurant aa = new SuggestedRestaurant("Pasta Resto", "Restoran Pasta Enak Di Kota Malang", R.drawable.pastaresto);
        SuggestedRestaurant bb = new SuggestedRestaurant("Ngopi Kopi", "Coffee Shop Faforit Untuk Yang Ke Malang", R.drawable.pastaresto);

        this.data2.add(aa);
        this.data2.add(bb);
        this.data2.add(aa);
        this.data2.add(bb);
        this.data2.add(aa);
        this.data2.add(bb);
        this.data2.add(aa);
        this.data2.add(bb);
        this.data2.add(aa);
        this.data2.add(bb);
        this.data2.add(aa);
        this.data2.add(bb);

        this.rvSuggestedResto = view.findViewById(R.id.rvSuggestedResto);
        SuggestedRestaurantAdapter suggestedRestaurantAdapter = new SuggestedRestaurantAdapter(getActivity(), data2);
        RecyclerView.LayoutManager lm2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        this.rvSuggestedResto.setLayoutManager(lm2);
        this.rvSuggestedResto.setAdapter(suggestedRestaurantAdapter);

        return view;
    }
}
