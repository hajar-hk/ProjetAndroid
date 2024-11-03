package com.example.superquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.superquiz.databinding.FragmentWelcomeBinding;

public class WelcomeFragment extends Fragment {
    public static WelcomeFragment newInstance() {
        WelcomeFragment fragment = new WelcomeFragment();
        return fragment;


    }

    // cree une variable globale de type FragmentWelcomeBinding:

    private FragmentWelcomeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // remplacer le contenu de cette fct par ceci, pour indiquer au systeme qu'il doit mainteneat passer pas la classe de liasion
        // ceci (return inflater.inflate(R.layout.fragment_welcome, container, false);) est remplacé par:

        binding = FragmentWelcomeBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }
    //utiliser onViewCreated pour dire que je vais utiliser classe de liaison pour lire le code XML au code java
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        // pour acceder aux elemnts d'interface dans le code java, il suffit de saisir binding.nomelemnt(notre id)
        binding.playButton.setEnabled(false);


        // verifier la saisie de user
        binding.usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {// la methide qui nous intersse

                // declarer une variable isEmpty est verifier le texte saisie par user(s)  est ce qu vide par metho isEmpty()
                boolean isEmpty = s.toString().isEmpty();
                // utiliser setter : setEnable et lui dire actuver le bouton si saisie n'est pas vide
                binding.playButton.setEnabled(!isEmpty);
            }
        });

        // detecter clic sur let's play
        binding.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // naviger vers framgmt de quiz

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                QuizFragment quizFragment = QuizFragment.newInstance();
                fragmentTransaction.replace(R.id.container, quizFragment);
                fragmentTransaction.commit();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        binding.playButton.setEnabled(false);  // désactiver le bouton de l'interface
    }
}
