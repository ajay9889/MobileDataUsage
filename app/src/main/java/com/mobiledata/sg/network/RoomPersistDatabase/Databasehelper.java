package com.mobiledata.sg.network.RoomPersistDatabase;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
/**
 * Created by Ajay on 08/09/18.
 */
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
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            Databasehelper.class, DataBaseName)
                            .fallbackToDestructiveMigration()
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void clearAllTables() {

    }
}
