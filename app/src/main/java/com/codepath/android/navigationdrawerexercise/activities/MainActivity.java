package com.codepath.android.navigationdrawerexercise.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.codepath.android.navigationdrawerexercise.R;
import com.codepath.android.navigationdrawerexercise.fragments.FamilyGuyFragment;
import com.codepath.android.navigationdrawerexercise.fragments.FuturamaFragment;
import com.codepath.android.navigationdrawerexercise.fragments.SimpsonsFragment;
import com.codepath.android.navigationdrawerexercise.fragments.SouthParkFragment;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView nvDrawer;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        // adds the specified listener to the list of listeners that will be notified of the
        // drawer events.
        // drawerToggle extends from the drawerlistener
        drawerLayout.addDrawerListener(drawerToggle);
        // navigationview Represents a standard navigation menu for application.
        // The menu contents can be populated by a menu resource file.
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // inflate the headerview at runtime
        // but start at version 23.1.0 of the design support library switchs navigationview to
        // using a recyclerview and causes npe on header lookups unless the header is added at
        // runtime. If you need to get a reference to the header, you need to use the new
        // getHeaderView() method introduced in the last v23.1.1
        // the method gets the header view at the specified position.
        View headerLayout = nvDrawer.getHeaderView(0);

        setupDrawerContent(nvDrawer);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // this will allow the actionbartoggle to handle the event
        // hamburger icon indicates to the user when a drawer is being open or closed. this is
        // done by drawertoggle class
        // pass the event to actionbardrawertoggle
        // if it returns true, then it has handled the navigationdrawer indicator touch event
        if (drawerToggle.onOptionsItemSelected(item)) return true;
        // the action bar home/up action will open or close the drawer
        switch (item.getItemId()) {
            case android.R.id.home: {
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // we are going to make sure to synchronize the state whenever the screen is restored ot
    // onPostCreate is called when activity start-up is completed after onstart
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // in order to show 3 bar hamburger icon, you have to override the onPostCreate method of
        // the activity where you'll call the sync() method of the actionbardrawertoggle. this
        // method syncs the state of the indicator with the drawer.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // pass any configuration change to the drawertoggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    // there is a configuration change.
    // tie the drawerlayout and toolbar together
    // ActionBarDrawerToggle: act as glu between actionbar/toolbar and drawerlayout
    private ActionBarDrawerToggle setupDrawerToggle() {
        //Construct a new ActionBarDrawerToggle with a Toolbar.
        // this: activity hosting the drawer
        // toolbar: toolbar to use
        // drawerlayout to link to the given activity's actionbar/toolbar
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string
                .drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        selectDrawerItem(item);
                        return true;
                    }
                }
        );
    }

    private void selectDrawerItem(MenuItem item) {
        // create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch (item.getItemId()) {
            case R.id.southPark : {
                fragmentClass = SouthParkFragment.class;
            }
            break;
            case R.id.family_guy : {
                fragmentClass = FamilyGuyFragment.class;
            }
            break;
            case R.id.simpsons : {
                fragmentClass = SimpsonsFragment.class;
            }
            break;
            default: {
                fragmentClass = FuturamaFragment.class;
            }
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        // insert the fragment by replacing any existing fragment
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.flContent, fragment).commit();
        // highlight the selected item has been done by NavigationView
        item.setChecked(true);
        // set action bar title
        setTitle(item.getTitle());
        // Close the navigation drawer
        drawerLayout.closeDrawers();
    }
}
