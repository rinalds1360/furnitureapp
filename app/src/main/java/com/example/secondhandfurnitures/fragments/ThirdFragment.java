package com.example.secondhandfurnitures.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.secondhandfurnitures.LocalDatabase;
import com.example.secondhandfurnitures.R;

public class ThirdFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LocalDatabase localdb = new LocalDatabase(getContext());
        localdb.open();
        View view;
        if (localdb.IsLoggedIn()) {
            view = inflater.inflate(R.layout.fragment_third, container, false);
        } else {
            view = inflater.inflate(R.layout.activity_notloogedin, container, false);
        }
        return view;
    }
}