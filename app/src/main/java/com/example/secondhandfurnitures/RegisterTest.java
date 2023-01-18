package com.example.secondhandfurnitures;

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
        if (password1 == password2){
            return true;
        } else {
            return false;
        }
    }

    public static boolean passwordIsGood(String password){
        String pattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\\$%\\^&\\*])(?=.{8,})";
        return password.matches(pattern);
    }

    public static boolean emailIsGood(String email){
        String pattern = "^(?=.*[^@])@(?=.*[^@])";
        return email.matches(pattern);
    }

}
