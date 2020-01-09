package com.cs4sample.authentication.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

public class SQLiteHelper extends SQLiteOpenHelper {
    // database
    public static String DB_NAME = DatabaseManager.ASSETS_DATABASE_NAME;
    public static String DB_PATH;
    public SQLiteDatabase mSQLiteDatabase;
    // mContext
    public Context mContext;
    // Extra space required for database (1MB).
    static final long DATABASE_PAD_SIZE = 1048576;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DatabaseManager.ASSETS_DATABASE_NAME,
                null, DatabaseManager.DATABASE_VERSION);
        this.mContext = context;
        String packageName = mContext.getPackageName();
        DB_NAME = DatabaseManager.ASSETS_DATABASE_NAME;
        DB_PATH = "data/data/" + packageName + "/databases/";
        openDataBase();
    }

    public SQLiteDatabase openDataBase() throws SQLException {
        String path = DB_PATH + DB_NAME;
        if (mSQLiteDatabase == null) {
            createDataBase();
            //this.getWritableDatabase();
            mSQLiteDatabase = SQLiteDatabase.openDatabase(path, null,
                    SQLiteDatabase.OPEN_READWRITE);
        }
        return mSQLiteDatabase;
    }


    @Override
    public synchronized void close() {
        if (mSQLiteDatabase != null) {
            mSQLiteDatabase.close();
        }
        super.close();
    }

    // check if database exists
    public boolean dbExists() {
        File databasePath = mContext.getDatabasePath(DB_NAME);

        if (databasePath.exists()) {
            SQLiteDatabase db = null;

            try {
                db = SQLiteDatabase.openDatabase(databasePath.toString(),
                        null, SQLiteDatabase.OPEN_READWRITE);
                db.setLocale(Locale.getDefault());
            } catch (SQLiteCantOpenDatabaseException ex){

            } catch (SQLiteException e) {
                db = null;
            }

            if (db == null) {
                return false;
            } else {
                db.close();
                return true;
            }
        } else
            return false;

    }

    //get the remaining available storage on the device in Bytes.
    private long getAvailableSpaceBytes() {
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        return stat.getAvailableBlocksLong() * stat.getBlockSizeLong();
    }

    //get the size of the DB file in Assets in Bytes.
    private long getAssetDBSizeBytes() throws IOException  {
        InputStream in = mContext.getAssets().open(DB_NAME);
        byte[] buffer = new byte[1024];
        int length;
        long totalLength = 0;
        while ((length = in.read(buffer)) > 0)
            totalLength += length;
        in.close();
        return totalLength;
    }

    //returns true if enough space to copy database, false if not.
    private boolean checkSpace() throws IOException  {
        long availableSpace = getAvailableSpaceBytes();
        long assetFileSize = getAssetDBSizeBytes();

        return availableSpace > (assetFileSize + DATABASE_PAD_SIZE);
    }

    // delete the database file from the phone is exists
    private void deleteDatabase() {
        File dbFile = mContext.getDatabasePath(DB_NAME);
        if (dbFile.exists())
            dbFile.delete();
    }

    private void copyDatabaseFromAssets() throws IOException  {
        InputStream myInput = null;
        OutputStream myOutput = null;
        try  {
            // Open db packaged as asset as the input stream
            myInput = mContext.getAssets().open(DB_NAME);
            // Open the db in the application package mContext:
            File path = mContext.getDatabasePath(DB_NAME);
            // Make sure the databases directory exists.
            path.getParentFile().mkdirs();

            myOutput = new FileOutputStream(path);
            // Transfer db file contents:
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0)  {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
        }
        finally  {
            // Close the streams
            if (myOutput != null) {
                myOutput.close();
            }
            if (myInput != null) {
                myInput.close();
            }
        }
    }

    public void createDataBase() {
        if (!dbExists()) {
            //this.getReadableDatabase();
            this.getWritableDatabase();//getReadableDatabase();//
            try {
                copyDatabaseFromAssets();
            } catch (IOException e) {
                Log.e(this.getClass().toString(), e.toString());
                throw new Error("Error copying database!");
            }
        } else {
            Log.i(this.getClass().toString(), "Database already exists");
        }
    }

    private long getActualDBSizeBytes() {
        return mContext.getDatabasePath(DB_NAME).length();
    }

    private void setDatabaseVersion()  {
        String databasePath = mContext.getDatabasePath(DB_NAME).toString();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE);
        db.setLocale(Locale.getDefault());
        db.setLockingEnabled(true);
        db.setVersion(DatabaseManager.DATABASE_VERSION);
        db.close();
    }

        @Override
    public void onCreate(SQLiteDatabase db) {
//        String createDatabase = "CREATE TABLE " + DatabaseManager.USERS_TABLE +
//                "(" + DatabaseManager.ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
//                + DatabaseManager.ROW_NAME + " TEXT,"
//                + DatabaseManager.ROW_PASSWORD + " TEXT" + ")";
//
//        db.execSQL(createDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        String upgrade = "DROP TABLE IF EXISTS " + DatabaseManager.USERS_TABLE;
//        db.execSQL(upgrade);
    }
}
