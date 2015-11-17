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
public class MyLayout extends FrameLayout {
    public static final String VIVZ = "VIVZ";
    Paint paint = null;
    public MyLayout(Context context) {
        super(context);
        init();
    }

    public MyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        setWillNotDraw(false);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch(ev.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                Log.d("VIVZ", "MyLayout  dispatchTouchEvent DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("VIVZ", "MyLayout  dispatchTouchEvent MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("VIVZ", "MyLayout  dispatchTouchEvent UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("VIVZ", "MyLayout  dispatchTouchEvent CANCEL");
                break;
        }
        boolean b = super.dispatchTouchEvent(ev);
        Log.d("VIVZ", "MyLayout  dispatchTouchEvent RETURN" + b);
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("VIVZ", "MyLayout  onTouch DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("VIVZ", "MyLayout  onTouch UP");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("VIVZ", "MyLayout  onTouch MOVW");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("VIVZ", "MyLayout  onTouch CANCEL");
                break;
        }
        boolean b = super.onTouchEvent(event);
        Log.d("VIVZ", "MyLayout  onTouch RETURN" + b);
        return b;
    }
}
