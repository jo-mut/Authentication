package com.cs4sample.authentication.database;

import android.database.sqlite.SQLiteDatabase;

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
    public static String CONTRIBUTION_PLAYERS = "contribution_players_tb";
    public static String INSURANCE_TO_COLLECTION_RELATIONSHIP = "insurance_to_collection_relationship";
    public static String CONTRIBUTOR_TO_INSURANCE_RELATIONSHIP = "contributor_to_insurance_relationship";

    //database common tables schema
    static String ROW_$ID = "d";
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

    /**Create database tables*/
    static void createDatabaseTables(SQLiteDatabase db) {
        // create gender table
        String genderTable = "CREATE TABLE " + DatabaseSchema.GENDER_TB +
                "( " + DatabaseSchema.ROW_ID + " INTEGER PRIMARY KEY NOT NULL,"
                + DatabaseSchema.GENDER_CHARACTER + " TEXT,"
                + DatabaseSchema.GENDER_NAME + ")";
        db.execSQL(genderTable);

    }


}
