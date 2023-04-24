package com.example.present.Controllers.Connectors;

import com.example.present.App.Present;
import com.example.present.Controllers.ConnectionResult;
import com.example.present.Helpers.Helper;
import com.example.present.Helpers.Security;
import com.example.present.Models.Contracts.Database.UserContract;
import com.example.present.Models.Contracts.General.VariableContract;
import com.example.present.Models.Entities.User;
import com.example.present.Models.Repositories.UserRepository;

public class UserController extends Helper {
    UserRepository userRepo = new UserRepository();

    public ConnectionResult insertUser(User user){
        ConnectionResult result = new ConnectionResult(UserContract.UserController.TAG);
        user.fixStrings();
        if(isTextLengthInvalid(user.getEmail(), VariableContract.EmailEntry.MIN_LENGTH, VariableContract.EmailEntry.MAX_LENGTH)){
            result.setResult(false);
            result.setMessage(VariableContract.EmailEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if(isEmailInvalid(user.getEmail())){
            result.setResult(false);
            result.setMessage(VariableContract.EmailEntry.INVALID_FORMAT_MSG);
            result.setMessageType(1);
            return result;
        }
        if(isTextLengthInvalid(user.getPassword(), VariableContract.PasswordEntry.MIN_LENGTH, VariableContract.PasswordEntry.MAX_LENGTH)){
            result.setResult(false);
            result.setMessage(VariableContract.PasswordEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if(user.isPasswordConfirmInvalid()){
            result.setResult(false);
            result.setMessage(VariableContract.PasswordEntry.INVALID_CONFIRM_MSG);
            result.setMessageType(1);
            return result;
        }
        if(isBooleanInvalid(user.getIsDoctor())){
            result.setResult(false);
            result.setMessage(VariableContract.IsDoctorEntry.INVALID_INPUT_MSG);
            result.setMessageType(1);
            return result;
        }
        if(userRepo.checkUserExistsByEmail(user.getEmail())){
            result.setResult(false);
            result.setMessage(VariableContract.EmailEntry.INVALID_USED_MSG);
            result.setMessageType(1);
            return result;
        }
        if(userRepo.insertUser(user)){
            result.setResult(true);
            result.setMessage(UserContract.UserController.INSERT_USER_SUCC);
            result.setMessageType(0);
        }else{
            result.setResult(false);
            result.setMessage(UserContract.UserController.INSERT_USER_ERR);
            result.setMessageType(2);
        }
        return result;
    }

    public ConnectionResult checkInsertUser(User user){
        ConnectionResult result = new ConnectionResult(UserContract.UserController.TAG);
        user.fixStrings();
        if(isTextLengthInvalid(user.getEmail(), VariableContract.EmailEntry.MIN_LENGTH, VariableContract.EmailEntry.MAX_LENGTH)){
            result.setResult(false);
            result.setMessage(VariableContract.EmailEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if(isEmailInvalid(user.getEmail())){
            result.setResult(false);
            result.setMessage(VariableContract.EmailEntry.INVALID_FORMAT_MSG);
            result.setMessageType(1);
            return result;
        }
        if(isTextLengthInvalid(user.getPassword(), VariableContract.PasswordEntry.MIN_LENGTH, VariableContract.PasswordEntry.MAX_LENGTH)){
            result.setResult(false);
            result.setMessage(VariableContract.PasswordEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if(user.isPasswordConfirmInvalid()){
            result.setResult(false);
            result.setMessage(VariableContract.PasswordEntry.INVALID_CONFIRM_MSG);
            result.setMessageType(1);
            return result;
        }
        if(isBooleanInvalid(user.getIsDoctor())){
            result.setResult(false);
            result.setMessage(VariableContract.IsDoctorEntry.INVALID_INPUT_MSG);
            result.setMessageType(1);
            return result;
        }
        if(userRepo.checkUserExistsByEmail(user.getEmail())){
            result.setResult(false);
            result.setMessage(VariableContract.EmailEntry.INVALID_USED_MSG);
            result.setMessageType(1);
            return result;
        }
        result.setResult(true);
        result.setMessage(UserContract.UserController.USER_SUCC);
        result.setMessageType(0);
        return result;
    }

    public ConnectionResult updateUserEmailById(User user){
        ConnectionResult result = new ConnectionResult(UserContract.UserController.TAG);
        user.fixStrings();
        User userSession = Present.getInstance().getUserSession();
        if(isTextLengthInvalid(user.getEmail(),VariableContract.EmailEntry.MIN_LENGTH , VariableContract.EmailEntry.MAX_LENGTH)){
            result.setResult(false);
            result.setMessage(VariableContract.EmailEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if(isEmailInvalid(user.getEmail())){
            result.setResult(false);
            result.setMessage(VariableContract.EmailEntry.INVALID_FORMAT_MSG);
            result.setMessageType(1);
            return result;
        }
        if(userSession.getId()==-1){
            result.setResult(false);
            result.setMessage(UserContract.UserController.USER_NOT_LOGGED_ERR);
            result.setMessageType(1);
            return result;
        }
        user.setId(userSession.getId());
        if(userRepo.checkUserExistsByEmail(user.getEmail())){
            result.setResult(false);
            result.setMessage(VariableContract.EmailEntry.INVALID_USED_MSG);
            result.setMessageType(1);
            return result;
        }
        if(userRepo.updateUserEmailById(user)){
            result.setResult(true);
            result.setMessage(UserContract.UserController.EMAIL_UPDATE_SUCC);
            result.setMessageType(0);
            Present.getInstance().setUserSession(user);
        }
        else{
            result.setResult(false);
            result.setMessage(UserContract.UserController.EMAIL_UPDATE_ERR);
            result.setMessageType(2);
        }
        return result;
    }

    public ConnectionResult updateUserPasswordById(User user) {
        ConnectionResult result = new ConnectionResult(UserContract.UserController.TAG);
        user.fixStrings();
        User userSession = Present.getInstance().getUserSession();
        if(isTextLengthInvalid(user.getPassword(), VariableContract.PasswordEntry.MIN_LENGTH, VariableContract.PasswordEntry.MAX_LENGTH)){
            result.setResult(false);
            result.setMessage(VariableContract.PasswordEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if(user.isPasswordConfirmInvalid()){
            result.setResult(false);
            result.setMessage(VariableContract.PasswordEntry.INVALID_CONFIRM_MSG);
            result.setMessageType(1);
            return result;
        }
        if(userSession.getId()==-1){
            result.setResult(false);
            result.setMessage(UserContract.UserController.USER_NOT_LOGGED_ERR);
            result.setMessageType(1);
            return result;
        }
        user.setId(userSession.getId());
        if(userRepo.updateUserPasswordById(user)){
            result.setResult(true);
            result.setMessageType(0);
            result.setMessage(UserContract.UserController.PASSWORD_UPDATE_SUCC);
        }
        else{
            result.setResult(false);
            result.setMessage(UserContract.UserController.PASSWORD_UPDATE_ERR);
            result.setMessageType(2);
        }
        return result;
    }

    public ConnectionResult loginUser(User user){
        ConnectionResult result = new ConnectionResult(UserContract.UserController.TAG);
        user.fixStrings();
        if(isTextLengthInvalid(user.getEmail(), VariableContract.EmailEntry.MIN_LENGTH, VariableContract.EmailEntry.MAX_LENGTH)){
            result.setResult(false);
            result.setMessage(VariableContract.EmailEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if(isEmailInvalid(user.getEmail())){
            result.setResult(false);
            result.setMessage(VariableContract.EmailEntry.INVALID_FORMAT_MSG);
            result.setMessageType(1);
            return result;
        }
        if(isTextLengthInvalid(user.getPassword(), VariableContract.PasswordEntry.MIN_LENGTH, VariableContract.PasswordEntry.MAX_LENGTH)){
            result.setResult(false);
            result.setMessage(VariableContract.PasswordEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        int id = userRepo.getUserIdByEmailAndPassword(user);
        if(id==-1){
            result.setResult(false);
            result.setMessage(UserContract.UserController.LOGIN_WRONG_CREDS);
            result.setMessageType(1);
            return result;
        }
        User loggedUser = userRepo.getUserById(id);
        if(loggedUser!=null){
            result.setResult(true);
            result.setMessage(UserContract.UserController.LOGIN_SUCC);
            result.setObj(loggedUser);
            result.setMessageType(0);
            Present.getInstance().setUserSession(loggedUser);
        }else{
            result.setResult(false);
            result.setMessage(UserContract.UserController.LOGIN_ERR);
            result.setMessageType(2);
        }
        return result;
    }

    public ConnectionResult logOutUser(){
        ConnectionResult result = new ConnectionResult(UserContract.UserController.TAG);
        Present.getInstance().setUserSession(new User(-1));
        if(Present.getInstance().getUserSession().getId()==-1){
            result.setResult(true);
            result.setMessage(UserContract.UserController.LOGOUT_SUCC);
            result.setMessageType(0);
        }
        else{
            result.setResult(false);
            result.setMessage(UserContract.UserController.LOGOUT_ERR);
            result.setMessageType(2);
        }
        return result;
    }

    public ConnectionResult CheckUserExistsById(int id){
        ConnectionResult result = new ConnectionResult(UserContract.UserController.TAG);
        if(id>=1){
            result.setResult(userRepo.checkUserExistsById(id));
        return result;
        }
        result.setResult(false);
        return result;
    }
}
