package com.example.superquiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
}
