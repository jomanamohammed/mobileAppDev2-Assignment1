package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.Fragment3Binding;

/**
 * Final onboarding step: shows the user's name and asks for confirmation.
 *
 * <p>The checkbox state is sent to the Host Activity through
 * {@link OnFragment3Listener}; the Activity then applies the state to the
 * Continue/Finish button.</p>
 */
public class Fragment3 extends Fragment {

    /**
     * Communication interface implemented by the Host Activity.
     */
    public interface OnFragment3Listener {
        void onAgreementChanged(boolean isChecked);
    }

    private static final String ARG_NAME = "arg_name";

    private Fragment3Binding binding;
    private OnFragment3Listener listener;
    private String name = "";

    /**
     * Use this factory method to create a new instance of Fragment3 using the
     * received name, which is passed with a Bundle.
     */
    public static Fragment3 newInstance(String name) {
        Fragment3 fragment = new Fragment3();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_NAME, "");
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragment3Listener) {
            listener = (OnFragment3Listener) context;
        } else {
            throw new IllegalStateException(
                    context + " must implement OnFragment3Listener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = Fragment3Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Display the name received from the Host Activity.
        binding.textViewName.setText(getString(R.string.greeting_name, name));

        // The button starts disabled with the text "Continue".
        binding.buttonFinish.setEnabled(false);
        binding.buttonFinish.setText(R.string.continue_label);

        // Send every checkbox change to the Host Activity via the interface.
        binding.checkBoxAgree.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (listener != null) {
                listener.onAgreementChanged(isChecked);
            }
        });

        binding.buttonFinish.setOnClickListener(v ->
                Toast.makeText(requireContext(),
                        getString(R.string.onboarding_complete, name),
                        Toast.LENGTH_LONG).show());
    }

    /**
     * Called by the Host Activity from the interface callback to update the
     * button's enabled state and text.
     */
    public void updateFinishButton(boolean isChecked) {
        if (binding == null) {
            return;
        }
        binding.buttonFinish.setEnabled(isChecked);
        binding.buttonFinish.setText(
                isChecked ? R.string.finish_label : R.string.continue_label);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        // Keep the button in sync after a configuration change or when the
        // Fragment's view state is restored.
        if (binding != null) {
            updateFinishButton(binding.checkBoxAgree.isChecked());
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
