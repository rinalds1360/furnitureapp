package com.example.secondhandfurnitures.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.secondhandfurnitures.Database;
import com.example.secondhandfurnitures.Item;
import com.example.secondhandfurnitures.MyAdapter;
import com.example.secondhandfurnitures.R;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    public TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);

        List<Item> items = new ArrayList<Item>();
        Database db = new Database(getContext());
        db.open();
        ArrayList<Integer> visiSludinajumi = db.visiSludinajumi();
        for (int i=0; i<visiSludinajumi.size();i++){
            ArrayList<String> isaSludinajumaInfo = db.isaSludinajumaInfo(visiSludinajumi.get(i));
            Bitmap photo = db.viensAttels(visiSludinajumi.get(i));
            items.add(new Item("Kategorija: "+isaSludinajumaInfo.get(0),isaSludinajumaInfo.get(1)+" EUR",isaSludinajumaInfo.get(2),photo));

        }



        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MyAdapter(getActivity().getApplicationContext(),items));
        return view;
    }

}