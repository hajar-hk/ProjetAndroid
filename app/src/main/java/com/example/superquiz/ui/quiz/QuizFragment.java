package com.example.superquiz.ui.quiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Arrays;
import java.util.List;

import com.example.superquiz.R;
import com.example.superquiz.data.Question;
import com.example.superquiz.databinding.FragmentQuizBinding;
import com.example.superquiz.injection.ViewModelFactory;
import com.example.superquiz.ui.welcome.WelcomeFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class QuizFragment extends Fragment {

    private FragmentQuizBinding binding;

    // variable de type quizviewmodel
    private QuizViewModel viewModel;

    public static QuizFragment newInstance() {
        QuizFragment fragment = new QuizFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // instance de la class quizviewmodel
        // créer le ViewModel  lors de la première création du fragment, ensuite de le lier à son cycle de vie via l’implémentation du ViewModelProvider  de Google
        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(QuizViewModel.class);;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentQuizBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    // dynamiser question et answer(1 2 3 4) pour afficher la question en cours accessible via l'état de type LiveData currentQuesion
    public void onViewCreated(View view,Bundle savedInstanceState){
        super.onViewCreated(view ,savedInstanceState);

        //pour écouter les modifications d’un LiveData  , il faut appeler la fonction observe  du LiveData  en question

          // DEMARER LE QUIZ:

        viewModel.startQuiz();// apll la meth startquiz de notre view model

          //OBSERVER LA QUESTION EN COURS
        viewModel.currentQuestion.observe(getViewLifecycleOwner(), new Observer<Question>() {
            @Override
            // onChanged sera apll lorqu'une new valeur sera émise dnas le LiveData (f class quizviewmodel)
            public void onChanged(Question question) {
                updateQuestion(question);//pour remplacer les textes des composants de l'interface via fct setText
            }
        });

          // EVENMENTS:


        // Récupérez la réponse de user
        // gerer les reponses de user aux question du quiz
        //recuperer l'action de clic de user grace a setOn...
        binding.answer1.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                updateAnswer(binding.answer1, 0);
            }
        });
        binding.answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAnswer(binding.answer2, 1);
            }
        });

        binding.answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAnswer(binding.answer3, 2);
            }
        });

        binding.answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAnswer(binding.answer4, 3);
            }
        });
        // gerer action de clic
        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isLastQuestion = viewModel.isLastQuestion.getValue();//  pour connaitre est ce que on est ds last question ou next question
                if(isLastQuestion != null && isLastQuestion){ // si on est f lastquestion faire:
                    // apll displaydialog pour afficher une dialogue de resultats(score final) à user
                    displayResultDialog();
                } else {//  sinon, passer question suivante
                    viewModel.nextQuestion(); // afficher nextquestion (appl nextquestion de notre viwmodel)
                    resetQuestion();// apll methode resetquestion pour activer answers et yrj3o les couleurs initiale  bch ybda user moraha ykhtare answer wybta ...
                }
            }
        });
        // observer état islastquestion
        viewModel.isLastQuestion.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLastQuestion) {
                if (isLastQuestion){
                    binding.next.setText("Finish");// remplacer next par finish si onest finde question
                } else {
                    binding.next.setText("Next");// sinon , next
                }
            }
        });

    }

    // la methode updateQuestion
    private void updateQuestion(Question question) {
        binding.question.setText(question.getQuestion());// setText pour remplacer question
        binding.answer1.setText(question.getChoiceList().get(0));// ... pour remplacer answer1
        binding.answer2.setText(question.getChoiceList().get(1));
        binding.answer3.setText(question.getChoiceList().get(2));
        binding.answer4.setText(question.getChoiceList().get(3));
        binding.next.setVisibility(View.INVISIBLE);
    }

    // la mthode updateAnswer
    private void updateAnswer(Button button, Integer index){
        showAnswerValidity(button, index); //vérifier si la réponse est correcte ou non
        enableAllAnswers(false);// controler etat des bttns
        binding.next.setVisibility(View.VISIBLE); //afficher next pour passer à la ques suivante
    }

    //vérifier si la réponse est correcte ou non:
    private void showAnswerValidity(Button button, Integer index){
        Boolean isValid = viewModel.isAnswerValid(index);// apll isAnswerValid de notre view model et l stock dans isValid
        if (isValid) { // si Answer est correcte:
            // changer couleur de btton en vert
            button.setBackgroundColor(Color.parseColor("#388e3c")); // dark green
            // afficher le composant de type validityText present dans notre layout avec goodanswer
            binding.validityText.setText("Good Answer ! 💪");
            button.announceForAccessibility("Good Answer !");// pour le cas des personnes aveugle
        } else { // si answer est false
            // changer couleur rouge
            button.setBackgroundColor(Color.parseColor("#E10102")); // Red
            // ..... avec badanswer
            binding.validityText.setText("Bad answer 😢");
            button.announceForAccessibility("Bad answer");
        }
        binding.validityText.setVisibility(View.VISIBLE);
    }

    // but d'activer ou de désactiver  les bttn de answers
    private void enableAllAnswers(Boolean enable){
        // cree une liste de button
        List<Button> allAnswers = Arrays.asList(binding.answer1, binding.answer2, binding.answer3, binding.answer4);
        // parcours de la liste
        allAnswers.forEach( answer -> {
            //activation/desactivation:
            //Pour chaque bouton, elle appelle setEnabled(enable),
            // ce qui active le bouton si enable est true et le désactive si enable est false.
            answer.setEnabled(enable);
        });
    }

    // la mthode qui gere situation si on a passer question suivante(réinitialiser etat de interface lorq on passe a suivante  pour preparer interface de question suivante)
    private void resetQuestion(){
        // creation de liste
        List<Button> allAnswers = Arrays.asList(binding.answer1, binding.answer2, binding.answer3, binding.answer4);
        // parcour de liste
        allAnswers.forEach( answer -> {
            //pour chaque bttn affecter cette couleur(color original)
            answer.setBackgroundColor(Color.parseColor("#6200EE"));
        });
        //masquer le texte validityText
        binding.validityText.setVisibility(View.INVISIBLE);
        // nous reactivons le clic sur ts bttns
        // etat de bttn : activer
        enableAllAnswers(true);
        // explication du principe: lorque user entre anl9aw tt les bttn diale answer activer (true) hit mazal mabta 3la tawa7da, et la couleur diale les bttns rahum aade f couleur move dialhum origine
    }

    // meth qui gere situation si on est f lastquestion
    private void displayResultDialog() {
        // creation d'un builder de dialg
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // recuperer le score
        Integer score = viewModel.score.getValue();// apll score de notre viewmodel

        // configu du dialg
        builder.setTitle("Finished !");// titre de dialog
        builder.setMessage("Your final score is "+ score); // message du dilog (la valeur de score)

        // ajouter nttn quit pour quitter quiz
        // losq user clic sur quit
        // onclicklistn  est declenché et faire apll method: gotowelcomefragment
        builder.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                goToWelcomeFragment();
            }
        });
        // cree l'objt alertdialog
        AlertDialog dialog = builder.create();
        // afficher ce alertdialog en appelant dialog.show()
        dialog.show();
    }
    // la meth qui sera apll si user clic sur Quit
    private void goToWelcomeFragment(){
        //Création d'une instance de WelcomeFragment
        WelcomeFragment welcomeFragment = WelcomeFragment.newInstance();

        // pour la gestion de fragments
        // on recuperer le gestionnaire de fragmt parent
        FragmentManager fragmentManager = getParentFragmentManager();

        // transaction de fragmnt
        //La transaction est engagée avec commit(), ce qui met à jour l'interface utilisateur pour afficher le nouveau fragment.
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.replace(R.id.container, welcomeFragment).commit();


        // explication du principe: lorque user clic sur quit user est redirigé vers un fragment d'accueil(welcomefragmnt)
    }
}


