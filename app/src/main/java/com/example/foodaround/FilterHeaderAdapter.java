package com.example.foodaround;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FilterHeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<FilterHeader> filterHeaders;


    public FilterHeaderAdapter(Context context, List<FilterHeader> filterHeaders){
        this.context = context;
        this.filterHeaders = filterHeaders;
    }

    public class VH extends RecyclerView.ViewHolder{
        private final TextView tvjenis;
        private final ImageView ivicon;

        public VH(@NonNull View itemView){
            super(itemView);
            this.tvjenis = itemView.findViewById(R.id.tvjenis);
            this.ivicon = itemView.findViewById(R.id.ivicon);
        }
    }
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vh = LayoutInflater.from(this.context)
                .inflate(R.layout.activity_filter_header, parent, false);
        return new VH(vh);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FilterHeader FH = this.filterHeaders.get(position);
        VH vh = (VH) holder;
        vh.tvjenis.setText(FH.jenis.toString());
        vh.ivicon.setImageResource(FH.icon);
    }

    @Override
    public int getItemCount() {
        return this.filterHeaders.size();
    }
}
