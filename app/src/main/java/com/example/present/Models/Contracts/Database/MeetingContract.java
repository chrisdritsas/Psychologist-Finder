package com.example.present.Models.Contracts.Database;

import android.provider.BaseColumns;

public final class MeetingContract {

    public static class MeetingEntry implements BaseColumns {

        public static final String TABLE_NAME = "Meeting";

        public static final String COLUMN_PATIENT_ID = "user_id";
        public static final String COLUMN_DOCTOR_ID = "doctor_id";
        public static final String COLUMN_DATE_TIME = "date_time";
        public static final String COLUMN_STATE = "state";
        public static final String COLUMN_PATIENT_DESCRIPTION = "patient_description";
        public static final String COLUMN_DOCTOR_DESCRIPTION = "doctor_description";

    }

    public static class MeetingRepository {
        public static final String TAG = "MeetingRepo";
        public static final String INSERT_MEETING_ERR = "Error while inserting meeting into the database.";
        public static final String UPDATE_MEETING_BY_ID_ERR = "Error while updating meeting into the database.";
        public static final String GET_MEETING_BY_ID_ERR = "Error while getting meeting using id from the database.";
        public static final String GET_MEETINGS_BY_PATIENT_ID_ERR = "Error while getting meetings using patient id from the database";
        public static final String GET_MEETINGS_BY_DOCTOR_ID_ERR = "Error while getting meetings using doctor id from the database";
        public static final String GET_DISTINCT_DOCTOR_MEETING_IDS_BY_PATIENT_ID_ERR = "Error while getting distinct doctor meeting ids by patient id.";
        public static final String GET_DISTINCT_PATIENT_MEETING_IDS_BY_DOCTOR_ID_ERR = "Error while getting distinct patient meeting ids by doctor id.";
    }

    public static class MeetingController {
        public static final String TAG = "MeetingCtrler";
        public static final String INSERT_MEETING_SUCC = "Successfully inserted meeting.";
        public static final String INSERT_MEETING_ERR = "Couldn't insert meeting.";
        public static final String GET_MEETING_ERR = "Couldn't retrieve meeting.";
        public static final String GET_MEETING_SUCC = "Successfully retrieved meeting.";
        public static final String UPDATE_MEETING_SUCC = "Successfully updated meeting.";
        public static final String UPDATE_MEETING_ERR = "Couldn't update meeting into database.";
        public static final String GET_DOCTOR_MEETINGS_SUCC = "Successfully retrieved doctor meetings.";
        public static final String GET_DOCTOR_MEETINGS_ERR = "Couldn't retrieve doctor meetings.";
        public static final String GET_PATIENT_MEETINGS_SUCC = "Successfully retrieved patient meetings.";
        public static final String GET_PATIENT_MEETINGS_ERR = "Couldn't retrieve patient meetings.";
        public static final String DOCTOR_NOT_EXISTS_MSG = "Couldn't find doctor.";
        public static final String MEETING_LOCK_MSG = "Meeting is locked as canceled.";


    }
}
