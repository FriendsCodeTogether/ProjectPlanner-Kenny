package com.mobileapps2.projectplanner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mobileapps2.projectplanner.R;
import com.mobileapps2.projectplanner.data.Entities.Board;
import com.mobileapps2.projectplanner.data.Entities.Task;

import java.util.ArrayList;

public class TaskListAdapter extends ArrayAdapter<Task> {
    private Context context;
    int resource;

    public TaskListAdapter(Context c, int resource, ArrayList<Task> tasks) {
        super(c, resource,tasks);
        this.context = c;
        this.resource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Get current Team
        Task task = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        //Get views to set
        TextView taskName = convertView.findViewById(R.id.taskName);
        ProgressBar taskProgress = convertView.findViewById(R.id.TaskProgressBar);
        TextView taskStatus = convertView.findViewById(R.id.taskStatus);

        //Set views
        taskName.setText(task.taskName);
        taskProgress.setMax(100);
        taskProgress.setMin(0);
        taskProgress.setProgress(task.progress);
        taskStatus.setText(task.status);

        return convertView;
    }
}
