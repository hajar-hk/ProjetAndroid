package com.example.superquiz.data;

import java.util.List;

public class QuestionRepository {
     private final QuestionBank questionBank;

    public QuestionRepository(QuestionBank questionBank) {
        this.questionBank = questionBank;
    }

    public List<Question> getQuestion(){
        // retourne la liste des questions issues de la banque de questions.
        // nous avons acces les question(les donnes) grace a get question
        return  questionBank.getQuestions();
        // getquestion() est prête a être appelée depuis le viewmodel
    }
}
