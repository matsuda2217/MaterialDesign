package com.example.tt.myapplication;

import android.app.ActionBar;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tt.myapplication.Services.MyService;
import com.example.tt.myapplication.anim.AnimationUtils;
import com.example.tt.myapplication.extras.SortListener;
import com.example.tt.myapplication.logging.L;
import com.example.tt.myapplication.network.VolleySingleton;
import com.example.tt.myapplication.tab.SlidingTabLayout;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;
import me.tatarka.support.os.PersistableBundle;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private static final int JOB_ID = 100;
    private Toolbar tb;
    NavigationDrawerFragment drawerFragment;
    ViewPager viewPager;
    SlidingTabLayout mTabs;

    private JobScheduler mJobScheduler;

    public static final int MOVIE_SEARCH_RESULTS = 0;
    public static final int MOVIE_HITS = 1;
    public static final int MOVIE_UPCOMING = 2;
    public static final String TAG_SORTNAME = "sortName";
    public static final String TAG_SORTNDATE = "sortDate";
    public static final String TAG_SORTRATING = "sortRating";

    MyPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //create jobscheduler
        mJobScheduler = JobScheduler.getInstance(this);

        //call jobinfo of jobscheduler
        constructJob();

        tb = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(tb);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_drawer_navigation);
        drawerFragment.setUp(R.id.fragment_drawer_navigation, (DrawerLayout) findViewById(R.id.drawer_layout), tb);


        viewPager = (ViewPager) findViewById(R.id.pager);
         adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);


        mTabs = (SlidingTabLayout) findViewById(R.id.tab);


        mTabs.setCustomTabView(R.layout.custom_tab_view, R.id.tabText);
       mTabs.setBackgroundColor(getResources().getColor(R.color.primaryColor));
        mTabs.setSelectedIndicatorColors(getResources().getColor(R.color.accentColor));
        mTabs.setDistributeEvenly(true);
        mTabs.setViewPager(viewPager);

        AnimationUtils.animate(tb);
        AnimationUtils.animate(mTabs);
        //create floating action button
        buildFAB();


    }

    public void onDrawerItemClick(int index) {
        viewPager.setCurrentItem(index-1);
    }

    public void constructJob() {
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(this, MyService.class));
        PersistableBundle per = new PersistableBundle();

        builder.setPeriodic(5000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true);


        mJobScheduler.schedule(builder.build());
    }
    public void buildFAB() {
        //create floating action button
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.ic_action_new);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(imageView)
                .setBackgroundDrawable(R.drawable.selector_button_red)
                .build();
        //--done
        //add menu item for floating action button
        ImageView iconSortName = new ImageView(this);
        iconSortName.setImageResource(R.drawable.ic_action_alphabets);
        ImageView iconSortDate = new ImageView(this);
        iconSortDate.setImageResource(R.drawable.ic_action_calendar);
        ImageView iconSortRating = new ImageView(this);
        iconSortRating.setImageResource(R.drawable.ic_action_important);
        //done
        //create floating menu with items above

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_sub_button_gray));

        //repeat to build sub button
        SubActionButton btnSortName = itemBuilder.setContentView(iconSortName).build();
        SubActionButton btnSortDate = itemBuilder.setContentView(iconSortDate).build();
        SubActionButton btnSortRating = itemBuilder.setContentView(iconSortRating).build();


        btnSortName.setTag(TAG_SORTNAME);
        btnSortDate.setTag(TAG_SORTNDATE);
        btnSortRating.setTag(TAG_SORTRATING);

        btnSortName.setOnClickListener(this);
        btnSortDate.setOnClickListener(this);
        btnSortRating.setOnClickListener(this);

        //create action menu and attach to action button
        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(btnSortName)
                .addSubActionView(btnSortDate)
                .addSubActionView(btnSortRating)
                        // .enableAnimations()
                .attachTo(actionButton)
                .build();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Toast.makeText(MainActivity.this, "Settings hit", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.navigate) {
            startActivity(new Intent(this, SubActivity.class));
        }
        if (id == R.id.tab_using_tab_library) {
            startActivity(new Intent(this,ActivityUsingTabLibrary.class));
        }
        if (id == R.id.vector_test_activity) {
            startActivity(new Intent(this, VectorTestActivity.class));
        }
        if (id == R.id.recycleer_item_animation) {
            startActivity(new Intent(this, RecyclerItemAnimationActivity.class));
        }
        if (id == R.id.item_transition) {
            startActivity(new Intent(this,Activitya.class));
        }
        if (id == R.id.item_share_element_transition) {
            startActivity(new Intent(this,ActivityShareA.class));
        }
        if (id == R.id.fb_login) {
            startActivity(new Intent(this, FaceBookLoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public  void onClick1(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.mkyong.com"));
        startActivity(intent);
        L.m("--------------------------------------------");
    }
    @Override
    public void onClick(View v) {
        Fragment fragment =  adapter.getItem(viewPager.getCurrentItem());// nullpointerexception
        Fragment fragment1 = (Fragment) adapter.instantiateItem(viewPager, viewPager.getCurrentItem());
        if (fragment instanceof SortListener) {
            if (v.getTag().equals(TAG_SORTNAME)) {
                // L.t(this,"sort name clicked");
                ((SortListener) fragment1).onSortByName();

            }
            if (v.getTag().equals(TAG_SORTNDATE)) {
                //  L.t(this,"sort date clicked");
                ((SortListener) fragment1).onSortByDate();
            }
            if (v.getTag().equals(TAG_SORTRATING)) {
                // L.t(this,"sort rating clicked");
                ((SortListener) fragment1).onSortByRating();
            }
        }

    }

    class MyPagerAdapter extends FragmentPagerAdapter{
        int[] icons = {R.drawable.imf43,R.drawable.img24,R.drawable.img50};
        String[] tabs;
        String[] tabsText = getResources().getStringArray(R.array.tabs);
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {
            //MyFragment myFragment = MyFragment.getInstance(position);
            // return myFragment;

            Fragment fragment = null;
            switch(position){
                case MOVIE_SEARCH_RESULTS:
                    fragment = FragmentSearch.newInstance("", "");
                    break;
                case MOVIE_HITS :
                    fragment = FragmentBoxOffice.newInstance("", "");
                    break;
                case MOVIE_UPCOMING:
                    fragment = FragmentUpComing.newInstance("", "");
                    break;
            }
            return  fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Drawable drawable = getResources().getDrawable(icons[position]);
            drawable.setBounds(0,0,48,48);
            ImageSpan imageSpan = new ImageSpan(drawable);
            SpannableString spannableString = new SpannableString(" ");
            spannableString.setSpan(imageSpan,0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }
    }
    public static class MyFragment extends  Fragment{
        private TextView  textView;
        public static MyFragment getInstance(int pos) {
            MyFragment myFragment = new MyFragment();
            Bundle args = new Bundle();
            args.putInt("position", pos);
            myFragment.setArguments(args);
            return myFragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.fragment_my, container, false);
            textView = (TextView) layout.findViewById(R.id.pos);
            Bundle bundle = getArguments();
            if (bundle != null) {
                textView.setText("The page selected is " +(bundle.getInt("position")+1));
            }

            /*RequestQueue requestQueue = VolleySingleton.getsIntance().getmRequestQueue();

            StringRequest request = new StringRequest(Request.Method.GET, "http://google.com/", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getActivity(), "RESPONSE " +response, Toast.LENGTH_SHORT).show();
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), "ERROR "+ error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            requestQueue.add(request);*/
            return layout;
        }
    }
}
