package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.Fragment1Binding;

/**
 * First onboarding step: a welcome screen.
 */
public class Fragment1 extends Fragment {

    /**
     * Communication interface implemented by the Host Activity.
     */
    public interface OnFragment1Listener {
        void onGetStartedClicked();
    }

    private Fragment1Binding binding;
    private OnFragment1Listener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragment1Listener) {
            listener = (OnFragment1Listener) context;
        } else {
            throw new IllegalStateException(
                    context + " must implement OnFragment1Listener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = Fragment1Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonGetStarted.setOnClickListener(v -> {
            if (listener != null) {
                listener.onGetStartedClicked();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
