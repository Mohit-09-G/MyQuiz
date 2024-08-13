package com.example.myquizmy.Game;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myquizmy.Database.TestDatabase;
import com.example.myquizmy.R;
import com.example.myquizmy.SessionManager;

public class GameFragment extends Fragment implements View.OnClickListener {
    TextView question;
    TextView opt1, opt2, opt3, opt4;
    TextView TotalquestionTextView;
    TextView next;
    Button Submit;

    private TestDatabase testDatabase;
    private SessionManager sessionManager;

    private static final String KEY_USER_PHONE = "userPhone";
    int Totalquestion = QuestionAns.questions.length;
    int CurrentQuestionIndex = 0;
    double Score = 0.0;
    String selectedAnswer = "";
    String passStatus = "";
    public String user_phone;

    public GameFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        testDatabase = new TestDatabase(getContext());
        sessionManager = new SessionManager(getActivity());
        user_phone = sessionManager.getUserDetails().get(KEY_USER_PHONE);

        TotalquestionTextView = view.findViewById(R.id.Total_Question);
        question = view.findViewById(R.id.Question);
        opt1 = view.findViewById(R.id.Option1);
        opt2 = view.findViewById(R.id.Option2);
        opt3 = view.findViewById(R.id.Option3);
        opt4 = view.findViewById(R.id.Option4);
        next = view.findViewById(R.id.next_que);
        Submit = view.findViewById(R.id.Submit_ans);

        TotalquestionTextView.setText("Total Questions: " + Totalquestion);
        loadNewQuestion();

        opt1.setOnClickListener(this);
        opt2.setOnClickListener(this);
        opt3.setOnClickListener(this);
        opt4.setOnClickListener(this);
        next.setOnClickListener(this);
        Submit.setOnClickListener(this);

        // Make the Submit button invisible initially
        Submit.setVisibility(View.GONE);

        return view;
    }

    private void loadNewQuestion() {
        if (CurrentQuestionIndex == Totalquestion) {
            finishQuiz();
            return;
        }
        resetOptionColors();
        question.setText(QuestionAns.questions[CurrentQuestionIndex]);
        opt1.setText(QuestionAns.options[CurrentQuestionIndex][0]);
        opt2.setText(QuestionAns.options[CurrentQuestionIndex][1]);
        opt3.setText(QuestionAns.options[CurrentQuestionIndex][2]);
        opt4.setText(QuestionAns.options[CurrentQuestionIndex][3]);

        selectedAnswer = "";
    }

    private void resetOptionColors() {
        opt1.setBackgroundColor(Color.TRANSPARENT);
        opt2.setBackgroundColor(Color.TRANSPARENT);
        opt3.setBackgroundColor(Color.TRANSPARENT);
        opt4.setBackgroundColor(Color.TRANSPARENT);
    }

    private void finishQuiz() {
        // Calculate money won based on the number of correct answers
        double moneyWon;
        Log.d("huigtyu", "finishQuiz: "+Score);
        if (Score == 10) { // All questions are correct
            moneyWon = 8.0;
        } else if (Score >= 8) { // 8 or more questions are correct
            moneyWon = 5.0;
        } else {
            moneyWon = 0.0;
        }


        double moneySpent = 10.0;


        testDatabase.addMoneyToBankBalance(user_phone, moneyWon );

        int wrongAnswers = Totalquestion - (int)Score;

        Bundle bundle = new Bundle();
        bundle.putInt("total_questions", Totalquestion);
        bundle.putDouble("correct_answers", Score);
        bundle.putInt("wrong_answers", wrongAnswers);
        bundle.putString("pass_status", passStatus);
        bundle.putDouble("money_won", moneyWon);

        saveGameDetails((int)Score, moneyWon);

        ResultFragment resultFragment = new ResultFragment();
        resultFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, resultFragment)
                .addToBackStack(null)
                .commit();
    }
    private void saveGameDetails(int correctAnswers, double moneyWon) {
        String gameName = "Quiz Game";
        double moneySpent = 10.0;
        String date = java.text.DateFormat.getDateTimeInstance().format(new java.util.Date());
        String ids = "UniqueGameId";
        boolean isInserted = testDatabase.insertGameDetails(user_phone, gameName, moneySpent, moneyWon,date, ids);
        if (!isInserted) {

        }
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.next_que) {
            if (!selectedAnswer.isEmpty()) {

                CurrentQuestionIndex++;
                if (selectedAnswer.equals(QuestionAns.answers[CurrentQuestionIndex - 1])) {
                    Score += 1;
                }
                loadNewQuestion();

                if (CurrentQuestionIndex == Totalquestion - 1) {
                    Submit.setVisibility(View.VISIBLE);
                    next.setVisibility(View.GONE);
                }
            }
        } else if (view.getId() == R.id.Submit_ans) {
            finishQuiz();
        } else {
            TextView clickedOption = (TextView) view;
            resetOptionColors();
            selectedAnswer = clickedOption.getText().toString();
            clickedOption.setBackgroundColor(Color.GREEN);
        }
    }
}
