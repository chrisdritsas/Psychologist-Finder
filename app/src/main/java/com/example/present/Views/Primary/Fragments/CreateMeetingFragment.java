package com.example.present.Views.Primary.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.present.App.Present;
import com.example.present.Controllers.ConnectionResult;
import com.example.present.Controllers.Connectors.MeetingController;
import com.example.present.Controllers.Connectors.NotificationController;
import com.example.present.Controllers.Connectors.UserDataController;
import com.example.present.Models.Contracts.Database.NotificationContract;
import com.example.present.Models.Entities.Meeting;
import com.example.present.Models.Entities.Notification;
import com.example.present.Models.Entities.UserData;
import com.example.present.R;
import com.example.present.Views.Primary.Activities.PrimaryScreenPatient;

public class CreateMeetingFragment extends Fragment {
    private final String contentTitle = "New meeting from ";
    private final UserDataController userDataController = new UserDataController();
    private final NotificationController notificationController = new NotificationController();
    private UserData userSession = new UserData();
    private final String notificationAction = NotificationContract.Controller.ACTION_MEETING;
    private final UserData selectedDoctor;
    private final MeetingController meetingController = new MeetingController();

    public CreateMeetingFragment(UserData selectedDoctor) {
        // Required empty public constructor
        this.selectedDoctor = selectedDoctor;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConnectionResult result = userDataController.getUserNameSurnameByUserId(Present.getInstance().getUserSession().getId());
        if(result.getResult()){
            userSession = (UserData)result.getObj();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_meeting, container, false);
        InitializeViewElements(view);
        return view;
    }

    private void InitializeViewElements(View view) {
        TextView doctorFullName = view.findViewById(R.id.doctorNameTxtViewCreateMeeting);
        doctorFullName.append(" "+selectedDoctor.getNameSurname());
        EditText dateTime = view.findViewById(R.id.dateTimeEditTxtCreateMeeting);
        EditText patientDesc = view.findViewById(R.id.patientDescEditTxtCreateMeeting);
        Button createMeetingBtn = view.findViewById(R.id.btnCreateMeetingCreateMeeting);
        createMeetingBtn.setOnClickListener(v-> {
            Meeting newMeeting = new Meeting();
            newMeeting.setDoctorId(selectedDoctor.getUserId());
            newMeeting.setDateTime(dateTime.getText().toString());
            newMeeting.setPatientDescription(patientDesc.getText().toString());
            ConnectionResult result = meetingController.insertMeetingAndReturnId(newMeeting);
            Toast.makeText(this.getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
            CloseSoftKeyboard();
            if(result.getResult()){
                int id=(int)result.getObj();
                newMeeting.setId(id);
                createNotification(newMeeting);
                requireActivity().onBackPressed();
            }
        });
    }
    private void createNotification(Meeting newMeeting){
        Notification notification = new Notification();
        notification.setReceiverId(selectedDoctor.getUserId());
        notification.setAction(notificationAction);
        notification.setChatSenderId(Present.getInstance().getUserSession().getId());
        notification.setMeetingId(newMeeting.getId());
        notification.setContentTitle(contentTitle+userSession.getNameSurname());
        notification.setContentText(newMeeting.getDateTime() + " " + newMeeting.getPatientDescription());
        notificationController.insertNotification(notification).showInLog();

    }
    private void CloseSoftKeyboard(){
        // Get a reference to the InputMethodManager
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        // Hide the soft keyboard
        View focusView = getActivity().getCurrentFocus();
        if (focusView != null) {
            imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
        }
    }
}