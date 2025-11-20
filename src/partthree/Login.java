/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package partthree;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login {

    private static ArrayList<UserData> registeredUsers = new ArrayList<>();

    public static class UserData {

        public String username;
        public String password;
        public String cellphoneNumber;
        public String firstName;
        public String lastName;

        public UserData(String username, String password, String cellphoneNumber, String firstName, String lastName) {
            this.username = username;
            this.password = password;
            this.cellphoneNumber = cellphoneNumber;
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }

    public static boolean checkUsername(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public static boolean checkPasswordComplexity(String password) {
        if (password.length() < 8) {
            return false;
        }
        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            return false;
        }
        if (!Pattern.compile("\\d").matcher(password).find()) {
            return false;
        }
        if (!Pattern.compile("[!@#$%^&*(),.?\":{}|<>]").matcher(password).find()) {
            return false;
        }
        return true;
    }

    public static boolean checkCellphoneNumber(String cellphoneNumber) {
        Pattern pattern = Pattern.compile("^\\+27\\d{9}$");
        Matcher matcher = pattern.matcher(cellphoneNumber);
        return matcher.matches();
    }

    public static String registerUser(String username, String password, String cellphoneNumber, String firstName, String lastName) {
        for (UserData user : registeredUsers) {
            if (user.username.equals(username)) {
                return "Registration unsuccessful: Username already exists.";
            }
        }

        if (!checkUsername(username)) {
            return "The username is incorrectly formatted.";
        }
        if (!checkPasswordComplexity(password)) {
            return "The password does not meet the complexity requirements.";
        }
        if (!checkCellphoneNumber(cellphoneNumber)) {
            return "The cellphone number is incorrectly formatted.";
        }

        registeredUsers.add(new UserData(username, password, cellphoneNumber, firstName, lastName));
        return "User has been registered successfully.";
    }

    public static String loginUser(String username, String password) {
        for (UserData user : registeredUsers) {
            if (user.username.equals(username) && user.password.equals(password)) {

                return "Welcome <" + user.firstName + " " + user.lastName + "> it is great to see you again.";
            }
        }
        return "Username or password incorrect, please try again.";

    }
}