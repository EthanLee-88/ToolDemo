package com.ethan.tooldemo.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.ethan.tooldemo.R;

import static com.ethan.tooldemo.fragment.FragmentOne.ALL;

public class FragmentTwo extends Fragment {
    private static final String TAG = "FragmentTwo";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_two, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,ALL + "onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,ALL + "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,ALL + "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,ALL + "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG,ALL + "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG,ALL + "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,ALL + "onDestroy");
    }
}
