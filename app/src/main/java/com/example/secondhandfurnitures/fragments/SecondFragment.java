package com.example.secondhandfurnitures.fragments;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.secondhandfurnitures.LocalDatabase;
import com.example.secondhandfurnitures.R;


public class SecondFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LocalDatabase localdb = new LocalDatabase(getContext());
        localdb.open();
        View view;

        if (localdb.IsLoggedIn()) {
            view = inflater.inflate(R.layout.fragment_second, container, false);


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
        } else {
            view = inflater.inflate(R.layout.activity_notloogedin, container, false);
        }
        return view;
    }
}