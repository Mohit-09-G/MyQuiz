package com.example.myquizmy.Game;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myquizmy.QuizFragment;
import com.example.myquizmy.R;

public class ResultFragment extends Fragment {
    private TextView totalQuestionsTextView;
    private TextView correctAnswersTextView;
    private TextView wrongAnswersTextView;
    private TextView passStatusTextView;
    private TextView moneyWonTextView;
    private Button exitButton;

    public ResultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        // Initialize TextViews
        totalQuestionsTextView = view.findViewById(R.id.Total);
        correctAnswersTextView = view.findViewById(R.id.right);
        wrongAnswersTextView = view.findViewById(R.id.wrong);
        passStatusTextView = view.findViewById(R.id.resulttxt_txt1);
        moneyWonTextView = view.findViewById(R.id.resulttxt_txt9);  // Assuming you have a TextView for money won
        exitButton = view.findViewById(R.id.exit);

        // Retrieve data from the Bundle
        Bundle args = getArguments();
        if (args != null) {
            int totalQuestions = args.getInt("total_questions");
            double correctAnswers = args.getDouble("correct_answers");
            int wrongAnswers = args.getInt("wrong_answers");
            String passStatus = args.getString("pass_status");
            double moneyWon = args.getDouble("money_won");

            // Set data to TextViews
            totalQuestionsTextView.setText(String.valueOf(totalQuestions));
            correctAnswersTextView.setText(String.valueOf(correctAnswers));
            wrongAnswersTextView.setText(String.valueOf(wrongAnswers));
            passStatusTextView.setText(passStatus);
            moneyWonTextView.setText(String.format("₹%.2f", moneyWon));
        }

        exitButton.setOnClickListener(v -> {
            if (getActivity() != null) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                while (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStackImmediate();
                }

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, new QuizFragment());
                transaction.addToBackStack(null);
                transaction.commit();


                getActivity().finish();
            }
        });

        return view;
    }
}
