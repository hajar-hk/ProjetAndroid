package com.example.superquiz.injection;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.superquiz.data.QuestionBank;
import com.example.superquiz.data.QuestionRepository;
import com.example.superquiz.ui.quiz.QuizViewModel;



import org.jetbrains.annotations.NotNull;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private final QuestionRepository questionRepository;
    private static ViewModelFactory factory;

    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory();
                }
            }
        }
        return factory;
    }
    // un constructeur contenant les objets dont nous avons besoin pour instancier correctement notre classe QuizViewModel
    private ViewModelFactory() {
        QuestionBank questionBank = QuestionBank.getInstance();
        this.questionRepository = new QuestionRepository(questionBank);
    }

    @Override
    @NotNull
    public <T extends ViewModel>  T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(QuizViewModel.class)) {
            return (T) new QuizViewModel(questionRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}