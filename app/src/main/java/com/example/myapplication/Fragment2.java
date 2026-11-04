package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.Fragment2Binding;

/**
 * Second onboarding step: collects the user's basic information and sends the
 * name to the Host Activity through {@link OnFragment2Listener}.
 */
public class Fragment2 extends Fragment {

    /**
     * Communication interface implemented by the Host Activity.
     */
    public interface OnFragment2Listener {
        void onFragment2Continue(String name);
    }

    private Fragment2Binding binding;
    private OnFragment2Listener listener;

    public static Fragment2 newInstance() {
        return new Fragment2();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragment2Listener) {
            listener = (OnFragment2Listener) context;
        } else {
            throw new IllegalStateException(
                    context + " must implement OnFragment2Listener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = Fragment2Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonContinue.setOnClickListener(v -> submitForm());
    }

    /**
     * Validates the form and sends the name to the Host Activity.
     */
    private void submitForm() {
        String name = binding.editTextName.getText().toString().trim();
        String email = binding.editTextEmail.getText().toString().trim();
        String age = binding.editTextAge.getText().toString().trim();

        if (name.isEmpty()) {
            binding.editTextName.setError(getString(R.string.error_name));
            binding.editTextName.requestFocus();
            return;
        }

        // Email is optional, but if the user enters one it must be valid.
        if (!email.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextEmail.setError(getString(R.string.error_email_invalid));
            binding.editTextEmail.requestFocus();
            return;
        }

        if (!age.isEmpty() && Integer.parseInt(age) > 120) {
            binding.editTextAge.setError(getString(R.string.error_age));
            binding.editTextAge.requestFocus();
            return;
        }

        // Gender, age and university are collected by the UI above. The
        // assignment requires sending the user's name to the Host Activity.
        if (listener != null) {
            listener.onFragment2Continue(name);
        }
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
