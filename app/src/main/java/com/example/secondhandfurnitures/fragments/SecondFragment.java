package com.example.secondhandfurnitures.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.ClipData;
import android.content.Context;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.secondhandfurnitures.Database;
import com.example.secondhandfurnitures.ImageAdapter;
import com.example.secondhandfurnitures.Item;
import com.example.secondhandfurnitures.LocalDatabase;
import com.example.secondhandfurnitures.MainActivity;
import com.example.secondhandfurnitures.MyAdapter;
import com.example.secondhandfurnitures.PhotoPOPUP;
import com.example.secondhandfurnitures.R;
import com.example.secondhandfurnitures.RegisterTest;


public class SecondFragment extends Fragment {
    int PICK_IMAGE_MULTIPLE = 1;
    ImageView addImage;
    List<Uri> mArrayUri = new ArrayList<Uri>();
    List<Bitmap> imageBitmap = new ArrayList<>();
    RecyclerView recyclerView;
    int mainPhoto = 0;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LocalDatabase localdb = new LocalDatabase(getContext());
        localdb.open();
        View view;

        if (localdb.IsLoggedIn()) {
            view = inflater.inflate(R.layout.fragment_second, container, false);

            Button btnAdd = view.findViewById(R.id.btnPievienot);
            CheckBox forFree = view.findViewById(R.id.bezmaksas);
            EditText price = view.findViewById(R.id.cenavalue);
            addImage = view.findViewById(R.id.attels1);
            recyclerView = view.findViewById(R.id.recyclerViewPhotos);
            context = getContext();
            EditText description = view.findViewById(R.id.aprakstsvalue);

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

            forFree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (forFree.isChecked()){
                        price.setEnabled(false);
                        price.setText("0.00");
                    } else {
                        price.setEnabled(true);
                        price.setText("");
                    }
                }
            });

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String category = spinnerCategory.getSelectedItem().toString();
                    String city = spinnerCities.getSelectedItem().toString();
                    String error = RegisterTest.checkAddConditions(category, price.getText().toString(), forFree.isChecked(), city, description.getText().toString(),mArrayUri.size());
                    if (error == null) {
                        LocalDatabase localDatabase = new LocalDatabase(getContext());
                        localDatabase.open();
                        String[] loggedUserDetails = localDatabase.loggedUserDetails();
                        Database db = new Database(getContext());
                        db.open();
                        db.addAd(category,price.getText().toString(),city,forFree.isChecked(),description.getText().toString(),mArrayUri,Integer.parseInt(loggedUserDetails[0]));
                        Toast.makeText(getContext(), "Sludinājums veiksmīgi ievietots", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                    }
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
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
            if (data.getClipData() != null) {
                int cout = data.getClipData().getItemCount();
                for (int i = 0; i < cout; i++) {
                    // adding imageuri in array
                    Uri imageurl = data.getClipData().getItemAt(i).getUri();
                    mArrayUri.add(imageurl);
                    try {
                        Bitmap originalBitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageurl);
                        int width = originalBitmap.getWidth();
                        int height = originalBitmap.getHeight();
                        int squareLength = Math.min(width, height);
                        Bitmap squareBitmap = Bitmap.createBitmap(originalBitmap, (width - squareLength) / 2, (height - squareLength) / 2, squareLength, squareLength);
                        imageBitmap.add(squareBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Uri imageurl = data.getData();
                mArrayUri.add(imageurl);
                try {
                    Bitmap originalBitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageurl);
                    int width = originalBitmap.getWidth();
                    int height = originalBitmap.getHeight();
                    int squareLength = Math.min(width, height);
                    Bitmap squareBitmap = Bitmap.createBitmap(originalBitmap, (width - squareLength) / 2, (height - squareLength) / 2, squareLength, squareLength);
                    imageBitmap.add(squareBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            ImageAdapter adapter = new ImageAdapter(getContext(), imageBitmap, new ImageAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(getContext(), PhotoPOPUP.class);
                    intent.putExtra("position",position);
                    if (mainPhoto == position) {
                        intent.putExtra("mainPhoto", true);
                    } else {
                        intent.putExtra("mainPhoto", false);
                    }
                    intent.putExtra("photoUri",mArrayUri.get(position));
                    startActivityForResult(intent, 2);
                }
            });

            recyclerView.setAdapter(adapter);
        }

        if (requestCode == 2 && resultCode == RESULT_OK){
            Boolean isMainPhoto = data.getBooleanExtra("mainPhoto",false);
            Boolean deletePhoto = data.getBooleanExtra("deletePhoto",false);
            int photoID = data.getIntExtra("photoId",0);
            if (deletePhoto) {
                mArrayUri.remove(photoID);
                imageBitmap.remove(photoID);
                if(mainPhoto>photoID){
                    mainPhoto = mainPhoto-1;
                } else if(mainPhoto==photoID){
                    mainPhoto = 0;
                }
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                ImageAdapter adapter = new ImageAdapter(getContext(), imageBitmap, new ImageAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(getContext(), PhotoPOPUP.class);
                        intent.putExtra("position",position);
                        if (mainPhoto == position) {
                            intent.putExtra("mainPhoto", true);
                        } else {
                            intent.putExtra("mainPhoto", false);
                        }
                        intent.putExtra("photoUri",mArrayUri.get(position));
                        startActivityForResult(intent, 2);
                    }


                });
            } else if (isMainPhoto) mainPhoto = photoID;
        }
    }

}