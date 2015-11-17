package com.example.tt.myapplication;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tt.myapplication.logging.L;

public class Activitya extends ActionBarActivity implements View.OnClickListener {
    ViewGroup viewGroup;
    int check = 1;
    Button btn1,btn2,btn3, btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            //by xml
            TransitionInflater inflater = TransitionInflater.from(this);
           Transition transition = inflater.inflateTransition(R.transition.transition_a);
            getWindow().setExitTransition(transition);
            //by property

            ChangeBounds slide = new ChangeBounds();
            slide.setDuration(2000);
            getWindow().setReenterTransition(slide);
        }
        setContentView(R.layout.activity_a);
        viewGroup = (ViewGroup) findViewById(R.id.container_a);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        viewGroup.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activitya, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
      /*  Explode fade = new Explode();
        fade.setDuration(5000);
        TransitionManager.beginDelayedTransition(viewGroup, fade);
        toggleVisibility(btn1,btn2,btn3,btn4);*/

    /*  // Explode fade = new Explode();
      // fade.setDuration(5000);
        TransitionManager.beginDelayedTransition(viewGroup);
        toggleHeight(btn1, btn2, btn3, btn4);*/
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, null);
        startActivity(new Intent(this, ActivityB.class),compat.toBundle() );

    }
    public void toggleHeight(View... views) {
       /* for (View cur : views) {
            if (cur.getVisibility() == View.VISIBLE) {
                ViewGroup.LayoutParams params = cur.getLayoutParams();
                params.height=100;
                params.width = 50;

                cur.setLayoutParams(params);
            }
        }*/
        check++;
        if (check % 2 != 0) {

            ViewGroup.LayoutParams params1 = null;
            for (int i = 0; i < views.length; i++) {
                if (views[i].getVisibility() == View.VISIBLE) {
                    ViewGroup.LayoutParams params = views[i].getLayoutParams();
                    params1 = params;
                    params.height = 100 + (100 * i);
                    params.width = 50 + (100 * i);

                    views[i].setLayoutParams(params);

                }
            }
        }
        L.m(check%2+"-----------------------------------");
        if (check % 2 == 0) {
            for (View cur : views) {
                if (cur.getVisibility() == View.VISIBLE) {


                    ViewGroup.LayoutParams params = cur.getLayoutParams();
                    params.height=100;
                    params.width = 50;

                    cur.setLayoutParams(params);
                    L.t(this,"aaaaaaaaaa");
                }
        }

    }
    }
   /* public void toggleVisibility(View... views) {
        for (View cur : views) {
            if (cur.getVisibility() == View.VISIBLE) {
                cur.setVisibility(View.INVISIBLE);
            }
        }
    }*/
}
