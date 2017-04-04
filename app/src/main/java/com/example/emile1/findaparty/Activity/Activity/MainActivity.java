package com.example.emile1.findaparty.Activity.Activity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.emile1.findaparty.Activity.fragment.CreateFragment;
import com.example.emile1.findaparty.Activity.fragment.HomeFragment;
import com.example.emile1.findaparty.Activity.fragment.SearchFragment;
import com.example.emile1.findaparty.Activity.fragment.SettingsFragment;
import com.example.emile1.findaparty.R;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private TextView textView;
    private ParseUser currentUser;
    private String firstName;
    private String lastName;
    private Toolbar toolbar;
    private HomeFragment homeFragment = HomeFragment.newInstance();
    private SearchFragment searchFragment = new SearchFragment();
    private CreateFragment createFragment = CreateFragment.newInstance();
    private SettingsFragment settingsFragment = SettingsFragment.newInstance();
    private Fragment [] fragments = new Fragment[]{homeFragment,searchFragment,createFragment,settingsFragment};
    private String [] fragmentTags = new String[]{"homeFragment_tag","searchFragment_tag","createFragment_tag","settingsFragment_tag"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            Log.i("MAIN","Null");
        } else{
            Log.i("MAIN", "Not null");
        }
        setContentView(R.layout.activity_main);
        setUserData();
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.material_indigo_500));
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        displaySelectedItem(R.id.nav_main);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        MenuItem menu = navigationView.getMenu().findItem(R.id.menu);
        SpannableString s = new SpannableString(menu.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s.length(), 0);
        menu.setTitle(s);
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

    private void setUserData(){
        currentUser = ParseUser.getCurrentUser();
        if(currentUser!=null){
            firstName = currentUser.getString("firstName");
            lastName = currentUser.getString("lastName");
        }
    }

    private void logOut(){
        ParseUser.logOutInBackground();
        Intent i = new Intent(getApplicationContext(),Login.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        SearchFragment searchFragment = new SearchFragment();
        if(requestCode == SearchFragment.REQUEST_CHECK_SETTINGS){
            searchFragment.onActivityResult(requestCode,resultCode,data);
        } else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

}
