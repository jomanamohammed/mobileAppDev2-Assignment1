package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.ActivityMainBinding;

/**
 * Host Activity for the onboarding flow.
 *
 * <p>It implements the listener interfaces declared inside the Fragments, so the
 * Fragments can send events/data back to the Activity without holding a direct
 * reference to it.</p>
 */
public class MainActivity extends AppCompatActivity
        implements Fragment1.OnFragment1Listener,
        Fragment2.OnFragment2Listener,
        Fragment3.OnFragment3Listener {

    private static final String STATE_NAME = "state_name";

    private ActivityMainBinding binding;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState != null) {
            userName = savedInstanceState.getString(STATE_NAME);
        }

        // Only add the first Fragment when the Activity is created for the first time.
        // On configuration changes the FragmentManager restores the current Fragment.
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Fragment1())
                    .commit();
        }
    }

    /* ---------------------------------------------------------------------
     * Fragment1 -> MainActivity
     * ------------------------------------------------------------------- */

    @Override
    public void onGetStartedClicked() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, Fragment2.newInstance())
                .addToBackStack(null)
                .commit();
    }

    /* ---------------------------------------------------------------------
     * Fragment2 -> MainActivity
     * ------------------------------------------------------------------- */

    @Override
    public void onFragment2Continue(String name) {
        // Data received from Fragment2 through the interface.
        userName = name;

        // Pass the name to Fragment3 using a Bundle (Fragment.setArguments).
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, Fragment3.newInstance(userName))
                .addToBackStack(null)
                .commit();
    }

    /* ---------------------------------------------------------------------
     * Fragment3 -> MainActivity
     * ------------------------------------------------------------------- */

    @Override
    public void onAgreementChanged(boolean isChecked) {
        // The Activity receives the checkbox event through the interface and
        // applies its effect to Fragment3's button.
        Fragment current = getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        if (current instanceof Fragment3) {
            ((Fragment3) current).updateFinishButton(isChecked);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_NAME, userName);
    }
}
