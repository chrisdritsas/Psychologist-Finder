package com.example.present.Models.Repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.present.App.Present;
import com.example.present.Models.Contracts.Database.NotificationContract;
import com.example.present.Models.Entities.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationRepository {

    static SQLiteDatabase db = Present.getInstance().getDatabase();

    public boolean insertNotification(Notification notification) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NotificationContract.Entry.COLUMN_REQUEST_CODE, notification.getRequestCode());
        contentValues.put(NotificationContract.Entry.COLUMN_CREATOR_ID, notification.getCreatorId());
        contentValues.put(NotificationContract.Entry.COLUMN_RECEIVER_ID, notification.getReceiverId());
        contentValues.put(NotificationContract.Entry.COLUMN_ACTION_TARGET, notification.getAction());
        contentValues.put(NotificationContract.Entry.COLUMN_CHAT_SENDER_ID, notification.getChatSenderId());
        contentValues.put(NotificationContract.Entry.COLUMN_MEETING_ID, notification.getMeetingId());
        contentValues.put(NotificationContract.Entry.COLUMN_CONTENT_TITLE, notification.getContentTitle());
        contentValues.put(NotificationContract.Entry.COLUMN_CONTENT_TEXT, notification.getContentText());
        contentValues.put(NotificationContract.Entry.COLUMN_STATE, notification.getState());
        try {
            db.beginTransaction();
            long result = db.insert(
                    NotificationContract.Entry.TABLE_NAME,
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
            Log.e(NotificationContract.Repository.TAG, NotificationContract.Repository.INSERT_NOTIFICATION_ERR);
            return false;
        } finally {
            db.endTransaction();
        }
    }


    public List<Notification> getNotificationByReceiverIdAndState(int receiverId, int state) {
        String[] projection = {
                NotificationContract.Entry._ID,
                NotificationContract.Entry.COLUMN_REQUEST_CODE,
                NotificationContract.Entry.COLUMN_CREATOR_ID,
                NotificationContract.Entry.COLUMN_RECEIVER_ID,
                NotificationContract.Entry.COLUMN_ACTION_TARGET,
                NotificationContract.Entry.COLUMN_CHAT_SENDER_ID,
                NotificationContract.Entry.COLUMN_MEETING_ID,
                NotificationContract.Entry.COLUMN_CONTENT_TITLE,
                NotificationContract.Entry.COLUMN_CONTENT_TEXT,
                NotificationContract.Entry.COLUMN_STATE
        };
        String selection = NotificationContract.Entry.COLUMN_RECEIVER_ID + "=? AND " + NotificationContract.Entry.COLUMN_STATE + "=?";
        String[] selectionArgs = {String.valueOf(receiverId), String.valueOf(state)};
        List<Notification> notificationList = new ArrayList<>();

        try (Cursor cursor = db.query(
                NotificationContract.Entry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {
            while (cursor.moveToNext()) {
                int indexId = cursor.getColumnIndex(NotificationContract.Entry._ID);
                int indexRequestCode = cursor.getColumnIndex(NotificationContract.Entry.COLUMN_REQUEST_CODE);
                int indexCreatorId = cursor.getColumnIndex(NotificationContract.Entry.COLUMN_CREATOR_ID);
                int indexReceiverId = cursor.getColumnIndex(NotificationContract.Entry.COLUMN_RECEIVER_ID);
                int indexAction = cursor.getColumnIndex(NotificationContract.Entry.COLUMN_ACTION_TARGET);
                int indexChatSenderId = cursor.getColumnIndex(NotificationContract.Entry.COLUMN_CHAT_SENDER_ID);
                int indexMeetingId = cursor.getColumnIndex(NotificationContract.Entry.COLUMN_MEETING_ID);
                int indexContentTitle = cursor.getColumnIndex(NotificationContract.Entry.COLUMN_CONTENT_TITLE);
                int indexContentText = cursor.getColumnIndex(NotificationContract.Entry.COLUMN_CONTENT_TEXT);
                int indexState = cursor.getColumnIndex(NotificationContract.Entry.COLUMN_STATE);
                if (indexId >= 0 && indexRequestCode >= 0 && indexCreatorId >= 0 && indexReceiverId >= 0 && indexAction >= 0 &&
                        indexChatSenderId >= 0 && indexMeetingId >= 0 && indexContentTitle >= 0 && indexContentText >= 0 && indexState >= 0) {
                    Notification notification = new Notification();
                    notification.setId(cursor.getInt(indexId));
                    notification.setRequestCode(cursor.getInt(indexRequestCode));
                    notification.setCreatorId(cursor.getInt(indexCreatorId));
                    notification.setReceiverId(cursor.getInt(indexReceiverId));
                    notification.setAction(cursor.getString(indexAction));
                    notification.setChatSenderId(cursor.getInt(indexChatSenderId));
                    notification.setMeetingId(cursor.getInt(indexMeetingId));
                    notification.setContentTitle(cursor.getString(indexContentTitle));
                    notification.setContentText(cursor.getString(indexContentText));
                    notification.setState(cursor.getInt(indexState));
                    notificationList.add(notification);
                }

            }

        } catch (Exception e) {
            Log.e(NotificationContract.Repository.TAG, NotificationContract.Repository.GET_NOTIFICATIONS_ERR);
            return null;
        }
        return notificationList;
    }

    public boolean updateNotificationsState(List<Notification> notifications, int state) {
        try {
            db.beginTransaction();
            for (Notification notification : notifications) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(NotificationContract.Entry.COLUMN_STATE, state);
                String selection = NotificationContract.Entry._ID + "=?";
                String[] selectionArgs = {String.valueOf(notification.getId())};
                int updatedRows = db.update(
                        NotificationContract.Entry.TABLE_NAME,
                        contentValues,
                        selection,
                        selectionArgs
                );
                if (updatedRows != 1) {
                    throw new Exception(NotificationContract.Repository.UPDATE_NOTIFICATION_ERR);
                }
            }
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.e(NotificationContract.Repository.TAG, NotificationContract.Repository.UPDATE_NOTIFICATION_ERROR);
            return false;
        } finally {
            db.endTransaction();
        }
    }


}
