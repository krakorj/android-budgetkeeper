package com.example.krakora.testapplication001.data;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(name="artists", database = DatabaseModule.class)
public class TableArtists extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    public long ArtistId;
    @Column
    public String Name;

    public TableArtists() {
        this.Name = ":*:)";
    }

    public static long getCount() {
        return new Select().from(TableArtists.class).count();
    }

}