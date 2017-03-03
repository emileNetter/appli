package com.example.emile1.findaparty.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.emile1.findaparty.R;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private TextView textView;
    private ParseUser currentUser;
    private String firstName;
    private String lastName;
    private Toolbar toolbar;
    private EditText startEditText;
    private EditText endEditText;
    private FragmentManager fm;
    private HomeFragment homeFragment;
    private CreateFragment createFragment;
    private SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUserData();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fm = getSupportFragmentManager();
        homeFragment = new HomeFragment();
        fm.beginTransaction().replace(R.id.content_main,homeFragment,homeFragment.getTag())
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        //allow to find nav header textview and instantiate it
        View header=navigationView.getHeaderView(0);
        textView = (TextView) header.findViewById(R.id.tvUserName);
        textView.setText(firstName +" "+ lastName);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        displaySelectedItem(item.getItemId());
        return true;
    }
    private void displaySelectedItem(int itemId){
        Fragment fragment = null;

        switch (itemId){
            case R.id.nav_main:
                fragment= new HomeFragment();
                toolbar.setTitle(getString(R.string.nav_home));
                break;
            case R.id.nav_search:
                fragment= new SearchFragment();
                toolbar.setTitle(getString(R.string.search));
                break;
            case R.id.nav_create:
                fragment = new CreateFragment();
                toolbar.setTitle(getString(R.string.create));
                break;
            case R.id.nav_settings:
                fragment= new SettingsFragment();
                setTitle(getString(R.string.nav_settings));
                break;
            case R.id.nav_logOut:
                logOut();
                break;
        }
        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main, fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
    public void setUserData(){
        currentUser = ParseUser.getCurrentUser();
        if(currentUser!=null){
            firstName = currentUser.getString("firstName");
            lastName = currentUser.getString("lastName");
        }
    }
    public void logOut(){
        ParseUser.logOutInBackground();
        Intent i = new Intent(getApplicationContext(),Login.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

}
