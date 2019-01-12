package com.mobiledata.sg.network.RoomPersistDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mobiledata.sg.network.ModelClass.MobileDataArtifact;

import java.util.List;

@Dao
public interface DaoInterface {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MobileDataArtifact... mMarkToDownload);

    @Query("SELECT * FROM MobileDataArtifact  WHERE quarter=:quarter")
    List<MobileDataArtifact> getAllMobileData(String quarter);
}
