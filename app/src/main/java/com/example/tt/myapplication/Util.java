package com.example.tt.myapplication;

import android.os.Build;

/**
 * Created by TT
 */
public class Util {
    public static boolean isLolliopOrGreater() {
        return Build.VERSION.SDK_INT >= 21 ? true : false;
    }

    public static boolean isJellybeanOrGreater() {
        return Build.VERSION.SDK_INT >= 16 ? true : false;
    }
}
