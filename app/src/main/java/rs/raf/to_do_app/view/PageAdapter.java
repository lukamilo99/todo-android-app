package rs.raf.to_do_app.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import rs.raf.to_do_app.fragment.CalendarFragment;
import rs.raf.to_do_app.fragment.DailyPlanFragment;
import rs.raf.to_do_app.fragment.LoginFragment;
import rs.raf.to_do_app.fragment.ProfileFragment;
import rs.raf.to_do_app.fragment.TaskChangeFragment;
import rs.raf.to_do_app.fragment.TaskDetailFragment;

public class PageAdapter extends FragmentPagerAdapter {

    private final int ITEM_COUNT = 6;
    public static final int  FRAGMENT_CALENDAR = 0;
    public static final int FRAGMENT_DAILY_PLAN = 1;
    public static final int FRAGMENT_PROFILE = 2;
    public static final int FRAGMENT_LOGIN = 3;
    public static final int FRAGMENT_DAILY_PLAN_CHANGE = 4;
    public static final int FRAGMENT_DAILY_PLAN_DETAIL = 5;

    public PageAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case FRAGMENT_CALENDAR: fragment = new CalendarFragment(); break;
            case FRAGMENT_DAILY_PLAN: fragment = new DailyPlanFragment(); break;
            case FRAGMENT_PROFILE: fragment = new ProfileFragment(); break;
            case FRAGMENT_DAILY_PLAN_CHANGE: fragment = new TaskChangeFragment(); break;
            case FRAGMENT_DAILY_PLAN_DETAIL: fragment = new TaskDetailFragment(); break;
            default: fragment = new LoginFragment(); break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }
}
