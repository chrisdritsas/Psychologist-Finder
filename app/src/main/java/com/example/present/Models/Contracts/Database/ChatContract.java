package com.example.present.Models.Contracts.Database;

import android.content.Context;
import android.provider.BaseColumns;

public final class ChatContract {

    public static class ChatEntry implements BaseColumns {

        public static final String TABLE_NAME = "Chat";

        public static final String COLUMN_SENDER_ID = "sender_id";
        public static final String COLUMN_RECEIVER_ID = "receiver_id";
        public static final String COLUMN_MESSAGE = "message";


    }

    public static class ChatRepository {
        public static final String TAG = "ChatRepo";
        public static final String INSERT_CHAT_ERR = "Error while inserting chat into the database.";
        public static final String GET_CHATS_BY_SENDER_ID_ERR = "Error while getting chats by sender id from database.";
        public static final String GET_CHATS_BY_RECEIVER_ID_ERR = "Error while getting chats by receiver id from database.";
        public static final String GET_DISTINCT_RECEIVER_IDS_BY_SENDER_ID_ERR = "Error while getting distinct receiver ids by sender id from database.";
        public static final String GET_CHATS_BY_SENDER_ID_AND_RECEIVER_ID_ERR = "Error while getting chats by sender id and receiver id from database.";
        public static final String GET_DISTINCT_SENDER_IDS_BY_RECEIVER_ID_ERR = "Error while getting distinct sender ids by receiver id from database.";

    }
    public static class ChatController{
        public static final String TAG = "ChatCtrler";
        public static final String INSERT_CHAT_SUCC = "Message sent.";
        public static final String INSERT_CHAT_ERR = "Couldn't send message.";
        public static final String GET_CONV_SUCC = "Successfully retrieved conversations.";
        public static final String GET_CONV_ERR = "Couldn't retrieve conversations.";
        public static final String GET_CONV_CHATS_SUCC = "Successfully retrieved conversation chats.";
        public static final String GET_CONV_CHATS_ERR = "Couldn't retrieve conversation chats.";
        public static final String GET_CONV_CHATS_ERROR = "Couldn't find conversation chats.";
        public static final String GET_CONV_ERROR = "Couldn't find conversations.";
    }
}
