package com.example.secondhandfurnitures;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.secondhandfurnitures.fragments.FirstFragment;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.security.AccessControlContext;
import java.util.ArrayList;

public class Database {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "furnitures.db";
    private static final String DATABASE_TABLE = "sludinajumi";
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

    public Database(Context context) {
        mContext = context;
    }


    public void open() {
        mDatabaseHelper = new DatabaseHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION);
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

    public ArrayList pilnaSludinajumaInfo(int sludinajumaid){
        ArrayList<String> arrayList = new ArrayList<>();
        Cursor cursor = mDB.rawQuery("SELECT * FROM sludinajumi\n" +
                "WHERE sludinajumaid = 1",null);
        cursor.moveToFirst();
        int count = cursor.getColumnCount();
        for (int i=0; i<count; i++){
            arrayList.add(cursor.getString(i));
        }
        return arrayList;
    }

    public ArrayList visiSludinajumi(){
        ArrayList<Integer> arrayList = new ArrayList<>();
        Cursor cursor = mDB.rawQuery("SELECT sludinajumaid FROM sludinajumi",null);
        while(cursor.moveToNext()) {
            int index;
            index = cursor.getColumnIndexOrThrow("sludinajumaid");
            arrayList.add(cursor.getInt(index));
        }
        return arrayList;
    }

    public Bitmap viensAttels(int id){
        String query = "SELECT attels FROM atteli\n" +
                "WHERE sludinajumaid ="+id;
        Cursor cursor = mDB.rawQuery(query, null);
        cursor.moveToFirst();
        byte[] bytesImage = cursor.getBlob(0);
        cursor.close();
        return BitmapFactory.decodeByteArray(bytesImage,0,bytesImage.length);
    }

    public ArrayList isaSludinajumaInfo(int id){
        Cursor cursor = mDB.rawQuery("SELECT kategorija, cena, apraksts FROM sludinajumi\n" +
                "WHERE sludinajumi.sludinajumaid = "+id, null);
        ArrayList<String> arrayList = new ArrayList<>();
        cursor.moveToFirst();
        int count = cursor.getColumnCount();
        for (int i=0; i<count; i++){
            arrayList.add(cursor.getString(i));
        }
        return arrayList;
    }

    public boolean vaiEksisteDatubaze(String value, String column){
        if (value.isEmpty() || column.isEmpty()){
            return false;
        }
        Cursor cursor = mDB.rawQuery("SELECT DISTINCT "+column+" FROM lietotaji WHERE "+column+" ='"+value+"'", null);
        cursor.moveToFirst();
        if (cursor.getCount() < 0 || cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void izveidoLietotaju(String name, String surname, String email, String code, String phone, String username, String password){
        Cursor cursor = mDB.rawQuery("INSERT INTO lietotaji (vards, uzvards, epasts, telefonavalstskods, telefonanumurs, lietotajvards, parole)\n" +
                "VALUES ('"+name+"', '"+surname+"', '"+email+"', +'"+code+"', '"+phone+"', '"+username+"', '"+password+"');", null);
        cursor.moveToFirst();
    }

    public String[] ielogosanas(String user, String password){
        if (user.equals("") || password.equals("")){
            return new String[0];
        }
        Cursor cursor = mDB.rawQuery("SELECT lietotajaid, vards, uzvards, epasts, telefonavalstskods, telefonanumurs, lietotajvards FROM lietotaji\n" +
                "WHERE (epasts = '"+user+"' OR lietotajvards = '"+user+"') AND parole = '"+password+"'", null);
        cursor.moveToFirst();
        if (cursor.getCount() < 0 || cursor.getCount() > 0) {
            String[] data = new String[cursor.getColumnCount()];
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                data[i] = cursor.getString(i);
            }
            return data;
        } else {
            return new String[0];
        }
    }

    public int getID (String username){
        Cursor cursor = mDB.rawQuery("SELECT lietotajaid FROM lietotaji\n" +
                "WHERE lietotajvards = '"+username+"'",null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }


}
