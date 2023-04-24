package com.example.present.Controllers.Connectors;

import com.example.present.App.Present;
import com.example.present.Controllers.ConnectionResult;
import com.example.present.Helpers.Helper;
import com.example.present.Models.Contracts.Database.OfficeContract;
import com.example.present.Models.Contracts.Database.UserContract;
import com.example.present.Models.Contracts.Database.UserDataContract;
import com.example.present.Models.Contracts.General.VariableContract;
import com.example.present.Models.Entities.Office;
import com.example.present.Models.Entities.User;
import com.example.present.Models.Repositories.OfficeRepository;
import com.example.present.Models.Repositories.UserDataRepository;

public class OfficeController extends Helper {

    OfficeRepository officeRepo = new OfficeRepository();
    UserDataRepository userDataRepo = new UserDataRepository();

    public ConnectionResult insertOffice(Office office) {
        ConnectionResult result = new ConnectionResult(OfficeContract.OfficeController.TAG);
        User userSession = Present.getInstance().getUserSession();
        office.fixStrings();

        if (isTextLengthInvalid(office.getAddress(), VariableContract.AddressEntry.MIN_LENGTH, VariableContract.AddressEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.AddressEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(office.getAddressCode(), VariableContract.AddressCodeEntry.MIN_LENGTH, VariableContract.AddressCodeEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.AddressCodeEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(office.getPhone(), VariableContract.TelephoneEntry.MIN_LENGTH, VariableContract.TelephoneEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.TelephoneEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(office.getMobile(), VariableContract.MobileEntry.MIN_LENGTH, VariableContract.MobileEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.MobileEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(office.getBiography(), VariableContract.BiographyEntry.MIN_LENGTH, VariableContract.BiographyEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.BiographyEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(office.getWorkHours(), VariableContract.WorkHoursEntry.MIN_LENGTH, VariableContract.WorkHoursEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.WorkHoursEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isDoubleLimitInvalid(office.getOnlinePrice(), VariableContract.OnlinePrice.MIN_VALUE, VariableContract.OnlinePrice.MAX_VALUE)) {
            result.setResult(false);
            result.setMessage(VariableContract.OnlinePrice.VALUE_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isDoubleLimitInvalid(office.getMeetingPrice(), VariableContract.MeetingPrice.MIN_VALUE, VariableContract.MeetingPrice.MAX_VALUE)) {
            result.setResult(false);
            result.setMessage(VariableContract.MeetingPrice.VALUE_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (userSession.getId() == -1) {
            result.setResult(false);
            result.setMessage(UserContract.UserController.USER_NOT_LOGGED_ERR);
            result.setMessageType(1);
            return result;
        }
        if (!userSession.getIsDoctor()) {
            result.setResult(false);
            result.setMessage(UserContract.UserController.USER_NOT_DOCTOR_MSG);
            result.setMessageType(1);
            return result;
        }
        Integer userDataId = userDataRepo.getUserDataIdByUserId(userSession.getId());
        if (userDataId == -1) {
            result.setResult(false);
            result.setMessage(UserDataContract.UserDataController.GET_USERDATA_ERR);
            result.setMessageType(1);
            return result;
        } else if (userDataId == null) {
            result.setResult(false);
            result.setMessage(UserDataContract.UserDataController.GET_USERDATA_ERROR);
            result.setMessageType(2);
            return result;
        }
        if (officeRepo.checkOfficeExistsByUserDataId(userDataId)) {
            result.setResult(false);
            result.setMessage(OfficeContract.OfficeController.OFFICE_EXISTS_MSG);
            result.setMessageType(1);
            return result;
        }
        office.setViews(0);
        office.setUserDataId(userDataId);
        if (officeRepo.insertOffice(office)) {
            result.setResult(true);
            result.setMessage(OfficeContract.OfficeController.INSERT_OFFICE_SUCC);
            result.setMessageType(0);
        } else {
            result.setResult(false);
            result.setMessage(OfficeContract.OfficeController.INSERT_OFFICE_ERR);
            result.setMessageType(2);
        }
        return result;
    }

    public ConnectionResult checkInsertOffice(Office office) {
        ConnectionResult result = new ConnectionResult(OfficeContract.OfficeController.TAG);
        office.fixStrings();

        if (isTextLengthInvalid(office.getAddress(), VariableContract.AddressEntry.MIN_LENGTH, VariableContract.AddressEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.AddressEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(office.getAddressCode(), VariableContract.AddressCodeEntry.MIN_LENGTH, VariableContract.AddressCodeEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.AddressCodeEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(office.getPhone(), VariableContract.TelephoneEntry.MIN_LENGTH, VariableContract.TelephoneEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.TelephoneEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(office.getMobile(), VariableContract.MobileEntry.MIN_LENGTH, VariableContract.MobileEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.MobileEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(office.getBiography(), VariableContract.BiographyEntry.MIN_LENGTH, VariableContract.BiographyEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.BiographyEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(office.getWorkHours(), VariableContract.WorkHoursEntry.MIN_LENGTH, VariableContract.WorkHoursEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.WorkHoursEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isDoubleLimitInvalid(office.getOnlinePrice(), VariableContract.OnlinePrice.MIN_VALUE, VariableContract.OnlinePrice.MAX_VALUE)) {
            result.setResult(false);
            result.setMessage(VariableContract.OnlinePrice.VALUE_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isDoubleLimitInvalid(office.getMeetingPrice(), VariableContract.MeetingPrice.MIN_VALUE, VariableContract.MeetingPrice.MAX_VALUE)) {
            result.setResult(false);
            result.setMessage(VariableContract.MeetingPrice.VALUE_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        result.setResult(true);
        result.setMessage(OfficeContract.OfficeController.OFFICE_SUCC);
        result.setMessageType(0);
        return result;
    }

    public ConnectionResult updateOffice(Office office) {
        ConnectionResult result = new ConnectionResult(OfficeContract.OfficeController.TAG);
        User userSession = Present.getInstance().getUserSession();
        office.fixStrings();

        if (isTextLengthInvalid(office.getAddress(), VariableContract.AddressEntry.MIN_LENGTH, VariableContract.AddressEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.AddressEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(office.getAddressCode(), VariableContract.AddressCodeEntry.MIN_LENGTH, VariableContract.AddressCodeEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.AddressCodeEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(office.getPhone(), VariableContract.TelephoneEntry.MIN_LENGTH, VariableContract.TelephoneEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.TelephoneEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(office.getMobile(), VariableContract.MobileEntry.MIN_LENGTH, VariableContract.MobileEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.MobileEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(office.getBiography(), VariableContract.BiographyEntry.MIN_LENGTH, VariableContract.BiographyEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.BiographyEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(office.getWorkHours(), VariableContract.WorkHoursEntry.MIN_LENGTH, VariableContract.WorkHoursEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.WorkHoursEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isDoubleLimitInvalid(office.getOnlinePrice(), VariableContract.OnlinePrice.MIN_VALUE, VariableContract.OnlinePrice.MAX_VALUE)) {
            result.setResult(false);
            result.setMessage(VariableContract.OnlinePrice.VALUE_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isDoubleLimitInvalid(office.getMeetingPrice(), VariableContract.MeetingPrice.MIN_VALUE, VariableContract.MeetingPrice.MAX_VALUE)) {
            result.setResult(false);
            result.setMessage(VariableContract.MeetingPrice.VALUE_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (userSession.getId() == -1) {
            result.setResult(false);
            result.setMessage(UserContract.UserController.USER_NOT_LOGGED_ERR);
            result.setMessageType(1);
            return result;
        }
        if (!userSession.getIsDoctor()) {
            result.setResult(false);
            result.setMessage(UserContract.UserController.USER_NOT_DOCTOR_MSG);
            result.setMessageType(1);
            return result;
        }
        Integer userDataId = userDataRepo.getUserDataIdByUserId(userSession.getId());
        if (userDataId == -1) {
            result.setResult(false);
            result.setMessage(UserDataContract.UserDataController.GET_USERDATA_ERR);
            result.setMessageType(1);
            return result;
        } else if (userDataId == null) {
            result.setResult(false);
            result.setMessage(UserDataContract.UserDataController.GET_USERDATA_ERROR);
            result.setMessageType(2);
            return result;
        }
        office.setUserDataId(userDataId);
        if (officeRepo.updateOffice(office)) {
            result.setResult(true);
            result.setMessage(OfficeContract.OfficeController.UPDATE_OFFICE_SUCC);
            result.setMessageType(0);
        } else {
            result.setResult(false);
            result.setMessage(OfficeContract.OfficeController.UPDATE_OFFICE_ERR);
            result.setMessageType(2);
        }
        return result;
    }

    public ConnectionResult getUserSessionOfficeData() {
        ConnectionResult result = new ConnectionResult(OfficeContract.OfficeController.TAG);
        User userSession = Present.getInstance().getUserSession();
        Office office;
        if (userSession.getId() == -1) {
            result.setResult(false);
            result.setMessage(UserContract.UserController.USER_NOT_LOGGED_ERR);
            result.setMessageType(1);
            return result;
        }
        if (!userSession.getIsDoctor()) {
            result.setResult(false);
            result.setMessage(UserContract.UserController.USER_NOT_DOCTOR_MSG);
            result.setMessageType(1);
            return result;
        }
        Integer userDataId = userDataRepo.getUserDataIdByUserId(userSession.getId());
        if (userDataId == -1) {
            result.setResult(false);
            result.setMessage(UserDataContract.UserDataController.GET_USERDATA_ERR);
            result.setMessageType(1);
            return result;
        } else if (userDataId == null) {
            result.setResult(false);
            result.setMessage(UserDataContract.UserDataController.GET_USERDATA_ERROR);
            result.setMessageType(2);
            return result;
        }
        office = officeRepo.getOfficeByUserDataId(userDataId);
        if (office != null) {
            result.setResult(true);
            result.setObj(office);
            result.setMessage(OfficeContract.OfficeController.GET_OFFICE_SUCC);
            result.setMessageType(0);
        } else {
            result.setResult(false);
            result.setMessage(OfficeContract.OfficeController.GET_OFFICE_ERR);
            result.setMessageType(2);
        }
        return result;
    }

    public ConnectionResult increaseOfficeViewsByOfficeId(int officeId) {
        ConnectionResult result = new ConnectionResult(OfficeContract.OfficeController.TAG);
        User userSession = Present.getInstance().getUserSession();
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
        if (officeId < 0) {
            result.setResult(false);
            result.setMessage(OfficeContract.OfficeController.OFFICE_ID_WRONG);
            result.setMessageType(1);
            return result;
        }
        if (officeRepo.increaseViewsByOfficeId(officeId)) {
            result.setResult(true);
            result.setMessage(OfficeContract.OfficeController.OFFICE_VIEW_INC_SUCC);
            result.setMessageType(0);
        } else {
            result.setResult(false);
            result.setMessage(OfficeContract.OfficeController.OFFICE_VIEW_INC_ERR);
            result.setMessageType(2);
        }
        return result;
    }

    public ConnectionResult getOfficeViewsByOfficeId(int officeId) {
        ConnectionResult result = new ConnectionResult(OfficeContract.OfficeController.TAG);
        User userSession = Present.getInstance().getUserSession();
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
        if (officeId < 0) {
            result.setResult(false);
            result.setMessage(OfficeContract.OfficeController.OFFICE_ID_WRONG);
            result.setMessageType(1);
            return result;
        }
        int views = officeRepo.getOfficeViewsByOfficeId(officeId);
        if (views == -1) {
            result.setResult(false);
            result.setMessage(OfficeContract.OfficeController.GET_OFFICE_VIEWS_ERR);
            result.setMessageType(2);
        } else {
            result.setResult(true);
            result.setMessage(OfficeContract.OfficeController.GET_OFFICE_VIEWS_SUCC);
            result.setMessageType(0);
            result.setObj(views);
        }
        return result;
    }
}
