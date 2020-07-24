package com.bhavani.divya.sharadhacomplaints;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        final EditText Username,Password;
        Username= findViewById(R.id.username_edittext);
        Password=findViewById(R.id.password_edittext);


        Button Signin;
        Signin= findViewById(R.id.signin_button);





        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String givenUsername,givenPassword;
                givenUsername = Username.getText().toString();
                givenPassword = Password.getText().toString();
                if(givenUsername.equals("student")&&givenPassword.equals("123456")){
                    Intent i = new Intent(LoginPage.this,HomeActivity.class);
                    i.putExtra("user","Student");
                    startActivity(i);
                }
                else if (givenUsername.equals("warden")&&givenPassword.equals("987654")){
                    Intent i = new Intent(LoginPage.this,HomeActivity.class);
                    i.putExtra("user","warden");
                    startActivity(i);
                }
                else {
                    Toast.makeText(LoginPage.this, "Incorrect Username or Password" , Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
