package com.example.present.Models.Repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.present.App.Present;
import com.example.present.Models.Contracts.Database.OfficeContract;
import com.example.present.Models.Contracts.Database.UserContract;
import com.example.present.Models.Contracts.Database.UserDataContract;
import com.example.present.Models.Entities.Office;
import com.example.present.Models.Entities.UserData;

import java.util.ArrayList;
import java.util.List;

public class UserDataRepository {

    static SQLiteDatabase db = Present.getInstance().getDatabase();

    public boolean insertUserData(UserData userData) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserDataContract.UserDataEntry.COLUMN_USER_ID, userData.getUserId());
        contentValues.put(UserDataContract.UserDataEntry.COLUMN_NAME, userData.getName());
        contentValues.put(UserDataContract.UserDataEntry.COLUMN_SURNAME, userData.getSurname());
        contentValues.put(UserDataContract.UserDataEntry.COLUMN_ADDRESS, userData.getAddress());
        contentValues.put(UserDataContract.UserDataEntry.COLUMN_ADDRESS_CODE, userData.getAddressCode());
        contentValues.put(UserDataContract.UserDataEntry.COLUMN_CITY, userData.getCity());
        contentValues.put(UserDataContract.UserDataEntry.COLUMN_TELEPHONE, userData.getTelephone());
        try {
            db.beginTransaction();
            long result = db.insert(
                    UserDataContract.UserDataEntry.TABLE_NAME,
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
            Log.e(UserDataContract.UserDataRepository.TAG, UserDataContract.UserDataRepository.INSERT_USER_DATA_ERR, e);
            return false;
        } finally {
            db.endTransaction();
        }
    }

    public boolean updateUserData(UserData userData) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserDataContract.UserDataEntry.COLUMN_NAME, userData.getName());
        contentValues.put(UserDataContract.UserDataEntry.COLUMN_SURNAME, userData.getSurname());
        contentValues.put(UserDataContract.UserDataEntry.COLUMN_ADDRESS, userData.getAddress());
        contentValues.put(UserDataContract.UserDataEntry.COLUMN_ADDRESS_CODE, userData.getAddressCode());
        contentValues.put(UserDataContract.UserDataEntry.COLUMN_CITY, userData.getCity());
        contentValues.put(UserDataContract.UserDataEntry.COLUMN_TELEPHONE, userData.getTelephone());
        contentValues.put(UserDataContract.UserDataEntry.COLUMN_PROFILE_PICTURE_PATH, userData.getProfilePicture());
        String[] selectionArgs = {String.valueOf(userData.getUserId())};
        String selection = UserDataContract.UserDataEntry.COLUMN_USER_ID + " = ?";
        try {
            db.beginTransaction();
            int result = db.update(
                    UserDataContract.UserDataEntry.TABLE_NAME,
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
            Log.e(UserDataContract.UserDataRepository.TAG, UserDataContract.UserDataRepository.UPDATE_USER_DATA_ERR, e);
            return false;
        } finally {
            db.endTransaction();
        }
    }

    public UserData getUserDataByUserId(int userId) {
        String[] projection = {
                UserDataContract.UserDataEntry._ID,
                UserDataContract.UserDataEntry.COLUMN_USER_ID,
                UserDataContract.UserDataEntry.COLUMN_NAME,
                UserDataContract.UserDataEntry.COLUMN_SURNAME,
                UserDataContract.UserDataEntry.COLUMN_ADDRESS,
                UserDataContract.UserDataEntry.COLUMN_ADDRESS_CODE,
                UserDataContract.UserDataEntry.COLUMN_CITY,
                UserDataContract.UserDataEntry.COLUMN_TELEPHONE,
                UserDataContract.UserDataEntry.COLUMN_PROFILE_PICTURE_PATH,
        };
        String selection = UserDataContract.UserDataEntry.COLUMN_USER_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(userId)};
        UserData userData = null;
        try (Cursor cursor = db.query(
                UserDataContract.UserDataEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {
            if (cursor.moveToFirst()) {
                int indexId = cursor.getColumnIndex(UserDataContract.UserDataEntry._ID);
                int indexUserId = cursor.getColumnIndex(UserDataContract.UserDataEntry.COLUMN_USER_ID);
                int indexName = cursor.getColumnIndex(UserDataContract.UserDataEntry.COLUMN_NAME);
                int indexSurname = cursor.getColumnIndex(UserDataContract.UserDataEntry.COLUMN_SURNAME);
                int indexAddress = cursor.getColumnIndex(UserDataContract.UserDataEntry.COLUMN_ADDRESS);
                int indexAddressCode = cursor.getColumnIndex(UserDataContract.UserDataEntry.COLUMN_ADDRESS_CODE);
                int indexCity = cursor.getColumnIndex(UserDataContract.UserDataEntry.COLUMN_CITY);
                int indexTelephone = cursor.getColumnIndex(UserDataContract.UserDataEntry.COLUMN_TELEPHONE);
                int indexProfilePic = cursor.getColumnIndex(UserDataContract.UserDataEntry.COLUMN_PROFILE_PICTURE_PATH);
                if (indexId >= 0 && indexUserId >= 0 && indexName >= 0 && indexSurname >= 0 && indexAddress >= 0 && indexAddressCode >= 0 && indexCity >= 0 && indexTelephone >= 0 && indexProfilePic >= 0) {
                    userData = new UserData(
                            cursor.getInt(indexUserId),
                            cursor.getString(indexName),
                            cursor.getString(indexSurname),
                            cursor.getString(indexAddress),
                            cursor.getString(indexAddressCode),
                            cursor.getString(indexCity),
                            cursor.getString(indexTelephone),
                            cursor.getString(indexProfilePic)
                    );
                }
            }
            return userData;
        } catch (Exception e) {
            Log.e(UserDataContract.UserDataRepository.TAG, UserDataContract.UserDataRepository.GET_USER_DATA_BY_USER_ID_ERR, e);
            return null;
        }
    }

    public UserData getUserDataNameSurnameByUserId(int userId) {
        String[] projection = {
                UserDataContract.UserDataEntry.COLUMN_NAME,
                UserDataContract.UserDataEntry.COLUMN_SURNAME,
        };
        String selection = UserDataContract.UserDataEntry.COLUMN_USER_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(userId)};
        UserData userData = null;
        try (Cursor cursor = db.query(
                UserDataContract.UserDataEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {
            if (cursor.moveToFirst()) {
                int indexName = cursor.getColumnIndex(UserDataContract.UserDataEntry.COLUMN_NAME);
                int indexSurname = cursor.getColumnIndex(UserDataContract.UserDataEntry.COLUMN_SURNAME);
                if (indexName >= 0 && indexSurname >= 0) {
                    userData = new UserData(
                            cursor.getString(indexName),
                            cursor.getString(indexSurname)
                    );
                }
            }
            return userData;
        } catch (Exception e) {
            Log.e(UserDataContract.UserDataRepository.TAG, UserDataContract.UserDataRepository.GET_USER_DATA_NAME_SURNAME_BY_USER_ID_ERR, e);
            return null;
        }
    }

    public boolean checkUserDataExistsByUserId(int userId) {
        String selection = UserDataContract.UserDataEntry.COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};
        try (Cursor cursor = db.query(
                UserDataContract.UserDataEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {
            int count = cursor.getCount();
            if (count > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            Log.e(UserDataContract.UserDataRepository.TAG, UserDataContract.UserDataRepository.CHECK_USER_DATA_EXISTS_BY_USER_ID_ERR, e);
            return false;
        }
    }

    public Integer getUserDataIdByUserId(int userId) {
        String[] projection = {
                UserDataContract.UserDataEntry._ID,
        };
        String selection = UserDataContract.UserDataEntry.COLUMN_USER_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(userId)};
        int userDataId = -1;
        try (Cursor cursor = db.query(
                UserDataContract.UserDataEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {
            if (cursor.moveToFirst()) {
                int indexId = cursor.getColumnIndex(UserDataContract.UserDataEntry._ID);
                if (indexId >= 0) {
                    userDataId = cursor.getInt(indexId);
                }
            }
            return userDataId;
        } catch (Exception e) {
            Log.e(UserDataContract.UserDataRepository.TAG, UserDataContract.UserDataRepository.GET_USER_DATA_ID_BY_USER_ID_ERR, e);
            return null;
        }
    }

    public List<UserData> getListOfUserDataAndOfficeOfUsersDoctor() {
        String[] projection = {
                "UD." + UserDataContract.UserDataEntry._ID,
                "UD." + UserDataContract.UserDataEntry.COLUMN_USER_ID,
                "UD." + UserDataContract.UserDataEntry.COLUMN_NAME,
                "UD." + UserDataContract.UserDataEntry.COLUMN_SURNAME,
                "UD." + UserDataContract.UserDataEntry.COLUMN_CITY,
                "UD." + UserDataContract.UserDataEntry.COLUMN_PROFILE_PICTURE_PATH,
                "O." + OfficeContract.OfficeEntry._ID,
                "O." + OfficeContract.OfficeEntry.COLUMN_VIEWS,
                "O." + OfficeContract.OfficeEntry.COLUMN_ADDRESS,
                "O." + OfficeContract.OfficeEntry.COLUMN_ADDRESS_CODE,
                "O." + OfficeContract.OfficeEntry.COLUMN_PHONE,
                "O." + OfficeContract.OfficeEntry.COLUMN_MOBILE,
                "O." + OfficeContract.OfficeEntry.COLUMN_BIOGRAPHY,
                "O." + OfficeContract.OfficeEntry.COLUMN_WORK_HOURS,
                "O." + OfficeContract.OfficeEntry.COLUMN_ONLINE_PRICE,
                "O." + OfficeContract.OfficeEntry.COLUMN_MEETING_PRICE
        };
        String selection = "U." + UserContract.UserEntry.COLUMN_IS_DOCTOR + " = 1";
        String[] selectionArgs = null;
        List<UserData> doctorsData = new ArrayList<>();
        try (Cursor cursor =
                     db.query(
                             UserContract.UserEntry.TABLE_NAME + " U INNER JOIN " + UserDataContract.UserDataEntry.TABLE_NAME + " UD ON "
                                     + "UD." + UserDataContract.UserDataEntry.COLUMN_USER_ID + " = " + "U." + UserContract.UserEntry._ID
                                     + " INNER JOIN " + OfficeContract.OfficeEntry.TABLE_NAME + " O ON "
                                     + "UD." + UserDataContract.UserDataEntry._ID + " = " + "O." + OfficeContract.OfficeEntry.COLUMN_USER_DATA_ID,
                             projection,
                             selection,
                             selectionArgs,
                             null,
                             null,
                             null
                     )) {
            int indexId = cursor.getColumnIndex(UserDataContract.UserDataEntry._ID);
            int indexUserId = cursor.getColumnIndex(UserDataContract.UserDataEntry.COLUMN_USER_ID);
            int indexName = cursor.getColumnIndex(UserDataContract.UserDataEntry.COLUMN_NAME);
            int indexSurname = cursor.getColumnIndex(UserDataContract.UserDataEntry.COLUMN_SURNAME);
            int indexCity = cursor.getColumnIndex(UserDataContract.UserDataEntry.COLUMN_CITY);
            int indexProfilePic = cursor.getColumnIndex(UserDataContract.UserDataEntry.COLUMN_PROFILE_PICTURE_PATH);
            int indexOfficeId = cursor.getColumnIndex(OfficeContract.OfficeEntry._ID);
            int indexViews = cursor.getColumnIndex(OfficeContract.OfficeEntry.COLUMN_VIEWS);
            int indexAddress = cursor.getColumnIndex(OfficeContract.OfficeEntry.COLUMN_ADDRESS);
            int indexAddressCode = cursor.getColumnIndex(OfficeContract.OfficeEntry.COLUMN_ADDRESS_CODE);
            int indexPhone = cursor.getColumnIndex(OfficeContract.OfficeEntry.COLUMN_PHONE);
            int indexMobile = cursor.getColumnIndex(OfficeContract.OfficeEntry.COLUMN_MOBILE);
            int indexBiography = cursor.getColumnIndex(OfficeContract.OfficeEntry.COLUMN_BIOGRAPHY);
            int indexWorkHours = cursor.getColumnIndex(OfficeContract.OfficeEntry.COLUMN_WORK_HOURS);
            int indexOnlinePrice = cursor.getColumnIndex(OfficeContract.OfficeEntry.COLUMN_ONLINE_PRICE);
            int indexMeetingPrice = cursor.getColumnIndex(OfficeContract.OfficeEntry.COLUMN_MEETING_PRICE);
            while (cursor.moveToNext()) {
                if (indexId >= 0 && indexUserId >= 0 && indexName >= 0 && indexSurname >= 0 && indexCity >= 0 && indexProfilePic >= 0 &&
                        indexAddress >= 0 && indexViews >= 0 && indexAddressCode >= 0 && indexPhone >= 0 && indexMobile >= 0 &&
                        indexBiography >= 0 && indexWorkHours >= 0 && indexOnlinePrice >= 0 && indexMeetingPrice >= 0) {
                    Office office = new Office(
                            cursor.getInt(indexOfficeId),
                            cursor.getInt(indexId),
                            cursor.getInt(indexViews),
                            cursor.getString(indexAddress),
                            cursor.getString(indexAddressCode),
                            cursor.getString(indexPhone),
                            cursor.getString(indexMobile),
                            cursor.getString(indexBiography),
                            cursor.getString(indexWorkHours),
                            cursor.getDouble(indexOnlinePrice),
                            cursor.getDouble(indexMeetingPrice)
                    );

                    UserData userData = new UserData(
                            cursor.getInt(indexId),
                            cursor.getInt(indexUserId),
                            cursor.getString(indexName),
                            cursor.getString(indexSurname),
                            cursor.getString(indexCity),
                            cursor.getString(indexProfilePic),
                            office
                    );
                    doctorsData.add(userData);
                }
            }
            return doctorsData;
        } catch (Exception e) {
            Log.e(UserDataContract.UserDataRepository.TAG, UserDataContract.UserDataRepository.GET_USER_DATA_AND_OFFICE_OF_DOCTORS, e);
            return null;
        }
    }


}
