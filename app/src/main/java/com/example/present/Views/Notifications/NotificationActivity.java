package com.example.present.Views.Notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.present.Controllers.ConnectionResult;
import com.example.present.Controllers.Connectors.MeetingController;
import com.example.present.Controllers.Connectors.UserDataController;
import com.example.present.Models.Contracts.Database.NotificationContract;
import com.example.present.Models.Entities.Meeting;
import com.example.present.Models.Entities.UserData;
import com.example.present.R;
import com.example.present.Views.Primary.Fragments.ChatFragment;
import com.example.present.Views.Primary.Fragments.UpdateMeetingFragment;

public class NotificationActivity extends AppCompatActivity {
    private final MeetingController meetingController = new MeetingController();
    private final UserDataController userDataController = new UserDataController();
    private boolean readyToStartChatFrag = false;
    private boolean readyToStartUpdateMeetingFrag = false;
    private int meetingId;
    private int chatUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserData selectedUserData = new UserData();

        setContentView(R.layout.activity_notification);
        FragmentContainerView fragmentContainerView = findViewById(R.id.fragContainerNotification);

        readNotification();
        openNotificationFragment();
    }

    protected void initializeViewElements(Activity activity) {

    }

    protected void readNotification() {
        Intent intent = getIntent();
        if (intent.getAction().equals(NotificationContract.Controller.ACTION_CHAT)) {
            if (intent.hasExtra(NotificationContract.Controller.CHAT_USER_ID)) {
                chatUserId = intent.getIntExtra(NotificationContract.Controller.CHAT_USER_ID, 0);
                if (chatUserId > 0) {
                    readyToStartChatFrag = true;
                }
            }
        } else if (intent.getAction().equals(NotificationContract.Controller.ACTION_MEETING)) {
            if (intent.hasExtra(NotificationContract.Controller.MEETING_ID)) {
                meetingId = intent.getIntExtra(NotificationContract.Controller.MEETING_ID, 0);
                if (meetingId > 0) {
                    readyToStartUpdateMeetingFrag = true;
                }
            }
        }
    }

    protected void openNotificationFragment() {
        if (readyToStartChatFrag) {
            ConnectionResult getUserDataNameSurnameResult = userDataController.getUserNameSurnameByUserId(chatUserId);
            if (getUserDataNameSurnameResult.getResult()) {
                UserData receiverUserData = (UserData) getUserDataNameSurnameResult.getObj();
                receiverUserData.setUserId(chatUserId);
                ChatFragment chatFrag = new ChatFragment(receiverUserData);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragContainerNotification, chatFrag);
                //transaction.addToBackStack(null); // add this if you want to enable back navigation
                transaction.commit();
            } else {
                Toast.makeText(this, getUserDataNameSurnameResult.getMessage(), Toast.LENGTH_SHORT).show();
            }
            readyToStartChatFrag = false;
        }
        if (readyToStartUpdateMeetingFrag) {
            ConnectionResult getMeetingResult = meetingController.getMeetingById(meetingId);
            if (getMeetingResult.getResult()) {
                Meeting meeting = (Meeting) getMeetingResult.getObj();
                UpdateMeetingFragment updateMeetingFrag = new UpdateMeetingFragment(meeting);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragContainerNotification, updateMeetingFrag);
                //transaction.addToBackStack(null); // add this if you want to enable back navigation
                transaction.commit();
            } else {
                Toast.makeText(this, getMeetingResult.getMessage(), Toast.LENGTH_SHORT).show();
            }
            readyToStartUpdateMeetingFrag = false;
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //do stuff
        setIntent(intent);
        readNotification();
        openNotificationFragment();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}