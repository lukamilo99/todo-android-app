package rs.raf.to_do_app.util;

import static rs.raf.to_do_app.activity.MainActivity.EMAIL_KEY;
import static rs.raf.to_do_app.activity.MainActivity.PASSWORD_KEY;
import static rs.raf.to_do_app.activity.MainActivity.SHARED_PREFERENCES_KEY;
import static rs.raf.to_do_app.activity.MainActivity.USERNAME_KEY;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rs.raf.to_do_app.R;

public class CredentialsChecker {

    public static final String ERROR_EMPTY_FIELD= "ERROR_EMPTY_FIELD";

    public static final String ERROR_BAD_FORMAT = "ERROR_BAD_FORMAT";

    public static final String ERROR_BAD_CREDENTIALS = "ERROR_BAD_CREDENTIALS";

    public static final String VALID_FORMAT = "VALID_FORMAT";

    public static final String VALID_CREDENTIALS = "VALID_CREDENTIALS";

    public static final String PASSWORDS_DO_NOT_MATCH = "PASSWORDS_DO_NOT_MATCH";

    public static final String PASSWORDS_ALREADY_USED = "PASSWORDS_ALREADY_USED";

    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d)(?=.*[a-z])(?!.*[~#^|$%&*!]).{5,}$";

    private static String[] credentials;

    public static void loadCredentials(Context context) {
        if(checkInSharedPreferences(context)) {
            InputStream inputStream = context.getResources().openRawResource(R.raw.credentials);

            if(inputStream != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                try {
                    credentials = bufferedReader.lines().toArray(String[]::new);
                    inputStream.close();
                    bufferedReader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                System.out.println("Error");
            }
        }
    }

    public static boolean checkInSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(USERNAME_KEY, null);
        String password = sharedPreferences.getString(PASSWORD_KEY, null);
        String email = sharedPreferences.getString(EMAIL_KEY, null);

        if(username == null || password == null || email == null) {
            return true;
        }
        else {
            credentials = new String[3];
            credentials[0] = username;
            credentials[1] = password;
            credentials[2] = email;
            return false;
        }
    }

    public static void changePassword(String newPassword) {
        credentials[1] = newPassword;
    }

    public static String authenticateUser(String username, String password, String email) {
        String format = checkCredentialsFormat(username, password, email);

        if(format.equals(VALID_FORMAT)) {
            if(credentials[0].equals(username) && credentials[1].equals(password) && credentials[2].equals(email))
                return VALID_CREDENTIALS;
            else return ERROR_BAD_CREDENTIALS;
        }
        else return format;
    }

    private static String checkCredentialsFormat(String username, String password, String email) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);

        if(username.isEmpty() || password.isEmpty() || email.isEmpty()) return ERROR_EMPTY_FIELD;
        else if(!matcher.matches()) return ERROR_BAD_FORMAT;
        else return VALID_FORMAT;
    }

    public static String checkPassword(String password, String confirmPassword) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);

        if (password.equals(credentials[1]))
            return PASSWORDS_ALREADY_USED;

        if(password.equals(confirmPassword))
            if(matcher.matches()) return VALID_CREDENTIALS;
            else return ERROR_BAD_FORMAT;
        else return PASSWORDS_DO_NOT_MATCH;
    }
}
