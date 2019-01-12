package com.mobiledata.sg.network.RoomPersistDatabase;

import androidx.room.Database;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.mobiledata.sg.network.ModelClass.MobileData;

import androidx.annotation.NonNull;
/**
 * Created by Ajay on 08/09/18.
 */
@Database(entities = { MobileData.class }, version = 1, exportSchema = false)
public abstract class Databasehelper extends RoomDatabase {
    public abstract DaoInterface mobileDataDao();
    private static Databasehelper INSTANCE;
    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    public SupportSQLiteOpenHelper getOpenHelper() {
        return super.getOpenHelper();
    }

    //   get Database instance to perform the Query functions
    public static Databasehelper getDatabase(final Context context , String DataBaseName) {
        if (INSTANCE == null) {
            synchronized (Databasehelper.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        Databasehelper.class, DataBaseName)
                        .fallbackToDestructiveMigration().allowMainThreadQueries()
                        .build();
            }
        }
        return INSTANCE;
    }

    @Override
    public void clearAllTables() {

    }
}
