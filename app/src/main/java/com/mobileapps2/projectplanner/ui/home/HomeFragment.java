package com.mobileapps2.projectplanner.ui.home;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.mobileapps2.projectplanner.R;
import com.mobileapps2.projectplanner.ui.boards.BoardActivity;
import com.mobileapps2.projectplanner.ui.teams.TeamActivity;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ImageButton navigateTeamsBtn;
    private ImageButton navigateBoardsBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        navigateBoardsBtn = (ImageButton) root.findViewById(R.id.NavigateBoardBtn);
        navigateTeamsBtn = (ImageButton) root.findViewById(R.id.NavigateTeamBtn);

        navigateBoardsBtn.setOnClickListener((v -> {
            Intent intent = new Intent(getActivity(),BoardActivity.class);
            startActivity(intent);
        }));

        navigateTeamsBtn.setOnClickListener((v -> {
            Intent intent = new Intent(getActivity(), TeamActivity.class);
            startActivity(intent);
        }));
        return root;
    }
}