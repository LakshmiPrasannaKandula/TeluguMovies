package com.example.dell.moviedb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter  extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    Context context;
    ArrayList<Model> arrayList;

    public Adapter(Context context, ArrayList<Model> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.inner,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Model model=arrayList.get(position);
        Glide.with(context).load("https://image.tmdb.org/t/p/w500"+model.getPoster_path()).into(holder.im);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView im;

        public MyViewHolder(View itemView) {
            super(itemView);
            im=itemView.findViewById(R.id.image);
        }
    }
}
