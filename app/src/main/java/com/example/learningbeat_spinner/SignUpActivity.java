package com.example.learningbeat_spinner;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    //Define variables for editText fields, button and database helper
    EditText username, password, rePassword;
    Button btnCreate;
    UserDBHelper userDBHelper;

    //Override onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //Hook all the variable with associated fields on the page
        username = findViewById(R.id.username1);
        password = findViewById(R.id.password1);
        rePassword = findViewById(R.id.rePassword);
        btnCreate = findViewById(R.id.btnCreate);
        userDBHelper = new UserDBHelper(this);

        //Handle click event for Create button
        btnCreate.setOnClickListener(new View.OnClickListener() {
            //Override onclick method
            @Override
            public void onClick(View view) {
                //Get the input text from the username, password and re-password fields
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = rePassword.getText().toString();
                //Check the content in the text fields
                if(user.equals("")||pass.equals("")||repass.equals(""))
                {
                    //If one of the text fields is null, display the reminder message
                    Toast.makeText(SignUpActivity.this, "Please enter all the fields",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Compare the content in the password and re-password fields
                    if(pass.equals(repass))
                    {
                        //When those fields match, create boolean to check the existence of username
                        Boolean checkUsername = userDBHelper.isUsernameExist(user);
                        //If the username does not exist
                        if(checkUsername==false)
                        {
                            //Insert the username and password into the database
                            Boolean isInserted = userDBHelper.insertData(user, pass);
                            //Check if the data is inserted into the database successfully
                            if(isInserted==true)
                            {
                                //Display message to confirm data is inserted
                                Toast.makeText(SignUpActivity.this,"Create new account successfully",
                                        Toast.LENGTH_SHORT).show();
                                //Create new intent linked with MainActivity class
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                //Display message to inform the sign up process is failed
                                Toast.makeText(SignUpActivity.this,"The process failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            //If the username exists, display the message
                            Toast.makeText(SignUpActivity.this, "Username is already" +
                                            "exists! Please choose another name or sign in",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        //If the password and re-password are not matching, display the message
                        Toast.makeText(SignUpActivity.this, "Passwords are not matching",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}