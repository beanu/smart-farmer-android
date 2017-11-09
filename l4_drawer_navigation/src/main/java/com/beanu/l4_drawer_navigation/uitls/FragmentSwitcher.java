package com.beanu.l4_drawer_navigation.uitls;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.HashMap;
import java.util.Map;

public class FragmentSwitcher {

    private int containerId;

    private FragmentManager mManager;

    private Fragment mCurFragment;

    private Map<String, Class<? extends Fragment>> mFragmentMap = new HashMap<>();

    public FragmentSwitcher(@IdRes int containerId, FragmentManager manager) {
        this.containerId = containerId;
        mManager = manager;
    }

    public FragmentSwitcher addFragment(String tag, Class<? extends Fragment> fragmentClz) {
        mFragmentMap.put(tag, fragmentClz);
        return this;
    }

    public Fragment switchFragment(@NonNull String tag) {
        FragmentTransaction transaction = mManager.beginTransaction();
        Fragment fragment = null;
        try {
            fragment = mManager.findFragmentByTag(tag);
            if (fragment == mCurFragment && mCurFragment != null) {
                return fragment;
            }

            if (mCurFragment != null) {
                transaction.hide(mCurFragment);
            }

            if (fragment == null) {
                fragment = findFragment(tag);
                transaction.add(containerId, fragment, tag);
            } else {
                transaction.show(fragment);
            }
            mCurFragment = fragment;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } finally {
            transaction.commit();
        }
        return fragment;
    }

    public Fragment findFragment(@NonNull String tag) throws IllegalAccessException, InstantiationException {
        return mFragmentMap.get(tag).newInstance();
    }

}
