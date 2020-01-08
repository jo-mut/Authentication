package com.cs4sample.authentication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cs4sample.authentication.models.User;

public class DatabaseManager {
    // Context Object
    private Context mContext;
    // The name of the database
    public static final String DATABASE_NAME = "sample_login";
    // The database table name
    public static final String DATABASE_TABLE = "users_table";
    // The version of the database
    public static final int DATABASE_VERSION = 1;
    // The id of the user
    public static final String ROW_ID = "_id";
    // The username of the user
    public static final String ROW_NAME = "name";
    // The email of the user
    public static final String ROW_PASSWORD = "password";
    // dummy username
    public static final String DUMMY_NAME = "fits";
    //dummy password
    public static final String DUMMY_PASSWORD = "yoyo";

    // SQLite Helper object
    private SQLiteHelper mSQLiteHelper;
    // Database Object
    private SQLiteDatabase mSQLiteDatabase;

    public DatabaseManager(Context context) {
        this.mContext = context;
        this.mSQLiteHelper = new SQLiteHelper(mContext);

    }

    private ContentValues prepareContentValues(User user) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseManager.ROW_NAME, user.getUsername());
        cv.put(DatabaseManager.ROW_PASSWORD, user.getPassword());

        return cv;
    }

    public boolean saveUser() {
        User user = new User();
        user.setUsername(DatabaseManager.DUMMY_NAME);
        user.setPassword(DatabaseManager.DUMMY_PASSWORD);
        this.mSQLiteDatabase = mSQLiteHelper.getWritableDatabase();

        long result = mSQLiteDatabase.insert(DatabaseManager.DATABASE_TABLE, null,
                prepareContentValues(user));

        return result > 0;
    }

    public User getUser(String username, String password) {
        User user = new User();

        if (saveUser()) {
            Log.d("saved User:", "success");
            // get the user from the database by the id
            String getUserByName = "SELECT * FROM " + DatabaseManager.DATABASE_TABLE + " WHERE "
                    + DatabaseManager.ROW_NAME + " =?";
            this.mSQLiteDatabase = mSQLiteHelper.getReadableDatabase();
            Cursor cursor = mSQLiteDatabase.rawQuery(getUserByName, new  String[]{"fits"});
            if (cursor != null) {
                cursor.moveToFirst();
                user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseManager.ROW_ID)));
                user.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseManager.ROW_NAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseManager.ROW_PASSWORD)));
            }
     
        }else {
            Log.d("failed to save:", "failed");

        }

        return user;

    }

//    public void deleteUser() {
//        // get the user from the database by the id
//        String getUserByName = "DELETE * FROM " + DatabaseManager.DATABASE_TABLE + " WHERE "
//                + DatabaseManager.ROW_NAME + " = " + DatabaseManager.DUMMY_NAME;
//
//        Cursor cursor = mSQLiteDatabase.delete(getUserByName, null);
//
//    }
}
