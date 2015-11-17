package com.example.tt.myapplication.logging;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by TT
 */
public class L {
    public static void m(String mes) {
        Log.d("VIVZ", "" + mes);
    }

    public static void t(Context context, String mes) {
        Toast.makeText(context,mes+ "", Toast.LENGTH_SHORT).show();
    }
}
