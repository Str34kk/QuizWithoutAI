package com.example.quizwithai.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quizwithai.models.QuizQuestion;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<Boolean> finishEvent = new MutableLiveData<>();
    private MutableLiveData<QuizQuestion> questionLiveData;
    private List<QuizQuestion> quizQuestions;
    private int currentQuestionIndex = 0;
    private int score = 0;

    public HomeViewModel() {
        questionLiveData = new MutableLiveData<>();

        // Initialize the list of questions
        quizQuestions = new ArrayList<>();

        // Add questions to the list
        quizQuestions.add(new QuizQuestion(
                "What is the capital of France?",
                new String[]{"Paris", "Berlin", "Rome", "Madrid"},
                0));

        quizQuestions.add(new QuizQuestion(
                "What is the capital of Germany?",
                new String[]{"Paris", "Berlin", "Rome", "Madrid"},
                1));

        quizQuestions.add(new QuizQuestion(
                "What is the capital of Italy?",
                new String[]{"Paris", "Berlin", "Rome", "Madrid"},
                2));

        quizQuestions.add(new QuizQuestion(
                "What is the capital of Spain?",
                new String[]{"Paris", "Berlin", "Rome", "Madrid"},
                3));

        loadNextQuestion();
    }

    public LiveData<QuizQuestion> getQuestion() {
        return questionLiveData;
    }

    public void checkAnswer(int userAnswerIndex) {
        QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);
        if (currentQuestion.getAnswer() == userAnswerIndex) {
            score++;
        }
        currentQuestionIndex++;
        if (currentQuestionIndex < quizQuestions.size()) {
            loadNextQuestion();
        } else {
            finishEvent.setValue(true);
        }
    }

    public int getScore() {
        return score;
    }

    public void loadNextQuestion() {
        questionLiveData.setValue(quizQuestions.get(currentQuestionIndex));
    }
    public LiveData<Boolean> getFinishEvent() {
        return finishEvent;
    }
}