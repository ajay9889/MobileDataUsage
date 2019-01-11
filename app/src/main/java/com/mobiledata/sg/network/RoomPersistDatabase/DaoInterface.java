package com.mobiledata.sg.network.RoomPersistDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import com.mobiledata.sg.network.ModelClass.MobileDataArtifact;

@Dao
public interface DaoInterface {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MobileDataArtifact... mMarkToDownload);
}
