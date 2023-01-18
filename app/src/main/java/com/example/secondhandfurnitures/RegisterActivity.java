package com.example.secondhandfurnitures;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.secondhandfurnitures.Database;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextView code = findViewById(R.id.countryCode);
        TextView name = findViewById(R.id.textVards);
        TextView surname = findViewById(R.id.textUzvards);
        TextView email = findViewById(R.id.tekstEpasts);
        TextView phone = findViewById(R.id.textPhone);
        TextView username = findViewById(R.id.textUsername);
        TextView password1 = findViewById(R.id.textPassword1);
        TextView password2 = findViewById(R.id.textPassword2);
        Button btnRegister = findViewById(R.id.btnregistreties);
        Database db = new Database(this);
        db.open();

        Resources res = getResources();
        String[] countries = res.getStringArray(R.array.country_data);
        String[] countryName = new String[countries.length];
        String[] countryCode = new String[countries.length];
        String[] countryInfo = new String[countries.length];

        for (int i=0;i<countries.length;i++){
            String[] temp = countries[i].split("(?=\\d)");
            countryName[i] = temp[0];
            String[] temp1 = countries[i].split(" ");
            countryCode[i] = "+"+temp1[temp1.length-3];
            countryInfo[i] = temp[0] + " "+countryCode[i];
        }

        Spinner spinner = (Spinner) findViewById(R.id.countries);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countryName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                code.setText(countryCode[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do something when nothing is selected
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strName = name.getText().toString();
                String strSurname = surname.getText().toString();
                String strEmail = email.getText().toString();
                String strPhone = phone.getText().toString();
                String strCode = code.getText().toString();
                String strUsername = username.getText().toString();
                String strPassword1 = password1.getText().toString();
                String strPassword2 = password2.getText().toString();
                if (!RegisterTest.inputTest(strName,strSurname,strEmail,strPhone,strCode,strUsername,strPassword1,strPassword2)) {
                    Toast.makeText(getApplicationContext(), "Visi lauki ir jāizpilda", Toast.LENGTH_LONG).show();
                } else if(db.vaiEksisteDatubaze(strEmail, "epasts")){
                    Toast.makeText(getApplicationContext(), "epasts ir jau piereģistrēts", Toast.LENGTH_LONG).show();
                } else if(db.vaiEksisteDatubaze(strUsername, "lietotajvards")){
                    Toast.makeText(getApplicationContext(), "lietotajvards ir jau pieregistrets", Toast.LENGTH_LONG).show();
                } else if(!RegisterTest.passwordIsGood(strPassword1)) {
                    Toast.makeText(getApplicationContext(), "Parolei jābūt vismaz 8 simbolus garai un jāiekļauj cipari, speciālie simboli un lielie burti", Toast.LENGTH_LONG).show();
                } else if(!RegisterTest.passwordCompatibility(strPassword1,strPassword2)){
                    Toast.makeText(getApplicationContext(), "Paroles nesakrīt", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Tiek veikta profila izveide", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}