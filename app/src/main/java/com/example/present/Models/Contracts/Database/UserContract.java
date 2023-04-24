package com.example.present.Models.Contracts.Database;

import android.provider.BaseColumns;

public final class UserContract {

    public static class UserEntry implements BaseColumns{
        public static final String TABLE_NAME = "User";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_IS_DOCTOR = "is_doctor";
    }

    public static class UserRepository{
        public static final String TAG = "UserRepo";
        public static final String INSERT_USER_ERR = "Error while inserting user into the database";
        public static final String UPDATE_USER_EMAIL_BY_ID_ERR = "Error while updating user email into the database";
        public static final String UPDATE_USER_PASSWORD_BY_ID_ERR = "Error while updating user password into the database";
        public static final String CHECK_USER_EXISTS_BY_ID_ERR = "Error while searching for user ID into the database";
        public static final String CHECK_USER_EXISTS_BY_EMAIL_ERR = "Error while searching for user email into the database";
        public static final String GET_USER_ID_BY_EMAIL_AND_PASSWORD_ERR = "Error while getting user id using email and password match from the database";
        public static final String GET_USER_BY_ID_ERR = "Error while getting user id using email and password match from the database";


    }

    public static class UserController{
        public static final String TAG = "UserCtrler";
        public static final String INSERT_USER_ERR="Couldn't insert user.";
        public static final String INSERT_USER_SUCC = "Successfully inserted user.";
        public static final String USER_NOT_LOGGED_ERR ="User is not logged in.";
        public static final String EMAIL_UPDATE_SUCC = "Successfully updated email.";
        public static final String EMAIL_UPDATE_ERR = "Couldn't update email.";
        public static final String PASSWORD_UPDATE_SUCC = "Password updated successfully.";
        public static final String PASSWORD_UPDATE_ERR = "Couldn't update password.";
        public static final String LOGIN_WRONG_CREDS = "Wrong credentials.";
        public static final String LOGIN_SUCC = "Login success.";
        public static final String LOGIN_ERR = "Couldn't retrieve log in data.";
        public static final String LOGOUT_SUCC = "Log out success.";
        public static final String LOGOUT_ERR = "Couldn't log out";
        public static final String USER_NOT_DOCTOR_MSG = "User is not a doctor.";
        public static final String USER_NOT_PATIENT_MSG = "User is not a patient.";
        public static final String USER_NOT_EXIST_ERR = "Receiver does not exist.";

        public static final String USER_SUCC = "Success.";

    }
}
