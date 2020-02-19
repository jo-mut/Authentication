package com.cs4sample.authentication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cs4sample.authentication.interfaces.DatabaseProgressListener;
import com.cs4sample.authentication.models.ContributionPayer;
import com.cs4sample.authentication.models.Country;
import com.cs4sample.authentication.models.EmployerOrgan;
import com.cs4sample.authentication.models.Gender;
import com.cs4sample.authentication.models.Insurance;
import com.cs4sample.authentication.models.Record;
import com.cs4sample.authentication.models.Forms;

import java.util.ArrayList;
import java.util.List;

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
    public static final int DATABASE_VERSION = 1;
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
    // SQLite Helper object
    private SQLiteHelper mSQLiteHelper;
    // Database Object
    private static SQLiteDatabase mWritableDatabase;
    private static SQLiteDatabase mReadableDatabase;
    // Database progress listner
    private DatabaseProgressListener progressListener;
    private static int progress = 0;

    public DatabaseManager(Context context) {
        this.mContext = context;
        this.mSQLiteHelper = new SQLiteHelper(mContext);
        mWritableDatabase = mSQLiteHelper.getWritableDatabase();
        mReadableDatabase = mSQLiteHelper.getReadableDatabase();
        DatabaseSchema.createDatabaseTables(mWritableDatabase);
    }

    /**Save gender list into the database*/
    private static boolean saveGender(Gender g) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseSchema.ROW_ID, g.getId());
        contentValues.put(DatabaseSchema.GENDER_NAME, g.getGenderName());
        contentValues.put(DatabaseSchema.GENDER_CHARACTER, g.getGenderCharacter());
        long result = mWritableDatabase.insert(DatabaseSchema.GENDER_TB, null, contentValues);

        return result > 0;
    }

    private static boolean saveCountry(Country country) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseSchema.ROW_ID, country.getId());
        contentValues.put(DatabaseSchema.COUNTRY_NAME, country.getName());
        contentValues.put(DatabaseSchema.ROW_CODE, country.getCode());
        long result = mWritableDatabase.insert(DatabaseSchema.COUNTRIES_TB, null, contentValues);
        return result > 0;
    }

    private static boolean saveEmployerOrganisation(EmployerOrgan organ) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseSchema.ROW_ID, organ.getId());
        contentValues.put(DatabaseSchema.ROW_NAME, organ.getName());
        contentValues.put(DatabaseSchema.ROW_CODE, organ.getCode());
        contentValues.put(DatabaseSchema.COMMUNE, organ.getCommune());
        contentValues.put(DatabaseSchema.INSURANCE_ID, organ.getInsuranceId());
        long result = mWritableDatabase.insert(DatabaseSchema.EMPLOYER_ORGAN_TB, null, contentValues);
        return result > 0;
    }

    private static boolean saveContributor(ContributionPayer contributionPayer) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseSchema.ROW_ID, contributionPayer.getId());
        contentValues.put(DatabaseSchema.ROW_NAME, contributionPayer.getName());
        contentValues.put(DatabaseSchema.ROW_CODE, contributionPayer.getCode());
        long result = mWritableDatabase.insert(DatabaseSchema.CONTRIBUTION_PLAYERS_TB, null, contentValues);
        return result > 0;
    }

    private static boolean saveInsuranceType(Insurance insurance) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseSchema.ROW_ID, insurance.getId());
        contentValues.put(DatabaseSchema.INSURANCE_TYPE, insurance.getType());
        contentValues.put(DatabaseSchema.ROW_CODE, insurance.getCode());
        contentValues.put(DatabaseSchema.CONTRIBUTOR_ID, insurance.getContributorId());
        long result = mWritableDatabase.insert(DatabaseSchema.INSURANCE_TYPE_TB, null, contentValues);
        return result > 0;
    }

    public boolean saveRegData() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseSchema.REG_ROW_SURNAME, Forms.surname);
        contentValues.put(DatabaseSchema.REG_ROW_OTHER_NAMES, Forms.otherNames);
        contentValues.put(DatabaseSchema.REG_ID_NUMBER, Forms.idNumber);
        contentValues.put(DatabaseSchema.REG_BIRTH_DATE, Forms.dateOfBirth);
        contentValues.put(DatabaseSchema.REG_COUNTRY, Forms.country);
        contentValues.put(DatabaseSchema.REG_GENDER, Forms.gender);
        contentValues.put(DatabaseSchema.REG_ORGANISATION, Forms.employerOrganisation);
        contentValues.put(DatabaseSchema.REG_CONTRIBUTION_PAYER, Forms.contributionPayer);
        contentValues.put(DatabaseSchema.REG_INSURANCE_TYPE, Forms.insuranceType);
        contentValues.put(DatabaseSchema.REG_PROFILE_PHOTO, Forms.profilePhoto);
        contentValues.put(DatabaseSchema.REG_ID_BACK, Forms.idBack);
        contentValues.put(DatabaseSchema.REG_ID_FRONT, Forms.idFront);

        Log.d("records-n", Forms.surname + " ");

        Log.d("records-n", contentValues + " ");

        long result = mWritableDatabase.insert(DatabaseSchema.REG_DATA_TB, null, contentValues);

        return result > 0;
    }

    public List<String> getGenderList() {
        List<String> genders = new ArrayList<>();
        Cursor cursor = mReadableDatabase.query(DatabaseSchema.GENDER_TB, null,
                null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String gender = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.GENDER_NAME));
                genders.add(gender);
            }
            cursor.close();
        }

        return genders;

    }

    public List<String> getTypeOfInsuranceList() {
        List<String> insuranceTypes = new ArrayList<>();
        Cursor cursor = mReadableDatabase.query(DatabaseSchema.INSURANCE_TYPE_TB, null,
                null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String type = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.INSURANCE_TYPE));
                insuranceTypes.add(type);
            }
            cursor.close();
        }

        return insuranceTypes;

    }

    public List<String> getContributorList() {
        List<String> contributors = new ArrayList<>();
        Cursor cursor = mReadableDatabase.query(DatabaseSchema.CONTRIBUTION_PLAYERS_TB, null,
                null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.ROW_NAME));
                contributors.add(name);
            }
            cursor.close();
        }

        return contributors;

    }

    public List<String> getCoutryList() {
        List<String> countries = new ArrayList<>();
        Cursor cursor = mReadableDatabase.query(DatabaseSchema.COUNTRIES_TB, null,
                null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.COUNTRY_NAME));
                countries.add(name);
            }
            cursor.close();
        }

        return countries;

    }

    public List<String> getEmployerOrgans() {
        List<String> organs = new ArrayList<>();
        Cursor cursor = mReadableDatabase.query(DatabaseSchema.EMPLOYER_ORGAN_TB, null,
                null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.ROW_NAME));
                organs.add(name);
            }
            cursor.close();
        }

        return organs;

    }

    /**Loop through the returned list of genders building a content value
     * and saving the data to the database*/
    public static  void saveGenderList(List<Gender> genders) {
        if (genders != null) {
            for (Gender g: genders) {
                saveGender(g);
            }
            //        progress ++;
//        progressListener.databaseProgress(progress);
            Log.d("database save ", "gender" + "");

        }

    }

    public static  void saveCountries(List<Country> countries) {
        if (countries != null) {
            for (Country country: countries) {
                saveCountry(country);
            }
//            progress ++;
//            progressListener.databaseProgress(progress);
            Log.d("database save ", "countries" + "");

        }

    }

    public static void saveContributors(List<ContributionPayer> contributionPayers) {
        if (contributionPayers != null) {
            for (ContributionPayer contributionPayer : contributionPayers) {
                saveContributor(contributionPayer);
            }
//            progress ++;
//            progressListener.databaseProgress(progress);
            Log.d("database save ", "contributors" + "");

        }

    }

    public static  void saveInsuranceTypes(List<Insurance> insurances) {
        if (insurances != null) {
            for (Insurance insurance: insurances) {
                saveInsuranceType(insurance);
            }
//            progress ++;
//            progressListener.databaseProgress(progress);
            Log.d("database save ", "insurance" + "");

        }

    }


    public static void saveEmployerOrgans(List<EmployerOrgan> organs) {
        if (organs != null) {
            for (EmployerOrgan organ: organs) {
                saveEmployerOrganisation(organ);
            }
//            progress ++;
//            progressListener.databaseProgress(progress);
            Log.d("database save ", "organs" + "");

        }

    }

    public List<Record> getRegData(){
        List<Record> records = new ArrayList<>();
        Cursor cursor = mReadableDatabase.query(DatabaseSchema.REG_DATA_TB, null,
                null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Record record = new Record();
                record.setSurname(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.REG_ROW_SURNAME)));
                record.setOtherNames(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.REG_ROW_OTHER_NAMES)));
                record.setIdNumber(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.REG_ID_NUMBER)));
                record.setDateOfBirth(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.REG_BIRTH_DATE)));
                record.setGender(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.REG_GENDER)));
                record.setCountry(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.REG_COUNTRY)));
                record.setEmployerOrganisation(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.REG_ORGANISATION)));
                record.setContributionPayer(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.REG_CONTRIBUTION_PAYER)));
                record.setInsuranceType(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.REG_INSURANCE_TYPE)));
                record.setProfilePhoto(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.REG_PROFILE_PHOTO)));
                record.setIdBack(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.REG_ID_BACK)));
                record.setIdFront(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.REG_ID_FRONT)));
                records.add(record);

                Log.d("records-d", record.getSurname() + " ");
                Log.d("records-d", record.getGender() + " ");
                Log.d("records-d", record.getProfilePhoto() + " ");
                Log.d("records-d", record.getOtherNames() + " ");
                Log.d("records-d", record.getContributionPayer() + " ");
                Log.d("records-d", record.getIdFront() + " ");
                Log.d("records-d", record.getProfilePhoto() + " ");
                Log.d("records-d", record.getCountry() + " ");
                Log.d("records-d", record.getDateOfBirth() + " ");
            }
            cursor.close();
        }
        return records;
    }

    public Record getRecordById(String idNumber) {
        Record record = new Record();
        String whereClause = "Id_number =?";
        String whereArgs = idNumber;

        Cursor cursor = mReadableDatabase.query(DatabaseSchema.REG_DATA_TB, null,
                whereClause, new String[]{whereArgs}, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                record.setSurname(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.REG_ROW_SURNAME)));
                record.setOtherNames(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.REG_ROW_OTHER_NAMES)));
                record.setIdNumber(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.REG_ID_NUMBER)));
                record.setDateOfBirth(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.REG_BIRTH_DATE)));
                record.setGender(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.REG_GENDER)));
                record.setCountry(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.REG_COUNTRY)));
                record.setEmployerOrganisation(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.REG_ORGANISATION)));
                record.setContributionPayer(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.REG_CONTRIBUTION_PAYER)));
                record.setInsuranceType(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.REG_INSURANCE_TYPE)));
                record.setProfilePhoto(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.REG_PROFILE_PHOTO)));
                record.setIdBack(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.REG_ID_BACK)));
                record.setIdFront(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.REG_ID_FRONT)));
            }
        }

        return record;
    }
}
