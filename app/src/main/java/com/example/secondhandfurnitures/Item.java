package com.example.secondhandfurnitures;

import android.graphics.Bitmap;

public class Item {

    String kategorija;
    String cena;
    String apraksts;
    Bitmap image;

    public Item(String kategorija, String cena, String apraksts, Bitmap image) {
        this.kategorija = kategorija;
        this.apraksts = apraksts;
        this.cena = cena;
        this.image = image;
    }

    public String getKategorija() {
        return kategorija;
    }

    public void setKategorija(String kategorija) {
        this.kategorija = kategorija;
    }

    public String getCena() {
        return cena;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }

    public String getApraksts() {
        return apraksts;
    }

    public void setApraksts(String apraksts) {
        this.apraksts = apraksts;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
