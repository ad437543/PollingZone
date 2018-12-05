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

public class QuestionsView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_questions);
        Log.d(AppConsts.TAG, "Questions Activity");
        User user = User.Instance();

        TextView pollName = findViewById(R.id.holdsPollName);
        pollName.setText(user.getActivePoll().getName());

        TextView questionText = findViewById(R.id.holdsQuestion);
        questionText.setText(user.getActivePoll().getText());

        TextView questionNumber = findViewById(R.id.holdsQuestionNumber);
        questionNumber.setText(user.getActivePoll().pollPosition());


        pressedSubmit();
    }
    //Todo: press submit adds the new question on to the screen and displays if user got the question right or wrong?
    private void pressedSubmit()
    {
        Button submit = findViewById(R.id.willSubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int answer = 0; // TODO: this is where we store the selected answer
                User user = User.Instance();
                boolean continuePoll = user.answerQuestion(answer);

                if(continuePoll) {
                    TextView pollName = findViewById(R.id.holdsPollName);
                    pollName.setText(user.getActivePoll().getName());

                    TextView questionNumber = findViewById(R.id.holdsQuestionNumber);
                    questionNumber.setText(user.getActivePoll().pollPosition());
                } else {
                    // TODO: Redirect to poll results page
                }
            }
        });
    }
}
