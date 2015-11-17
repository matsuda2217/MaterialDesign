package com.example.tt.myapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.tt.myapplication.adapter.RecyclerItemAnimAdapter;

import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

public class RecyclerItemAnimationActivity extends ActionBarActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    RecyclerItemAnimAdapter recyclerItemAnimAdapter;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_item_animation);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        editText = (EditText) findViewById(R.id.text_input);

        recyclerView = (RecyclerView) findViewById(R.id.recycleItemAnimation);
        recyclerItemAnimAdapter = new RecyclerItemAnimAdapter(this);
        //animator recycler
       /* DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(1000);
        defaultItemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(defaultItemAnimator);*/

        //Use AnimationUtils can animate item too;-----
        //wasabeef recycler item animator
        //ScaleInAnimator animator = new ScaleInAnimator();
        FlipInTopXAnimator animator = new FlipInTopXAnimator();
        animator.setAddDuration(1000);
        animator.setRemoveDuration(1000);

        recyclerView.setItemAnimator(animator);


        recyclerView.setAdapter(recyclerItemAnimAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void addItem(View view) {
        if (editText.getText() != null) {
            String text = editText.getText().toString();
            if (text != null && text.trim().length() > 0) {
                recyclerItemAnimAdapter.addItem(text);

            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recycler_item_animation, menu);
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
}
