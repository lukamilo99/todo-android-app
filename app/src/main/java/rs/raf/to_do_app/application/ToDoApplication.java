package rs.raf.to_do_app.application;

import android.app.Application;

import rs.raf.to_do_app.util.CredentialsChecker;
import timber.log.Timber;

public class ToDoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        CredentialsChecker.loadCredentials(getApplicationContext());
    }
}
