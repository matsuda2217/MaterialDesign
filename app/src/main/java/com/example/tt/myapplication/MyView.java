package com.example.tt.myapplication;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by TT
 */
public class MyView  extends FrameLayout{
    Paint paint = null;
    public static final String VIVZ = "VIVZ";
    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch(ev.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                Log.d("VIVZ", "View  dispatchTouchEvent DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("VIVZ", "View  dispatchTouchEvent MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("VIVZ", "View  dispatchTouchEvent UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("VIVZ", "View  dispatchTouchEvent CANCEL");
                break;
        }
        boolean b = super.dispatchTouchEvent(ev);
        Log.d("VIVZ", "View  dispatchTouchEvent RETURN" + b);
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("VIVZ", "View  onTouch DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("VIVZ", "View  onTouch UP");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("VIVZ", "View  onTouch MOVW");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("VIVZ", "View  onTouch CANCEL");
                break;
        }
        boolean b = super.onTouchEvent(event);
        Log.d("VIVZ", "View  onTouch RETURN" + b);
        return b;
    }
}
