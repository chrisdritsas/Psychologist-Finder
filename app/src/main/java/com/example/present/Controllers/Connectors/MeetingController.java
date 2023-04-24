package com.example.present.Controllers.Connectors;

import com.example.present.App.Present;
import com.example.present.Controllers.ConnectionResult;
import com.example.present.Helpers.Helper;
import com.example.present.Models.Contracts.Database.ChatContract;
import com.example.present.Models.Contracts.Database.MeetingContract;
import com.example.present.Models.Contracts.Database.UserContract;
import com.example.present.Models.Contracts.General.VariableContract;
import com.example.present.Models.Entities.Meeting;
import com.example.present.Models.Entities.User;
import com.example.present.Models.Repositories.MeetingRepository;

import java.util.ArrayList;
import java.util.List;

public class MeetingController extends Helper {
    MeetingRepository meetingRepo = new MeetingRepository();
    UserController userController = new UserController();

    public ConnectionResult insertMeeting(Meeting meeting) {
        ConnectionResult result = new ConnectionResult(MeetingContract.MeetingController.TAG);
        User userSession = Present.getInstance().getUserSession();
        meeting.fixStrings();

        if (isTextLengthInvalid(meeting.getDateTime(), VariableContract.DateTime.MIN_LENGTH, VariableContract.DateTime.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.DateTime.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(meeting.getPatientDescription(), VariableContract.MeetingDescription.MIN_LENGTH, VariableContract.MeetingDescription.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.MeetingDescription.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (userSession.getId() == -1) {
            result.setResult(false);
            result.setMessage(UserContract.UserController.USER_NOT_LOGGED_ERR);
            result.setMessageType(1);
            return result;
        }
        if (userSession.getIsDoctor()) {
            result.setResult(false);
            result.setMessage(UserContract.UserController.USER_NOT_PATIENT_MSG);
            result.setMessageType(1);
            return result;
        }
        meeting.setPatientId(userSession.getId());
        meeting.setDoctorDescription("");
        meeting.setState(0);
        if (!userController.CheckUserExistsById(meeting.getDoctorId()).getResult()) {
            result.setResult(false);
            result.setMessage(MeetingContract.MeetingController.DOCTOR_NOT_EXISTS_MSG);
            result.setMessageType(1);
        }
        if (meetingRepo.insertMeeting(meeting)) {
            result.setResult(true);
            result.setMessage(MeetingContract.MeetingController.INSERT_MEETING_SUCC);
            result.setMessageType(0);
        } else {
            result.setResult(false);
            result.setMessage(MeetingContract.MeetingController.INSERT_MEETING_ERR);
            result.setMessageType(2);
        }
        return result;
    }

    public ConnectionResult insertMeetingAndReturnId(Meeting meeting) {
        ConnectionResult result = new ConnectionResult(MeetingContract.MeetingController.TAG);
        User userSession = Present.getInstance().getUserSession();
        meeting.fixStrings();

        if (isTextLengthInvalid(meeting.getDateTime(), VariableContract.DateTime.MIN_LENGTH, VariableContract.DateTime.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.DateTime.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(meeting.getPatientDescription(), VariableContract.MeetingDescription.MIN_LENGTH, VariableContract.MeetingDescription.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.MeetingDescription.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (userSession.getId() == -1) {
            result.setResult(false);
            result.setMessage(UserContract.UserController.USER_NOT_LOGGED_ERR);
            result.setMessageType(1);
            return result;
        }
        if (userSession.getIsDoctor()) {
            result.setResult(false);
            result.setMessage(UserContract.UserController.USER_NOT_PATIENT_MSG);
            result.setMessageType(1);
            return result;
        }
        meeting.setPatientId(userSession.getId());
        meeting.setDoctorDescription("");
        meeting.setState(0);
        if (!userController.CheckUserExistsById(meeting.getDoctorId()).getResult()) {
            result.setResult(false);
            result.setMessage(MeetingContract.MeetingController.DOCTOR_NOT_EXISTS_MSG);
            result.setMessageType(1);
        }
        int id = meetingRepo.insertMeetingAndReturnId(meeting);
        if (id!=-1) {
            result.setResult(true);
            result.setObj(id);
            result.setMessage(MeetingContract.MeetingController.INSERT_MEETING_SUCC);
            result.setMessageType(0);
        } else {
            result.setResult(false);
            result.setMessage(MeetingContract.MeetingController.INSERT_MEETING_ERR);
            result.setMessageType(2);
        }
        return result;
    }

    public ConnectionResult updateMeetingById(Meeting newMeeting) {
        ConnectionResult result = new ConnectionResult(MeetingContract.MeetingController.TAG);
        User userSession = Present.getInstance().getUserSession();
        Meeting oldMeeting;

        if (isTextLengthInvalid(newMeeting.getDateTime(), VariableContract.DateTime.MIN_LENGTH, VariableContract.DateTime.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.DateTime.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(newMeeting.getPatientDescription(), VariableContract.MeetingDescription.MIN_LENGTH, VariableContract.MeetingDescription.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.MeetingDescription.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(newMeeting.getDoctorDescription(), VariableContract.MeetingDescription.MIN_LENGTH, VariableContract.MeetingDescription.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.MeetingDescription.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isStateValueInvalid(newMeeting.getState(), userSession.getIsDoctor()).getResult()) {
            result.setResult(false);
            result.setMessage(isStateValueInvalid(newMeeting.getState(), userSession.getIsDoctor()).getMessage());
            result.setMessageType(1);
            return result;
        }
        if (userSession.getId() == -1) {
            result.setResult(false);
            result.setMessage(UserContract.UserController.USER_NOT_LOGGED_ERR);
            result.setMessageType(1);
            return result;
        }
        oldMeeting = meetingRepo.getMeetingById(newMeeting.getId());
        if (oldMeeting == null) {
            result.setResult(false);
            result.setMessage(MeetingContract.MeetingController.GET_MEETING_ERR);
            result.setMessageType(2);
            return result;
        }

        if (oldMeeting.getState() == 2 || oldMeeting.getState() == 3) {
            result.setResult(false);
            result.setMessage(MeetingContract.MeetingController.MEETING_LOCK_MSG);
            result.setMessageType(1);
            return result;
        }
        if (userSession.getIsDoctor()) {
            newMeeting.setPatientDescription(oldMeeting.getPatientDescription());
        } else {
            newMeeting.setDoctorDescription(oldMeeting.getDoctorDescription());
        }
        if (meetingRepo.updateMeetingById(newMeeting)) {
            result.setResult(true);
            result.setMessage(MeetingContract.MeetingController.UPDATE_MEETING_SUCC);
            result.setMessageType(0);
        } else {
            result.setResult(false);
            result.setMessage(MeetingContract.MeetingController.UPDATE_MEETING_ERR);
            result.setMessageType(2);
        }
        return result;
    }

    public ConnectionResult getUserMeetingList() {
        ConnectionResult result = new ConnectionResult(MeetingContract.MeetingController.TAG);
        User userSession = Present.getInstance().getUserSession();
        List<Meeting> meetingIds;
        if (userSession.getId() == -1) {
            result.setResult(false);
            result.setMessage(UserContract.UserController.USER_NOT_LOGGED_ERR);
            result.setMessageType(1);
            return result;
        }
        if (userSession.getIsDoctor()) {
            meetingIds = meetingRepo.getMeetingsByDoctorId(userSession.getId());
            if (meetingIds != null) {
                result.setResult(true);
                result.setMessage(MeetingContract.MeetingController.GET_DOCTOR_MEETINGS_SUCC);
                result.setObj(meetingIds);
                result.setMessageType(0);
            } else {
                result.setResult(false);
                result.setMessage(MeetingContract.MeetingController.GET_DOCTOR_MEETINGS_ERR);
                result.setMessageType(2);
            }
        } else {
            meetingIds = meetingRepo.getMeetingsByPatientId(userSession.getId());
            if (meetingIds != null) {
                result.setResult(true);
                result.setMessage(MeetingContract.MeetingController.GET_PATIENT_MEETINGS_SUCC);
                result.setObj(meetingIds);
                result.setMessageType(0);
            } else {
                result.setResult(false);
                result.setMessage(MeetingContract.MeetingController.GET_PATIENT_MEETINGS_ERR);
                result.setMessageType(2);
            }
        }
        return result;
    }

    public ConnectionResult getMeetingById(int meetingId) {
        ConnectionResult result = new ConnectionResult(MeetingContract.MeetingController.TAG);
        User userSession = Present.getInstance().getUserSession();
        Meeting meeting;
        if (userSession.getId() == -1) {
            result.setResult(false);
            result.setMessage(UserContract.UserController.USER_NOT_LOGGED_ERR);
            result.setMessageType(1);
            return result;
        }
        meeting = meetingRepo.getMeetingById(meetingId);
        if (meeting == null) {
            result.setResult(false);
            result.setMessage(MeetingContract.MeetingController.GET_MEETING_ERR);
            result.setMessageType(2);

        }
        else{
            result.setResult(true);
            result.setMessage(MeetingContract.MeetingController.GET_MEETING_SUCC);
            result.setMessageType(2);
            result.setObj(meeting);
        }
        return result;
    }
}
