package rs.raf.to_do_app.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rs.raf.to_do_app.R;
import timber.log.Timber;

public class CredentialsChecker {

    public static final String ERROR_EMPTY_FIELD= "ERROR_EMPTY_FIELD";

    public static final String ERROR_BAD_FORMAT = "ERROR_BAD_FORMAT";

    public static final String ERROR_BAD_CREDENTIALS = "ERROR_BAD_CREDENTIALS";

    public static final String VALID_FORMAT = "VALID_FORMAT";

    public static final String VALID_CREDENTIALS = "VALID_CREDENTIALS";

    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d)(?=.*[a-z])(?!.*[~#^|$%&*!]).{5,}$";

    private static String[] credentials;

    public static void loadCredentials(Context context) {
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

    public static String authenticateUser(String username, String password, String email) {

        String format = checkCredentialsFormat(username, password, email);

        if(format.equals(VALID_FORMAT)) {
            Timber.e(credentials[0]);
            Timber.e(credentials[1]);
            Timber.e(credentials[2]);
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
}
