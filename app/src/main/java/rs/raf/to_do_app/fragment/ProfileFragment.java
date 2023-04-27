package rs.raf.to_do_app.fragment;

import static rs.raf.to_do_app.activity.MainActivity.EMAIL_KEY;
import static rs.raf.to_do_app.activity.MainActivity.PASSWORD_KEY;
import static rs.raf.to_do_app.activity.MainActivity.SHARED_PREFERENCES_KEY;
import static rs.raf.to_do_app.activity.MainActivity.USERNAME_KEY;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import rs.raf.to_do_app.R;
import rs.raf.to_do_app.activity.MainActivity;
import rs.raf.to_do_app.util.CredentialsChecker;
import rs.raf.to_do_app.view.PageAdapter;

public class ProfileFragment extends Fragment {

    private MainActivity mainActivity;
    private TextView usernameTextView;
    private TextView emailTextView;
    private Button logoutButton;
    private Button changePasswordButton;

    private EditText enterPasswordEditText;
    private EditText confirmPasswordEditText;
    private Button confirmButton;
    private SharedPreferences sharedPreferences;

    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof MainActivity){
            mainActivity = (MainActivity) context;
        }
        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initialize(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initialize(View view) {
        initializeView(view);
        initializeButton(view);
    }

    private void initializeView(View view) {
        usernameTextView = view.findViewById(R.id.usernameTextView);
        emailTextView = view.findViewById(R.id.emailTextView);
        usernameTextView.setText(sharedPreferences.getString(USERNAME_KEY, null));
        emailTextView.setText(sharedPreferences.getString(EMAIL_KEY, null));
        enterPasswordEditText = view.findViewById(R.id.enterPasswordEditText);
        confirmPasswordEditText = view.findViewById(R.id.confirmPasswordEditText);
    }

    private void initializeButton(View view) {
        confirmButton = view.findViewById(R.id.confirmPasswordButton);
        logoutButton = view.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(view1 -> {
            sharedPreferences
                    .edit()
                    .clear()
                    .apply();
            mainActivity.navigate(PageAdapter.FRAGMENT_LOGIN, View.GONE);
        });
        changePasswordButton = view.findViewById(R.id.changePasswordButton);
        changePasswordButton.setOnClickListener(view1 -> {
            changePasswordButton.setVisibility(View.GONE);
            logoutButton.setVisibility(View.GONE);
            confirmButton.setVisibility(View.VISIBLE);
            confirmPasswordEditText.setVisibility(View.VISIBLE);
            enterPasswordEditText.setVisibility(View.VISIBLE);
        });
        confirmButton.setOnClickListener(view1 -> {
            String newPassword = enterPasswordEditText.getText().toString();
            String confirmNewPassword= confirmPasswordEditText.getText().toString();

            String message = CredentialsChecker.checkPassword(newPassword, confirmNewPassword);
            if (message.equals(CredentialsChecker.VALID_CREDENTIALS)) {
                sharedPreferences.edit().putString(PASSWORD_KEY, newPassword).apply();
                CredentialsChecker.changePassword(newPassword);
                changePasswordButton.setVisibility(View.VISIBLE);
                logoutButton.setVisibility(View.VISIBLE);
                confirmPasswordEditText.setText("");
                enterPasswordEditText.setText("");
                confirmButton.setVisibility(View.GONE);
                confirmPasswordEditText.setVisibility(View.GONE);
                enterPasswordEditText.setVisibility(View.GONE);
                Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
            }
            else {
                changePasswordButton.setVisibility(View.VISIBLE);
                logoutButton.setVisibility(View.VISIBLE);
                confirmButton.setVisibility(View.GONE);
                confirmPasswordEditText.setText("");
                enterPasswordEditText.setText("");
                confirmPasswordEditText.setVisibility(View.GONE);
                enterPasswordEditText.setVisibility(View.GONE);
                Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
