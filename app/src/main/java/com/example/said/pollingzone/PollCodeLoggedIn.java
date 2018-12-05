package com.example.said.pollingzone;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PollCodeLoggedIn extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll_code_signedin);
        Log.d(AppConsts.TAG, "User Pollcode Activity");
        TextView editName = findViewById(R.id.editName);
        User u = User.Instance();

        //Email: testing1@yahoo.com
        //Pass: dddddd

        editName.setText("Welcome " + u.getFirstName() + " " + u.getLastName() + "!");




       // configureToPollActivity();
       // configureToSignIn();
       // configureToCreateActivity();
    }


}
