package com.cs4sample.authentication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.cs4sample.authentication.models.Player;
import com.cs4sample.authentication.models.User;

import java.util.ArrayList;

public class DatabaseManager {
    // Context Object
    private Context mContext;
    // The name of the database
    public static final String LOGIN_SAMPLE_DB = "login_sample.db";
    // The name of the table of the users with an account
    public static final String USERS_TABLE = "users_table";
    // The name of the players tables
    public static final String PLAYERS_TB = "players_tb";
    // The version of the database
    public static final int DATABASE_VERSION = 3;
    // The id of the user
    public static final String ROW_ID = "_id";
    // The username of the user
    public static final String ROW_USERNAME = "name";
    // The email of the user
    public static final String ROW_PASSWORD = "password";
    // The name of the language column
    public static final String ROW_LANGUAGE = "language";
    // The name of the account name column
    public static final String ROW_ACCOUNT_NAME = "account_name";
    // The name of the branch column
    public static final String ROW_BRANCH = "branch";
    // dummy username
    public static final String DUMMY_NAME = "fits";
    //dummy password
    public static final String DUMMY_PASSWORD = "yoyo";
    // content values
    private ContentValues mContentValues;
    // SQLite Helper object
    private SQLiteHelper mSQLiteHelper;
    // Database Object
    private SQLiteDatabase mSQLiteDatabase;

    public DatabaseManager(Context context) {
        this.mContext = context;
        this.mSQLiteHelper = new SQLiteHelper(mContext);

    }

    private ContentValues prepareContentValues(User user) {
        mContentValues = new ContentValues();
        mContentValues.put(DatabaseManager.ROW_USERNAME, user.getUsername());
        mContentValues.put(DatabaseManager.ROW_PASSWORD, user.getPassword());

        return mContentValues;
    }

    public boolean savePlayer(Player player) {
        mContentValues = new ContentValues();
        mContentValues.put(Player.ROW_IMAGE, player.getImage());

        if (!TextUtils.isEmpty(player.getName())){
            mContentValues.put(Player.ROW_NAME,player.getName());
        }

        if (!TextUtils.isEmpty(player.getAge())){
            mContentValues.put(Player.ROW_AGE, player.getAge());
        }

        if (!TextUtils.isEmpty(player.getPosition())){
            mContentValues.put(Player.ROW_POSITION, player.getPosition());
        }

        if (player.getImage() != null) {
            String image = Base64.encodeToString(player.getImage().getBytes(), Base64.DEFAULT);
            mContentValues.put(Player.ROW_IMAGE, image);
        }


        mSQLiteDatabase = mSQLiteHelper.getWritableDatabase();
        long result = mSQLiteDatabase.insert(Player.PLAYER_TB, null, mContentValues);

        return result > 0;
    }

    public boolean saveAuthCredentials(User user) {
        mContentValues = new ContentValues();

        if (!TextUtils.isEmpty(user.getUsername())){
            mContentValues.put(DatabaseManager.ROW_USERNAME,user.getUsername());
        }
        if (user.getPassword() != null) {
            mContentValues.put(DatabaseManager.ROW_PASSWORD, user.getPassword());
        }

        if (!TextUtils.isEmpty(user.getAccountName())){
            mContentValues.put(DatabaseManager.ROW_ACCOUNT_NAME, user.getAccountName());
        }

        if (!TextUtils.isEmpty(user.getLanguage())){
            mContentValues.put(DatabaseManager.ROW_LANGUAGE, user.getLanguage());
        }

        if (!TextUtils.isEmpty(user.getBranch())){
            mContentValues.put(DatabaseManager.ROW_BRANCH, user.getBranch());
        }

        mSQLiteDatabase = mSQLiteHelper.getWritableDatabase();
        long result = mSQLiteDatabase.insert(DatabaseManager.LOGIN_SAMPLE_DB, null, mContentValues);

        return result > 0;
    }

    public User getUser(String username, String password) {
        User user = new User();

        // get the user from the database by the id
        String getUserByName = "SELECT * FROM " + DatabaseManager.USERS_TABLE + " WHERE "
                + DatabaseManager.ROW_USERNAME + " =?";
        this.mSQLiteDatabase = mSQLiteHelper.getReadableDatabase();
        Cursor cursor = mSQLiteDatabase.rawQuery(getUserByName, new  String[]{username, password});
        if (cursor != null) {
            cursor.moveToFirst();
            user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseManager.ROW_ID)));
            user.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseManager.ROW_USERNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseManager.ROW_PASSWORD)));

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
                player.setImage(cursor.getString(cursor.getColumnIndexOrThrow(Player.ROW_POSITION)));
                players.add(player);


            }
            cursor.close();
        }


        return players;

    }

    public boolean updatePlayer(String name, Player player) {
        Log.d("update player image", player.getImage());

        mContentValues = new ContentValues();
        mContentValues.put(Player.ROW_IMAGE, player.getImage());

        if (!TextUtils.isEmpty(player.getName())){
            mContentValues.put(Player.ROW_NAME,player.getName());
        }

        if (!TextUtils.isEmpty(player.getAge())){
            mContentValues.put(Player.ROW_AGE, player.getAge());
        }

        if (!TextUtils.isEmpty(player.getPosition())){
            mContentValues.put(Player.ROW_POSITION, player.getPosition());
        }

        if (!TextUtils.isEmpty(player.getImage())) {
            String image = Base64.encodeToString(player.getImage().getBytes(), Base64.DEFAULT);
            Log.d("update player image", image);
            mContentValues.put(Player.ROW_IMAGE, image);
        }

        String whereClause = Player.ROW_NAME + "=?";
        String [] whereArgs = new String[]{name};
        mSQLiteDatabase = mSQLiteHelper.getWritableDatabase();
        long result = mSQLiteDatabase.update(Player.PLAYER_TB, mContentValues, whereClause, whereArgs);

        return result > 0;
    }

}
