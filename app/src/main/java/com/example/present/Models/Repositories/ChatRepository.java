package com.example.present.Models.Repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.present.App.Present;
import com.example.present.Models.Contracts.Database.ChatContract;
import com.example.present.Models.Contracts.Database.MeetingContract;
import com.example.present.Models.Contracts.Database.OfficeContract;
import com.example.present.Models.Entities.Chat;
import com.example.present.Models.Entities.Meeting;
import com.example.present.Models.Entities.Office;

import java.util.ArrayList;
import java.util.List;

public class ChatRepository {
    static SQLiteDatabase db = Present.getInstance().getDatabase();

    public boolean insertChat(Chat chat) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ChatContract.ChatEntry.COLUMN_SENDER_ID, chat.getSenderId());
        contentValues.put(ChatContract.ChatEntry.COLUMN_RECEIVER_ID, chat.getReceiverId());
        contentValues.put(ChatContract.ChatEntry.COLUMN_MESSAGE, chat.getMessage());
        try {
            db.beginTransaction();
            long result = db.insert(
                    ChatContract.ChatEntry.TABLE_NAME,
                    null,
                    contentValues
            );
            if (result == -1) {
                return false;
            } else {
                db.setTransactionSuccessful();
                return true;
            }
        } catch (Exception e) {
            Log.e(ChatContract.ChatRepository.TAG, ChatContract.ChatRepository.INSERT_CHAT_ERR);
            return false;
        } finally {
            db.endTransaction();
        }
    }

    public List<Chat> getChatsBySenderId(int senderId) {
        String[] projection = {
                ChatContract.ChatEntry.COLUMN_SENDER_ID,
                ChatContract.ChatEntry.COLUMN_RECEIVER_ID,
                ChatContract.ChatEntry.COLUMN_MESSAGE
        };
        String selection = ChatContract.ChatEntry.COLUMN_SENDER_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(senderId)};
        List<Chat> chats = new ArrayList<>();
        try (Cursor cursor = db.query(
                ChatContract.ChatEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {
            while (cursor.moveToNext()) {
                int indexId = cursor.getColumnIndex(ChatContract.ChatEntry._ID);
                int indexSenderId = cursor.getColumnIndex(ChatContract.ChatEntry.COLUMN_SENDER_ID);
                int indexReceiverId = cursor.getColumnIndex(ChatContract.ChatEntry.COLUMN_RECEIVER_ID);
                int indexMessage = cursor.getColumnIndex(ChatContract.ChatEntry.COLUMN_MESSAGE);
                if (indexId >= 0 && indexSenderId >= 0 && indexReceiverId >= 0 && indexMessage >= 0) {
                    chats.add(new Chat(
                            cursor.getInt(indexId),
                            cursor.getInt(indexSenderId),
                            cursor.getInt(indexReceiverId),
                            cursor.getString(indexMessage)
                    ));
                }
            }
            return chats;
        } catch (Exception e) {
            Log.e(ChatContract.ChatRepository.TAG, ChatContract.ChatRepository.GET_CHATS_BY_SENDER_ID_ERR, e);
            return null;
        }
    }

    public List<Chat> getChatsByReceiverId(int receiverId) {
        String[] projection = {
                ChatContract.ChatEntry.COLUMN_SENDER_ID,
                ChatContract.ChatEntry.COLUMN_RECEIVER_ID,
                ChatContract.ChatEntry.COLUMN_MESSAGE
        };
        String selection = ChatContract.ChatEntry.COLUMN_RECEIVER_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(receiverId)};
        List<Chat> chats = new ArrayList<>();
        try (Cursor cursor = db.query(
                ChatContract.ChatEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {
            while (cursor.moveToNext()) {
                int indexId = cursor.getColumnIndex(ChatContract.ChatEntry._ID);
                int indexSenderId = cursor.getColumnIndex(ChatContract.ChatEntry.COLUMN_SENDER_ID);
                int indexReceiverId = cursor.getColumnIndex(ChatContract.ChatEntry.COLUMN_RECEIVER_ID);
                int indexMessage = cursor.getColumnIndex(ChatContract.ChatEntry.COLUMN_MESSAGE);
                if (indexId >= 0 && indexSenderId >= 0 && indexReceiverId >= 0 && indexMessage >= 0) {
                    chats.add(new Chat(
                            cursor.getInt(indexId),
                            cursor.getInt(indexSenderId),
                            cursor.getInt(indexReceiverId),
                            cursor.getString(indexMessage)
                    ));
                }
            }
            return chats;
        } catch (Exception e) {
            Log.e(ChatContract.ChatRepository.TAG, ChatContract.ChatRepository.GET_CHATS_BY_RECEIVER_ID_ERR, e);
            return null;
        }
    }

    public List<Integer> getDistinctReceiverIdsBySenderId(int senderId) {
        String[] projection = {
                ChatContract.ChatEntry.COLUMN_RECEIVER_ID
        };
        String selection = ChatContract.ChatEntry.COLUMN_SENDER_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(senderId)};
        List<Integer> receiverIds = new ArrayList<>();
        try (Cursor cursor = db.query(
                ChatContract.ChatEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {
            while (cursor.moveToNext()) {
                int indexReceiverId = cursor.getColumnIndex(ChatContract.ChatEntry.COLUMN_RECEIVER_ID);
                if (indexReceiverId >= 0) {
                    int receiverId = cursor.getInt(indexReceiverId);
                    if (!receiverIds.contains(receiverId)) {
                        receiverIds.add(receiverId);
                    }
                }
            }
            return receiverIds;
        } catch (Exception e) {
            Log.e(ChatContract.ChatRepository.TAG, ChatContract.ChatRepository.GET_DISTINCT_RECEIVER_IDS_BY_SENDER_ID_ERR, e);
            return null;
        }
    }

    public List<Integer> getDistinctSenderIdsByReceiverId(int receiverId) {
        String[] projection = {
                ChatContract.ChatEntry.COLUMN_SENDER_ID
        };
        String selection = ChatContract.ChatEntry.COLUMN_RECEIVER_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(receiverId)};
        List<Integer> senderIds = new ArrayList<>();
        try (Cursor cursor = db.query(
                ChatContract.ChatEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {
            while (cursor.moveToNext()) {
                int indexSenderId = cursor.getColumnIndex(ChatContract.ChatEntry.COLUMN_SENDER_ID);
                if (indexSenderId >= 0) {
                    int senderId = cursor.getInt(indexSenderId);
                    if (!senderIds.contains(senderId)) {
                        senderIds.add(senderId);
                    }
                }
            }
            return senderIds;
        } catch (Exception e) {
            Log.e(ChatContract.ChatRepository.TAG, ChatContract.ChatRepository.GET_DISTINCT_SENDER_IDS_BY_RECEIVER_ID_ERR, e);
            return null;
        }
    }

    public List<Chat> getConversationChatsBySenderIdAndReceiverId(int senderId, int receiverId) {
        String[] projection = {
                ChatContract.ChatEntry._ID,
                ChatContract.ChatEntry.COLUMN_SENDER_ID,
                ChatContract.ChatEntry.COLUMN_RECEIVER_ID,
                ChatContract.ChatEntry.COLUMN_MESSAGE
        };
        String selection = "(" + ChatContract.ChatEntry.COLUMN_SENDER_ID + " = ? AND " + ChatContract.ChatEntry.COLUMN_RECEIVER_ID + " = ? )"
                + " OR (" + ChatContract.ChatEntry.COLUMN_SENDER_ID + " = ? AND " + ChatContract.ChatEntry.COLUMN_RECEIVER_ID + " = ? )";
        String[] selectionArgs = {String.valueOf(senderId), String.valueOf(receiverId), String.valueOf(receiverId), String.valueOf(senderId)};
        List<Chat> chats = new ArrayList<>();
        try (Cursor cursor = db.query(
                ChatContract.ChatEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {
            while (cursor.moveToNext()) {
                int indexId = cursor.getColumnIndex(ChatContract.ChatEntry._ID);
                int indexSenderId = cursor.getColumnIndex(ChatContract.ChatEntry.COLUMN_SENDER_ID);
                int indexReceiverId = cursor.getColumnIndex(ChatContract.ChatEntry.COLUMN_RECEIVER_ID);
                int indexMessage = cursor.getColumnIndex(ChatContract.ChatEntry.COLUMN_MESSAGE);
                if (indexId >= 0 && indexSenderId >= 0 && indexReceiverId >= 0 && indexMessage >= 0) {
                    chats.add(new Chat(
                            cursor.getInt(indexId),
                            cursor.getInt(indexSenderId),
                            cursor.getInt(indexReceiverId),
                            cursor.getString(indexMessage)
                    ));
                }
            }
            return chats;
        } catch (Exception e) {
            Log.e(ChatContract.ChatRepository.TAG, ChatContract.ChatRepository.GET_CHATS_BY_SENDER_ID_AND_RECEIVER_ID_ERR, e);
            return null;
        }
    }


    public List<Chat> getChatsBySenderIdAndReceiverId(int senderId, int receiverId) {
        String[] projection = {
                ChatContract.ChatEntry._ID,
                ChatContract.ChatEntry.COLUMN_SENDER_ID,
                ChatContract.ChatEntry.COLUMN_RECEIVER_ID,
                ChatContract.ChatEntry.COLUMN_MESSAGE
        };
        String selection = ChatContract.ChatEntry.COLUMN_SENDER_ID + " = ? AND " + ChatContract.ChatEntry.COLUMN_RECEIVER_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(senderId), String.valueOf(receiverId)};
        List<Chat> chats = new ArrayList<>();
        try (Cursor cursor = db.query(
                ChatContract.ChatEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {
            while (cursor.moveToNext()) {
                int indexId = cursor.getColumnIndex(ChatContract.ChatEntry._ID);
                int indexSenderId = cursor.getColumnIndex(ChatContract.ChatEntry.COLUMN_SENDER_ID);
                int indexReceiverId = cursor.getColumnIndex(ChatContract.ChatEntry.COLUMN_RECEIVER_ID);
                int indexMessage = cursor.getColumnIndex(ChatContract.ChatEntry.COLUMN_MESSAGE);
                if (indexId >= 0 && indexSenderId >= 0 && indexReceiverId >= 0 && indexMessage >= 0) {
                    chats.add(new Chat(
                            cursor.getInt(indexId),
                            cursor.getInt(indexSenderId),
                            cursor.getInt(indexReceiverId),
                            cursor.getString(indexMessage)
                    ));
                }
            }
            return chats;
        } catch (Exception e) {
            Log.e(ChatContract.ChatRepository.TAG, ChatContract.ChatRepository.GET_CHATS_BY_SENDER_ID_AND_RECEIVER_ID_ERR, e);
            return null;
        }
    }

}
