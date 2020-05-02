package com.maccoux.busybadger.UIMain;

import android.content.Context;
import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.maccoux.busybadger.R;

import java.util.Date;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter implements CalendarFragment.CalendarDataListener {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.Planner, R.string.Today, R.string.Calendar};
    private final Context mContext;
    SparseArray<Fragment> registeredFragments = new SparseArray<>();

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if(position == 0) {
            return PlannerFragment.newInstance();
        }
        if(position == 1) {
            return TodayFragment.newInstance();
        }
        if(position == 2) {
            return CalendarFragment.newInstance();
        }

        return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment)super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    @Override
    public void onDataReceived(Date selectedDate) {
        TodayFragment fragment = (TodayFragment)registeredFragments.get(1);
        if(fragment != null) {
            fragment.onDataReceived(selectedDate);
        }
    }
}
