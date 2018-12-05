package com.example.said.pollingzone;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Poll {
    // Other necessary fields here i.e. poll title, id number ect
    private String roomID;
    private String name;
    private String startTime;
    private String expireTime;
    private String owner;
    private ArrayList<Question> questions;
    private int currentQuestion;

    public Poll() {
        this.questions = new ArrayList<>();
        this.currentQuestion = 0;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void addQuestion(String qText, String questionID, ArrayList<String> qAnswers) {
        this.questions.add(new Question(qText, questionID, qAnswers));
    }

    public boolean answerQuestion(String userid, String sessionID, int selectedAnswer) {


        Map<String, String> postData = new HashMap<>();
        postData.put("userID", userid);
        postData.put("sessionID", sessionID);
        postData.put("roomID", this.getRoomID());
        postData.put("questionID", this.questions.get(currentQuestion).questionID);
        postData.put("choice", Integer.toString(selectedAnswer));

        HttpPostAsyncTask task = new HttpPostAsyncTask(postData, new AsyncResponse() {
            public void processFinish(String output) {
                try {
                    JSONObject data = (JSONObject) new JSONTokener(output).nextValue();
                    String error = data.getString("error");

                    if(error.equals("")) {
                        return;
                    } else {
                        Log.e(AppConsts.TAG, "Error Answering Question : " + error);
                    }
                } catch (JSONException e) {
                    Log.e(AppConsts.TAG, e.getLocalizedMessage());
                }
            }
        });
        task.execute(AppConsts.PHP_location + "/AnswerQuestion.php");

        this.questions.get(currentQuestion).answerQuestion(selectedAnswer);
        this.currentQuestion++;

        if(this.currentQuestion == this.questions.size()) return false;
        else return true;
    }

    public String[] getPossibleAnswers(){
        return this.questions.get(this.currentQuestion).getAnswers();
    }

    public String getText() {
        return this.questions.get(this.currentQuestion).getText();
    }

    public int[] getUserAnswers() {
        int[] answers = new int[this.questions.size()];
        for(int i = 0; i < this.questions.size(); i++) {
            answers[i] = this.questions.get(i).getUserAnswer();
        }
        return answers;
    }

    public String pollPosition() {
        return (this.currentQuestion + 1) + "/" + this.questions.size();
    }


    @Override
    public String toString() {
        return "Poll{" +
                "roomID='" + roomID + '\'' +
                ", name='" + name + '\'' +
                ", startTime='" + startTime + '\'' +
                ", expireTime='" + expireTime + '\'' +
                ", owner='" + owner + '\'' +
                ", questions=" + questions +
                ", currentQuestion=" + currentQuestion +
                '}';
    }

    private class Question {
        private String text;
        private String questionID;
        private ArrayList<String> possibleAnswers;
        private int userAnswer;

        private Question() {
            this.text = new String();
            this.possibleAnswers = new ArrayList<>();
        }

        private Question(String qText, String questionID, ArrayList<String> qAnswers) {
            this.text = qText;
            this.questionID = questionID;
            this.possibleAnswers = new ArrayList<>();
            this.possibleAnswers.addAll(qAnswers);
            this.userAnswer = -1;
        }

        private void answerQuestion(int answer) {
            this.userAnswer = answer;
        }

        public String getQuestionID() {
            return questionID;
        }

        public void setQuestionID(String questionID) {
            this.questionID = questionID;
        }

        private String[] getAnswers() {
            String[] answers = new String[this.possibleAnswers.size()];
            answers = this.possibleAnswers.toArray(answers);
            return answers;
        }

        private String getText() {
            return this.text;
        }

        private int getUserAnswer() {
            return this.userAnswer;
        }

        @Override
        public String toString() {
            return "Question{" +
                    "text='" + text + '\'' +
                    ", questionID='" + questionID + '\'' +
                    ", possibleAnswers=" + possibleAnswers +
                    ", userAnswer=" + userAnswer +
                    '}';
        }
    }
}
