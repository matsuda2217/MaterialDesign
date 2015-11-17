package com.example.tt.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created by TT
 */
public class MyApplication extends Application {
    private static MyApplication sIntance;
    public static final String myKey = "54wzfswsa4qmjg8hjwa64d4c";


    @Override
    public void onCreate() {
        super.onCreate();
        sIntance = this;
        printHashKey();
    }

    public void printHashKey() {
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.tt.myapplication",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("TT", Base64.encodeToString(md.digest(), Base64.DEFAULT)+"--------------");
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
    public static MyApplication getsIntance() {
        return sIntance;
    }

    public static Context getAppContext() {
        return  sIntance.getApplicationContext();
    }
}
