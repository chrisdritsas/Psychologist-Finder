package com.example.present.Controllers.Connectors;

import com.example.present.App.Present;
import com.example.present.Controllers.ConnectionResult;
import com.example.present.Helpers.Helper;
import com.example.present.Models.Contracts.Database.NotificationContract;
import com.example.present.Models.Contracts.Database.UserContract;
import com.example.present.Models.Contracts.Database.UserDataContract;
import com.example.present.Models.Entities.Notification;
import com.example.present.Models.Entities.User;
import com.example.present.Models.Repositories.NotificationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotificationController extends Helper {
    private final NotificationRepository notificationRepo = new NotificationRepository();
    private final UserController userController = new UserController();


    public ConnectionResult insertNotification(Notification notification) {
        ConnectionResult insertNotificationResult = new ConnectionResult(NotificationContract.Controller.TAG);
        User userSession = Present.getInstance().getUserSession();
        if (userSession.getId() == -1) {
            insertNotificationResult.setResult(false);
            insertNotificationResult.setMessage(UserContract.UserController.USER_NOT_LOGGED_ERR);
            insertNotificationResult.setMessageType(1);
            return insertNotificationResult;
        }
        if (!notification.getAction().equals(NotificationContract.Controller.ACTION_CHAT) && !notification.getAction().equals(NotificationContract.Controller.ACTION_MEETING)) {
            insertNotificationResult.setMessage("Invalid action.");
            insertNotificationResult.setMessageType(1);
            return insertNotificationResult;
        }
        if (notification.getReceiverId() < 1) {
            insertNotificationResult.setMessage("Invalid receiver id.");
            insertNotificationResult.setMessageType(1);
            return insertNotificationResult;
        }
        if (!userController.CheckUserExistsById(notification.getReceiverId()).getResult()) {
            insertNotificationResult.setResult(false);
            insertNotificationResult.setMessage(UserDataContract.UserDataController.USER_EXIST_ERR);
            insertNotificationResult.setMessageType(2);
            return insertNotificationResult;
        }
        if (notification.getAction().equals(NotificationContract.Controller.ACTION_CHAT)) {
            if (notification.getChatSenderId() < 1) {
                insertNotificationResult.setMessage("Invalid chat sender id.");
                insertNotificationResult.setMessageType(1);
                return insertNotificationResult;
            }
        }
        //if (notification.getAction().equals(NotificationContract.Controller.ACTION_MEETING)) {
        //    if (notification.getMeetingId() < 1) {
        //        insertNotificationResult.setMessage("Invalid meeting id.");
        //        insertNotificationResult.setMessageType(1);
        //        return insertNotificationResult;
        //    }
        //}
        Random random = new Random();
        notification.setRequestCode(random.nextInt(Integer.MAX_VALUE));
        notification.setCreatorId(userSession.getId());
        if (notificationRepo.insertNotification(notification)) {
            insertNotificationResult.setResult(true);
            insertNotificationResult.setMessage("Successfully inserted notification.");
            insertNotificationResult.setMessageType(0);
        } else {
            insertNotificationResult.setResult(false);
            insertNotificationResult.setMessage("Couldn't insert notification");
            insertNotificationResult.setMessageType(2);
        }
        return insertNotificationResult;
    }

    public ConnectionResult getSessionUserUnreadNotificationList() {
        ConnectionResult getNotificationResult = new ConnectionResult(NotificationContract.Controller.TAG);
        User userSession = Present.getInstance().getUserSession();
        List<Notification> notificationList;
        if (userSession.getId() == -1) {
            getNotificationResult.setResult(false);
            getNotificationResult.setMessage(UserContract.UserController.USER_NOT_LOGGED_ERR);
            getNotificationResult.setMessageType(1);
            return getNotificationResult;
        }
        notificationList = notificationRepo.getNotificationByReceiverIdAndState(userSession.getId(), NotificationContract.Controller.NOTIFICATION_UNREAD);
        if (notificationList == null) {
            getNotificationResult.setResult(false);
            getNotificationResult.setMessage("Couldn't retrieve notification list.");
            getNotificationResult.setMessageType(2);
        } else {
            getNotificationResult.setResult(true);
            getNotificationResult.setObj(notificationList);
            getNotificationResult.setMessage("Successfully retrieved notification list.");
            getNotificationResult.setMessageType(0);
            setNotificationListAsRead(notificationList).showInLog();
        }
        return getNotificationResult;
    }

    public ConnectionResult setNotificationListAsRead(List<Notification> readNotificationList) {
        ConnectionResult updateNotificationResult = new ConnectionResult(NotificationContract.Controller.TAG);
        User userSession = Present.getInstance().getUserSession();
        if (userSession.getId() == -1) {
            updateNotificationResult.setResult(false);
            updateNotificationResult.setMessage(UserContract.UserController.USER_NOT_LOGGED_ERR);
            updateNotificationResult.setMessageType(1);
            return updateNotificationResult;
        }
        updateNotificationResult.setResult(notificationRepo.updateNotificationsState(readNotificationList, NotificationContract.Controller.NOTIFICATION_READ));
        if(updateNotificationResult.getResult()){
            updateNotificationResult.setMessage("Successfully updated notification list.");
            updateNotificationResult.setMessageType(0);
        }
        else{
            updateNotificationResult.setMessage("Couldn't update notification list.");
            updateNotificationResult.setMessageType(2);
        }
        return updateNotificationResult;
    }


}
