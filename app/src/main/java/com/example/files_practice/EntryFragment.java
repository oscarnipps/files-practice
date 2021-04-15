package com.example.files_practice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.files_practice.databinding.FragmentEntryBinding;

import org.jetbrains.annotations.NotNull;

public class EntryFragment extends Fragment {

    private FragmentEntryBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireActivity()), R.layout.fragment_entry, container, false);

        setUpNavigation();



        return binding.getRoot();
    }

    private void setUpNavigation() {

        binding.imagesView.setOnClickListener(v -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.navigate_to_images);
        });


    }
}