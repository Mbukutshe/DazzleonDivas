package com.payghost.dazzleondivas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import android.widget.TextView;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView Heading,oper;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private ViewFlipper mViewFlipper;
    private Context mContext;
    private final GestureDetector detector = new GestureDetector(new MainActivity.SwipeGestureDetector());
    RelativeLayout layout;

    private boolean fabExpanded = false;
    private FloatingActionButton fabSettings,FabMap,FabSlip;
    private LinearLayout layoutFabSave;
    private LinearLayout layoutFabEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mViewFlipper = (ViewFlipper)findViewById(R.id.view_flipper);
        mViewFlipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                detector.onTouchEvent(event);
                return true;
            }
        });

        mViewFlipper.setAutoStart(true);
        mViewFlipper.setFlipInterval(4000);
        mViewFlipper.startFlipping();

        Heading = (TextView)findViewById(R.id.heading);
        Heading.setText(Html.fromHtml("<u color='#DAA520'>Welcome to Dazzle-on Divas</u>"));

        oper = (TextView)findViewById(R.id.operation);
        oper.setText(Html.fromHtml("<u color='#DAA520'>MAKEUP& HAIR BY PRESHIKA</u>"));



        FabMap = (FloatingActionButton) this.findViewById(R.id.fabFacebook);
        FabMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Dazzleondivas/"));
                startActivity(browserIntent);
            }
        });


        // Fabs
        fabSettings = (FloatingActionButton) this.findViewById(R.id.fabSetting);
        layoutFabEdit = (LinearLayout) this.findViewById(R.id.layoutFabEdit);
        //layoutFabSettings = (LinearLayout) this.findViewById(R.id.layoutFabSettings);

        //When main Fab (Settings) is clicked, it expands if not expanded already.
        //Collapses if main FAB was open already.
        //This gives FAB (Settings) open/close behavior
        fabSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabExpanded == true){
                    closeSubMenusFab();
                } else {
                    openSubMenusFab();
                }
            }
        });

        //Only main FAB is visible in the beginning
        closeSubMenusFab();



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gallery) {
        startActivity(new Intent(getApplicationContext(),GalleryImages.class));
        }else if (id == R.id.nav_subscribe) {
          startActivity(new Intent(getApplicationContext(),SubscribeActivity.class));
        } else if (id == R.id.nav_location) {
            startActivity(new Intent(getApplicationContext(),MapsActivity.class));
        } else if (id == R.id.nav_services) {
            startActivity(new Intent(getApplicationContext(),Main2Activity.class));
        } else if (id == R.id.nav_messages) {
            startActivity(new Intent(getApplicationContext(),RetrivesMessages.class));
        } else if (id == R.id.nav_website) {
            startActivity(new Intent(getApplicationContext(),Website.class));
        } else if (id == R.id.nav_login) {
            startActivity(new Intent(getApplicationContext(),Login.class));
        } else if (id == R.id.nav_about) {
            android.app.AlertDialog dialog;
            android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.aboutus_dialog, null);
            mBuilder.setView(mView);

            dialog = mBuilder.create();
            dialog.show();

        } else if (id == R.id.nav_share) {
           Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,"Check out \"DAZZLE ON DIVAS APP\"https://play.google.com/store/app/details?id=com.payghost.dazzleondivas");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } else if (id == R.id.nav_contsct_us) {
            startActivity(new Intent(getApplicationContext(),ContactUs.class));
        } else if (id == R.id.nav_exit){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage(Html.fromHtml("<font color='#627984'>Are you sure you want to exit?</font>"))
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            System.exit(0);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_in));
                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_out));
                    mViewFlipper.showNext();
                    return true;
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_in));
                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext,R.anim.right_out));
                    mViewFlipper.showPrevious();
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }
    }

    //closes FAB submenus
    private void closeSubMenusFab(){
        layoutFabEdit.setVisibility(View.INVISIBLE);
        fabSettings.setImageResource(R.drawable.icons8exit);
        fabExpanded = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab(){
        layoutFabEdit.setVisibility(View.VISIBLE);
        //Change settings icon to 'X' icon
        fabSettings.setImageResource(R.drawable.ic_menu_share);
        fabExpanded = true;
    }
}
