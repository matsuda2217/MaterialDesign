package com.example.tt.myapplication.anim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.Interpolator;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.tt.myapplication.adapter.AdapterBoxOffice;
import com.example.tt.myapplication.adapter.RecyclerItemAnimAdapter;
import com.example.tt.myapplication.logging.L;
import com.example.tt.myapplication.tab.SlidingTabLayout;

/**
 * Created by TT
 */
public class AnimationUtils {
    public static void animate(AdapterBoxOffice.ViewHolderBoxOffice holder,boolean goesDown) {
        //use library  daijamia
        YoYo.with(Techniques.DropOut)
                .duration(1000)
                .playOn(holder.itemView);

        ///use ObjectAnimator
       /* AnimatorSet animSet = new AnimatorSet();

        ObjectAnimator animScaleX = ObjectAnimator.ofFloat(holder.itemView, "scaleX", 0.5f, 0.8f, 1.0f);
        ObjectAnimator animScaleY = ObjectAnimator.ofFloat(holder.itemView, "scaleY", 0.5f, 0.8f, 1.0f);
        ObjectAnimator animTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationY", goesDown == true ? 300 : -300, 0);

        //   L.m(goesDown+"--");
        ObjectAnimator animTranslateX = ObjectAnimator.ofFloat(holder.itemView, "translationX", -25, 25, -20, 20, -15, 15, -10, 10, -5, 5, 0);


        animSet.playTogether(animTranslateX,animTranslateY,animScaleX,animScaleY);
        animSet.setDuration(1000);
        animSet.setInterpolator(new AnticipateInterpolator()) ;
        animSet.start();*/
    }
    public static void animate(RecyclerItemAnimAdapter.MyRecyclerItemViewHolder holder,boolean goesDown) {
        //use library  daijamia
        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .playOn(holder.itemView);

        ///use ObjectAnimator
      /*  AnimatorSet animSet = new AnimatorSet();

        //  ObjectAnimator animScaleX = ObjectAnimator.ofFloat(holder.itemView, "scaleX", 0.5f, 0.8f, 1.0f);
        //  ObjectAnimator animScaleY = ObjectAnimator.ofFloat(holder.itemView, "scaleY", 0.5f, 0.8f, 1.0f);
        ObjectAnimator animTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationY", goesDown == true ? 300 : -300, 0);

        //   L.m(goesDown+"--");
        ObjectAnimator animTranslateX = ObjectAnimator.ofFloat(holder.itemView, "translationX", -25, 25, -20, 20, -15, 15, -10, 10, -5, 5, 0);


        animSet.playTogether(animTranslateX, animTranslateY);
        animSet.setDuration(1000);
        animSet.setInterpolator(new AnticipateInterpolator());
        animSet.start();*/
        //end
    }
    public static void animate(Toolbar tb) {
        //use library  daijamia
        YoYo.with(Techniques.RubberBand)
                .duration(5000)
                .playOn(tb);

        ///use ObjectAnimator
      /*  AnimatorSet animSet = new AnimatorSet();

        //  ObjectAnimator animScaleX = ObjectAnimator.ofFloat(holder.itemView, "scaleX", 0.5f, 0.8f, 1.0f);
        //  ObjectAnimator animScaleY = ObjectAnimator.ofFloat(holder.itemView, "scaleY", 0.5f, 0.8f, 1.0f);
        ObjectAnimator animTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationY", goesDown == true ? 300 : -300, 0);

        //   L.m(goesDown+"--");
        ObjectAnimator animTranslateX = ObjectAnimator.ofFloat(holder.itemView, "translationX", -25, 25, -20, 20, -15, 15, -10, 10, -5, 5, 0);


        animSet.playTogether(animTranslateX, animTranslateY);
        animSet.setDuration(1000);
        animSet.setInterpolator(new AnticipateInterpolator());
        animSet.start();*/
        //end
    }
    public static void animate(SlidingTabLayout tab) {
        //use library  daijamia
        YoYo.with(Techniques.RubberBand)
                .duration(5000)
                .playOn(tab);
    }
}
