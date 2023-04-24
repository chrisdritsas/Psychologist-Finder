package com.example.present.Models.Contracts.Database;

import android.provider.BaseColumns;

public final class NotificationContract {

    public final static class Entry implements BaseColumns {

        public static final String TABLE_NAME = "Notification";

        public static final String COLUMN_REQUEST_CODE = "request_code";
        public static final String COLUMN_CREATOR_ID = "creator_id";
        public static final String COLUMN_RECEIVER_ID = "receiver_id";
        public static final String COLUMN_ACTION_TARGET = "action_target";
        public static final String COLUMN_CHAT_SENDER_ID = "chat_sender_id";
        public static final String COLUMN_MEETING_ID = "meeting_id";
        public static final String COLUMN_CONTENT_TITLE = "content_title";
        public static final String COLUMN_CONTENT_TEXT = "content_text";
        public static final String COLUMN_STATE = "state";
    }

    public final static class Repository {
        public static final String TAG = "NotificationRepo";

        public static final String INSERT_NOTIFICATION_ERR = "Error while inserting notification into the database.";
        public static final String GET_NOTIFICATIONS_ERR = "Error while getting notifications by receiver id and state from the database.";
        public static final String UPDATE_NOTIFICATION_ERR = "Failed to update notification state.";
        public static final String UPDATE_NOTIFICATION_ERROR = "Error while updating notification state in the database.";
    }

    public static final class Controller {
        public static final String TAG = "NotificationCtrler";

        public static final String ACTION_CHAT = "com.example.Present.NotificationClick.ChatFragment";
        public static final String ACTION_MEETING = "com.example.Present.NotificationClick.UpdateMeetingFragment";
        public static final String MEETING_ID = "meetingId";
        public static final String CHAT_USER_ID = "userId";
        public static final int NOTIFICATION_READ = 1;
        public static final int NOTIFICATION_UNREAD = 0;

    }
}
