package com.pastir.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.pastir.R;
import com.pastir.databinding.ActivityMainBinding;
import com.pastir.fragment.BaseFragment;
import com.pastir.fragment.DonationFragment;
import com.pastir.fragment.HomeFragment;
import com.pastir.fragment.LessonsFragment;
import com.pastir.fragment.MorningVersesFragment;
import com.pastir.storage.DataSource;
import com.pastir.util.Utils;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int PREVIOUSLY_SELECTED = -1;
    private BaseFragment mCurrentFragment;
    private ActivityMainBinding mBinding;
    private NavigationView mNavigationView;
    private boolean loadingFromMenu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mBinding.setActionBar(DataSource.getInstance().getActionBar());

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        loadFragment(new HomeFragment());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
        loadingFromMenu = true;
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            loadFragment(new HomeFragment());
        } else if (id == R.id.nav_morning_verses) {
            loadFragment(new MorningVersesFragment());
        } else if (id == R.id.nav_lesson) {
            loadFragment(new LessonsFragment());
        } else if (id == R.id.nav_donate) {
            loadFragment(new DonationFragment());
        } else {
            Utils.SingleToast.show(this, getString(R.string.not_implemented));
        }

        closeDrawerIfOpened();


        return true;
    }

    public void loadFragment(Fragment fragmentToLoad) {
        loadFragment(fragmentToLoad, true);
    }

    /**
     * Loads fragments with animation going forward or backward dependent on loadForward
     **/
    public void loadFragment(Fragment fragmentToLoad, boolean loadForward) {
        int animationInId, animationOutId;
        int animationInIdOut, animationOutIdOut;
        if (loadForward) {
            animationInId = R.anim.right_in;
            animationOutId = R.anim.left_out;

            animationInIdOut = R.anim.in_from_left;
            animationOutIdOut = R.anim.out_to_right;
        } else {
            animationInId = R.anim.bottom_up;
            animationOutId = R.anim.bottom_up_out;

            animationInIdOut = R.anim.top_down_in;
            animationOutIdOut = R.anim.top_down;
        }

        //If we are trying to load the same fragment that is being currently displayed don't let it
        if (mCurrentFragment != null && Utils.General.compareClasses(mCurrentFragment, fragmentToLoad)) {
            closeDrawerIfOpened();
            loadingFromMenu = false;
            return;
        }

        if (mCurrentFragment != null && loadingFromMenu)
            mCurrentFragment.onNextMenuFragmentLoaded();

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(animationInId, animationOutId, animationInIdOut, animationOutIdOut)
                .replace(R.id.flContent, fragmentToLoad)
                .addToBackStack(fragmentToLoad.getClass().getName())
                .commit();
        loadingFromMenu = false;

    }

    private void closeDrawerIfOpened() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        }
    }

    public void openDrawerIfClosed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (!drawer.isDrawerVisible(GravityCompat.END)) {
            drawer.openDrawer(GravityCompat.END);
        }
    }

    public void setCurrentFragment(BaseFragment loadedFragment) {
        mCurrentFragment = loadedFragment;
        if (loadedFragment.getHomePresenter() != null)
            mBinding.setPresenter(loadedFragment.getHomePresenter());
        setNavigationItem(loadedFragment.getNavigationId());
    }

    public void setNavigationItem(int navId) {
        if (navId != PREVIOUSLY_SELECTED)
            mNavigationView.setCheckedItem(navId);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
