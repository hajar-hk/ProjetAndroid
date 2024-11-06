package com.example.superquiz.ui.quiz;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.superquiz.data.Question;
import com.example.superquiz.data.QuestionRepository;

import java.util.List;

public class QuizViewModel  extends ViewModel {

    private final QuestionRepository questionRepository;
    private List<Question> questions;
    private  Integer currentQuestionsIndex = 0;

    public QuizViewModel(QuestionRepository questionRepository){
        this.questionRepository = questionRepository;
    }

    // initaliser les trois etats:

    // etat : la question en cours
    MutableLiveData<Question> currentQuestion = new MutableLiveData<Question>();

    // etat: un bool si user est arrivé à la dernière questiion , initialiser a faux
    MutableLiveData<Boolean> isLastquestion = new MutableLiveData<Boolean>(false);

    // etat : score initialisé a 0
    MutableLiveData<Integer> score = new MutableLiveData<Integer>(0);

    // gestion des evnmts

    // la fonction startQuiz
    public  void  startQuiz(){
        // recuperer les question
        questions = questionRepository.getQuestion();
        //poster 1 question dans currentqestion
        currentQuestion.postValue(questions.get(0));
    }
     // la fonction isAnswerValid
    public Boolean isAnswerValid(Integer answerIndex ){
        // recuperer la question en cours
        Question question = currentQuestion.getValue();
        //Vérifie si la question n'est pas nulle et si l'index de la réponse fournie correspond à l'index de la bonne réponse.
        boolean isValid = question != null && question.getAnswerIndex() == answerIndex;
        Integer currentScore = score.getValue();
        if(currentScore != null && isValid){
            score.setValue((currentScore +1));
        }
        return isValid;
    }

    // la fonction NextQuestion
    public void NextQuestion(){
        //l’index de la question en cour: nextIndex
        Integer nextIndex = currentQuestionsIndex +1;
        //Vérifie si nextIndex dépasse la taille de la liste de questions
        if(nextIndex >= questions.size()){
            return; // rien faire
        }
        //Si nextIndex est le dernier index
        else if (nextIndex == questions.size() -1){
            isLastquestion.postValue(true);
        }
        currentQuestionsIndex = nextIndex;
        currentQuestion.postValue(questions.get(currentQuestionsIndex));
    }

}
