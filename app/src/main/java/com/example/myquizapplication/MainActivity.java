package com.example.myquizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansA, ansB, ansC;
    Button submitBtn;

    int score = 0; // total score
    int currentQuestionIndex = 0;
    int ctr = 0;
    String msg = "The Senior’s AD8 range fall to 2 or Higher.\n" +
            "\n" +
            "We regret that we are unable to process your registration. The application is designed for older adults with mild cognitive impairment. It is recommended to consult a primary care physician if scoring results indicates potential dementia. \n" +
            "\n" +
            "PLEASE NOTE: \n" +
            "The AD8 diagnostic test cannot diagnose dementing disorders. Nevertheless, the test effectively detects the onset of many common dementias at an early stage of the disease.\n" +
            "(Validation of AD8-Philippines (AD8-P): A Brief Informant-Based Questionnaire for Dementia Screening in the Philippines)";
    int totalQuestion = 7; // number of questions to be generated in front end


    String selectedAnswer = ""; // user selected answer

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // id reference for the views
        totalQuestionsTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        submitBtn = findViewById(R.id.submit_btn);



        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        submitBtn.setOnClickListener(this);


        loadNewQuestion();

    }

    @Override
    public void onClick(View view) {

        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);


        Button clickedButton = (Button) view;
        if(clickedButton.getId() == R.id.submit_btn){
            ++ctr; // increase question counter
            Log.d("currentQuestionIndex inside onClick()", String.valueOf(currentQuestionIndex));

            if(selectedAnswer.equals(QuestionAnswer.correctAnswers[currentQuestionIndex])){
                score++;
            }
            currentQuestionIndex++;
            loadNewQuestion();


        }else{
            //choices button clicked
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.MAGENTA);
        }

    }
    void loadNewQuestion(){

        Log.d("COUNTER", String.valueOf(ctr)); // print current question counter


        if(ctr == totalQuestion){
            finishQuiz();
            return;
        }

        Log.d("currentQuestionIndex inside loadNewQuestion()", String.valueOf(currentQuestionIndex));

        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
        ansA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        ansB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
        ansC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
    }
    void finishQuiz(){
        if(score <= 1){
            new AlertDialog.Builder(this)
                    .setTitle("Congratulations!")
                    .setMessage("You have passed the assessment. You can now register to our application.\"")
                    .setPositiveButton("Register",(dialogInterface, i) -> register())
                    .setCancelable(false)
                    .show();
        }else{
            new AlertDialog.Builder(this)
                    .setTitle("The AD8 score equates to the following: " +
                            "\n 0 - 1:\tNormal/Mild Cognition Impairment \n2 or Higher: Potential Dementia")
                    .setMessage(msg)
                    .setPositiveButton("I understand", (dialogInterface, i) -> exit())
                    .setCancelable(true)
                    .show();
        }

    }
    void register(){
        // go to registration page
        Toast.makeText(this, "Register",
                Toast.LENGTH_LONG).show();
    }
    void exit(){
        // exit the app
        Toast.makeText(this, "Exit",
                Toast.LENGTH_LONG).show();
    }

}
