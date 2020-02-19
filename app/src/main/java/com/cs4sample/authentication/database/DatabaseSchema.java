package com.cs4sample.authentication.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseSchema {
    //database table names
    public static String PROFESSION_TB = "professions_tb";
    public static String GENDER_TB = "gender_tb";
    public static String INSURANCE_TYPE_TB = "insurance_type_tb";
    public static String COUNTRIES_TB = "countries_tb";
    public static String PLACE_OF_RESIDENCE_TB = "places_of_residence_tb";
    public static String MARITAL_STATUS_TB = "marital_status_tb";
    public static String MEDICAL_CENTERS_TB = "medical_centers_tb";
    public static String CIVILISATION_TB = "civilisation_tb";
    public static String ID_TYPES = "id_types_tb";
    public static String EMPLOYER_ORGAN_TB = "employer_organ_tb";
    public static String ORGANISME_TB = "organisme_tb";
    public static String CONTRIBUTION_PLAYERS_TB = "contribution_players_tb";
    public static String INSURANCE_TO_COLLECTION_RELATIONSHIP = "insurance_to_collection_relationship";
    public static String CONTRIBUTOR_TO_INSURANCE_RELATIONSHIP = "contributor_to_insurance_relationship";
    public static String REG_DATA_TB = "registration_tb";

    //database common tables schema
    static String ROW_ID = "Id";
    static String ROW_NAME = "Name";
    static String ROW_CODE = "Code";

    // gender table schema
    static String GENDER_NAME = "Gender_name";
    static String GENDER_CHARACTER = "Gender_character";

    //countries table schema
    static String COUNTRY_NAME = "Country";

    // marital table schema
    static String MARITAL_STATUS = "Status";

    // civilisation table schema
    static String GENDER = "Gender";

    // employer organ list schema
    static String COMMUNE = "Commune";
    static String INSURANCE_ID = "Insurance_id";

    // insurance type
    static String INSURANCE_TYPE = "Type";
    // contriubtuor
    static String CONTRIBUTOR_ID = "Contributor_id";

    // Registration data
    static String REG_ROW_ID = "Id";
    static String REG_ROW_SURNAME = "Surname";
    static String REG_ROW_OTHER_NAMES = "Othernames";
    static String REG_ID_NUMBER = "Id_number";
    static String REG_BIRTH_DATE = "Date_of_birth";
    static String REG_COUNTRY = "Country";
    static String REG_GENDER = "Gender";
    static String REG_ORGANISATION = "Employer_organisation";
    static String REG_CONTRIBUTION_PAYER = "Contribution_payer";
    static String REG_INSURANCE_TYPE = "Insurance_type";
    static String REG_PROFILE_PHOTO = "Profile_photo";
    static String REG_ID_FRONT = "Id_front";
    static String REG_ID_BACK = "Id_back";

    /**Create database tables*/
    static void createDatabaseTables(SQLiteDatabase db) {
        // create gender table
        String genderTable = "CREATE TABLE IF NOT EXISTS " + DatabaseSchema.GENDER_TB +
                "(" + DatabaseSchema.ROW_ID + " INTEGER PRIMARY KEY NOT NULL,"
                + DatabaseSchema.GENDER_CHARACTER + " TEXT,"
                + DatabaseSchema.GENDER_NAME + " TEXT " + ")";
        db.execSQL(genderTable);

        String countriesTable = "CREATE TABLE IF NOT EXISTS " + DatabaseSchema.COUNTRIES_TB +
                "(" + DatabaseSchema.ROW_ID + " INTEGER PRIMARY KEY NOT NULL,"
                + DatabaseSchema.COUNTRY_NAME + " TEXT,"
                + DatabaseSchema.ROW_CODE + " TEXT " + ")";
        db.execSQL(countriesTable);

        String employerOrganTable = "CREATE TABLE IF NOT EXISTS " + DatabaseSchema.EMPLOYER_ORGAN_TB +
                "(" + DatabaseSchema.ROW_ID + " INTEGER PRIMARY KEY NOT NULL,"
                + DatabaseSchema.ROW_NAME + " TEXT,"
                + DatabaseSchema.ROW_CODE + " TEXT,"
                + DatabaseSchema.COMMUNE + " TEXT,"
                + DatabaseSchema.INSURANCE_ID + " TEXT" + ")";
        db.execSQL(employerOrganTable);

        String insuranceTypeTable = "CREATE TABLE IF NOT EXISTS " + DatabaseSchema.INSURANCE_TYPE_TB +
                "(" + DatabaseSchema.ROW_ID + " INTEGER PRIMARY KEY NOT NULL,"
                + DatabaseSchema.ROW_CODE + " TEXT,"
                + DatabaseSchema.INSURANCE_TYPE + " TEXT,"
                + DatabaseSchema.CONTRIBUTOR_ID +  " TEXT " + ")";
        db.execSQL(insuranceTypeTable);

        String contributors = "CREATE TABLE IF NOT EXISTS " + DatabaseSchema.CONTRIBUTION_PLAYERS_TB +
                "(" + DatabaseSchema.ROW_ID + " INTEGER PRIMARY KEY NOT NULL,"
                + DatabaseSchema.ROW_NAME + " TEXT,"
                + DatabaseSchema.ROW_CODE + " TEXT " + ")";
        db.execSQL(contributors);

        String reg_data = "CREATE TABLE IF NOT EXISTS " + DatabaseSchema.REG_DATA_TB +
                "(" + DatabaseSchema.REG_ROW_ID + " INTEGER PRIMARY KEY NOT NULL,"
                + DatabaseSchema.REG_ROW_SURNAME + " TEXT,"
                + DatabaseSchema.REG_ROW_OTHER_NAMES + " TEXT,"
                + DatabaseSchema.REG_ID_NUMBER + " TEXT,"
                + DatabaseSchema.REG_BIRTH_DATE + " TEXT,"
                + DatabaseSchema.REG_COUNTRY + " TEXT,"
                + DatabaseSchema.REG_GENDER + " TEXT,"
                + DatabaseSchema.REG_ORGANISATION + " TEXT,"
                + DatabaseSchema.REG_CONTRIBUTION_PAYER + " TEXT,"
                + DatabaseSchema.REG_INSURANCE_TYPE + " TEXT,"
                + DatabaseSchema.REG_PROFILE_PHOTO + " TEXT,"
                + DatabaseSchema.REG_ID_FRONT + " TEXT,"
                + DatabaseSchema.REG_ID_BACK + " TEXT" + ")";
        db.execSQL(reg_data);
        Log.d("successful reg save", reg_data + " ");


    }


}
