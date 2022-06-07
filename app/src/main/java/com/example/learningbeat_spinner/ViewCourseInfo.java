package com.example.learningbeat_spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

public class ViewCourseInfo extends AppCompatActivity implements AdapterView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {
    //Define all needed variables for database, cursor, spinner, text view, button, drawerLayout
    //and actionBarDrawerToggle
    private UserDBHelper db;
    public static Cursor cursor;
    public static Spinner spinner;
    public static TextView tvCourseName, tvId, tvStart, tvEnd, tvTarget, tvASM1, tvASM2, tvASM3, tvMid, tvFinal, tvTotal;
    private Button btnUpdate;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    //Override onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course_info);
        //Create a new toolbar and hook it with the toolbar on the page
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Set the title for the toolbar
        toolbar.setTitle("Learning Beat");
        //Set the support action bar with the toolbar
        setSupportActionBar(toolbar);

        //Hook the drawerLayout with it id
        drawerLayout = findViewById(R.id.dl);
        //Set the actionBarDrawerToggle for the drawer
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        //Enable drawer indicator
        drawerToggle.setDrawerIndicatorEnabled(true);
        //Add drawerToggle as a listener of drawerLayout
        drawerLayout.addDrawerListener(drawerToggle);
        //Sync the drawer
        drawerToggle.syncState();
        //Display toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Create navigation view and hook with its id
        NavigationView navigationView = findViewById(R.id.nav_bar);
        //Bring the navigation view to front to set on item selected
        navigationView.bringToFront();
        //Set the context for the item in the navigation
        navigationView.setNavigationItemSelectedListener(this);
        //Define view of navigation bar header
        View header = navigationView.getHeaderView(0);
        //Get the text view in the header of navigation bar
        TextView tvNav = header.findViewById(R.id.tvNav);
        //Set text for the text view in the header of navigation bar
        tvNav.setText("Welcome " + MainActivity.username.getText().toString() + "!");

        //Check camera permission
        if(ContextCompat.checkSelfPermission(ViewCourseInfo.this, Manifest.permission.CAMERA)
        != PackageManager.PERMISSION_GRANTED)
        {
            //If does not have, request for the permission
            ActivityCompat.requestPermissions(ViewCourseInfo.this, new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }

        //Hook all the variables to the associated fields
        spinner = findViewById(R.id.spinnerCourse);
        tvId = findViewById(R.id.tvId);
        tvCourseName = findViewById(R.id.tvCourseName);
        tvStart = findViewById(R.id.tvStart);
        tvEnd = findViewById(R.id.tvEnd);
        tvTarget = findViewById(R.id.tvTarget);
        tvASM1 = findViewById(R.id.tvASM1);
        tvASM2 = findViewById(R.id.tvASM2);
        tvASM3 = findViewById(R.id.tvASM3);
        tvMid = findViewById(R.id.tvMid);
        tvFinal = findViewById(R.id.tvFinal);
        tvTotal = findViewById(R.id.tvTotal);
        db = new UserDBHelper(this);
        spinner.setOnItemSelectedListener(this);
        btnUpdate = findViewById(R.id.btnUpdate);
        //Handle on click event for update button
        btnUpdate.setOnClickListener(v->{
            //Create new intent linked with the EditCourse page
            Intent intent = new Intent(this, EditCourse.class);
            startActivity(intent);
        });

        //Check if the Course table content any data
        if(!db.hasData(MainActivity.username.getText().toString()))
        {
            //If not, access the database
            db.open();
            //Insert new courses
            db.insertCourses(new Course("Android","1/1/2022","31/3/2022",
                    10.0,7.0,5.0,6.0,8.0,6.0),
                    MainActivity.username.getText().toString());
            db.insertCourses(new Course("Android1","1/1/2022","31/3/2022",
                    100.0,9.0,6.0,8.0,4.0,3.0),
                    MainActivity.username.getText().toString());
            db.insertCourses(new Course("Android2","1/1/2022","31/3/2022",
                    80.0,8.0,8.0,9.0,9.0,7.0),
                    MainActivity.username.getText().toString());
            //Close the database
            db.close();
        }
        //Execute refreshData function
        refreshData();
    }

    //Create refreshData method
    public void refreshData() {
        //Access the database
        db.open();
        //Store all the information received from the getAllCoursesInfo into the cursor
        cursor = db.getAllCoursesInfo(MainActivity.username.getText().toString());
        //Define variables to display content in the spinner
        String[] col = new String[]{db.NAME};
        int[] views = new int[]{android.R.id.text1};
        //Create adapter for the spinner
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,cursor,col, views);
        //Set the adapter for the spinner
        spinner.setAdapter(adapter);
        //Close the database
        db.close();
    }

    //Override the onItemSelected method
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Set text displayed for all the textview and editText boxes on the page
        tvId.setText(cursor.getString(1));
        tvCourseName.setText(cursor.getString(3));
        tvStart.setText(cursor.getString(4));
        tvEnd.setText(cursor.getString(5));
        tvTarget.setText(cursor.getString(6));
        tvASM1.setText(cursor.getString(7));
        tvASM2.setText(cursor.getString(8));
        tvASM3.setText(cursor.getString(9));
        tvMid.setText(cursor.getString(10));
        tvFinal.setText(cursor.getString(11));
        tvTotal.setText(cursor.getString(12));
    }

    //Override the onNothingSelected method
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //If nothing is selected from the spinner, display the message
        Toast.makeText(this,"Nothing selected",Toast.LENGTH_SHORT).show();
    }

    //Override onOptionsItemSelected method
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    //Override onNavigationItemSelected method
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Switch between different item in the menu
        switch (item.getItemId())
        {
            //For photo
            case R.id.photo:
                //Create new intent and execute camera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);
                return true;
            //For logout
            case R.id.logout:
                //Create new intent linked to the MainActivity page and display a message
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                Toast.makeText(ViewCourseInfo.this,"Logout",Toast.LENGTH_SHORT).show();
                return true;
            //For notification
            case R.id.notification:
                //Create calendar
                Calendar calendar = Calendar.getInstance();
                //Set the hour, minute and second for the notification
                calendar.set(Calendar.HOUR_OF_DAY,8);
                calendar.set(Calendar.MINUTE,0);
                calendar.set(Calendar.SECOND,0);
                //Create new intent linked to the notificationReceiver class
                Intent intent2 = new Intent(getApplicationContext(), NotificationReceiver.class);
                //Create PendingIntent to get the broadcast from notificationReceiver class
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,
                        intent2,PendingIntent.FLAG_UPDATE_CURRENT);
                //Create alarmManager
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                //Set the repeating of alarmManager
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY,pendingIntent);
                //Display a message
                Toast.makeText(ViewCourseInfo.this,"Notification is turned on",Toast.LENGTH_SHORT).show();
                return true;
            //For notificationOff
            case R.id.notificationOff:
                //Execute cancelNotification method
                cancelNotification();
                //Display a message
                Toast.makeText(ViewCourseInfo.this,"Notification is turned off",Toast.LENGTH_SHORT).show();
                return true;
        }
        return true;
    }

    //Override onBackPressed
    @Override
    public void onBackPressed() {
        //If drawer is open
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            //Close the drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
        {
            super.onBackPressed();
        }
    }

    //Create cancelNotification method
    private void cancelNotification()
    {
        //Create NotificationManager
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //Cancel the notification
        notificationManager.cancel(0);
    }
}