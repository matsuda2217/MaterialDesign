package com.example.tt.myapplication;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.tt.myapplication.adapter.AdapterDrawer;
import com.example.tt.myapplication.logging.L;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment implements ViziAdapter.Clicklistener {

    private RecyclerView recyclerView;
    public static final String PREF_FILE_NAME = "testPref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private boolean mUserLearnedDrawer;
    private boolean fromSaveInstanceState;
    private  View containerView;
    private AdapterDrawer adapter;
    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       mUserLearnedDrawer= Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            fromSaveInstanceState = true;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.recycler);
        adapter = new AdapterDrawer(getActivity(),getData());

        //adapter.setClicklistener(this);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View v = rv.findChildViewUnder(e.getX(), e.getY());

               int id = rv.getChildPosition(v);
                ((MainActivity)getActivity()).onDrawerItemClick(id);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                L.m("onTouchEvent");
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
                L.m("onRequestDisallowInterceptTouchEvent");
            }
        });
        return layout;
    }
    public  List<Information> getData() {
        List<Information> data = new ArrayList<Information>();
        int[] icons = {R.drawable.imf43, R.drawable.img24, R.drawable.img50};
        String[] titles =  getResources().getStringArray(R.array.drawer_tabs);

        for (int i = 0; i < titles.length; i++) {
            Information curInfo = new Information();
            curInfo.iconId = icons[i%icons.length];
            curInfo.title = titles[i%icons.length];
            data.add(curInfo);

        }
        return  data;
    }

    public void setUp(int fragmentId,DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if(slideOffset<0.6)
                toolbar.setAlpha(1-slideOffset);
                Log.d("TT","offset: " + slideOffset);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();

            }
        };

        if (!mUserLearnedDrawer && !fromSaveInstanceState) {
            mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                actionBarDrawerToggle.syncState();
            }
        });

    }
 /*  public class RecylclerTouchListener implements RecyclerView.OnItemTouchListener{
        GestureDetector gestureDetector;
        public RecylclerTouchListener(Context context, RecyclerView recyclerView, Clicklistener clicklistener) {
            Log.d("tagt", "constructer invoked");
            gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    Log.d("tagt", "onSingleTapUp invoked");
                    return super.onSingleTapUp(e);
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    Log.d("tagt", "onLongPress invoked");
                    super.onLongPress(e);
                }
            });
        }
        @Override
        public  boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            ((MainActivity) getActivity()).onDrawerItemClick(rv.getId());
            return true;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            Log.d("tagt", "onTouchEvent invoked " + e);
        }

       @Override
       public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

       }


   }*/
     public interface Clicklistener{
         void onClick(View v, int pos);

         void onLongClick(View v, int pos);
    }
    public static void saveToPreferences(Context context, String prefName, String prefValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(prefName, prefValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String prefName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(prefName, defaultValue);
    }

    @Override
    public void itemClick(View v, int pos) {
        startActivity(new Intent(getActivity(), SubActivity.class));
    }
}
