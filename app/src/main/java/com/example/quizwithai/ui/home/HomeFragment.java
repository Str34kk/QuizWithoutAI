package com.example.quizwithai.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.quizwithai.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Button[] optionButtons;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        TextView questionText = binding.textQuestion;
        optionButtons = new Button[4];
        optionButtons[0] = binding.optionButton1;
        optionButtons[1] = binding.optionButton2;
        optionButtons[2] = binding.optionButton3;
        optionButtons[3] = binding.optionButton4;

        homeViewModel.getQuestion().observe(getViewLifecycleOwner(), quizQuestion -> {
            questionText.setText(quizQuestion.getQuestion());
            for (int i = 0; i < 4; i++) {
                optionButtons[i].setText(quizQuestion.getOptions()[i]);
                int finalI = i;
                optionButtons[i].setOnClickListener(v -> {
                    homeViewModel.checkAnswer(finalI);
                    if (finalI == quizQuestion.getAnswer()) {
                        Toast.makeText(getContext(), "Correct!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Wrong answer, try again.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        final TextView scoreTextView = binding.scoreText;

        homeViewModel.getFinishEvent().observe(getViewLifecycleOwner(), isFinished -> {
            questionText.setVisibility(View.GONE);
            if (isFinished) {
                for (Button optionButton : optionButtons) {
                    optionButton.setVisibility(View.GONE);
                }

                scoreTextView.setVisibility(View.VISIBLE);
                scoreTextView.setText("Your score: " + homeViewModel.getScore());
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}