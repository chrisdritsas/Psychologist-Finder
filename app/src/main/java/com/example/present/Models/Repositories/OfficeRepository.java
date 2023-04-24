package com.example.present.Models.Repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.present.App.Present;
import com.example.present.Models.Contracts.Database.OfficeContract;
import com.example.present.Models.Entities.Office;

public class OfficeRepository {
    static SQLiteDatabase db = Present.getInstance().getDatabase();

    public boolean insertOffice(Office office) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(OfficeContract.OfficeEntry.COLUMN_USER_DATA_ID, office.getUserDataId());
        contentValues.put(OfficeContract.OfficeEntry.COLUMN_VIEWS, office.getViews());
        contentValues.put(OfficeContract.OfficeEntry.COLUMN_ADDRESS, office.getAddress());
        contentValues.put(OfficeContract.OfficeEntry.COLUMN_ADDRESS_CODE, office.getAddressCode());
        contentValues.put(OfficeContract.OfficeEntry.COLUMN_PHONE, office.getPhone());
        contentValues.put(OfficeContract.OfficeEntry.COLUMN_MOBILE, office.getMobile());
        contentValues.put(OfficeContract.OfficeEntry.COLUMN_BIOGRAPHY, office.getBiography());
        contentValues.put(OfficeContract.OfficeEntry.COLUMN_WORK_HOURS, office.getWorkHours());
        contentValues.put(OfficeContract.OfficeEntry.COLUMN_ONLINE_PRICE, office.getOnlinePrice());
        contentValues.put(OfficeContract.OfficeEntry.COLUMN_MEETING_PRICE, office.getMeetingPrice());
        try {
            db.beginTransaction();
            long result = db.insert(
                    OfficeContract.OfficeEntry.TABLE_NAME,
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
            Log.e(OfficeContract.OfficeRepository.TAG, OfficeContract.OfficeRepository.INSERT_OFFICE_ERR);
            return false;
        } finally {
            db.endTransaction();
        }
    }

    public boolean updateOffice(Office office) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(OfficeContract.OfficeEntry.COLUMN_ADDRESS, office.getAddress());
        contentValues.put(OfficeContract.OfficeEntry.COLUMN_ADDRESS_CODE, office.getAddressCode());
        contentValues.put(OfficeContract.OfficeEntry.COLUMN_PHONE, office.getPhone());
        contentValues.put(OfficeContract.OfficeEntry.COLUMN_MOBILE, office.getMobile());
        contentValues.put(OfficeContract.OfficeEntry.COLUMN_BIOGRAPHY, office.getBiography());
        contentValues.put(OfficeContract.OfficeEntry.COLUMN_WORK_HOURS, office.getWorkHours());
        contentValues.put(OfficeContract.OfficeEntry.COLUMN_ONLINE_PRICE, office.getOnlinePrice());
        contentValues.put(OfficeContract.OfficeEntry.COLUMN_MEETING_PRICE, office.getMeetingPrice());
        String[] selectionArgs = {String.valueOf(office.getUserDataId())};
        String selection = OfficeContract.OfficeEntry.COLUMN_USER_DATA_ID + " = ?";
        try {
            db.beginTransaction();
            int result = db.update(
                    OfficeContract.OfficeEntry.TABLE_NAME,
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
            Log.e(OfficeContract.OfficeRepository.TAG, OfficeContract.OfficeRepository.UPDATE_OFFICE_ERR, e);
            return false;
        } finally {
            db.endTransaction();
        }
    }

    public Office getOfficeByUserDataId(int userDataId) {
        String[] projection = {
                OfficeContract.OfficeEntry.COLUMN_USER_DATA_ID,
                OfficeContract.OfficeEntry.COLUMN_VIEWS,
                OfficeContract.OfficeEntry.COLUMN_ADDRESS,
                OfficeContract.OfficeEntry.COLUMN_ADDRESS_CODE,
                OfficeContract.OfficeEntry.COLUMN_PHONE,
                OfficeContract.OfficeEntry.COLUMN_MOBILE,
                OfficeContract.OfficeEntry.COLUMN_BIOGRAPHY,
                OfficeContract.OfficeEntry.COLUMN_WORK_HOURS,
                OfficeContract.OfficeEntry.COLUMN_ONLINE_PRICE,
                OfficeContract.OfficeEntry.COLUMN_MEETING_PRICE
        };
        String selection = OfficeContract.OfficeEntry.COLUMN_USER_DATA_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(userDataId)};
        Office office = null;
        try (Cursor cursor = db.query(
                OfficeContract.OfficeEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {
            if (cursor.moveToFirst()) {
                int indexUserDataId = cursor.getColumnIndex(OfficeContract.OfficeEntry.COLUMN_USER_DATA_ID);
                int indexViews = cursor.getColumnIndex(OfficeContract.OfficeEntry.COLUMN_VIEWS);
                int indexAddress = cursor.getColumnIndex(OfficeContract.OfficeEntry.COLUMN_ADDRESS);
                int indexAddressCode = cursor.getColumnIndex(OfficeContract.OfficeEntry.COLUMN_ADDRESS_CODE);
                int indexPhone = cursor.getColumnIndex(OfficeContract.OfficeEntry.COLUMN_PHONE);
                int indexMobile = cursor.getColumnIndex(OfficeContract.OfficeEntry.COLUMN_MOBILE);
                int indexBiography = cursor.getColumnIndex(OfficeContract.OfficeEntry.COLUMN_BIOGRAPHY);
                int indexWorkHours = cursor.getColumnIndex(OfficeContract.OfficeEntry.COLUMN_WORK_HOURS);
                int indexOnlinePrice = cursor.getColumnIndex(OfficeContract.OfficeEntry.COLUMN_ONLINE_PRICE);
                int indexMeetingPrice = cursor.getColumnIndex(OfficeContract.OfficeEntry.COLUMN_MEETING_PRICE);
                if (indexUserDataId >= 0 && indexViews >= 0 && indexAddress >= 0 && indexAddressCode >= 0 && indexPhone >= 0 && indexMobile >= 0 && indexBiography >= 0 && indexWorkHours >= 0 && indexOnlinePrice >= 0 && indexMeetingPrice >= 0) {
                    office = new Office(
                            cursor.getInt(indexUserDataId),
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
                }
            }
            return office;
        } catch (Exception e) {
            Log.e(OfficeContract.OfficeRepository.TAG, OfficeContract.OfficeRepository.GET_OFFICE_BY_USER_DATA_ID_ERR, e);
            return null;
        }
    }

    public boolean checkOfficeExistsByUserDataId(int userDataId) {
        String selection = OfficeContract.OfficeEntry.COLUMN_USER_DATA_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userDataId)};
        try (Cursor cursor = db.query(
                OfficeContract.OfficeEntry.TABLE_NAME,
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
            Log.e(OfficeContract.OfficeRepository.TAG, OfficeContract.OfficeRepository.CHECK_OFFICE_EXISTS_BY_USER_DATA_ID_ERR, e);
            return false;
        }
    }


    public int getOfficeViewsByOfficeId(int officeId) {
        String[] selectionArgs = {String.valueOf(officeId)};
        String[] projection = {OfficeContract.OfficeEntry.COLUMN_VIEWS};
        String selection = OfficeContract.OfficeEntry._ID + " = ?";
        try {
            db.beginTransaction();
            Cursor cursor = db.query(
                    OfficeContract.OfficeEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
            if (cursor.moveToFirst()) {
                int viewsIndex = cursor.getColumnIndex(OfficeContract.OfficeEntry.COLUMN_VIEWS);
                if (viewsIndex >= 0) {
                    int views = cursor.getInt(viewsIndex);
                    if (views >= 0) {
                        db.setTransactionSuccessful();
                        return views;
                    }
                }
            }
            return -1;
        } catch (Exception e) {
            Log.e(OfficeContract.OfficeRepository.TAG, OfficeContract.OfficeRepository.GET_OFFICE_VIEWS_ERR, e);
            return -1;
        } finally {
            db.endTransaction();
        }
    }

    public boolean increaseViewsByOfficeId(int officeId) {
        String[] selectionArgs = {String.valueOf(officeId)};
        String[] projection = {OfficeContract.OfficeEntry.COLUMN_VIEWS};
        String selection = OfficeContract.OfficeEntry._ID + " = ?";
        try {
            db.beginTransaction();
            // get the current value of the COLUMN_VIEWS using a query
            Cursor cursor = db.query(
                    OfficeContract.OfficeEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
            if (cursor.moveToFirst()) {
                int viewsIndex = cursor.getColumnIndex(OfficeContract.OfficeEntry.COLUMN_VIEWS);
                if (viewsIndex >= 0) {
                    int views = cursor.getInt(viewsIndex);
                    // increment the value of the COLUMN_VIEWS by 1
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(OfficeContract.OfficeEntry.COLUMN_VIEWS, views + 1);
                    int result = db.update(
                            OfficeContract.OfficeEntry.TABLE_NAME,
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
                }
            }
            return false;
        } catch (Exception e) {
            Log.e(OfficeContract.OfficeRepository.TAG, OfficeContract.OfficeRepository.INC_OFFICE_VIEWS_ERR, e);
            return false;
        } finally {
            db.endTransaction();
        }
    }


    public boolean increaseViewsByOfficeIdOld(int officeId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(OfficeContract.OfficeEntry.COLUMN_VIEWS, OfficeContract.OfficeEntry.COLUMN_VIEWS + " + 1");
        String[] selectionArgs = {String.valueOf(officeId)};
        String selection = OfficeContract.OfficeEntry._ID + " = ?";
        try {
            db.beginTransaction();
            int result = db.update(
                    OfficeContract.OfficeEntry.TABLE_NAME,
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
            Log.e(OfficeContract.OfficeRepository.TAG, OfficeContract.OfficeRepository.INC_OFFICE_VIEWS_ERR, e);
            return false;
        } finally {
            db.endTransaction();
        }
    }

}
