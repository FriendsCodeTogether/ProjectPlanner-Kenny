package com.mobileapps2.projectplanner;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mobileapps2.projectplanner.data.DAOs.*;
import com.mobileapps2.projectplanner.Entities.*;

@Database(entities = {Board.class, Task.class, Team.class, User.class},version = 3,exportSchema = false)
public abstract class ProjectPlannerDb extends RoomDatabase{
    private static ProjectPlannerDb minstance;
    private static final String DB_NAME = "projectPlanner.db";

    public abstract BoardDAO getBoardDAO();
    public abstract TaskDAO getTaskDAO();
    public abstract TeamDAO getTeamDAO();
    public abstract UserDAO getUserDAO();

    public static synchronized ProjectPlannerDb getInstance(Context ctx) {
        if (minstance == null){
            minstance = Room.databaseBuilder(ctx.getApplicationContext(), ProjectPlannerDb.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return minstance;
    }
}
