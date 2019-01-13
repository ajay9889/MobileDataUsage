package com.mobiledata.sg.network.RoomPersistDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import com.mobiledata.sg.network.ModelClass.MobileData;

/*
* Handling the data from local database to cach show the result also in absense of network connection
* **/
@Dao
public interface DaoInterface {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MobileData... mMarkToDownload);

    String query= "select SUM(volume_of_mobile_data) , MIN(volume_of_mobile_data), year , quarter from MobileData WHERE year>2007 group by year";
    @Query(query)  //
    Cursor getAllMobileDatas();

    @Query("DELETE FROM MobileData")
    void deleteTable();
}
