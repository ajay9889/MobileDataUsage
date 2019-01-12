package com.mobiledata.sg.network.RoomPersistDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.mobiledata.sg.network.ModelClass.MobileDataArtifact;

@Dao
public interface DaoInterface {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MobileDataArtifact... mMarkToDownload);
}
