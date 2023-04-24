package com.example.present.Controllers.Connectors;

import com.example.present.App.Present;
import com.example.present.Controllers.ConnectionResult;
import com.example.present.Helpers.Helper;
import com.example.present.Models.Contracts.Database.ChatContract;
import com.example.present.Models.Contracts.Database.UserContract;
import com.example.present.Models.Contracts.General.VariableContract;
import com.example.present.Models.Entities.Chat;
import com.example.present.Models.Entities.User;
import com.example.present.Models.Repositories.ChatRepository;
import com.example.present.Models.Repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class ChatController extends Helper {
    ChatRepository chatRepo = new ChatRepository();
    UserRepository userRepo = new UserRepository();

    public ConnectionResult insertChat(Chat chat){
        ConnectionResult result = new ConnectionResult(ChatContract.ChatController.TAG);
        chat.fixStrings();
        User userSession = Present.getInstance().getUserSession();
        if(isTextLengthInvalid(chat.getMessage(),VariableContract.MessageEntry.MIN_LENGTH,VariableContract.MessageEntry.MAX_LENGTH)){
            result.setResult(false);
            result.setMessage(VariableContract.MessageEntry.LENGTH_ERROR_MSG);
            result.setMessageType(1);
            return result;
        }
        if(Present.getInstance().getUserSession().getId()==-1){
            result.setResult(false);
            result.setMessage(UserContract.UserController.USER_NOT_LOGGED_ERR);
            result.setMessageType(1);
            return result;
        }
        chat.setSenderId(userSession.getId());
        if(!userRepo.checkUserExistsById(chat.getReceiverId())){
            result.setResult(false);
            result.setMessage(UserContract.UserController.USER_NOT_EXIST_ERR);
            result.setMessageType(1);
            return result;
        }
        if(chatRepo.insertChat(chat)){
            result.setResult(true);
            result.setMessage(ChatContract.ChatController.INSERT_CHAT_SUCC);
            result.setMessageType(0);
        }
        else{
            result.setResult(false);
            result.setMessage(ChatContract.ChatController.INSERT_CHAT_ERR);
            result.setMessageType(2);
        }
        return result;
    }

    public ConnectionResult getConversationIdsByUserSession(){
        ConnectionResult result = new ConnectionResult(ChatContract.ChatController.TAG);
        User userSession = Present.getInstance().getUserSession();
        if(userSession.getId()==-1){
            result.setResult(false);
            result.setMessage(UserContract.UserController.USER_NOT_LOGGED_ERR);
            result.setMessageType(1);
            return result;
        }
        List<Integer> convIds;
        if(userSession.getIsDoctor()){
            convIds=chatRepo.getDistinctSenderIdsByReceiverId(userSession.getId());
        }
        else{
            convIds=chatRepo.getDistinctReceiverIdsBySenderId(userSession.getId());
        }
        if(convIds==null){
            result.setResult(false);
            result.setMessage(ChatContract.ChatController.GET_CONV_ERR);
            result.setMessageType(2);
            return result;
        }
        if(!convIds.isEmpty()){
            result.setResult(true);
            result.setMessage(ChatContract.ChatController.GET_CONV_SUCC);
            result.setObj(convIds);
            result.setMessageType(0);
        }
        else{
            result.setResult(false);
            result.setMessage(ChatContract.ChatController.GET_CONV_ERROR);
            result.setObj((convIds));
            result.setMessageType(1);
        }
        return result;
    }

    public ConnectionResult getConversationChatsByUserSessionAndConvId(int convId){
        ConnectionResult result = new ConnectionResult(ChatContract.ChatController.TAG);
        User userSession = Present.getInstance().getUserSession();
        if(userSession.getId()==-1){
            result.setResult(false);
            result.setMessage(UserContract.UserController.USER_NOT_LOGGED_ERR);
            result.setMessageType(1);
            return result;
        }
        List<Chat> chats = chatRepo.getConversationChatsBySenderIdAndReceiverId(userSession.getId(), convId);
        if(chats==null){
            result.setResult(false);
            result.setMessage(ChatContract.ChatController.GET_CONV_CHATS_ERR);
            result.setMessageType(2);
        }
        if(!chats.isEmpty()){
            result.setResult(true);
            result.setMessage(ChatContract.ChatController.GET_CONV_CHATS_SUCC);
            result.setObj(chats);
            result.setMessageType(0);
        }
        else{
            result.setResult(false);
            result.setMessage(ChatContract.ChatController.GET_CONV_CHATS_ERROR);
            result.setMessageType(1);
        }
        return result;
    }

}
