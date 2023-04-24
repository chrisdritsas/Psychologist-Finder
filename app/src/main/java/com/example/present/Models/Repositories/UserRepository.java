package com.example.present.Models.Repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.present.App.Present;
import com.example.present.Models.Contracts.Database.UserContract;
import com.example.present.Models.Entities.User;

public final class UserRepository {
    static SQLiteDatabase db = Present.getInstance().getDatabase();

    public UserRepository() {
    }

    public boolean insertUser(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.UserEntry.COLUMN_EMAIL, user.getEmail());
        contentValues.put(UserContract.UserEntry.COLUMN_PASSWORD, user.getPassword());
        contentValues.put(UserContract.UserEntry.COLUMN_IS_DOCTOR, user.getIsDoctor());
        try {
            db.beginTransaction();
            long result = db.insert(
                    UserContract.UserEntry.TABLE_NAME,
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
            Log.e(UserContract.UserRepository.TAG,UserContract.UserRepository.INSERT_USER_ERR , e);
            return false;
        } finally {
            db.endTransaction();
        }
    }

    public boolean updateUserEmailById(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.UserEntry.COLUMN_EMAIL, user.getEmail());
        String[] selectionArgs = {String.valueOf(user.getId())};
        String selection = UserContract.UserEntry._ID + " = ?";
        try {
            db.beginTransaction();
            int result = db.update(
                    UserContract.UserEntry.TABLE_NAME,
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
            Log.e(UserContract.UserRepository.TAG,UserContract.UserRepository.UPDATE_USER_EMAIL_BY_ID_ERR , e);
            return false;
        } finally {
            db.endTransaction();
        }
    }

    public boolean updateUserPasswordById(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.UserEntry.COLUMN_PASSWORD, user.getPassword());
        String[] selectionArgs = {String.valueOf(user.getId())};
        String selection = UserContract.UserEntry._ID + " = ?";
        try {
            db.beginTransaction();
            int result = db.update(
                    UserContract.UserEntry.TABLE_NAME,
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
            Log.e(UserContract.UserRepository.TAG,UserContract.UserRepository.UPDATE_USER_PASSWORD_BY_ID_ERR, e);
            return false;
        } finally {
            db.endTransaction();
        }
    }

    public boolean checkUserExistsById(int id) {
        String selection = UserContract.UserEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        try (Cursor cursor = db.query(
                UserContract.UserEntry.TABLE_NAME,
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
            Log.e(UserContract.UserRepository.TAG,UserContract.UserRepository.CHECK_USER_EXISTS_BY_ID_ERR, e);
            return false;
        }
    }

    public boolean checkUserExistsByEmail(String email) {
        String selection = UserContract.UserEntry.COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};
        try (Cursor cursor = db.query(
                UserContract.UserEntry.TABLE_NAME,
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
            Log.e(UserContract.UserRepository.TAG,UserContract.UserRepository.CHECK_USER_EXISTS_BY_EMAIL_ERR, e);
            return false;
        }
    }

    public int getUserIdByEmailAndPassword(User user) {
        int userId = -1;
        String selection = UserContract.UserEntry.COLUMN_EMAIL + " = ? AND " + UserContract.UserEntry.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {user.getEmail(), user.getPassword()};
        try (Cursor cursor = db.query(UserContract.UserEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {

            if (cursor.moveToFirst()) {
                int index = cursor.getColumnIndex(UserContract.UserEntry._ID);
                if (index >= 0) {
                    userId = cursor.getInt(index);
                }
            }
            return userId;
        } catch (Exception e) {
            Log.e(UserContract.UserRepository.TAG,UserContract.UserRepository.GET_USER_ID_BY_EMAIL_AND_PASSWORD_ERR , e);
            return -1;
        }
    }

    public User getUserById(int id) {
        String[] projection = {
                UserContract.UserEntry._ID,
                UserContract.UserEntry.COLUMN_EMAIL,
                UserContract.UserEntry.COLUMN_IS_DOCTOR
        };
        String selection = UserContract.UserEntry._ID + " = ? ";
        String[] selectionArgs = {String.valueOf(id)};

        User user = null;
        try (Cursor cursor =
                     db.query(
                             UserContract.UserEntry.TABLE_NAME,
                             projection,
                             selection,
                             selectionArgs,
                             null,
                             null,
                             null
                     )) {
            if (cursor.moveToFirst()) {
                int indexId = cursor.getColumnIndex(UserContract.UserEntry._ID);
                int indexEmail = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_EMAIL);
                int indexIsDoctor = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_IS_DOCTOR);
                if (indexId >= 0 && indexEmail >= 0 && indexIsDoctor >= 0) {
                    int userId = cursor.getInt(indexId);
                    String email = cursor.getString(indexEmail);
                    int isDoctor = cursor.getInt(indexIsDoctor);
                    user = new User(userId, email, null, isDoctor == 1);
                }
            }
            return user;
        } catch (Exception e) {
            Log.e(UserContract.UserRepository.TAG,UserContract.UserRepository.GET_USER_BY_ID_ERR , e);
            return null;
        }
    }
}
