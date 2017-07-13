package com.example.krakora.testapplication001.data;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

@Table(name="tracks", database = DatabaseModule.class)
public class TableTracks extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    public long TrackId;
    @Column
    public String Name;
    @Column
    public long AlbumId;
    @Column
    public long MediaTypeId;
    @Column
    public long GenreId;
    @Column
    public String Composer;
    @Column
    public long Milliseconds;
    @Column
    public long Bytes;
    @Column
    public double UnitPrice;

    public TableTracks() {
        this.Name = ":*:)";
        this.AlbumId = 0;
        this.MediaTypeId = 0;
        this.GenreId = 0;
        this.Composer = "God";
        this.Milliseconds = 0;
        this.Bytes = 0;
        this.UnitPrice = 1000000.0;
    }

    public static long getCount() {
        return new Select().from(TableTracks.class).count();
    }

}
