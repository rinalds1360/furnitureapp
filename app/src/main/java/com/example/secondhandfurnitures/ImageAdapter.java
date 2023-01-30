package com.example.secondhandfurnitures;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Uri> mImageUris;

    public ImageAdapter(Context context, List<Uri> imageUris) {
        mContext = context;
        mImageUris = imageUris;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.photos_card, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Uri imageUri = mImageUris.get(position);
        Glide.with(mContext).load(imageUri).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mImageUris.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
        }
    }
}

