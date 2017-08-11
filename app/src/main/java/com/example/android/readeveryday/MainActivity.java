package com.example.android.readeveryday;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.android.readeveryday.UI.AboutDialogFragment;
import com.example.android.readeveryday.UI.ArticleFragment;
import com.example.android.readeveryday.UI.ArticleFragmentPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private boolean nightMode;
    private GestureDetectorCompat gestureDetector;
    private ArticleFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nightMode = MyApplication.getNightMode();
        initView();
        setSupportActionBar(toolbar);
        toolbar.setTitle("美阅");
        adapter = new ArticleFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        if (!isNetworkConnected()) {
            Snackbar.make(tabLayout, "网络连接不可用", Snackbar.LENGTH_INDEFINITE).setAction("重试", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recreate();
                }
            }).show();
        }
        toolbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
        gestureDetector = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                ArticleFragment fragment = (ArticleFragment) adapter.getCurrentFragment();
                fragment.scrollToUp();
                return super.onDoubleTap(e);
            }
        });
    }

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favourite:
                startActivity(new Intent(this, FavouriteActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.about:
                launchDialog();
                break;
        }
        return true;
    }

    private void launchDialog() {
        DialogFragment dialogFragment = new AboutDialogFragment();
        dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, 0);
        dialogFragment.show(getSupportFragmentManager(), "about");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.nightMode != MyApplication.getNightMode()) {
            MyApplication.setRecreate(true);
            this.recreate();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable();
    }
}
