package com.example.quizmeapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizmeapp.Activity.model.QuestionModel;
import com.example.quizmeapp.R;
import com.example.quizmeapp.databinding.ActivityQuizBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    ActivityQuizBinding binding;

    ArrayList<QuestionModel> questionArray;
    int index = 0;
    QuestionModel questionModel;
    CountDownTimer timer;
    FirebaseFirestore database;
    int correctAnswer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        questionArray = new ArrayList<>();
        database = FirebaseFirestore.getInstance();


/////////////////////////smaple test/////////////////////////
//        question.add(new QuestionModel("What is your name", "gulsghab", "kuldeep", "neetu", "pinki", "kuldeep"));
//        question.add(new QuestionModel("What is earth name", "gulsghab", "kuldeep", "neetu", "pinki", "pinki"));
/////////////////////////smaple test/////////////////////////
        final String catId = getIntent().getStringExtra("catId");

        Random random = new Random();
        final int rand = random.nextInt(12);
        database.collection("categories")
                .document(catId)
                .collection("questions")
                .whereGreaterThanOrEqualTo("index", rand)
                .orderBy("index")
                .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.getDocuments().size() < 5) {
                    database.collection("categories")
                            .document(catId)
                            .collection("questions")
                            .whereLessThanOrEqualTo("index", rand)
                            .orderBy("index")
                            .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                                QuestionModel questionModeforConvert = snapshot.toObject(QuestionModel.class);
                                questionArray.add(questionModeforConvert);
                            }
                            setNextQuestion();
                        }
                    });
                } else {
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                        QuestionModel questionModeforConvert = snapshot.toObject(QuestionModel.class);
                        questionArray.add(questionModeforConvert);
                    }
                    setNextQuestion();
                }
            }
        });
        resetTimer();
    }

    void resetTimer() {
        timer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.timer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {

            }
        };
    }

    void setNextQuestion() {
        if (timer != null)
            timer.cancel();
        resetTimer();
        timer.start();
        if (index < questionArray.size()) {
            binding.quizCounter.setText(String.format("%d/%d", (index + 1), questionArray.size()));
            questionModel = questionArray.get(index);
            binding.question.setText(questionModel.getQuestion());
            binding.option1.setText(questionModel.getOption1());
            binding.option2.setText(questionModel.getOption2());
            binding.option3.setText(questionModel.getOption3());
            binding.option4.setText(questionModel.getOption4());
        }
    }


    void showAnswer() {
        if (questionModel.getAnswer().equals(binding.option1.getText().toString()))
            binding.option1.setBackground(getResources().getDrawable(R.drawable.option_right));
        else if (questionModel.getAnswer().equals(binding.option2.getText().toString()))
            binding.option2.setBackground(getResources().getDrawable(R.drawable.option_right));
        else if (questionModel.getAnswer().equals(binding.option3.getText().toString()))
            binding.option3.setBackground(getResources().getDrawable(R.drawable.option_right));
        else if (questionModel.getAnswer().equals(binding.option4.getText().toString()))
            binding.option4.setBackground(getResources().getDrawable(R.drawable.option_right));

    }

    void checkAnswer(TextView textView) {
        String selectAnswer = textView.getText().toString();
        if (selectAnswer.equals(questionModel.getAnswer())) {
            correctAnswer++;
            textView.setBackground(getResources().getDrawable(R.drawable.option_right));
        } else {
            showAnswer();
            textView.setBackground(getResources().getDrawable(R.drawable.option_wrong));
        }
    }


    void resetAllOption() {
        binding.option1.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option2.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option3.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option4.setBackground(getResources().getDrawable(R.drawable.option_unselected));
    }

    public void onClick(View view) {
        if (timer != null)
            timer.cancel();
        switch (view.getId()) {
            case R.id.option_1:
            case R.id.option_2:
            case R.id.option_3:
            case R.id.option_4:
                TextView selected = (TextView) view;
                checkAnswer(selected);
                Toast.makeText(this, "option click", Toast.LENGTH_SHORT).show();
                break;

            case R.id.next_button:
                if (index <= questionArray.size()) {
                    index++;
                    resetAllOption();
                    setNextQuestion();
                } else {
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);

                    intent.putExtra("correct", correctAnswer);
                    intent.putExtra("total",questionArray.size() );
                    startActivity(intent);
                    Toast.makeText(this, "Quiz Finished", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}