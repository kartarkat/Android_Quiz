package com.kartarkat.androidquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity  {


    //true false buttons
    private Button mTrueButton;
    private Button mFalseButton;

    //next prev buttons
    private Button mNextButton;
    private Button mPrevButton;

    //one button for one question
    private ArrayList<Integer> mQuestionAsked = new ArrayList<Integer>(10);


    // updating score
    private int mTrueAnswer = 0;

    private TextView mQuestionTextView;
    private Question[] mQuestionBank = new Question[] {

            new Question(R.string.question_1, false),
            new Question(R.string.question_2, true),
            new Question(R.string.question_3, true),
            new Question(R.string.question_4, false),
            new Question(R.string.question_5, true),
            new Question(R.string.question_6, true),
            new Question(R.string.question_7, false),
            new Question(R.string.question_8, true),
            new Question(R.string.question_9, true),
            new Question(R.string.question_10, false),


    };
    private int mCurrentIndex = 0;


    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");  //log id
        setContentView(R.layout.activity_quiz);

        //updating log files
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            //one button for one question
            mQuestionAsked = savedInstanceState.getIntegerArrayList("myArray");
        }


        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

//true button
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });


//false button
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });


//next button
        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
        updateQuestion();


//prev button
        mPrevButton = (Button) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                if(mCurrentIndex==-1)
                    mCurrentIndex=0;
                else
                updateQuestion();
            }
        });
        updateQuestion();


    }//closing oncreate



    //updating question
    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
       //one button for one question
        mFalseButton.setEnabled(true);
        mTrueButton.setEnabled(true);

        for (Integer i = 0; i < mQuestionAsked.size(); i++) {
            if (mCurrentIndex == mQuestionAsked.get(i)) {
                mFalseButton.setEnabled(false);
                mTrueButton.setEnabled(false);
            }
        }
        // calculating score
        int resultResId = (mTrueAnswer*100) / 10;
        if (mQuestionAsked.size() > 9) {
            Toast.makeText(this, "QUIZ ended\n"+Integer.toString(resultResId)+"% correct answers", Toast.LENGTH_LONG)
                    .show();
        }


    }



    //checking aanswer
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        //one button for one question
        mQuestionAsked.add(mCurrentIndex);
        mFalseButton.setEnabled(false);
        mTrueButton.setEnabled(false);

        int messageResId = 0;
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            // updating score
            mTrueAnswer = mTrueAnswer + 1;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show();
    }





    //log files
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    //sharing log files
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        // challenge 3.1
        savedInstanceState.putIntegerArrayList("myArray", mQuestionAsked);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

}
