package com.example.secondhandfurnitures;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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

        spinner.setOnItemSelectedListener(new
                                                  AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                code.setText(countryCode[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do something when nothing is selected
            }
        });
    }
}