package com.mobiledata.sg.network.RoomPersistDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mobiledata.sg.network.ModelClass.MobileDataArtifact;

import java.util.List;

@Dao
public interface DaoInterface {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MobileDataArtifact... mMarkToDownload);

    @Query("SELECT * FROM MobileDataArtifact  WHERE quarter=:quarter")
    List<MobileDataArtifact> getAllMobileData(String quarter);
}
