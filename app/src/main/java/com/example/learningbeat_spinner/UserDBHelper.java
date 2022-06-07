package com.example.learningbeat_spinner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDBHelper extends SQLiteOpenHelper {
    //Define all variables to store needed information for the database
    private static final String USER_DB = "user1.db";
    private static final int DB_VER = 1;
    private static final String USER_TABLE = "UsersInfo";
    private static final String COURSE_TABLE = "CourseInfo";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String ID = "id";
    public static final String NAME = "name";
    private static final String START = "start";
    private static final String END = "end";
    private static final String TARGET = "target";
    private static final String AS1 = "as1";
    private static final String AS2 = "as2";
    private static final String AS3 = "as3";
    private static final String MID = "mid";
    private static final String FINAL_EX = "finalEx";
    private static final String TOTAL = "total";
    private SQLiteDatabase sqlDB;

    //Define the constructor for UserDBHelper class
    public UserDBHelper(Context context) {
        super(context, USER_DB, null, DB_VER);
    }

    //Override onCreate method
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Define a string to create user table
        String sCreateTableUser = String.format("CREATE TABLE %s(%s text primary key, " +
                "%s text not null);", USER_TABLE, USERNAME, PASSWORD);
        //Define a string to create course table
        String sCreateTableCourse = String.format("CREATE TABLE %s(%s integer primary key autoincrement, " +
                "%s text not null, %s text not null, %s text not null, %s text not null, " +
                "%s double not null, %s double not null, %s double not null, %s double not null, " +
                "%s double not null, %s double not null, %s double not null);", COURSE_TABLE, ID,
                USERNAME, NAME, START, END, TARGET, AS1, AS2, AS3, MID, FINAL_EX, TOTAL);
        //Create user and course table
        sqLiteDatabase.execSQL(sCreateTableUser);
        sqLiteDatabase.execSQL(sCreateTableCourse);
    }

    //Override onUpgrade method
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Drop user or course table if exists
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + COURSE_TABLE);
        onCreate(sqLiteDatabase);
    }

    //Create open method to access the database
    public void open()
    {
        sqlDB = this.getWritableDatabase();
    }

    //Create close method to close the database
    public void close()
    {
        sqlDB.close();
    }

    //Create insertCourses method to insert courses into the database
    public long insertCourses(Course course, String username)
    {
        //Define new contentValues and add all needed information for the course to be inserted
        //into the database
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME, username);
        contentValues.put(NAME, course.sName);
        contentValues.put(START, course.sStartDate);
        contentValues.put(END, course.sEndDate);
        contentValues.put(TARGET, course.dTargetPoint);
        contentValues.put(AS1, course.dAs1);
        contentValues.put(AS2, course.dAs2);
        contentValues.put(AS3, course.dAs3);
        contentValues.put(MID, course.dMid);
        contentValues.put(FINAL_EX, course.dFinal);
        contentValues.put(TOTAL, course.dTotal);
        long autoId = sqlDB.insert(COURSE_TABLE, null, contentValues);
        course.id = autoId;
        //return id number of the course
        return autoId;
    }

    //Create insertData method to insert new username and password into the database
    public boolean insertData(String username, String password)
    {
        //Access the database
        open();
        //Create new contentValues and add username and password into it
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        //Define user's id variable generated when executing insert method to insert username and
        //password into the database
        long users = sqlDB.insert(USER_TABLE, null, contentValues);
        //Check the value of user's id
        if(users==-1)
        {
            //If id = -1, return false
            return false;
        }
        else
        {
            //If id != -1, return true
            return true;
        }
    }

    //Create isUsernameExist method to check if the username is exist in the database
    public boolean isUsernameExist(String username)
    {
        //Access the database
        open();
        //Define a string to select all the data that has a specific username input by user
        String sQuery = "SELECT * FROM " + USER_TABLE + " WHERE username = ?";
        //Define a cursor to store the data received from the query
        Cursor cursor = sqlDB.rawQuery(sQuery, new String[] {username});
        //Check the content of the cursor
        if(cursor.getCount()>0)
        {
            //If cursor includes data, return true
            return true;
        }
        else
        {
            //If not, return false
            return false;
        }
    }

    //Create isCourseNameExist method to check if the courseName exits in the database
    public boolean isCourseNameExist(String courseName, String username)
    {
        //Access the database
        open();
        //Create a string to select all the data that has a specific courseName and username
        String sQuery = "SELECT * FROM " + COURSE_TABLE + " WHERE " + NAME + " = ? AND " + USERNAME
                + " = ?";
        //Define a cursor to store the data received from the query
        Cursor cursor = sqlDB.rawQuery(sQuery, new String[] {courseName, username});
        //Check the content of the cursor
        if(cursor.getCount()>0)
        {
            //If cursor includes data, return true
            return true;
        }
        else
        {
            //If not, return false
            return false;
        }
    }

    //Create checkUsernamePassword to check if the username and password exit in the database
    public boolean checkUsernamePassword(String username, String password)
    {
        //Access the database
        open();
        //Create a string to select all the data that has a specific username and password
        String sQuery = "SELECT * FROM " + USER_TABLE + " WHERE username = ? AND password = ?";
        //Define a cursor to store the data received from the query
        Cursor cursor = sqlDB.rawQuery(sQuery, new String[] {username, password});
        //Check the content of the cursor
        if(cursor.getCount()>0)
        {
            //If cursor includes data, return true
            return true;
        }
        else
        {
            //If not, return false
            return false;
        }
    }

    //Create updateCourse method to update the information of a specific course
    public boolean updateCourse(Course course)
    {
        //Check if the id of the course exists
        if(course.id<0)
        {
            //If the id < 0, return false
            return false;
        }
        //Define new contentValues and add all needed information for the course to be updated
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, course.sName);
        contentValues.put(START, course.sStartDate);
        contentValues.put(END, course.sEndDate);
        contentValues.put(TARGET, course.dTargetPoint);
        contentValues.put(AS1, course.dAs1);
        contentValues.put(AS2, course.dAs2);
        contentValues.put(AS3, course.dAs3);
        contentValues.put(MID, course.dMid);
        contentValues.put(FINAL_EX, course.dFinal);
        contentValues.put(TOTAL, course.dTotal);
        //Execute update function to update the information of the course that has the id matching
        // with the id provided by the user
        return sqlDB.update(COURSE_TABLE,contentValues,ID + " = " + course.id,null)>0;
    }

    //Create deleteCourse method to delete a course from the database
    public boolean deleteCourse(Course course)
    {
        //Execute delete function to delete the course that hat the id matching with the id
        //provided by the user
        return sqlDB.delete(COURSE_TABLE,ID + " = " + course.id,null)>0;
    }

    //Create getAllCoursesInfo method to get all the courses' information based on the username
    public Cursor getAllCoursesInfo(String username)
    {
        //Define a string array to store all the column's name that need to get the data from the database
        String[] sFields = new String[]{"rowid _id", ID, USERNAME, NAME, START, END, TARGET, AS1, AS2, AS3, MID, FINAL_EX, TOTAL};
        //Define a cursor to store all the data retrieved from the database
        Cursor cursor = sqlDB.query(COURSE_TABLE,sFields,USERNAME+" = ?", new String[]{username},null,null,null);
        //Check the content of the cursor
        if(cursor!=null)
        {
            //If the cursor has content, move to first
            cursor.moveToFirst();
        }
        //return data from the cursor
        return cursor;
    }

    //Create hasData method to check if the Course table includes any data
    public boolean hasData(String username)
    {
        //Access the database
        open();
        //Create a string to select all the data from the course table
        String sQuery = "SELECT * FROM " + COURSE_TABLE + " WHERE " + USERNAME + " = ?";
        //Define a cursor to store all the data from the query
        Cursor cursor = sqlDB.rawQuery(sQuery, new String[]{username});
        //Check the content of the cursor
        if(cursor.getCount()>0)
        {
            //If cursor includes data, return true
            return true;
        }
        else
        {
            //If not, return false
            return false;
        }
    }
}

