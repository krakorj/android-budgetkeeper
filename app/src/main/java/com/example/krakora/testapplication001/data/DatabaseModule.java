package com.example.krakora.testapplication001.data;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = DatabaseModule.NAME, version = DatabaseModule.VERSION)
public class DatabaseModule {
    public static final String NAME = "database";
    public static final int VERSION = 1;
}
