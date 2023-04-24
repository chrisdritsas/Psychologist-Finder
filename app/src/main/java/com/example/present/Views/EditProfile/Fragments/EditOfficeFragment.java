package com.example.present.Views.EditProfile.Fragments;

import static java.lang.Double.parseDouble;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.present.Controllers.ConnectionResult;
import com.example.present.Controllers.Connectors.OfficeController;
import com.example.present.Models.Entities.Office;
import com.example.present.Models.Entities.UserData;
import com.example.present.R;

public class EditOfficeFragment extends Fragment {

    private final OfficeController officeController = new OfficeController();
    public EditOfficeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_office, container, false);
        InitializeViewElements(view);
        return view;
    }

    private void InitializeViewElements(View view) {
        EditText newAddress = view.findViewById(R.id.editTxtAddressOfficeEdit);
        EditText newAddressCode = view.findViewById(R.id.editTxtAddressCodeOfficeEdit);
        EditText newTelephone = view.findViewById(R.id.editTxtPhoneOfficeEdit);
        EditText newMobile = view.findViewById(R.id.editTxtMobileOfficeEdit);
        EditText newWorkHours = view.findViewById(R.id.editTxtWorkHoursOfficeEdit);
        EditText newOnlinePrice = view.findViewById(R.id.editTxtOnlinePriceOfficeEdit);
        EditText newMeetingPrice=view.findViewById(R.id.editTxtMeetingPriceOfficeEdit);
        EditText newBiography=view.findViewById(R.id.editTxtBiographyOfficeEdit);
        Office oldOffice;

        ConnectionResult result1 = officeController.getUserSessionOfficeData();
        if (result1.getResult()) {
            oldOffice = (Office) result1.getObj();
            newAddress.setText(oldOffice.getAddress());
            newAddressCode.setText(oldOffice.getAddressCode());
            newTelephone.setText(oldOffice.getPhone());
            newMobile.setText(oldOffice.getMobile());
            newWorkHours.setText(oldOffice.getWorkHours());
            newOnlinePrice.setText(String.valueOf(oldOffice.getOnlinePrice()));
            newMeetingPrice.setText(String.valueOf(oldOffice.getMeetingPrice()));
            newBiography.setText(oldOffice.getBiography());
        } else {
            Toast.makeText(this.getContext(), result1.getMessage(), Toast.LENGTH_SHORT).show();
        }
        Button updateBtn = view.findViewById(R.id.btnUpdateOffice);
        updateBtn.setOnClickListener(v -> {

            Office office = new Office();
            office.setAddress(newAddress.getText().toString());
            office.setAddressCode(newAddressCode.getText().toString());
            office.setPhone(newTelephone.getText().toString());
            office.setMobile(newMobile.getText().toString());
            office.setWorkHours(newWorkHours.getText().toString());
            if (!newOnlinePrice.getText().toString().isEmpty()) {
                office.setOnlinePrice(Double.parseDouble(newOnlinePrice.getText().toString()));
            }
            if(!newMeetingPrice.getText().toString().isEmpty()){
                office.setMeetingPrice(Double.parseDouble(newMeetingPrice.getText().toString()));
            }
            office.setBiography(newBiography.getText().toString());
            ConnectionResult result2 = officeController.updateOffice(office);
            if(result2.getResult()){
                Intent intent = new Intent("com.example.Present.OFFICE_UPDATE");
                requireActivity().sendBroadcast(intent);
            }
            Toast.makeText(this.getContext(), result2.getMessage(), Toast.LENGTH_SHORT).show();
            CloseSoftKeyboard();
        });
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