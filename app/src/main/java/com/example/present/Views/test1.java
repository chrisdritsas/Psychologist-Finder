package com.example.present.Views;


import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.present.App.Present;
import com.example.present.Controllers.ConnectionResult;
import com.example.present.Controllers.Connectors.ChatController;
import com.example.present.Controllers.Connectors.MeetingController;
import com.example.present.Controllers.Connectors.OfficeController;
import com.example.present.Controllers.Connectors.UserController;
import com.example.present.Controllers.Connectors.UserDataController;
import com.example.present.Models.Contracts.General.VariableContract;
import com.example.present.Models.Entities.Chat;
import com.example.present.Models.Entities.Meeting;
import com.example.present.Models.Entities.Office;
import com.example.present.Models.Entities.User;
import com.example.present.Models.Entities.UserData;
import com.example.present.Models.Repositories.OfficeRepository;
import com.example.present.R;

import java.util.List;

public class test1 extends AppCompatActivity {
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int value = Integer.parseInt(extras.getString("count"));
            i = value;
            Log.w("Test1 Activity", "IM HERE " + i);
        }

        ConnectionResult result = new ConnectionResult();
        UserController userController = new UserController();
        UserDataController userDataController = new UserDataController();
        OfficeController officeController = new OfficeController();
        MeetingController meetingController = new MeetingController();
        ChatController chatController = new ChatController();

        User user2 = new User("admin@admin.com","admin5389","admin5389",true);
        userController.insertUser(user2);

        User user1 = new User ("john.v.vergos@gmail.com","20061992","20061992",false);
        userController.insertUser(user1).showInLog();
        userController.loginUser(user1).showInLog();
        user1.setId(Present.getInstance().getUserSession().getId());
        //user1.setEmail("john.v.vergos@gmail.com");
       // userController.updateUserEmailById(user1).showInLog();
        //user1.setPassword("20061992");
        //user1.setPasswordConfirm("20061992");
        //userController.updateUserPasswordById(user1).showInLog();
        UserData user1Data = new UserData("Giannisssss","Vergossssss","Taugetou 1sssss","11364sssss","Athensssssss","6985719771sssss");
        userDataController.insertUserData(user1Data).showInLog();
        UserData user1DataNew = new UserData("Giannis","Vergos","Taugetou 1","11364","Athens","6985719771");
        userDataController.updateUserData(user1DataNew).showInLog();
        userController.logOutUser().showInLog();



        userController.loginUser(user2).showInLog();
        user2.setId(Present.getInstance().getUserSession().getId());
        UserData user2Data = new UserData("Giannis 1","Vergos 1","Taugetou 1 1","11364 1","Athens 1","6985719771 1");
        userDataController.insertUserData(user2Data).showInLog();
        UserData user2DataNew = new UserData("Doctorname 1","Doctorsurname 1","Taugetou 1 1","11364 1","Athens 1","6985719771 1");
        userDataController.updateUserData(user2DataNew).showInLog();
        Office user2Office = new Office("office address sssss","addcode","phone sssssssss","mobile sssssss","biography sssssssssssssssssssss","work hours ssssssssssss",12345d,23456d);
        officeController.insertOffice(user2Office).showInLog();
        Office user2OfficeNew = new Office("office address","address c","phone12345","mobile12345","biography this is a random biography im a good doctor\t and i press tab \n and i press enter and stuff","work hours 9-5",50.5d,35.35d);
        officeController.updateOffice(user2OfficeNew).showInLog();
        officeController.getUserSessionOfficeData().showInLog();
        userDataController.getUserDataByUserSession().showInLog();
        userController.logOutUser().showInLog();

        userController.loginUser(user1).showInLog();
        Meeting meeting1 = new Meeting(user2.getId(),666,"Tetarth 17-18","8elw polu ena radevu giatre","test this must not show");
        meetingController.insertMeeting(meeting1).showInLog();
        Meeting meeting1New = new Meeting(1, VariableContract.MeetingState.APPROVED,"8elw polu ena radevu giatre","test this must not show");
        meetingController.updateMeetingById(meeting1New).showInLog();
        Meeting meeting2 = new Meeting(2, 20,"8elw polu ena radevu giatre","test this must not show");
        meetingController.updateMeetingById(meeting2).showInLog();
        Chat chat1 = new Chat(user2.getId(),"giatre 8elw voh8eia!!");
        Chat chat2 = new Chat(user2.getId(),"giatre heeeelp!!");
        chatController.insertChat(chat1).showInLog();
        chatController.insertChat(chat2).showInLog();
        userController.logOutUser().showInLog();

        userController.loginUser(user2).showInLog();
        Meeting meeting1DoctorNew = new Meeting(1,VariableContract.MeetingState.APPROVED,"i must not see this gamw to 8eo","Malista paidi mou 8a se dw amesa");
        meetingController.updateMeetingById(meeting1DoctorNew).showInLog();
        Meeting meeting1DoctorNew1 = new Meeting(1,VariableContract.MeetingState.CANCELED_BY_DOCTOR,"i must not see this gamw to 8eo","Malista paidi mou 8a se dw amesa");
        meetingController.updateMeetingById(meeting1DoctorNew1).showInLog();
        Meeting meeting1DoctorNew10 = new Meeting(1,VariableContract.MeetingState.APPROVED,"i must not see this gamw to 8eo","Malista paidi mou 8a se dw amesa");
        meetingController.updateMeetingById(meeting1DoctorNew10).showInLog();
        Meeting meeting1DoctorNew100 = new Meeting(2,100,"i must not see this gamw to 8eo","Malista paidi mou 8a se dw amesa");
        meetingController.updateMeetingById(meeting1DoctorNew100).showInLog();
        Chat chat3 = new Chat(user1.getId(),"malista paidi mou, eisai ka8usterimeno?");
        Chat chat4 = new Chat(user1.getId(),"       ti anarwtiemai kai gw gamw to 3estauri mou...  ...");
        chatController.insertChat(chat3).showInLog();
        chatController.insertChat(chat4).showInLog();
        userController.logOutUser().showInLog();

        User user3 = new User("emailDoctor2@gmail.com","passworddoctor2","passworddoctor2",true);
        User user4 = new User("emailDoctor3@gmail.com","passworddoctor3","passworddoctor3",true);
        User user5 = new User("emailDoctor4@gmail.com","passworddoctor4","passworddoctor4",true);
        userController.insertUser(user3).showInLog();
        userController.insertUser(user4).showInLog();
        userController.insertUser(user5).showInLog();

        userController.loginUser(user3).showInLog();
        user3.setId(Present.getInstance().getUserSession().getId());
        UserData user3Data = new UserData("Doctorname 2","Doctorsurname 2","Taugetou 2","11364 2","Athens 2","6985719771 2");
        userDataController.insertUserData(user3Data).showInLog();
        Office user3Office = new Office("office2","addcode2","phone22222","mobile2222","biography doctor 2","work hours 2",2.2d,2.2d);
        officeController.insertOffice(user3Office).showInLog();
        userController.logOutUser().showInLog();

        userController.loginUser(user4).showInLog();
        user4.setId(Present.getInstance().getUserSession().getId());
        UserData user4Data = new UserData("Doctorname 3","Doctorsurname 3","Taugetou 3","11364 3","Athens 3","6985719771 3");
        userDataController.insertUserData(user4Data).showInLog();
        Office user4Office = new Office("office3","addcode3","phone3333","mobile333","biography doctor 3","work hours 3",3.3d,3.3d);
        officeController.insertOffice(user4Office).showInLog();
        userController.logOutUser().showInLog();

        userController.loginUser(user5).showInLog();
        user5.setId(Present.getInstance().getUserSession().getId());
        UserData user5Data = new UserData("Doctorname 4","Doctorsurname 4","Taugetou 4","11364 4","Athens 4","6985719771 4");
        userDataController.insertUserData(user5Data).showInLog();
        Office user5Office = new Office("office4","addcode4","phone444","mobile444","biography doctor 4","work hours 4",4.4d,4.4d);
        officeController.insertOffice(user5Office).showInLog();
        userController.logOutUser().showInLog();

        userController.loginUser(user1).showInLog();
        user1.setId(Present.getInstance().getUserSession().getId());

        result=userDataController.getDoctorList();
        userController.logOutUser().showInLog();
        if(result.getResult()) {
            List<UserData> doctorList = (List<UserData>) result.getObj();
            for(UserData doctor : doctorList){
                Log.d("doctor",doctor.toString());
            }
        }
        userController.loginUser(user1).showInLog();

        result = chatController.getConversationIdsByUserSession();
        result.showInLog();
        List<Integer> convList = (List<Integer>) result.getObj();
        if(result.getResult()) {
            for (Integer convId : convList) {
                Log.d("convId", convId.toString());
                ConnectionResult result2 = chatController.getConversationChatsByUserSessionAndConvId(convId);
                if(result.getResult()) {
                    List<Chat> chats = (List<Chat>)result2.getObj();
                    for(Chat chat : chats){
                        Log.d("chat", chat.toString());
                    }
                }
            }
        }
        meetingController.getUserMeetingList().showInLog();
        userController.logOutUser().showInLog();


    }
}
