package com.example.secondhandfurnitures;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
        LocalDatabase localDB = new LocalDatabase(this);
        localDB.open();

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
                String strName = name.getText().toString().trim();
                String strSurname = surname.getText().toString().trim();
                String strEmail = email.getText().toString().trim();
                String strPhone = phone.getText().toString().trim();
                String strCode = code.getText().toString().trim();
                String strUsername = username.getText().toString().trim();
                String strPassword1 = password1.getText().toString().trim();
                String strPassword2 = password2.getText().toString().trim();
                if (!RegisterTest.inputTest(strName,strSurname,strEmail,strPhone,strCode,strUsername,strPassword1,strPassword2)) {
                    Toast.makeText(getApplicationContext(), "Visi lauki ir jāizpilda", Toast.LENGTH_LONG).show();
                } else if(db.vaiEksisteDatubaze(strEmail, "epasts")){
                    Toast.makeText(getApplicationContext(), "epasts ir jau piereģistrēts", Toast.LENGTH_LONG).show();
                } else if(!RegisterTest.emailIsGood(strEmail)) {
                    Toast.makeText(getApplicationContext(), "epasts neeksistē", Toast.LENGTH_LONG).show();
                } else if (db.vaiEksisteDatubaze(strUsername, "lietotajvards")) {
                    Toast.makeText(getApplicationContext(), "lietotajvards ir jau pieregistrets", Toast.LENGTH_LONG).show();
                } else if(!RegisterTest.passwordIsGood(strPassword1)) {
                    Toast.makeText(getApplicationContext(), "Parolei jābūt vismaz 8 simbolus garai un jāiekļauj cipari, speciālie simboli un lielie burti", Toast.LENGTH_LONG).show();
                } else if(!RegisterTest.passwordCompatibility(strPassword1,strPassword2)){
                    Toast.makeText(getApplicationContext(), "Paroles nesakrīt", Toast.LENGTH_LONG).show();
                } else {
                    db.izveidoLietotaju(strName,strSurname,strEmail,strCode,strPhone,strUsername,strPassword1);
                    int userID = db.getID(strUsername);
                    db.close();
                    localDB.login(userID,strName,strSurname,strEmail,strCode,strPhone,strUsername,strPassword1);
                    localDB.close();
                    Toast.makeText(getApplicationContext(), "Profils izveidots", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }
}