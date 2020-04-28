package com.example.cameraapp;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.annotation.RequiresApi;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v7.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.cameraapp.Fragments.CameraFragment;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMemoryLog();
        loadFragment(new CameraFragment(), false);

    }

    private static String convertToHumanizedMemorysize(long memoryBytes) {
        DecimalFormat twoDecimalForm = new DecimalFormat("#.##");
        String finalValue;

        double kb = memoryBytes / 1024.0;
        double mb = memoryBytes / 1048576.0;
        double gb = memoryBytes / 1073741824.0;
        double tb = memoryBytes / 1099511627776.0;

        if (tb > 1) {
            finalValue = twoDecimalForm.format(tb).concat(" TB");
        } else if (gb > 1) {
            finalValue = twoDecimalForm.format(gb).concat(" GB");
        } else if (mb > 1) {
            finalValue = twoDecimalForm.format(mb).concat(" MB");
        } else if (kb > 1) {
            finalValue = twoDecimalForm.format(mb).concat(" KB");
        } else {
            finalValue = twoDecimalForm.format(memoryBytes).concat(" Bytes");
        }

        return finalValue;
    }

    private void initMemoryLog() {
        final Handler handler = new Handler();
        final int delay = 1000; //milliseconds
        handler.postDelayed(new Runnable() {
            public void run() {
                ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
                ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
                activityManager.getMemoryInfo(memoryInfo);
                String totalMemoryUsage = convertToHumanizedMemorysize(memoryInfo.totalMem);
                String availableMemory = convertToHumanizedMemorysize(memoryInfo.availMem);
                String threshold = convertToHumanizedMemorysize(memoryInfo.threshold);
                boolean isLowMemory = memoryInfo.lowMemory;
                Log.d("Camera App", "Memory values: AM: " + availableMemory + " | TM: " + totalMemoryUsage + " | T: " + threshold);
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    public void loadFragment(Fragment fragment, Boolean bool) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        if (bool)
            transaction.addToBackStack(null);
        transaction.commit();
    }

}