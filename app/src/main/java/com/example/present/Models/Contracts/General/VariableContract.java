package com.example.present.Models.Contracts.General;

public final class VariableContract {

    public static class EmailEntry {
        public static final int MIN_LENGTH = 5;
        public static final int MAX_LENGTH = 64;
        public static final String LENGTH_ERROR_MSG = "Email must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters long.";
        public static final String INVALID_FORMAT_MSG = "Invalid email address.";
        public static final String INVALID_USED_MSG = "Email is already linked with an account.";
    }

    public static class PasswordEntry {

        public static final int MIN_LENGTH = 6;
        public static final int MAX_LENGTH = 30;
        public static final String LENGTH_ERROR_MSG = "Password must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters long.";
        public static final String INVALID_CONFIRM_MSG = "Passwords don't match.";
    }

    public static class IsDoctorEntry{
        public static final String INVALID_INPUT_MSG = "Type must be patient or doctor.";

    }

    public static class NameEntry {
        public static final int MIN_LENGTH = 2;
        public static final int MAX_LENGTH = 16;
        public static final String LENGTH_ERROR_MSG = "Name must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters long.";

    }

    public static class SurnameEntry {
        public static final int MIN_LENGTH = 2;
        public static final int MAX_LENGTH = 20;
        public static final String LENGTH_ERROR_MSG = "Surname must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters long.";
    }

    public static class AddressEntry {
        public static final int MIN_LENGTH = 3;
        public static final int MAX_LENGTH = 50;
        public static final String LENGTH_ERROR_MSG = "Address must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters long.";
    }

    public static class AddressCodeEntry {
        public static final int MIN_LENGTH = 2;
        public static final int MAX_LENGTH = 10;
        public static final String LENGTH_ERROR_MSG = "Address code must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters long.";
    }

    public static class CityEntry {
        public static final int MIN_LENGTH = 5;
        public static final int MAX_LENGTH = 20;
        public static final String LENGTH_ERROR_MSG = "City must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters long.";
    }

    public static class TelephoneEntry {
        public static final int MIN_LENGTH = 5;
        public static final int MAX_LENGTH = 20;
        public static final String LENGTH_ERROR_MSG = "Telephone number must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters long.";
    }

    public static class MessageEntry {
        public static final int MIN_LENGTH = 1;
        public static final int MAX_LENGTH = 200;
        public static final String LENGTH_ERROR_MSG = "Message must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters long.";
    }

    public static class MobileEntry {

        public static final int MIN_LENGTH = 5;
        public static final int MAX_LENGTH = 20;
        public static final String LENGTH_ERROR_MSG = "Mobile number must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters long.";

    }

    public static class BiographyEntry {

        public static final int MIN_LENGTH = 1;
        public static final int MAX_LENGTH = 1000;
        public static final String LENGTH_ERROR_MSG = "Biography number must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters long.";
    }

    public static class WorkHoursEntry {

        public static final int MIN_LENGTH = 1;
        public static final int MAX_LENGTH = 100;
        public static final String LENGTH_ERROR_MSG = "Working hours must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters long.";
    }

    public static class OnlinePrice {
        public static final double MIN_VALUE = 0.1d;
        public static final double MAX_VALUE = 99999.99d;
        public static final String VALUE_ERROR_MSG = "Online price must be between " + MIN_VALUE + " € and " + MAX_VALUE + " €.";
    }

    public static class MeetingPrice {
        public static final double MIN_VALUE = 0.1d;
        public static final double MAX_VALUE = 99999.99d;
        public static final String VALUE_ERROR_MSG = "Meeting price must be between " + MIN_VALUE + " € and " + MAX_VALUE + " €.";
    }

    public static class DateTime {
        public static final int MIN_LENGTH = 5;
        public static final int MAX_LENGTH = 100;
        public static final String LENGTH_ERROR_MSG = "Date must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters long.";
    }

    public static class MeetingDescription {
        public static final int MIN_LENGTH = 0;
        public static final int MAX_LENGTH = 200;
        public static final String LENGTH_ERROR_MSG = "Meeting description must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters long.";
    }

    public static class MeetingState{
        public static final int PENDING = 0;
        public static final int APPROVED = 1;
        public static final int CANCELED_BY_PATIENT = 2;
        public static final int CANCELED_BY_DOCTOR = 3;
        public static final String INVALID_PATIENT_VALUE = "Invalid patient meeting state.";
        public static final String INVALID_DOCTOR_VALUE = "Invalid doctor meeting state.";
    }
}
