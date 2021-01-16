package com.mobileapps2.projectplanner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mobileapps2.projectplanner.Entities.Team;
import com.mobileapps2.projectplanner.R;

import java.util.ArrayList;

public class TeamListAdapter extends ArrayAdapter<Team> {

    private Context context;
    int resource;

    public TeamListAdapter(Context c, int resource, ArrayList<Team> teams) {
        super(c, resource, teams);
        this.context = c;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Get current Team
        Team team = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        //Get views to set
        ImageView image = convertView.findViewById(R.id.TeamLogo);
        TextView teamName = convertView.findViewById(R.id.teamName);

        //Set views
        // image.setImageResource(teams[position].image);
        teamName.setText(team.teamName);

        return convertView;
    }
}
