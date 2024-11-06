package com.example.superquiz.data;


import java.util.List;

// cette class va contenir:
   // la question
   // les quates reponses
   // la bonne reponse correspondante
public class Question {

    // decalaration des attributs (question, 4 reponses , bonne reponse)

    private final String question;
    private  final List<String> choiceList;
    private final  int answerIndex;

    // constructeur et les fcts getters et setters
    public Question(String question, List<String> choiceList, int answerIndex) {
        this.question = question;
        this.choiceList = choiceList;
        this.answerIndex = answerIndex;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getChoiceList() {
        return choiceList;
    }

    public int getAnswerIndex() {
        return answerIndex;
    }



}
