package com.example.present.Views.Primary.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class UpdateMeetingFragment extends Fragment {

    private final String notificationAction = NotificationContract.Controller.ACTION_MEETING;
    private final String contentTitle = " has updated your meeting!";
    private UserData userSession = new UserData();
    private final Meeting selectedMeeting;
    private final int oldState;
    private final String[] spinnerStatesDoctor = {"Change meeting state:", "Approve Meeting", "Cancel Meeting"};
    private final String[] spinnerStatesPatient = {"Change meeting state:", "Cancel Meeting"};
    private final UserDataController userDataController = new UserDataController();
    private final MeetingController meetingController = new MeetingController();
    private final NotificationController notificationController = new NotificationController();


    public UpdateMeetingFragment(Meeting selectedMeeting) {
        // Required empty public constructor
        this.selectedMeeting = selectedMeeting;
        oldState = selectedMeeting.getState();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConnectionResult result = userDataController.getUserNameSurnameByUserId(Present.getInstance().getUserSession().getId());
        if (result.getResult()) {
            userSession = (UserData) result.getObj();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_meeting, container, false);
        if (Present.getInstance().getUserSession().getIsDoctor()) {
            InitializeViewElementsForDoctor(view);
        } else {
            InitializeViewElementsForPatient(view);
        }
        return view;

    }

    private void InitializeViewElementsForDoctor(View view) {
        TextView patientName = view.findViewById(R.id.patientNameTxtView);
        EditText dateTime = view.findViewById(R.id.dateTimeEditTxt);
        dateTime.setEnabled(false);
        EditText stateString = view.findViewById(R.id.stateEditTxt);
        stateString.setEnabled(false);
        EditText patientDesc = view.findViewById(R.id.patientDescEditTxt);
        patientDesc.setEnabled(false);
        EditText doctorDesc = view.findViewById(R.id.doctorDescEditTxt);
        Spinner state = view.findViewById(R.id.stateSpinner);
        Button btnUpdate = view.findViewById(R.id.btnUpdateMeeting);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spinnerStatesDoctor);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(adapter);
        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item value
                String selectedValue = parent.getItemAtPosition(position).toString();
                if (selectedValue.equals(spinnerStatesDoctor[0])) {
                    selectedMeeting.setState(oldState);
                } else if (selectedValue.equals(spinnerStatesDoctor[1])) {
                    selectedMeeting.setState(1);
                } else if (selectedValue.equals(spinnerStatesDoctor[2])) {
                    selectedMeeting.setState(3);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        dateTime.setText(selectedMeeting.getDateTime());
        stateString.setText(selectedMeeting.getStateToString());
        patientDesc.setText(selectedMeeting.getPatientDescription());
        doctorDesc.setText(selectedMeeting.getDoctorDescription());
        ConnectionResult result = userDataController.getUserNameSurnameByUserId(selectedMeeting.getPatientId());
        if (result.getResult()) {
            UserData patientData = (UserData) result.getObj();
            patientName.append(" " + patientData.getNameSurname());
        }
        // TODO: an ftiaxtoun ta fragment stacks xreiazetai na kanei update th lista otan allazei sth vash to meeting
        btnUpdate.setOnClickListener(v -> {
            //state handled on spinner
            selectedMeeting.setDoctorDescription(doctorDesc.getText().toString());
            ConnectionResult updateResult = meetingController.updateMeetingById(selectedMeeting);
            Toast.makeText(this.getContext(), updateResult.getMessage(), Toast.LENGTH_SHORT).show();
            CloseSoftKeyboard();
            if (updateResult.getResult()) {
                createNotification(selectedMeeting);
                //requireActivity().getSupportFragmentManager().popBackStack("updateMeetingFrag" + selectedMeeting.getId(), 0);
                MeetingListFragment meetingListFrag = (MeetingListFragment) requireActivity().getSupportFragmentManager().findFragmentByTag("meetingListFrag");
                if (meetingListFrag != null) {
                    meetingListFrag.onDataChanged();
                }
                requireActivity().onBackPressed();
            }
        });
    }

    private void InitializeViewElementsForPatient(View view) {
        TextView doctorName = view.findViewById(R.id.patientNameTxtView);
        EditText dateTime = view.findViewById(R.id.dateTimeEditTxt);
        dateTime.setEnabled(false);
        EditText stateString = view.findViewById(R.id.stateEditTxt);
        stateString.setEnabled(false);
        EditText patientDesc = view.findViewById(R.id.patientDescEditTxt);
        EditText doctorDesc = view.findViewById(R.id.doctorDescEditTxt);
        doctorDesc.setEnabled(false);
        Spinner state = view.findViewById(R.id.stateSpinner);
        Button btnUpdate = view.findViewById(R.id.btnUpdateMeeting);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spinnerStatesPatient);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(adapter);
        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item value
                String selectedValue = parent.getItemAtPosition(position).toString();
                if (selectedValue.equals(spinnerStatesPatient[0])) {
                    selectedMeeting.setState(oldState);
                } else if (selectedValue.equals(spinnerStatesPatient[1])) {
                    selectedMeeting.setState(2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        dateTime.setText(selectedMeeting.getDateTime());
        stateString.setText(selectedMeeting.getStateToString());
        patientDesc.setText(selectedMeeting.getPatientDescription());
        doctorDesc.setText(selectedMeeting.getDoctorDescription());
        ConnectionResult result = userDataController.getUserNameSurnameByUserId(selectedMeeting.getDoctorId());
        if (result.getResult()) {
            UserData doctorData = (UserData) result.getObj();
            doctorName.append(" " + doctorData.getNameSurname());
        }
        // TODO: an ftiaxtoun ta fragment stacks xreiazetai na kanei update th lista otan allazei sth vash to meeting
        btnUpdate.setOnClickListener(v -> {
            //state handled on spinner
            selectedMeeting.setPatientDescription(patientDesc.getText().toString());
            ConnectionResult updateResult = meetingController.updateMeetingById(selectedMeeting);
            Toast.makeText(this.getContext(), updateResult.getMessage(), Toast.LENGTH_SHORT).show();
            CloseSoftKeyboard();
            if (updateResult.getResult()) {
                createNotification(selectedMeeting);
                //requireActivity().getSupportFragmentManager().popBackStack("updateMeetingFrag" + selectedMeeting.getId(), 0);
                MeetingListFragment meetingListFrag = (MeetingListFragment) requireActivity().getSupportFragmentManager().findFragmentByTag("meetingListFrag");
                if (meetingListFrag != null) {
                    meetingListFrag.onDataChanged();
                }
                requireActivity().onBackPressed();
                requireActivity().onBackPressed();
            }
        });
    }

    private void createNotification(Meeting meeting) {
        if (Present.getInstance().getUserSession().getIsDoctor()) {
            Notification notification = new Notification();
            notification.setReceiverId(meeting.getPatientId());
            notification.setAction(notificationAction);
            notification.setMeetingId(meeting.getId());
            notification.setContentTitle(userSession.getNameSurname() + contentTitle);
            notification.setContentText(meeting.getDateTime() + " " + meeting.getPatientDescription());
            notificationController.insertNotification(notification).showInLog();

        } else {
            Notification notification = new Notification();
            notification.setReceiverId(meeting.getDoctorId());
            notification.setAction(notificationAction);
            notification.setMeetingId(meeting.getId());
            notification.setContentTitle(userSession.getNameSurname() + contentTitle);
            notification.setContentText(meeting.getDateTime() + " " + meeting.getPatientDescription());
            notificationController.insertNotification(notification).showInLog();


        }

    }

    private void CloseSoftKeyboard() {
        // Get a reference to the InputMethodManager
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        // Hide the soft keyboard
        View focusView = getActivity().getCurrentFocus();
        if (focusView != null) {
            imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
        }
    }


}