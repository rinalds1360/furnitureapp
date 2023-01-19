package com.example.secondhandfurnitures.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.secondhandfurnitures.Database;
import com.example.secondhandfurnitures.LocalDatabase;
import com.example.secondhandfurnitures.MainActivity;
import com.example.secondhandfurnitures.R;
import com.example.secondhandfurnitures.RegisterActivity;

public class FourthFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LocalDatabase localDB = new LocalDatabase(getContext());
        localDB.open();
        Database db = new Database(getContext());
        db.open();

        View view;

        if(localDB.IsLoggedIn()){
            view = inflater.inflate(R.layout.activity_profile, container, false);
            TextView name = view.findViewById(R.id.textName);
            TextView email = view.findViewById(R.id.textEmail);
            TextView phone = view.findViewById(R.id.textPhoneNumber);
            Button edit = view.findViewById(R.id.btnRedigetDatus);
            Button changePassword = view.findViewById(R.id.btnMainitParoli);
            Button logout = view.findViewById(R.id.btnIzrakstities);
            String[] userDetails = localDB.loggedUserDetails();

            name.setText(userDetails[1]+" "+userDetails[2]);
            email.setText(userDetails[3]);
            phone.setText(userDetails[4]+" "+userDetails[5]);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Šī funkcija pašlaik nedarbojas", Toast.LENGTH_LONG).show();
                }
            });

            changePassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Šī funkcija pašlaik nedarbojas", Toast.LENGTH_LONG).show();
                }
            });

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    localDB.logout();
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });

        } else {
            view = inflater.inflate(R.layout.fragment_fourth, container, false);
            Button btnLogin = view.findViewById(R.id.btnMainitParoli);
            Button btnRegister = view.findViewById(R.id.btnRedigetDatus);
            TextView username = view.findViewById(R.id.lietotajvards);
            TextView password = view.findViewById(R.id.parole);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] lietotajaDati = db.ielogosanas(username.getText().toString().trim(),password.getText().toString().trim());
                    if (lietotajaDati.length == 0) {
                        Toast.makeText(getContext(), "Lietotājvārds vai parole nav pareiza", Toast.LENGTH_LONG).show();
                        password.setText("");
                    } else {
                        Toast.makeText(getContext(), "Ielogošanās izdevās", Toast.LENGTH_LONG).show();
                        localDB.login(Integer.parseInt(lietotajaDati[0]),lietotajaDati[1],lietotajaDati[2],lietotajaDati[3],lietotajaDati[4],lietotajaDati[5],lietotajaDati[6],password.getText().toString().trim());
                        localDB.close();
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
            });

            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(view.getContext(), RegisterActivity.class);
                    startActivity(intent);

                }
            });
        }


        return view;
    }

}