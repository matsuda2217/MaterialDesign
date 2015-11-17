package com.example.tt.myapplication.network;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.tt.myapplication.MyApplication;

/**
 * Created by TT
 */
public class VolleySingleton {
    public static final String myKey = "54wzfswsa4qmjg8hjwa64d4c";
    private static VolleySingleton sIntance = null;
    private RequestQueue  mRequestQueue;
    private ImageLoader  imageLoader;
    private VolleySingleton() {
        mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());

        imageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private LruCache<String, Bitmap> cache = new LruCache<>((int)Runtime.getRuntime().maxMemory()/1024/8);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public static VolleySingleton getsIntance() {
        if (sIntance == null) {
            sIntance = new VolleySingleton();
        }
        return sIntance;
    }

    public RequestQueue getmRequestQueue() {
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
