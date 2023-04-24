package com.example.present.Helpers;

import com.example.present.Controllers.ConnectionResult;
import com.example.present.Models.Contracts.Database.MeetingContract;
import com.example.present.Models.Contracts.General.VariableContract;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

    protected boolean isTextLengthInvalid(String text, int min, int max) {
        if (text == null) {
            return false;
        }
        if (text.length() <= max && text.length() >= min) {
            return false;
        }
        return true;
    }

    protected boolean isDoubleLimitInvalid(double number, double lowerValue, double maxValue) {
        if (number < lowerValue || number > maxValue) {
            return true;
        }
        return false;
    }

    protected boolean isBooleanInvalid(Boolean bool) {
        if (bool == null) {
            return true;
        }
        return false;
    }

    protected boolean isEmailInvalid(String email) {

        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(email);
        return !matcher.matches();
    }

    protected ConnectionResult isStateValueInvalid(int state, boolean isDoctor) {
        ConnectionResult result = new ConnectionResult(MeetingContract.MeetingController.TAG);
        if (isDoctor) {
            if (state != VariableContract.MeetingState.PENDING && state != VariableContract.MeetingState.APPROVED && state != VariableContract.MeetingState.CANCELED_BY_DOCTOR) {
                result.setResult(true);
                result.setMessage(VariableContract.MeetingState.INVALID_DOCTOR_VALUE);
                return result;
            }
        } else {
            if (state != VariableContract.MeetingState.PENDING && state != VariableContract.MeetingState.CANCELED_BY_PATIENT) {
                result.setResult(true);
                result.setMessage(VariableContract.MeetingState.INVALID_PATIENT_VALUE);
                return result;
            }
        }
        result.setResult(false);
        return result;
    }

    protected String textTrimmer(String text) {
        return text.trim();
    }


}
