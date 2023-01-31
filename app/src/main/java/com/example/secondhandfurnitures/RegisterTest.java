package com.example.secondhandfurnitures;

import android.text.Editable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterTest {


    public static boolean inputTest(String name, String surname, String email, String phone,
                                    String code, String username, String password1, String password2){
        if (name.isEmpty()||surname.isEmpty()||email.isEmpty()||phone.isEmpty()||code.isEmpty()||username.isEmpty()||password1.isEmpty()||password2.isEmpty()){
            return false;
        } else {
            return true;
        }
    }

    public static boolean passwordCompatibility(String password1, String password2){
        if (password1.equals(password2)){
            return true;
        } else {
            return false;
        }
    }

    public static boolean passwordIsGood(String password){
        String pattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\\$%\\^&\\*])(?=.{8,})";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(password);
        return matcher.find();
    }

    public static boolean emailIsGood(String email){
        String pattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(email);
        return matcher.find();
    }

    public static String checkAddConditions(String category, String price, boolean forFree, String city, String description, int photoCount){
        if (category.equals("Izvēlieties kategoriju")) return "Nepieciešams izvēlēties kategoriju";
        if (city.equals("Izvēlieties pilsētu")) return "Nepieciešams izvēlēties pilsētu";
        if (description.length()<20) return "Aprakstam jābūt vismaz 20 rakstzīmēm garam";
        if (photoCount<1) return "Nepieciešams pievienot fotogrāfiju";
        if (price.equals("")) return "norādiet cenu";
        return null;
    }

}
