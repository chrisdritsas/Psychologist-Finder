package com.example.present.Models.Contracts.Database;

import android.provider.BaseColumns;

public final class UserDataContract {

    public static class UserDataEntry implements BaseColumns {

        public static final String TABLE_NAME = "User_Data";

        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SURNAME = "surname";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_ADDRESS_CODE = "address_code";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_TELEPHONE = "telephone";
        public static final String COLUMN_PROFILE_PICTURE_PATH = "picture_path";

    }

    public static class UserDataRepository {
        public static final String TAG = "UserDataRepo";
        public static final String INSERT_USER_DATA_ERR = "Error while inserting userdata into the database";
        public static final String UPDATE_USER_DATA_ERR = "Error while updating user data into the database";
        public static final String GET_USER_DATA_BY_USER_ID_ERR = "Error while getting user data using user id from the database";
        public static final String GET_USER_DATA_NAME_SURNAME_BY_USER_ID_ERR = "Error while getting user data name and surname using user id from the database";
        public static final String CHECK_USER_DATA_EXISTS_BY_USER_ID_ERR = "Error while searching for user data ID into the database";
        public static final String GET_USER_DATA_ID_BY_USER_ID_ERR = "Error while getting user data id using user id from the database.";
        public static final String GET_USER_DATA_AND_OFFICE_OF_DOCTORS = "Error while getting doctors user data and office from the database";
    }

    public static class UserDataController {
        public static final String TAG = "UserDataCtrler";
        public static final String USER_EXIST_ERR = "Couldn't find user to add data.";
        public static final String USER_HAS_USERDATA_ERR = "User already has user data in database.";
        public static final String INSERT_USERDATA_SUCC = "Successfully added user data.";
        public static final String INSERT_USERDATA_ERR = "Couldn't insert user data.";
        public static final String UPDATE_USERDATA_SUCC = "Successfully updated user data.";
        public static final String UPDATE_USERDATA_ERR = "Couldn't updated user data.";
        public static final String GET_USERDATA_SUCC = "Successfully retrieved user data.";
        public static final String GET_USERDATA_ERR = "Couldn't find user data.";
        public static final String GET_USERDATA_NAME_SURNAME_ERR = "Couldn't find user data name and surname.";
        public static final String GET_USERDATA_ERROR = "Couldn't retrieve user data.";
        public static final String USER_ID_ERR = "Invalid user id.";
        public static final String GET_DOCTORS_LIST_ERR = "Couldn't find doctors.";
        public static final String GET_DOCTORS_LIST_SUCC = "Successfully retrieved doctors.";
        public static final String GET_DOCTORS_LIST_ERROR = "Couldn't retrieve doctors.";
        public static final String USER_DATA_SUCC = "Success.";

    }
}
