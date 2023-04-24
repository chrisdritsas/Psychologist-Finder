package com.example.present.Controllers.Connectors;

import android.util.Log;

import com.example.present.App.Present;
import com.example.present.Controllers.ConnectionResult;
import com.example.present.Helpers.Helper;
import com.example.present.Models.Contracts.Database.UserContract;
import com.example.present.Models.Contracts.Database.UserDataContract;
import com.example.present.Models.Contracts.General.VariableContract;
import com.example.present.Models.Entities.User;
import com.example.present.Models.Entities.UserData;
import com.example.present.Models.Repositories.UserDataRepository;

import java.util.List;

public class UserDataController extends Helper {

   private final UserDataRepository userDataRepository = new UserDataRepository();
    private final UserController userController = new UserController();

    public ConnectionResult insertUserData(UserData userData) {
        ConnectionResult result = new ConnectionResult(UserDataContract.UserDataController.TAG);
        userData.fixStrings();
        User userSession = Present.getInstance().getUserSession();
        if (isTextLengthInvalid(userData.getName(), VariableContract.NameEntry.MIN_LENGTH, VariableContract.NameEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.NameEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(userData.getSurname(), VariableContract.SurnameEntry.MIN_LENGTH, VariableContract.SurnameEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.SurnameEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(userData.getAddress(), VariableContract.AddressEntry.MIN_LENGTH, VariableContract.AddressEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.AddressEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(userData.getAddressCode(), VariableContract.AddressCodeEntry.MIN_LENGTH, VariableContract.AddressCodeEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.AddressCodeEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(userData.getCity(), VariableContract.CityEntry.MIN_LENGTH, VariableContract.CityEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.CityEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(userData.getTelephone(), VariableContract.TelephoneEntry.MIN_LENGTH, VariableContract.TelephoneEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.TelephoneEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if(userSession.getId()==-1){
            result.setResult(false);
            result.setMessage(UserContract.UserController.USER_NOT_LOGGED_ERR);
            result.setMessageType(1);
            return result;
        }
        userData.setUserId(userSession.getId());
        if (!userController.CheckUserExistsById(userData.getUserId()).getResult()) {
            result.setResult(false);
            result.setMessage(UserDataContract.UserDataController.USER_EXIST_ERR);
            result.setMessageType(2);
            return result;
        }
        if (userDataRepository.checkUserDataExistsByUserId(userData.getUserId())) {
            result.setResult(false);
            result.setMessage(UserDataContract.UserDataController.USER_HAS_USERDATA_ERR);
            result.setMessageType(1);
            return result;
        }
        if (userDataRepository.insertUserData(userData)) {
            result.setResult(true);
            result.setMessage(UserDataContract.UserDataController.INSERT_USERDATA_SUCC);
            result.setMessageType(0);
        } else {
            result.setResult(false);
            result.setMessage(UserDataContract.UserDataController.INSERT_USERDATA_ERR);
            result.setMessageType(2);
        }
        return result;
    }

    public ConnectionResult checkInsertUserData(UserData userData){
        ConnectionResult result = new ConnectionResult(UserDataContract.UserDataController.TAG);
        userData.fixStrings();
        if (isTextLengthInvalid(userData.getName(), VariableContract.NameEntry.MIN_LENGTH, VariableContract.NameEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.NameEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(userData.getSurname(), VariableContract.SurnameEntry.MIN_LENGTH, VariableContract.SurnameEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.SurnameEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(userData.getAddress(), VariableContract.AddressEntry.MIN_LENGTH, VariableContract.AddressEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.AddressEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(userData.getAddressCode(), VariableContract.AddressCodeEntry.MIN_LENGTH, VariableContract.AddressCodeEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.AddressCodeEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(userData.getCity(), VariableContract.CityEntry.MIN_LENGTH, VariableContract.CityEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.CityEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(userData.getTelephone(), VariableContract.TelephoneEntry.MIN_LENGTH, VariableContract.TelephoneEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.TelephoneEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        result.setResult(true);
        result.setMessage(UserDataContract.UserDataController.USER_DATA_SUCC);
        result.setMessageType(0);
        return result;
    }

    public ConnectionResult updateUserData(UserData userData) {
        ConnectionResult result = new ConnectionResult(UserDataContract.UserDataController.TAG);
        userData.fixStrings();
        User userSession = Present.getInstance().getUserSession();
        if (isTextLengthInvalid(userData.getName(), VariableContract.NameEntry.MIN_LENGTH, VariableContract.NameEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.NameEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(userData.getSurname(), VariableContract.SurnameEntry.MIN_LENGTH, VariableContract.SurnameEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.SurnameEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(userData.getAddress(), VariableContract.AddressEntry.MIN_LENGTH, VariableContract.AddressEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.AddressEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(userData.getAddressCode(), VariableContract.AddressCodeEntry.MIN_LENGTH, VariableContract.AddressCodeEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.AddressCodeEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(userData.getCity(), VariableContract.CityEntry.MIN_LENGTH, VariableContract.CityEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.CityEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if (isTextLengthInvalid(userData.getTelephone(), VariableContract.TelephoneEntry.MIN_LENGTH, VariableContract.TelephoneEntry.MAX_LENGTH)) {
            result.setResult(false);
            result.setMessage(VariableContract.TelephoneEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if(userSession.getId()==-1){
            result.setResult(false);
            result.setMessage(UserContract.UserController.USER_NOT_LOGGED_ERR);
            result.setMessageType(1);
            return result;
        }
        userData.setUserId(userSession.getId());
        if (userDataRepository.updateUserData(userData)) {
            result.setResult(true);
            result.setMessage(UserDataContract.UserDataController.UPDATE_USERDATA_SUCC);
            result.setMessageType(0);
        } else {
            result.setResult(false);
            result.setMessage(UserDataContract.UserDataController.UPDATE_USERDATA_ERR);
            result.setMessageType(2);
        }
        return result;
    }

    public ConnectionResult getUserDataByUserSession() {
        ConnectionResult result = new ConnectionResult(UserDataContract.UserDataController.TAG);
        User userSession = Present.getInstance().getUserSession();
        if(userSession.getId()==-1){
            result.setResult(false);
            result.setMessage(UserContract.UserController.USER_NOT_LOGGED_ERR);
            result.setMessageType(1);
            return result;
        }
        UserData userData = userDataRepository.getUserDataByUserId(userSession.getId());
        if(userData==null){
            result.setResult(false);
            result.setMessage(UserDataContract.UserDataController.GET_USERDATA_ERR);
            result.setMessageType(2);
        }
        result.setObj(userData);
        result.setResult(true);
        result.setMessage(UserDataContract.UserDataController.GET_USERDATA_SUCC);
        result.setMessageType(0);
        return result;
    }

    public ConnectionResult getDoctorList(){
        ConnectionResult result = new ConnectionResult(UserDataContract.UserDataController.TAG);
        User userSession = Present.getInstance().getUserSession();
        List<UserData> doctorsData;
        if(userSession.getId()==-1){
            result.setResult(false);
            result.setMessage(UserContract.UserController.USER_NOT_LOGGED_ERR);
            result.setMessageType(1);
            return result;
        }
        //doctor can access the list to view his profile
        //if(userSession.getIsDoctor()!= false){
        //    result.setResult(false);
        //    result.setMessage(UserContract.UserController.USER_NOT_PATIENT_MSG);
        //    result.setMessageType(1);
        //    return result;
        //}
        doctorsData = userDataRepository.getListOfUserDataAndOfficeOfUsersDoctor();
        if(doctorsData==null){
            result.setResult(false);
            result.setMessage(UserDataContract.UserDataController.GET_DOCTORS_LIST_ERROR);
            result.setMessageType(2);
            return result;
        }
        if(!doctorsData.isEmpty()){
            result.setResult(true);
            result.setMessage(UserDataContract.UserDataController.GET_DOCTORS_LIST_SUCC);
            result.setObj(doctorsData);
            result.setMessageType(0);
        }
        else{
            result.setResult(false);
            result.setMessage(UserDataContract.UserDataController.GET_DOCTORS_LIST_ERR);
            result.setObj(doctorsData);
            result.setMessageType(1);
        }
        return result;
    }

    public ConnectionResult getUserNameSurnameByUserId(int userId){
        ConnectionResult result = new ConnectionResult(UserDataContract.UserDataController.TAG);
        if(userId==-1){
            result.setResult(false);
            result.setMessage(UserDataContract.UserDataController.USER_ID_ERR);
            result.setMessageType(1);
            return result;
        }
        UserData userData = userDataRepository.getUserDataNameSurnameByUserId(userId);
        if(userData==null){
            result.setResult(false);
            result.setMessage(UserDataContract.UserDataController.GET_USERDATA_ERR);
            result.setMessageType(2);
        }
        result.setObj(userData);
        result.setResult(true);
        result.setMessage(UserDataContract.UserDataController.GET_USERDATA_SUCC);
        result.setMessageType(0);
        return result;
    }

}





