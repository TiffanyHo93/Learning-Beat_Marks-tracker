package com.example.learningbeat_spinner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Define needed variables for editText fields, button, database helper and checkbox
    public static EditText username, password;
    private Button login, signup;
    private UserDBHelper userDBHelper;
    private CheckBox checkBox;

    //Override onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Hook all the variable with the associated fields
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.btnLogin);
        signup = findViewById(R.id.btnSignUp);
        checkBox = findViewById(R.id.checkBox);
        userDBHelper = new UserDBHelper(this);

        //Handle on click event for sign up button
        signup.setOnClickListener(new View.OnClickListener() {
            //Override onClick method
            @Override
            public void onClick(View view) {
                //Create new intent linked to the SignUpActivity page
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        //Handle on click event for login button
        login.setOnClickListener(new View.OnClickListener() {
            //Override onClick method
            @Override
            public void onClick(View view) {
                //Get the value from the username and password fields
                String user = username.getText().toString();
                String pass = password.getText().toString();
                //Check the values in the username and password fields
                if(user.equals("")||pass.equals(""))
                {
                    //If one of the fields is empty, display the message
                    Toast.makeText(MainActivity.this, "Please enter all the fields",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Create boolean variable to check if the username and password match with
                    //the data in the database
                    Boolean checkUsernameAndPass = userDBHelper.checkUsernamePassword(user,pass);
                    //Check the username and password
                    if(checkUsernameAndPass==true)
                    {
                        //If match with the data in the database, display a message
                        Toast.makeText(MainActivity.this,"Login successfully",
                                Toast.LENGTH_SHORT).show();
                        //Create new intent linked with ViewCourseInfo page
                        Intent intent = new Intent(getApplicationContext(), ViewCourseInfo.class);
                        startActivity(intent);
                    }
                    else
                    {
                        //If they don't match, display a message
                        Toast.makeText(MainActivity.this, "Invalid username or password." +
                                "Please try again!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //Handle onChecked event for the Remember me check box
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            //Override the onCheckedChanged method
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //If the box is checked
                if(buttonView.isChecked())
                {
                    //Create a sharedPreferences to store the values
                    SharedPreferences preferences = getSharedPreferences("remember", MODE_PRIVATE);
                    //Create editor to edit the sharedPreferences
                    SharedPreferences.Editor editor = preferences.edit();
                    //Input values into the sharedPreferences
                    editor.putString("rememberMe", "true");
                    editor.putString("user", username.getText().toString());
                    editor.putString("pass", password.getText().toString());
                    //Apply the editor
                    editor.apply();
                    //Display a message shows the check box is checked
                    Toast.makeText(MainActivity.this, "Checked", Toast.LENGTH_SHORT).show();
                }
                else if(!buttonView.isChecked())//If the check box is unchecked
                {
                    //Create a sharedPreferences to store the values
                    SharedPreferences preferences = getSharedPreferences("remember", MODE_PRIVATE);
                    //Create editor to edit the sharedPreferences
                    SharedPreferences.Editor editor = preferences.edit();
                    //Input values into the sharedPreferences
                    editor.putString("rememberMe", "false");
                    //Apply the editor
                    editor.apply();
                    //Display a message shows the check box is unchecked
                    Toast.makeText(MainActivity.this, "UnChecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Override onResume method
    @Override
    protected void onResume() {
        super.onResume();
        //Create sharedPreferences to get value from initial sharedPrefernces
        SharedPreferences preferences = getSharedPreferences("remember", MODE_PRIVATE);
        //Define variables to store the information retrieved from the SharedPrefernces
        String sCheck = preferences.getString("rememberMe", "");
        String sUser = preferences.getString("user","");
        String sPass = preferences.getString("pass","");
        //Check if the checkbox is checked
        if(sCheck.equals("true"))
        {
            //Set the value for username and password
            username.setText(sUser);
            password.setText(sPass);
            //Set check box to checked
            checkBox.setChecked(true);
        }
        else if(sCheck.equals("false"))
        {
            //If not, set the value for username and password = null
            username.setText("");
            password.setText("");
        }
    }
}