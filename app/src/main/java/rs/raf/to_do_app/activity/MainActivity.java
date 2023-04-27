package rs.raf.to_do_app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import rs.raf.to_do_app.R;
import rs.raf.to_do_app.database.AppDatabase;
import rs.raf.to_do_app.view.PageAdapter;
import rs.raf.to_do_app.viewmodel.SplashViewModel;

public class MainActivity extends AppCompatActivity {

    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";
    public static final String EMAIL_KEY = "email";
    public static final String SHARED_PREFERENCES_KEY = "key";

    public static AppDatabase appDatabase;

    private ViewPager viewPager;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        showSplashScreen();
        initializeDatabase();
        initializeViewPager();
        initializeNavigation();
        redirectUnauthenticatedUser();
    }

    private void initializeDatabase() {
        appDatabase = AppDatabase.getInstance(getApplicationContext());
    }

    private void initializeViewPager() {
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager()));
    }

    private void initializeNavigation() {
        bottomNavigationView = ((BottomNavigationView)findViewById(R.id.bottomNavigation));
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_calendar: viewPager.setCurrentItem(PageAdapter.FRAGMENT_CALENDAR, true); break;
                case R.id.navigation_daily_plan: viewPager.setCurrentItem(PageAdapter.FRAGMENT_DAILY_PLAN, true); break;
                case R.id.navigation_profile: viewPager.setCurrentItem(PageAdapter.FRAGMENT_PROFILE, true); break;
            }
            return true;
        });
    }

    private void showSplashScreen() {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        SplashViewModel splashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        splashScreen.setKeepOnScreenCondition(() -> {
            Boolean value = splashViewModel.isLoading().getValue();
            if (value == null) return false;

            return value;
        });
    }

    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        String password = sharedPreferences.getString(PASSWORD_KEY, null);
        String username = sharedPreferences.getString(USERNAME_KEY, null);
        String email = sharedPreferences.getString(EMAIL_KEY, null);

        if(password == null || username == null || email == null) return false;
        else return true;
    }

    private void redirectUnauthenticatedUser() {
        if(!isUserLoggedIn()) {
            navigate(PageAdapter.FRAGMENT_LOGIN, View.GONE);
        }
    }

    public void navigate(int fragmentIndex, int bottomNavigationVisibility) {
        findViewById(R.id.bottomNavigation).setVisibility(bottomNavigationVisibility);
        changeFocus(fragmentIndex);
        viewPager.setCurrentItem(fragmentIndex, false);
    }

    private void changeFocus(int fragmentIndex) {
        switch (fragmentIndex) {
            case 0 : {
                MenuItem menuItem = bottomNavigationView.getMenu().findItem(R.id.navigation_calendar);
                menuItem.setChecked(true);
            }
            break;
            case 1 : {
                MenuItem menuItem = bottomNavigationView.getMenu().findItem(R.id.navigation_daily_plan);
                menuItem.setChecked(true);
            }
            break;
            case 2 : {
                MenuItem menuItem = bottomNavigationView.getMenu().findItem(R.id.navigation_profile);
                menuItem.setChecked(true);
            }
            break;
            default: break;
        }
    }

    @Override
    public void onBackPressed() {
        int fragmentIndex = viewPager.getCurrentItem();

        switch (fragmentIndex) {
            case 3 :
            case 0 : super.onBackPressed(); break;
            case 1 :
            case 2 :
                navigate(PageAdapter.FRAGMENT_CALENDAR, View.VISIBLE); break;
            case 5 :
            case 4 : navigate(PageAdapter.FRAGMENT_DAILY_PLAN, View.VISIBLE); break;
        }
    }
}