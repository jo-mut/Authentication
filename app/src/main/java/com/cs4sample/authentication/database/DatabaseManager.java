package com.cs4sample.authentication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.cs4sample.authentication.adapters.PlayersAdapter;
import com.cs4sample.authentication.models.Player;
import com.cs4sample.authentication.models.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    // Context Object
    private Context mContext;
    // The name of the database
    public static final String ASSETS_DATABASE_NAME = "login_sample.db";
    // The name of the table of the users with an account
    public static final String USERS_TABLE = "users_table";
    // The name of the players tables
    public static final String PLAYERS_TB = "players_tb";
    // The version of the database
    public static final int DATABASE_VERSION = 3;
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

        String drop = "DROP TABLE IF EXISTS " + DatabaseManager.USERS_TABLE;
        mSQLiteDatabase.execSQL(drop);

        String createDatabase = "CREATE TABLE " + DatabaseManager.USERS_TABLE +
                "(" + DatabaseManager.ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + DatabaseManager.ROW_NAME + " TEXT,"
                + DatabaseManager.ROW_PASSWORD + " TEXT" + ")";

        mSQLiteDatabase.execSQL(createDatabase);
        long result = mSQLiteDatabase.insert(DatabaseManager.USERS_TABLE, null,
                prepareContentValues(user));

        return result > 0;
    }

    public User getUser(String username, String password) {
        User user = new User();

        if (saveUser()) {
            Log.d("saved User:", "success");
            // get the user from the database by the id
            String getUserByName = "SELECT * FROM " + DatabaseManager.USERS_TABLE + " WHERE "
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

    public ArrayList<Player> getPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        this.mSQLiteDatabase = mSQLiteHelper.getReadableDatabase();
        Cursor cursor = mSQLiteDatabase.query(DatabaseManager.PLAYERS_TB, null,
                null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Player player = new Player();
                player.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Player.ROW_ID)));
                player.setAge(cursor.getString(cursor.getColumnIndexOrThrow(Player.ROW_AGE)));
                player.setName(cursor.getString(cursor.getColumnIndexOrThrow(Player.ROW_NAME)));
                player.setPosition(cursor.getString(cursor.getColumnIndexOrThrow(Player.ROW_POSITION)));
                players.add(player);


            }
            cursor.close();
        }


        return players;

    }

    public boolean updatePlayer(String name, Player player) {
        String updatePLayer = " UPDATE " + Player.PLAYER_TB + " SET "
                + Player.ROW_NAME + "=?," + Player.ROW_AGE + "=?,"
                + Player.ROW_POSITION + "=?," + Player.ROW_IMAGE + "=? " + "WHERE " + Player.ROW_NAME + "=?";
        mSQLiteDatabase = mSQLiteHelper.getWritableDatabase();

        SQLiteStatement s = mSQLiteDatabase.compileStatement(updatePLayer);
        s.bindString(1, player.getName());
        s.bindString(2,player.getAge());
        s.bindString(3, player.getPosition());
        if (player.getImage() != null) {
            s.bindBlob(4, player.getImage());
        }
        s.bindString(5, name);
        long result = s.executeUpdateDelete();

        return result > 0;
    }

}
