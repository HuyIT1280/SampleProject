/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;


/**
 *
 * @author ledin
 */
public class Validated {

    // Kiểm tra chuỗi không được để trống
    public static boolean isEmpty(String s) {
        return s != null && !s.trim().isEmpty();
    }

    // Kiểm tra độ dài của pass
    public static boolean checkPass(char[] password) {
        String passString = new String(password).trim();
        return passString.matches("^[a-zA-Z0-9]{6,15}$");
    }

    // Kiểm tra định dạng email
    public static boolean email(String email) {
        if (email == null) {
            return false;
        }
        String regex = "^\\w+@\\w+(\\.\\w+){1,2}$";
        return email.matches(regex);
    }

    //Kiểm tra định dạng số điện thoại
    public static boolean phoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }
        String regex = "^0[0-9]{9}$";
        return phoneNumber.matches(regex);
    }

    //Kiểm tra định dạng tên
    public static boolean name(String name) {
        if (name == null) {
            return false;
        }
        String regex = "^[\\p{L} ]+$";
        return name.matches(regex);
    }

    // Kiểm tra định dạng số thực
    public static boolean isNumericDouble(String numeric) {
        if (numeric == null) {
            return false;
        }
        String regex = "^(?!0$)[0-9]*\\.?[0-9]+$";
        return numeric.matches(regex);
    }

    // Kiểm tra định dạng số nguyên
    public static boolean isNumericInt(String numeric) {
        if (numeric == null) {
            return false;
        }
        String regex = "^(?!0$)[0-9]+$";
        return numeric.matches(regex);
    }

    public static boolean checkEmpty(String... str) {
        for (String string : str) {
            if (!isEmpty(string)) {
                return false;
            }
        }
        return true;
    }
}
