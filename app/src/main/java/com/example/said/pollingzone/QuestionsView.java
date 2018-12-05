package com.example.said.pollingzone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuestionsView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_questions);
        Log.d(AppConsts.TAG, "Questions Activity");

        pressedSubmit();
    }
    //Todo: press submit adds the new question on to the screen and displays if user got the question right or wrong?
    private void pressedSubmit()
    {

    }
}
