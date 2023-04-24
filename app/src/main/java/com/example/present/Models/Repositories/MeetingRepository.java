package com.example.present.Models.Repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.present.App.Present;
import com.example.present.Models.Contracts.Database.MeetingContract;
import com.example.present.Models.Entities.Meeting;

import java.util.ArrayList;
import java.util.List;

public class MeetingRepository {

    static SQLiteDatabase db = Present.getInstance().getDatabase();

    public boolean insertMeeting(Meeting meeting) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MeetingContract.MeetingEntry.COLUMN_PATIENT_ID, meeting.getPatientId());
        contentValues.put(MeetingContract.MeetingEntry.COLUMN_DOCTOR_ID, meeting.getDoctorId());
        contentValues.put(MeetingContract.MeetingEntry.COLUMN_STATE, meeting.getState());
        contentValues.put(MeetingContract.MeetingEntry.COLUMN_DATE_TIME, meeting.getDateTime());
        contentValues.put(MeetingContract.MeetingEntry.COLUMN_PATIENT_DESCRIPTION, meeting.getPatientDescription());
        contentValues.put(MeetingContract.MeetingEntry.COLUMN_DOCTOR_DESCRIPTION, meeting.getDoctorDescription());
        try {
            db.beginTransaction();
            long result = db.insert(
                    MeetingContract.MeetingEntry.TABLE_NAME,
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
            Log.e(MeetingContract.MeetingRepository.TAG, MeetingContract.MeetingRepository.INSERT_MEETING_ERR);
            return false;
        } finally {
            db.endTransaction();
        }
    }

    public int insertMeetingAndReturnId(Meeting meeting) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MeetingContract.MeetingEntry.COLUMN_PATIENT_ID, meeting.getPatientId());
        contentValues.put(MeetingContract.MeetingEntry.COLUMN_DOCTOR_ID, meeting.getDoctorId());
        contentValues.put(MeetingContract.MeetingEntry.COLUMN_STATE, meeting.getState());
        contentValues.put(MeetingContract.MeetingEntry.COLUMN_DATE_TIME, meeting.getDateTime());
        contentValues.put(MeetingContract.MeetingEntry.COLUMN_PATIENT_DESCRIPTION, meeting.getPatientDescription());
        contentValues.put(MeetingContract.MeetingEntry.COLUMN_DOCTOR_DESCRIPTION, meeting.getDoctorDescription());

        try {
            db.beginTransaction();
            long result = db.insert(
                    MeetingContract.MeetingEntry.TABLE_NAME,
                    null,
                    contentValues
            );
            if (result == -1) {
                return -1;
            } else {
                db.setTransactionSuccessful();
                return (int)result; // return the inserted row ID
            }
        } catch (Exception e) {
            Log.e(MeetingContract.MeetingRepository.TAG, MeetingContract.MeetingRepository.INSERT_MEETING_ERR);
            return -1;
        } finally {
            db.endTransaction();
        }
    }

    public boolean updateMeetingById(Meeting meeting) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MeetingContract.MeetingEntry.COLUMN_STATE, meeting.getState());
        //contentValues.put(MeetingContract.MeetingEntry.COLUMN_DATE_TIME, meeting.getDateTime());
        contentValues.put(MeetingContract.MeetingEntry.COLUMN_PATIENT_DESCRIPTION, meeting.getPatientDescription());
        contentValues.put(MeetingContract.MeetingEntry.COLUMN_DOCTOR_DESCRIPTION, meeting.getDoctorDescription());
        String[] selectionArgs = {String.valueOf(meeting.getId())};
        String selection = MeetingContract.MeetingEntry._ID + " = ?";
        try {
            db.beginTransaction();
            int result = db.update(
                    MeetingContract.MeetingEntry.TABLE_NAME,
                    contentValues,
                    selection,
                    selectionArgs
            );
            if (result == 1) {
                db.setTransactionSuccessful();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            Log.e(MeetingContract.MeetingRepository.TAG, MeetingContract.MeetingRepository.UPDATE_MEETING_BY_ID_ERR, e);
            return false;
        } finally {
            db.endTransaction();
        }
    }

    public Meeting getMeetingById(int meetingId) {
        String[] projection = {
                MeetingContract.MeetingEntry._ID,
                MeetingContract.MeetingEntry.COLUMN_PATIENT_ID,
                MeetingContract.MeetingEntry.COLUMN_DOCTOR_ID,
                MeetingContract.MeetingEntry.COLUMN_STATE,
                MeetingContract.MeetingEntry.COLUMN_DATE_TIME,
                MeetingContract.MeetingEntry.COLUMN_PATIENT_DESCRIPTION,
                MeetingContract.MeetingEntry.COLUMN_DOCTOR_DESCRIPTION
        };
        String selection = MeetingContract.MeetingEntry._ID + " = ? ";
        String[] selectionArgs = {String.valueOf(meetingId)};
        Meeting meeting = null;
        try (Cursor cursor = db.query(
                MeetingContract.MeetingEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {
            if (cursor.moveToFirst()) {
                int indexId = cursor.getColumnIndex(MeetingContract.MeetingEntry._ID);
                int indexPatientId = cursor.getColumnIndex(MeetingContract.MeetingEntry.COLUMN_PATIENT_ID);
                int indexDoctorId = cursor.getColumnIndex(MeetingContract.MeetingEntry.COLUMN_DOCTOR_ID);
                int indexState = cursor.getColumnIndex(MeetingContract.MeetingEntry.COLUMN_STATE);
                int indexDateTime = cursor.getColumnIndex(MeetingContract.MeetingEntry.COLUMN_DATE_TIME);
                int indexPatientDescription = cursor.getColumnIndex(MeetingContract.MeetingEntry.COLUMN_PATIENT_DESCRIPTION);
                int indexDoctorDescription = cursor.getColumnIndex(MeetingContract.MeetingEntry.COLUMN_DOCTOR_DESCRIPTION);
                if (indexId >= 0 && indexPatientId >= 0 && indexDoctorId >= 0 && indexState >= 0 && indexDateTime >= 0 && indexPatientDescription >= 0 && indexDoctorDescription >= 0) {
                    meeting = new Meeting(
                            cursor.getInt(indexId),
                            cursor.getInt(indexPatientId),
                            cursor.getInt(indexDoctorId),
                            cursor.getInt(indexState),
                            cursor.getString(indexDateTime),
                            cursor.getString(indexPatientDescription),
                            cursor.getString(indexDoctorDescription)
                    );
                }
            }
            return meeting;
        } catch (Exception e) {
            Log.e(MeetingContract.MeetingRepository.TAG, MeetingContract.MeetingRepository.GET_MEETING_BY_ID_ERR, e);
            return null;
        }
    }

    public List<Meeting> getMeetingsByPatientId(int patientId) {
        String[] projection = {
                MeetingContract.MeetingEntry._ID,
                MeetingContract.MeetingEntry.COLUMN_PATIENT_ID,
                MeetingContract.MeetingEntry.COLUMN_DOCTOR_ID,
                MeetingContract.MeetingEntry.COLUMN_STATE,
                MeetingContract.MeetingEntry.COLUMN_DATE_TIME,
                MeetingContract.MeetingEntry.COLUMN_PATIENT_DESCRIPTION,
                MeetingContract.MeetingEntry.COLUMN_DOCTOR_DESCRIPTION
        };
        String selection = MeetingContract.MeetingEntry.COLUMN_PATIENT_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(patientId)};
        List<Meeting> meetings = new ArrayList<>();
        try (Cursor cursor = db.query(
                MeetingContract.MeetingEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {
            while (cursor.moveToNext()) {
                int indexId = cursor.getColumnIndex(MeetingContract.MeetingEntry._ID);
                int indexPatientId = cursor.getColumnIndex(MeetingContract.MeetingEntry.COLUMN_PATIENT_ID);
                int indexDoctorId = cursor.getColumnIndex(MeetingContract.MeetingEntry.COLUMN_DOCTOR_ID);
                int indexState = cursor.getColumnIndex(MeetingContract.MeetingEntry.COLUMN_STATE);
                int indexDateTime = cursor.getColumnIndex(MeetingContract.MeetingEntry.COLUMN_DATE_TIME);
                int indexPatientDescription = cursor.getColumnIndex(MeetingContract.MeetingEntry.COLUMN_PATIENT_DESCRIPTION);
                int indexDoctorDescription = cursor.getColumnIndex(MeetingContract.MeetingEntry.COLUMN_DOCTOR_DESCRIPTION);
                if (indexId >= 0 && indexPatientId >= 0 && indexDoctorId >= 0 && indexState >= 0 && indexDateTime >= 0 && indexPatientDescription >= 0 && indexDoctorDescription >= 0) {
                    Meeting meeting = new Meeting(
                            cursor.getInt(indexId),
                            cursor.getInt(indexPatientId),
                            cursor.getInt(indexDoctorId),
                            cursor.getInt(indexState),
                            cursor.getString(indexDateTime),
                            cursor.getString(indexPatientDescription),
                            cursor.getString(indexDoctorDescription)
                    );
                    meetings.add(meeting);
                }
            }
            return meetings;
        } catch (Exception e) {
            Log.e(MeetingContract.MeetingRepository.TAG, MeetingContract.MeetingRepository.GET_MEETINGS_BY_PATIENT_ID_ERR, e);
            return null;
        }
    }

    public List<Meeting> getMeetingsByDoctorId(int doctorId) {
        String[] projection = {
                MeetingContract.MeetingEntry._ID,
                MeetingContract.MeetingEntry.COLUMN_PATIENT_ID,
                MeetingContract.MeetingEntry.COLUMN_DOCTOR_ID,
                MeetingContract.MeetingEntry.COLUMN_STATE,
                MeetingContract.MeetingEntry.COLUMN_DATE_TIME,
                MeetingContract.MeetingEntry.COLUMN_PATIENT_DESCRIPTION,
                MeetingContract.MeetingEntry.COLUMN_DOCTOR_DESCRIPTION
        };
        String selection = MeetingContract.MeetingEntry.COLUMN_DOCTOR_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(doctorId)};
        List<Meeting> meetings = new ArrayList<>();
        try (Cursor cursor = db.query(
                MeetingContract.MeetingEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {
            while (cursor.moveToNext()) {
                int indexId = cursor.getColumnIndex(MeetingContract.MeetingEntry._ID);
                int indexPatientId = cursor.getColumnIndex(MeetingContract.MeetingEntry.COLUMN_PATIENT_ID);
                int indexDoctorId = cursor.getColumnIndex(MeetingContract.MeetingEntry.COLUMN_DOCTOR_ID);
                int indexState = cursor.getColumnIndex(MeetingContract.MeetingEntry.COLUMN_STATE);
                int indexDateTime = cursor.getColumnIndex(MeetingContract.MeetingEntry.COLUMN_DATE_TIME);
                int indexPatientDescription = cursor.getColumnIndex(MeetingContract.MeetingEntry.COLUMN_PATIENT_DESCRIPTION);
                int indexDoctorDescription = cursor.getColumnIndex(MeetingContract.MeetingEntry.COLUMN_DOCTOR_DESCRIPTION);
                if (indexId >= 0 && indexPatientId >= 0 && indexDoctorId >= 0 && indexState >= 0 && indexDateTime >= 0 && indexPatientDescription >= 0 && indexDoctorDescription >= 0) {
                    meetings.add(new Meeting(
                            cursor.getInt(indexId),
                            cursor.getInt(indexPatientId),
                            cursor.getInt(indexDoctorId),
                            cursor.getInt(indexState),
                            cursor.getString(indexDateTime),
                            cursor.getString(indexPatientDescription),
                            cursor.getString(indexDoctorDescription)
                    ));
                }
            }
            return meetings;
        } catch (Exception e) {
            Log.e(MeetingContract.MeetingRepository.TAG, MeetingContract.MeetingRepository.GET_MEETINGS_BY_DOCTOR_ID_ERR, e);
            return null;
        }
    }


    public List<Integer> getDistinctDoctorMeetingIdsByPatientId(int patientId) {
        String[] projection = {
                MeetingContract.MeetingEntry.COLUMN_DOCTOR_ID,
        };
        String selection = MeetingContract.MeetingEntry.COLUMN_PATIENT_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(patientId)};
        List<Integer> meetingIds = new ArrayList<>();
        try (Cursor cursor = db.query(
                MeetingContract.MeetingEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null,
                null
        )) {
            while (cursor.moveToNext()) {
                int indexId = cursor.getColumnIndex(MeetingContract.MeetingEntry.COLUMN_DOCTOR_ID);
                if (indexId >= 0) {
                    int meetingId = cursor.getInt(indexId);
                    if (!meetingIds.contains(meetingId)) {
                        meetingIds.add(meetingId);
                    }
                }
            }
            return meetingIds;
        } catch (Exception e) {
            Log.e(MeetingContract.MeetingRepository.TAG, MeetingContract.MeetingRepository.GET_DISTINCT_DOCTOR_MEETING_IDS_BY_PATIENT_ID_ERR, e);
            return null;
        }
    }

    public List<Integer> getDistinctPatientMeetingIdsByDoctorId(int doctorId) {
        String[] projection = {
                MeetingContract.MeetingEntry.COLUMN_PATIENT_ID,
        };
        String selection = MeetingContract.MeetingEntry.COLUMN_DOCTOR_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(doctorId)};
        List<Integer> meetingIds = new ArrayList<>();
        try (Cursor cursor = db.query(
                MeetingContract.MeetingEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null,
                null
        )) {
            while (cursor.moveToNext()) {
                int indexId = cursor.getColumnIndex(MeetingContract.MeetingEntry.COLUMN_PATIENT_ID);
                if (indexId >= 0) {
                    int meetingId = cursor.getInt(indexId);
                    if (!meetingIds.contains(meetingId)) {
                        meetingIds.add(meetingId);
                    }
                }
            }
            return meetingIds;
        } catch (Exception e) {
            Log.e(MeetingContract.MeetingRepository.TAG, MeetingContract.MeetingRepository.GET_DISTINCT_PATIENT_MEETING_IDS_BY_DOCTOR_ID_ERR, e);
            return null;
        }
    }

}
