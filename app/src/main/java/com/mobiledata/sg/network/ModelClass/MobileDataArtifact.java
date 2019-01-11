package com.mobiledata.sg.network.ModelClass;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(indices = {@Index(value = "_id", unique = true)})
public class MobileDataArtifact implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
//    {
//        "volume_of_mobile_data": "0.000384",
//            "quarter": "2004-Q3",
//            "_id": 1
//    }

    @ColumnInfo(name = "_id")
    private String _id;
    @ColumnInfo(name = "quarter")
    private String quarter;
    @ColumnInfo(name = "volume_of_mobile_data")
    private String volume_of_mobile_data;


    /**
     * Setter and getter function
     * */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String getVolume_of_mobile_data() {
        return volume_of_mobile_data;
    }

    public void setVolume_of_mobile_data(String volume_of_mobile_data) {
        this.volume_of_mobile_data = volume_of_mobile_data;
    }
}
