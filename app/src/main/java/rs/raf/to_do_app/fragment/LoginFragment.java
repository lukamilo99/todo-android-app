package rs.raf.to_do_app.fragment;

import static rs.raf.to_do_app.activity.MainActivity.EMAIL_KEY;
import static rs.raf.to_do_app.activity.MainActivity.PASSWORD_KEY;
import static rs.raf.to_do_app.activity.MainActivity.SHARED_PREFERENCES_KEY;
import static rs.raf.to_do_app.activity.MainActivity.USERNAME_KEY;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import rs.raf.to_do_app.R;
import rs.raf.to_do_app.activity.MainActivity;
import rs.raf.to_do_app.util.CredentialsChecker;
import rs.raf.to_do_app.view.PageAdapter;

public class LoginFragment extends Fragment {

    private MainActivity mainActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof MainActivity){
            mainActivity = (MainActivity) context;
        }
    }

    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeListeners(view);
    }

    private void initializeListeners(View view) {
        view.findViewById(R.id.loginButton).setOnClickListener(v -> {
            String username = ((EditText) view.findViewById(R.id.usernameEditText)).getText().toString().trim();
            String password = ((EditText) view.findViewById(R.id.passwordEditText)).getText().toString().trim();
            String email = ((EditText) view.findViewById(R.id.emailEditText)).getText().toString().trim();

            String message = CredentialsChecker.authenticateUser(username, password, email);
            
            if(message.equals(CredentialsChecker.VALID_CREDENTIALS)) {
                SharedPreferences sharedPreferences =  requireActivity().getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
                sharedPreferences
                        .edit()
                        .putString(USERNAME_KEY, username)
                        .apply();
                sharedPreferences
                        .edit()
                        .putString(PASSWORD_KEY, password)
                        .apply();
                sharedPreferences
                        .edit()
                        .putString(EMAIL_KEY, email)
                        .apply();
                mainActivity.navigate(PageAdapter.FRAGMENT_CALENDAR, View.VISIBLE);
            }
            else Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
        });
    }
}
