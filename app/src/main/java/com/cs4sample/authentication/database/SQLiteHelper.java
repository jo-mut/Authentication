package com.cs4sample.authentication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(@Nullable Context context) {
        super(context, DatabaseManager.LOGIN_SAMPLE_DB,
                null, DatabaseManager.DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        DatabaseSchema.createDatabaseTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "DROP TABLE IF EXISTS " + DatabaseSchema.GENDER_TB;
        db.execSQL(dropTable);
    }

}
