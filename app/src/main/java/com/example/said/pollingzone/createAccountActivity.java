package com.example.said.pollingzone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

public class createAccountActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Log.d(AppConsts.TAG, "Register Activity");


        configureBackToPollCode();
    }

    private void configureBackToPollCode()
    {
        Button backButton = findViewById(R.id.willSubmit);

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText password = findViewById(R.id.holdsPassword);
                EditText confirmPassword = findViewById(R.id.holdsConfirmedPassword);
                EditText email = findViewById(R.id.holdsEmail);
                EditText firstName = findViewById(R.id.holdsFirstName);
                EditText lastName = findViewById(R.id.holdsLastName);
                EditText studentID = findViewById(R.id.holdsStudentID);



                if(firstName.getText().toString().isEmpty() || lastName.getText().toString().isEmpty() || email.getText().toString().isEmpty() || confirmPassword.getText().toString().isEmpty() || password.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),
                            "CAN NOT HAVE BLANK FIELDS",Toast.LENGTH_SHORT).show();

                    return;
                }
                else if(!isValidEmail(email.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),
                            "EMAIL IS INVALID",Toast.LENGTH_SHORT).show();

                    return;
                }
                else if(password.getText().toString().length() < 6 || password.getText().toString().length() > 20)
                {
                    Toast.makeText(getApplicationContext(),
                            "PASSWORD MUST BE OF LENGTH 6 THROUGH 20",Toast.LENGTH_SHORT).show();

                    return;
                }
                else if(!password.getText().toString().equals(confirmPassword.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),
                            "PASSWORDS DO NOT MATCH",Toast.LENGTH_SHORT).show();

                    return;
                }

                Map<String, String> postData = new HashMap<>();
                postData.put("firstName", firstName.getText().toString());
                postData.put("lastName", lastName.getText().toString());
                postData.put("optionalName", studentID.getText().toString());
                postData.put("userEmail", email.getText().toString());
                postData.put("password", AppConsts.getSHA(password.getText().toString()));

                HttpPostAsyncTask task = new HttpPostAsyncTask(postData, new AsyncResponse() {
                    public void processFinish(String output) {
                        try  {
                            JSONObject data = (JSONObject) new JSONTokener(output).nextValue();
                            String error = data.getString("error");
                            if(error.equals("")) {
                                Toast.makeText(getApplicationContext(),
                                        "ACCOUNT CREATED",Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(createAccountActivity.this, SignInActivity.class));
                                finish();
                            } else {
                                // TODO: this means there is an error logging in, likely same email
                                // TODO: see if we can get back an error code for when an email is already taken
                                Toast.makeText(getApplicationContext(),
                                        "ACCOUNT COULD NOT BE MADE",Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            Log.e(AppConsts.TAG, e.getLocalizedMessage());
                        }
                    }
                });
                task.execute(AppConsts.PHP_location + "/Register.php");

                // TODO: finish();

            }
        });
    }

    private boolean isValidEmail(String Email)
    {
        char[] charArray = Email.toCharArray();

        for(int i = 0; i < charArray.length; i++)
        {
            if(charArray[i] == '@')
            {
                return true;
            }
        }

        return false;
    }

}
