package com.example.secondhandfurnitures;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class LocalDatabase {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "localData.db";
    private static final String DATABASE_TABLE = "RegistredUser";
    public static final String DATABASE_ID = "sludinajumaid";
    String[] columns = {"sludinajumaid",
            "kategorija",
            "cena",
            "pilseta",
            "bezmaksas",
            "apraksts",
            "autoraid",
            "datums",
    };
    private final Context mContext;
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDB;

    public LocalDatabase(Context context) {
        mContext = context;
    }


    public void open() {
        mDatabaseHelper = new com.example.secondhandfurnitures.LocalDatabase.DatabaseHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDatabaseHelper.getWritableDatabase();
    }


    public void close() {
        if (mDatabaseHelper != null) mDatabaseHelper.close();
    }

    public Cursor getDatabase() {
        return mDB.query(DATABASE_TABLE, columns, null, null, null, null, null);
    }

    public class DatabaseHelper extends SQLiteAssetHelper {

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
    }

    public void login(int userid, String name, String surname, String email, String code, String phone, String username, String password){

        Cursor cursor = mDB.rawQuery("SELECT * FROM registreduser", null);
        cursor.moveToFirst();
        if (cursor.getCount() < 0 || cursor.getCount() > 0) {
            cursor = mDB.rawQuery("DELETE FROM RegistredUser",null);
            cursor.moveToFirst();
        }
        cursor = mDB.rawQuery("INSERT INTO RegistredUser\n" +
                "VALUES ("+userid+",'"+name+"', '"+surname+"', '"+email+"', '"+code+"', '"+phone+"', '"+username+"', '"+password+"');",null);
        cursor.moveToFirst();
    }

    public boolean IsLoggedIn(){
        Cursor cursor = mDB.rawQuery("SELECT * FROM registreduser", null);
        return cursor.getCount() > 0;
    }

    public String[] loggedUserDetails(){
        Cursor cursor = mDB.rawQuery("select * from RegistredUser",null);
        cursor.moveToFirst();
        String[] data = new String[cursor.getColumnCount()];
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            data[i] = cursor.getString(i);
        }
        return data;
    }

    public void logout() {
        Cursor cursor = mDB.rawQuery("DELETE FROM RegistredUser",null);
        cursor.moveToFirst();
    }

}
