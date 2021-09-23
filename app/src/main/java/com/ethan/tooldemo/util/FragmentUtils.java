package com.ethan.tooldemo.util;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

public class FragmentUtils {
    public static void addFragment(FragmentManager fragmentManager, Fragment fragment) {
        if (fragment != null && fragmentManager != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fragment_container, fragment);
            transaction.commit();
        }
    }

    public static void replaceFragment(FragmentManager fragmentManager, Fragment fragment) {
        if (fragment != null && fragmentManager != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.addToBackStack(fragment.getClass().getSimpleName());
            transaction.commit();
        }
    }

    public static void replaceFragmentWithTransition(FragmentManager fragmentManager, Fragment fragment, View view1,String t1){
        fragmentManager.popBackStack();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addSharedElement(view1,t1);
//        transaction.addSharedElement(view2,t2);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();

    }

    public static void loadFragment(FragmentManager fragmentManager, Fragment fragment, int frameId, boolean addToBackStack,  int tag) {
        if (fragment != null && fragmentManager != null) {
            Bundle args = new Bundle();
            args.putInt("tag", tag);
            fragment.setArguments(args);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(frameId, fragment);
            if (addToBackStack) {
                transaction.addToBackStack(fragment.getClass().getSimpleName());
            }
            transaction.commit();
        }
    }

    public static void loadFragment(FragmentManager fragmentManager, Fragment fragment, int frameId) {

        if (fragment != null && fragmentManager != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(frameId, fragment);
//            transaction.addToBackStack(fragment.getClass().getSimpleName());
            transaction.commitAllowingStateLoss();
        }
    }
}
