package com.example.secondhandfurnitures;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView kategorijaView,cenaView, aprakstsView;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageview);
        kategorijaView = itemView.findViewById(R.id.kategorija);
        cenaView = itemView.findViewById(R.id.Cena);
        aprakstsView = itemView.findViewById(R.id.apraksts);
    }
}
