package com.example.present.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.present.Models.Contracts.Database.ChatContract;
import com.example.present.Models.Contracts.Database.MeetingContract;
import com.example.present.Models.Contracts.Database.NotificationContract;
import com.example.present.Models.Contracts.Database.OfficeContract;
import com.example.present.Models.Contracts.Database.UserContract;
import com.example.present.Models.Contracts.Database.UserDataContract;


public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Present_Database";
    private static final int DATABASE_VERSION = 20;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("CREATE TABLE " + UserContract.UserEntry.TABLE_NAME + " ("
               + UserContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
               + UserContract.UserEntry.COLUMN_EMAIL + " TEXT NOT NULL, "
               + UserContract.UserEntry.COLUMN_PASSWORD + " TEXT NOT NULL,"
               + UserContract.UserEntry.COLUMN_IS_DOCTOR + " BOOLEAN NOT NULL)");

        db.execSQL("CREATE TABLE " + UserDataContract.UserDataEntry.TABLE_NAME + " ("
                + UserDataContract.UserDataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UserDataContract.UserDataEntry.COLUMN_USER_ID + " INTEGER NOT NULL, "
                + UserDataContract.UserDataEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + UserDataContract.UserDataEntry.COLUMN_SURNAME + " TEXT NOT NULL, "
                + UserDataContract.UserDataEntry.COLUMN_ADDRESS + " TEXT NOT NULL, "
                + UserDataContract.UserDataEntry.COLUMN_ADDRESS_CODE + " TEXT NOT NULL, "
                + UserDataContract.UserDataEntry.COLUMN_CITY + " TEXT NOT NULL, "
                + UserDataContract.UserDataEntry.COLUMN_TELEPHONE + " TEXT NOT NULL,"
                + UserDataContract.UserDataEntry.COLUMN_PROFILE_PICTURE_PATH + " TEXT, "
                + " FOREIGN KEY (" + UserDataContract.UserDataEntry.COLUMN_USER_ID + ") REFERENCES user(id))");

        db.execSQL("CREATE TABLE " + OfficeContract.OfficeEntry.TABLE_NAME + " ("
                + OfficeContract.OfficeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + OfficeContract.OfficeEntry.COLUMN_VIEWS + " INTEGER NOT NULL, "
                + OfficeContract.OfficeEntry.COLUMN_USER_DATA_ID + " INTEGER NOT NULL, "
                + OfficeContract.OfficeEntry.COLUMN_ADDRESS + " TEXT NOT NULL, "
                + OfficeContract.OfficeEntry.COLUMN_ADDRESS_CODE + " TEXT NOT NULL, "
                + OfficeContract.OfficeEntry.COLUMN_PHONE + " TEXT NOT NULL, "
                + OfficeContract.OfficeEntry.COLUMN_MOBILE + " TEXT NOT NULL, "
                + OfficeContract.OfficeEntry.COLUMN_BIOGRAPHY + " TEXT NOT NULL, "
                + OfficeContract.OfficeEntry.COLUMN_WORK_HOURS + " TEXT NOT NULL, "
                + OfficeContract.OfficeEntry.COLUMN_ONLINE_PRICE + " REAL NOT NULL, "
                + OfficeContract.OfficeEntry.COLUMN_MEETING_PRICE + " REAL NOT NULL, "
                + " FOREIGN KEY (" + OfficeContract.OfficeEntry.COLUMN_USER_DATA_ID + ") REFERENCES user_data(id))");

        db.execSQL("CREATE TABLE " + ChatContract.ChatEntry.TABLE_NAME + "("
                + ChatContract.ChatEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ChatContract.ChatEntry.COLUMN_SENDER_ID + " INTEGER NOT NULL,"
                + ChatContract.ChatEntry.COLUMN_RECEIVER_ID + " INTEGER NOT NULL,"
                + ChatContract.ChatEntry.COLUMN_MESSAGE + " TEXT NOT NULL, "
                + " FOREIGN KEY(" + ChatContract.ChatEntry.COLUMN_SENDER_ID + ") REFERENCES user(user_id),"
                + " FOREIGN KEY(" + ChatContract.ChatEntry.COLUMN_RECEIVER_ID + ") REFERENCES user(user_id))");

        db.execSQL("CREATE TABLE " + MeetingContract.MeetingEntry.TABLE_NAME + "("
                + MeetingContract.MeetingEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MeetingContract.MeetingEntry.COLUMN_PATIENT_ID + " INTEGER NOT NULL,"
                + MeetingContract.MeetingEntry.COLUMN_DOCTOR_ID + " INTEGER NOT NULL,"
                + MeetingContract.MeetingEntry.COLUMN_DATE_TIME + " TEXT NOT NULL,"
                + MeetingContract.MeetingEntry.COLUMN_STATE + " INTEGER NOT NULL,"
                + MeetingContract.MeetingEntry.COLUMN_PATIENT_DESCRIPTION + " TEXT,"
                + MeetingContract.MeetingEntry.COLUMN_DOCTOR_DESCRIPTION + " TEXT,"
                + " FOREIGN KEY(" + MeetingContract.MeetingEntry.COLUMN_PATIENT_ID + ") REFERENCES user(user_id),"
                + " FOREIGN KEY(" + MeetingContract.MeetingEntry.COLUMN_DOCTOR_ID + ") REFERENCES user(user_id))");

        db.execSQL("CREATE TABLE " + NotificationContract.Entry.TABLE_NAME + "("
                + NotificationContract.Entry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NotificationContract.Entry.COLUMN_REQUEST_CODE + " INTEGER NOT NULL,"
                + NotificationContract.Entry.COLUMN_CREATOR_ID + " INTEGER NOT NULL,"
                + NotificationContract.Entry.COLUMN_RECEIVER_ID + " INTEGER NOT NULL,"
                + NotificationContract.Entry.COLUMN_ACTION_TARGET + " TEXT NOT NULL,"
                + NotificationContract.Entry.COLUMN_CHAT_SENDER_ID + " INTEGER NOT NULL,"
                + NotificationContract.Entry.COLUMN_MEETING_ID + " INTEGER NOT NULL,"
                + NotificationContract.Entry.COLUMN_CONTENT_TITLE + " TEXT NOT NULL,"
                + NotificationContract.Entry.COLUMN_CONTENT_TEXT + " TEXT NOT NULL,"
                + NotificationContract.Entry.COLUMN_STATE + " TEXT NOT NULL,"
                + " FOREIGN KEY(" + NotificationContract.Entry.COLUMN_CREATOR_ID + ") REFERENCES user(user_id),"
                + " FOREIGN KEY(" + NotificationContract.Entry.COLUMN_RECEIVER_ID + ") REFERENCES user(user_id))");

        Log.d("DataBaseHelper","Created new db!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + UserContract.UserEntry.TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + UserDataContract.UserDataEntry.TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + OfficeContract.OfficeEntry.TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + ChatContract.ChatEntry.TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + MeetingContract.MeetingEntry.TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + NotificationContract.Entry.TABLE_NAME);
        Log.d("DataBaseHelper","Dropped existing db!");
        onCreate(db);
    }

}
