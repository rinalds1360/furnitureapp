package com.example.secondhandfurnitures.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.secondhandfurnitures.ImageAdapter;
import com.example.secondhandfurnitures.Item;
import com.example.secondhandfurnitures.LocalDatabase;
import com.example.secondhandfurnitures.MyAdapter;
import com.example.secondhandfurnitures.R;


public class SecondFragment extends Fragment {
    int PICK_IMAGE_MULTIPLE = 1;
    ImageView addImage;
    List<Uri> mArrayUri = new ArrayList<Uri>();
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LocalDatabase localdb = new LocalDatabase(getContext());
        localdb.open();
        View view;

        if (localdb.IsLoggedIn()) {
            view = inflater.inflate(R.layout.fragment_second, container, false);

            Button btnAdd = view.findViewById(R.id.btnPievienot);
            addImage = view.findViewById(R.id.attels1);
            recyclerView = view.findViewById(R.id.recyclerViewPhotos);

            Resources res = getResources();
            String[] furnitures = res.getStringArray(R.array.furnitures);
            Spinner spinnerCategory = (Spinner) view.findViewById(R.id.spinnerkategorija);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, furnitures);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategory.setAdapter(adapter);

            String[] pilsetas = res.getStringArray(R.array.pilsetas);
            Spinner spinnerCities = (Spinner) view.findViewById(R.id.spinnerpilseta);
            adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, pilsetas);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCities.setAdapter(adapter);

            addImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);

                }
            });





        } else {
            view = inflater.inflate(R.layout.activity_notloogedin, container, false);
        }


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // When an Image is picked
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
            // Get the Image from data
            if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();
                int cout = data.getClipData().getItemCount();
                for (int i = 0; i < cout; i++) {
                    // adding imageuri in array
                    Uri imageurl = data.getClipData().getItemAt(i).getUri();
                    mArrayUri.add(imageurl);
                }
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                ImageAdapter adapter = new ImageAdapter(getContext(), mArrayUri);
                recyclerView.setAdapter(adapter);
            } else {
                // show this if no image is selected
                Toast.makeText(getContext(), "Attēls nav izvēlēts", Toast.LENGTH_LONG).show();
            }
        }
    }
}