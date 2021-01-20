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

import com.mobileapps2.projectplanner.data.Entities.Board;
import com.mobileapps2.projectplanner.R;

import java.util.ArrayList;

public class BoardListAdapter  extends ArrayAdapter<Board> {
    private Context context;
    int resource;

    public BoardListAdapter(Context c, int resource, ArrayList<Board> boards) {
        super(c, resource, boards);
        this.context = c;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Get current Team
        Board board = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        //Get views to set
        ImageView image = convertView.findViewById(R.id.BoardLogo);
        TextView boardName = convertView.findViewById(R.id.boardName);
        TextView boardDescription = convertView.findViewById(R.id.boardDescription);

        //Set views
        // image.setImageResource(teams[position].image);
        boardName.setText(board.boardName);
        boardDescription.setText(board.boardDescription);

        return convertView;
    }
}
