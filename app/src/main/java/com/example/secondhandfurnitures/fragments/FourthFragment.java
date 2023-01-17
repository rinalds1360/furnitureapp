package com.example.secondhandfurnitures.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.secondhandfurnitures.R;
import com.example.secondhandfurnitures.RegisterActivity;

import org.w3c.dom.Text;

public class FourthFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fourth, container, false);
        Button btnLogin = view.findViewById(R.id.ieiet);
        Button btnRegister = view.findViewById(R.id.registreties);
        TextView username = view.findViewById(R.id.lietotajvards);
        TextView parole = view.findViewById(R.id.parole);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), RegisterActivity.class);
                startActivity(intent);

            }
        });

        return view;
    }

}