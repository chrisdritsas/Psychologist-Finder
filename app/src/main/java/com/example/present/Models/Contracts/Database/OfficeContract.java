package com.example.present.Models.Contracts.Database;

import android.provider.BaseColumns;

public final class OfficeContract {

    public static class OfficeEntry implements BaseColumns {

        public static final String TABLE_NAME = "Office";

        public static final String COLUMN_VIEWS = "views";
        public static final String COLUMN_USER_DATA_ID = "user_data_id";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_ADDRESS_CODE = "address_code";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_MOBILE = "mobile";
        public static final String COLUMN_BIOGRAPHY = "biography";
        public static final String COLUMN_ONLINE_PRICE = "online_price";
        public static final String COLUMN_MEETING_PRICE = "meeting_price";
        public static final String COLUMN_WORK_HOURS = "work_hours";
    }

    public static class OfficeRepository {
        public static final String TAG = "OfficeRepo";
        public static final String INSERT_OFFICE_ERR = "Error while inserting office into the database.";
        public static final String UPDATE_OFFICE_ERR = "Error while updating office into the database.";
        public static final String INC_OFFICE_VIEWS_ERR = "Error while increasing office views into the database.";
        public static final String GET_OFFICE_VIEWS_ERR = "Error while getting office views from the database.";
        public static final String GET_OFFICE_BY_USER_DATA_ID_ERR = "Error while getting office using user data id from the database.";
        public static final String CHECK_OFFICE_EXISTS_BY_USER_DATA_ID_ERR = "Error while searching for office ID into the database";
    }

    public static class OfficeController {
        public static final String TAG = "OfficeCtrler";
        public static final String INSERT_OFFICE_SUCC = "Successfully inserted office.";
        public static final String INSERT_OFFICE_ERR = "Couldn't insert office.";
        public static final String UPDATE_OFFICE_SUCC = "Office updated successfully.";
        public static final String UPDATE_OFFICE_ERR = "Couldn't update office.";
        public static final String GET_OFFICE_SUCC = "Successfully retrieved office.";
        public static final String GET_OFFICE_ERR = "Couldn't retrieve office.";
        public static final String OFFICE_EXISTS_MSG = "User data already has office.";
        public static final String OFFICE_SUCC = "Success.";
        public static final String OFFICE_ID_WRONG = "Wrong office id.";
        public static final String OFFICE_VIEW_INC_ERR = "Couldn't increase the office views";
        public static final String OFFICE_VIEW_INC_SUCC = "Successfully increased office views";
        public static final String GET_OFFICE_VIEWS_SUCC = "Successfully retrieved office views.";
        public static final String GET_OFFICE_VIEWS_ERR = "Couldn't retrieve office views.";

    }
}
