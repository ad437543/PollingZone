package com.example.said.pollingzone;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class QuestionsView extends AppCompatActivity {

    private ArrayList<String> mAnswerChoices = new ArrayList<>();
    private ArrayList<String> mImagesUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_questions);

        User user = User.Instance();
        TextView pollName = findViewById(R.id.holdsPollName);
        pollName.setText(user.getActivePoll().getName());

        TextView questionText = findViewById(R.id.holdsQuestion);
        questionText.setText(user.getActivePoll().getText());

        TextView questionNumber = findViewById(R.id.holdsQuestionNumber);
        questionNumber.setText(user.getActivePoll().pollPosition());


        pressedSubmit();

        initImageBitmaps();
    }
    //Todo: press submit adds the new question on to the screen and displays if user got the question right or wrong?
    private void pressedSubmit()
    {
        Button submit = findViewById(R.id.willSubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int answer = RecyclerViewAdapter.lastClicked; // TODO: test
                Log.d(AppConsts.TAG, answer + " was selected");
                User user = User.Instance();
                boolean continuePoll = user.answerQuestion(answer);

                if(continuePoll) {

                    TextView questionText = findViewById(R.id.holdsQuestion);
                    questionText.setText(user.getActivePoll().getText());

                    TextView questionNumber = findViewById(R.id.holdsQuestionNumber);
                    questionNumber.setText(user.getActivePoll().pollPosition());

                    initImageBitmaps();
                } else {
                    // TODO: Redirect to poll results page
                    Log.d(AppConsts.TAG, "End of Poll");
                    Log.d(AppConsts.TAG, "User Answers: " + Arrays.toString(user.getActivePoll().getUserAnswers()));
                    if(user.getUserid().equals("")) {
                        startActivity(new Intent(QuestionsView.this, pollCode.class));
                    } else {
                        startActivity(new Intent(QuestionsView.this, PollCodeLoggedIn.class));
                    }
                }
            }
        });
    }

    private void initImageBitmaps(){
        String []Letters = new String[16];
        Letters[0] = "https://cdn.pixabay.com/photo/2017/02/09/11/46/alphabet-2051722__340.png";
        Letters[1] = "https://cdn.pixabay.com/photo/2017/02/09/11/45/alphabet-2051720__340.png";
        Letters[2] = "https://cdn.pixabay.com/photo/2017/02/09/11/43/alphabet-2051715__340.png";
        Letters[3] = "https://cdn.pixabay.com/photo/2017/02/09/11/42/alphabet-2051714__340.png";
        Letters[4] = "https://cdn.pixabay.com/photo/2017/02/09/11/40/alphabet-2051710__340.png";
        Letters[5]= "https://cdn.pixabay.com/photo/2017/02/09/11/38/alphabet-2051709__340.png";
        Letters[6] = "https://cdn.pixabay.com/photo/2017/02/09/11/37/alphabet-2051708__340.png";
        Letters[7] = "https://cdn.pixabay.com/photo/2017/02/09/11/35/alphabet-2051707__340.png";
        Letters[8] = "https://cdn.pixabay.com/photo/2017/02/09/11/33/alphabet-2051706__340.png";
        Letters[9] = "https://cdn.pixabay.com/photo/2017/02/09/11/32/alphabet-2051705__340.png";
        Letters[10] = "https://cdn.pixabay.com/photo/2017/02/09/11/31/alphabet-2051703__340.png"; // K
        Letters[11] = "https://cdn.pixabay.com/photo/2017/02/09/11/29/alphabet-2051690__340.png";
        Letters[12] = "https://cdn.pixabay.com/photo/2017/02/09/11/28/alphabet-2051689__340.png";
        Letters[13] = "https://cdn.pixabay.com/photo/2017/02/09/11/26/alphabet-2051688__340.png";
        Letters[14] = "https://cdn.pixabay.com/photo/2017/02/09/11/25/alphabet-2051685__340.png";
        Letters[15] = "https://cdn.pixabay.com/photo/2017/02/09/11/23/alphabet-2051683__340.png";

        mAnswerChoices.clear();
        String[] answers = User.Instance().getActivePoll().getPossibleAnswers();
        for(int i = 0; i < answers.length; i++) {
            mImagesUrls.add(Letters[i]);
            mAnswerChoices.add(answers[i]);
        }

        //Todo: for loop, for every answer choice, i = 0, i < answerchoices, read from json and add it to mAnswerChoices, and do mImagesUrls.add(Letters[i]);
        /*
        mImagesUrls.add(Letters[0]);
        mAnswerChoices.add("Answer Choice A");

        mImagesUrls.add(Letters[1]);
        mAnswerChoices.add("Answer Choice B");

        mImagesUrls.add(Letters[2]);
        mAnswerChoices.add("Answer Choice C");

        mImagesUrls.add(Letters[3]);
        mAnswerChoices.add("Answer Choice D");

        mImagesUrls.add(Letters[4]);
        mAnswerChoices.add("Answer Choice E");

        mImagesUrls.add(Letters[5]);
        mAnswerChoices.add("Answer Choice E");
        mImagesUrls.add(Letters[6]);
        mAnswerChoices.add("Answer Choice E");
        mImagesUrls.add(Letters[7]);
        mAnswerChoices.add("Answer Choice E");
        mImagesUrls.add(Letters[8]);
        mAnswerChoices.add("Answer Choice E");
        mImagesUrls.add(Letters[9]);
        mAnswerChoices.add("Answer Choice E");
        mImagesUrls.add(Letters[10]);
        mAnswerChoices.add("Answer Choice E");
        mImagesUrls.add(Letters[11]);
        mAnswerChoices.add("Answer Choice E");
        mImagesUrls.add(Letters[12]);
        mAnswerChoices.add("Answer Choice E");
        mImagesUrls.add(Letters[13]);
        mAnswerChoices.add("Answer Choice E");
        mImagesUrls.add(Letters[14]);
        mAnswerChoices.add("Answer Choice E");
        mImagesUrls.add(Letters[15]);
        mAnswerChoices.add("Answer Choice E");
        */

        initRecyclerView();

    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mImagesUrls, mAnswerChoices, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
