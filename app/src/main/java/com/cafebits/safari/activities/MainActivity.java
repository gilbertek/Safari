package com.cafebits.safari.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cafebits.safari.R;
import com.cafebits.safari.events.DrawerSectionItemClickedEvent;
import com.cafebits.safari.fragments.ExhibitsListFragment;
import com.cafebits.safari.fragments.GalleryFragment;
import com.cafebits.safari.fragments.SafariMapFragment;
import com.cafebits.safari.utils.EventBus;
import com.squareup.otto.Subscribe;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private String mCurrentFragmentTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setHomeButtonEnabled( true );

        mDrawerLayout = (DrawerLayout) findViewById( R.id.drawer_layout );
        mActionBarDrawerToggle = new ActionBarDrawerToggle( this, mDrawerLayout,
                R.string.drawer_opened, R.string.drawer_close ) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle( R.string.drawer_opened );
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if ( getSupportActionBar() != null )
                    getSupportActionBar().setTitle( R.string.drawer_close );
            }
        };

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        displayInitialFragment();

    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if ( mActionBarDrawerToggle.onOptionsItemSelected( item ) )
            return true;

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if ( id == R.id.action_settings ) {
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getInstance().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getInstance().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onDrawerSectionItemClickEvent( DrawerSectionItemClickedEvent event ) {

        mDrawerLayout.closeDrawers();

        if ( event == null
                || TextUtils.isEmpty( event.section)
                || event.section.equalsIgnoreCase( mCurrentFragmentTitle ) ) {
            return;
        }

        Toast.makeText( this, "MainActivity: Section Clicked: " + event.section, Toast.LENGTH_SHORT ).show();

        if ( event.section.equalsIgnoreCase( "maps" ) ) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace( R.id.container, SafariMapFragment.getInstance() )
                    .commit();
        } else if ( event.section.equalsIgnoreCase( "gallery" ) ) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace( R.id.container, GalleryFragment.getInstance() )
                    .commit();
        } else if ( event.section.equalsIgnoreCase( "exhibits" ) ) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace( R.id.container, ExhibitsListFragment.getInstance() )
                    .commit();
        } else {
            return;
        }

        mCurrentFragmentTitle = event.section;
    }

    private void displayInitialFragment() {
        getSupportFragmentManager().beginTransaction().replace( R.id.container, ExhibitsListFragment.getInstance() ).commit();

        mCurrentFragmentTitle = "Exhibits";
    }
}
