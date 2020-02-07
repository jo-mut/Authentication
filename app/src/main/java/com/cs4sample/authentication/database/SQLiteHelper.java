package com.cs4sample.authentication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(@Nullable Context context) {
        super(context, DatabaseManager.LOGIN_SAMPLE_DB,
                null, DatabaseManager.DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
//            DatabaseSchema.createDatabaseTables(db);
        String genderTable = "CREATE TABLE " + DatabaseSchema.GENDER_TB +
                "( " + DatabaseSchema.ROW_ID + " INTEGER PRIMARY KEY NOT NULL,"
                + DatabaseSchema.GENDER_CHARACTER + " TEXT,"
                + DatabaseSchema.GENDER_NAME + ")";
        db.execSQL(genderTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "DROP TABLE IF EXISTS " + DatabaseSchema.GENDER_TB;
        db.execSQL(dropTable);
    }
}
