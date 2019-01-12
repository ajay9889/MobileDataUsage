package com.mobiledata.sg.network.RoomPersistDatabase;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

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
