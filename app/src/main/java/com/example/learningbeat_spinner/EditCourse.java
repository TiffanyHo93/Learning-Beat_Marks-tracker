package com.example.learningbeat_spinner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditCourse extends AppCompatActivity{
    //Define variables to store data from textview and editText
    private TextView tvId, tvTotal;
    private EditText etCourseName, etStart, etEnd, etTarget, etASM1, etASM2, etASM3, etMid, etFinal;
    //Define New, Delete and Save buttons
    private Button btnNew, btnDelete, btnSave;
    //Define database helper
    private UserDBHelper db;

    //Create newClicked functions for the New button to add new course into database
    private void newClicked(View v)
    {
        //Check all the required fields are input
        if(etCourseName.getText().toString().equals("")||etStart.getText().toString().equals("")||
                etEnd.getText().toString().equals("")||etTarget.getText().toString().equals("")||
                etASM1.getText().toString().equals("")||etASM2.getText().toString().equals("")||
                etASM3.getText().toString().equals("")||etMid.getText().toString().equals("")
                ||etFinal.getText().toString().equals(""))
        {
            //If one of these fields are null, display a reminder
            Toast.makeText(EditCourse.this, "Please enter all the fields",
                    Toast.LENGTH_SHORT).show();
        }
        else if(!checkInput())
        {
            //If input points are invalid, display message
            Toast.makeText(EditCourse.this, "Marks cannot larger than 100",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            //Access the database
            db.open();
            //Check if the course's name exists
            if(db.isCourseNameExist(etCourseName.getText().toString(),MainActivity.username.getText().toString()))
            {
                //If the course's name exists in the database, display a reminder
                Toast.makeText(EditCourse.this, "Course name exits. Please choose " +
                                "another name", Toast.LENGTH_SHORT).show();
            }
            else
            {
                //Define new course object
                Course course = new Course(etCourseName.getText().toString(), etStart.getText().toString(),
                        etEnd.getText().toString(), Double.parseDouble(etTarget.getText().toString()),
                        Double.parseDouble(etASM1.getText().toString()),Double.parseDouble(etASM2.getText().toString()),
                        Double.parseDouble(etASM3.getText().toString()),Double.parseDouble(etMid.getText().toString()),
                        Double.parseDouble(etFinal.getText().toString()));
                //Insert new course into the database
                db.insertCourses(course, MainActivity.username.getText().toString());
                //Reload the database
                refresh();
                //Close the database
                db.close();
                //Return to the View course page
                Intent intent = new Intent(getApplicationContext(),ViewCourseInfo.class);
                startActivity(intent);
            }
        }
    }

    //Create deleteClicked method for the Delete button to delete a course from the database
    private void deleteClicked(View v)
    {
        //Check all the required fields are input
        if(etCourseName.getText().toString().equals("")||etStart.getText().toString().equals("")||
                etEnd.getText().toString().equals("")||etTarget.getText().toString().equals("")||
                etASM1.getText().toString().equals("")||etASM2.getText().toString().equals("")||
                etASM3.getText().toString().equals("")||etMid.getText().toString().equals("")
                ||etFinal.getText().toString().equals(""))
        {
            //If one of these fields are null, display a reminder
            Toast.makeText(EditCourse.this, "Please enter all the fields",
                    Toast.LENGTH_SHORT).show();
        }
        else if(!checkInput())
        {
            //If input points are invalid, display message
            Toast.makeText(EditCourse.this, "Marks cannot larger than 100",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            //Check the Id number
            if (tvId.getText().toString().length() < 0) return;
            //Access the database
            db.open();
            //Define new course object
            Course course = new Course(Long.parseLong(tvId.getText().toString()), etCourseName.getText().toString(),
                    etStart.getText().toString(), etEnd.getText().toString(), Double.parseDouble(etTarget.getText().toString()),
                    Double.parseDouble(etASM1.getText().toString()), Double.parseDouble(etASM2.getText().toString()),
                    Double.parseDouble(etASM3.getText().toString()), Double.parseDouble(etMid.getText().toString()),
                    Double.parseDouble(etFinal.getText().toString()));
            //Delete the course from the database
            db.deleteCourse(course);
            //Reload the database
            refresh();
            //Close the database
            db.close();
            //Return to the View course page
            Intent intent1 = new Intent(getApplicationContext(), ViewCourseInfo.class);
            startActivity(intent1);
        }
    }

    private void saveClicked(View v)
    {
        //Check all the required fields are input
        if(etCourseName.getText().toString().equals("")||etStart.getText().toString().equals("")||
                etEnd.getText().toString().equals("")||etTarget.getText().toString().equals("")||
                etASM1.getText().toString().equals("")||etASM2.getText().toString().equals("")||
                etASM3.getText().toString().equals("")||etMid.getText().toString().equals("")
                ||etFinal.getText().toString().equals(""))
        {
            //If one of these fields are null, display a reminder
            Toast.makeText(EditCourse.this, "Please enter all the fields",
                    Toast.LENGTH_SHORT).show();
        }
        else if(!checkInput())
        {
            //If input points are invalid, display message
            Toast.makeText(EditCourse.this, "Marks cannot larger than 100",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            //Access the database
            db.open();
            //Check if the course's name exists
            /*if(db.isCourseNameExist(etCourseName.getText().toString(),MainActivity.username.getText().toString()))
            {
                //If the course's name exists in the database, display a reminder
                Toast.makeText(EditCourse.this, "Course name exits. Please choose " +
                        "another name", Toast.LENGTH_SHORT).show();
            }
            else {*/
                //Define new course object
                Course course = new Course(Long.parseLong(tvId.getText().toString()), etCourseName.getText().toString(), etStart.getText().toString(),
                        etEnd.getText().toString(), Double.parseDouble(etTarget.getText().toString()),
                        Double.parseDouble(etASM1.getText().toString()), Double.parseDouble(etASM2.getText().toString()),
                        Double.parseDouble(etASM3.getText().toString()), Double.parseDouble(etMid.getText().toString()),
                        Double.parseDouble(etFinal.getText().toString()));
                //Update the course's information to the database
                db.updateCourse(course);
                //Reload the database
                refresh();
                //Close the database
                db.close();
                //Return to the View course page
                Intent intent = new Intent(getApplicationContext(), ViewCourseInfo.class);
                startActivity(intent);
            //}
        }
    }

    //Create refresh method to refresh the database after editing
    private void refresh()
    {
        //Get all the courses in the database and store into the cursor
        ViewCourseInfo.cursor = db.getAllCoursesInfo(MainActivity.username.getText().toString());
        //Define information displayed in the spinner
        String[] col = new String[]{db.NAME};
        int[] views = new int[]{android.R.id.text1};
        //Create an adapter for the spinner
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,ViewCourseInfo.cursor,col, views);
        //Set the adapter for the spinner
        ViewCourseInfo.spinner.setAdapter(adapter);
    }

    //Create checkInput method to check the points input
    private boolean checkInput()
    {
        //If the points are bigger than 100, return false
        if(Double.parseDouble(etTarget.getText().toString())>100||
                Double.parseDouble(etASM1.getText().toString())>100||
                Double.parseDouble(etASM2.getText().toString())>100||
                Double.parseDouble(etASM3.getText().toString())>100||
                Double.parseDouble(etMid.getText().toString())>100||
                Double.parseDouble(etFinal.getText().toString())>100)
        {
            return false;
        }
        else
        {
            //If not, return true
            return true;
        }
    }

    //Override on Create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        //Hook the variables with associated fields on the page
        tvId = findViewById(R.id.tvId);
        etCourseName = findViewById(R.id.etCourseName);
        etStart = findViewById(R.id.etStart);
        etEnd = findViewById(R.id.etEnd);
        etTarget = findViewById(R.id.etTarget);
        etASM1 = findViewById(R.id.etASM1);
        etASM2 = findViewById(R.id.etASM2);
        etASM3 = findViewById(R.id.etASM3);
        etMid = findViewById(R.id.etMid);
        etFinal = findViewById(R.id.etFinal);
        tvTotal = findViewById(R.id.tvTotal);
        btnNew = findViewById(R.id.btnNew);
        btnNew.setOnClickListener(v->newClicked(v));
        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(v->deleteClicked(v));
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v->saveClicked(v));
        db = new UserDBHelper(this);

        //Set text displayed for all the textview and editText boxes on the page
        tvId.setText(ViewCourseInfo.tvId.getText().toString());
        etCourseName.setText(ViewCourseInfo.tvCourseName.getText().toString());
        etStart.setText(ViewCourseInfo.tvStart.getText().toString());
        etEnd.setText(ViewCourseInfo.tvEnd.getText().toString());
        etTarget.setText(ViewCourseInfo.tvTarget.getText().toString());
        etASM1.setText(ViewCourseInfo.tvASM1.getText().toString());
        etASM2.setText(ViewCourseInfo.tvASM2.getText().toString());
        etASM3.setText(ViewCourseInfo.tvASM3.getText().toString());
        etMid.setText(ViewCourseInfo.tvMid.getText().toString());
        etFinal.setText(ViewCourseInfo.tvFinal.getText().toString());
        tvTotal.setText(ViewCourseInfo.tvTotal.getText().toString());

        //Define Calendar variable for start date
        Calendar calendar1 = Calendar.getInstance();
        //Set Date listener for start date
        DatePickerDialog.OnDateSetListener start = new DatePickerDialog.OnDateSetListener() {
            //Override onDateSet method to set the date for the calendar1
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                //Execute updateCalendar function
                updateCalendar();
            }
            //Create updateCalendar method
            private void updateCalendar(){
                //Define a string to store format of the date
                String date = "dd/MM/yyyy";
                //Define simple date format
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(date, Locale.CANADA);
                //Set the content for the start date edit text
                etStart.setText(simpleDateFormat.format(calendar1.getTime()));
            }
        };
        //Handle onclick event for start date
        etStart.setOnClickListener(new View.OnClickListener() {
            //Override onClick method to view the date picker
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditCourse.this, start, calendar1.get(Calendar.YEAR),
                        calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //Define Calendar variable for end date
        Calendar calendar2 = Calendar.getInstance();
        //Set Date listener for end date
        DatePickerDialog.OnDateSetListener end = new DatePickerDialog.OnDateSetListener() {
            //Override onDateSet method to set the date for the calendar2
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar2.set(Calendar.YEAR, year);
                calendar2.set(Calendar.MONTH, month);
                calendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                //Execute updateCalendar function
                updateCalendar();
            }
            //Create updateCalendar method
            private void updateCalendar(){
                //Define a string to store format of the date
                String date = "dd/MM/yyyy";
                //Define simple date format
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(date, Locale.CANADA);
                //Set the content for the end date edit text
                etEnd.setText(simpleDateFormat.format(calendar2.getTime()));
            }
        };
        //Handle onclick event for start date
        etEnd.setOnClickListener(new View.OnClickListener() {
            //Override onClick method to view the date picker
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditCourse.this, end, calendar2.get(Calendar.YEAR),
                        calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
}