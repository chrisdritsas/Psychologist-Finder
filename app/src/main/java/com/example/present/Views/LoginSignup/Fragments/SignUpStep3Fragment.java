package com.example.present.Views.LoginSignup.Fragments;

import static java.lang.Double.parseDouble;
import static java.lang.Double.valueOf;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.present.App.Present;
import com.example.present.Controllers.ConnectionResult;
import com.example.present.Controllers.Connectors.OfficeController;
import com.example.present.Controllers.Connectors.UserController;
import com.example.present.Controllers.Connectors.UserDataController;
import com.example.present.Models.Entities.Office;
import com.example.present.Models.Entities.User;
import com.example.present.Models.Entities.UserData;
import com.example.present.R;
import com.example.present.Views.Primary.Activities.PrimaryScreenDoctor;

public class SignUpStep3Fragment extends Fragment {

    private EditText address;
    private EditText addressCode;
    private EditText telephone;
    private EditText mobile;
    private EditText workHours;
    private EditText onlinePrice;
    private EditText meetingPrice;
    private EditText biography;
    private Button next;

    private User user;
    private UserData userData;
    private Office office;

    private UserController userController = new UserController();
    private UserDataController userDataController = new UserDataController();
    private OfficeController officeController = new OfficeController();

    public SignUpStep3Fragment() {
        // Required empty public constructor
    }

    public SignUpStep3Fragment(User user, UserData userData, Office office) {
        this.user = user;
        this.userData = userData;
        this.office = office;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_step3, container, false);
        InitializeViewElements(view);

        return view;
    }

    private void InitializeViewElements(View view) {
        address = view.findViewById(R.id.editTxtAddressSignUp3);
        address.setText(office.getAddress());
        address.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                office.setAddress(address.getText().toString());
            }
        });
        addressCode = view.findViewById(R.id.editTxtAddressCodeSignUp3);
        addressCode.setText(office.getAddressCode());
        addressCode.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                office.setAddressCode((addressCode.getText().toString()));
            }
        });
        telephone = view.findViewById(R.id.editTxtPhoneSignUp3);
        telephone.setText(office.getPhone());
        telephone.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                office.setPhone((telephone.getText().toString()));
            }
        });
        mobile = view.findViewById(R.id.editTxtMobileSignUp3);
        mobile.setText(office.getMobile());
        mobile.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                office.setMobile((mobile.getText().toString()));
            }
        });
        workHours = view.findViewById(R.id.editTxtWorkHoursSignUp3);
        workHours.setText(office.getWorkHours());
        workHours.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                office.setWorkHours((workHours.getText().toString()));
            }
        });
        onlinePrice = view.findViewById(R.id.editTxtOnlinePriceSignUp3);
        if (office.getOnlinePrice() != 0.0d) {
            onlinePrice.setText(String.valueOf(office.getOnlinePrice()));
        }
        onlinePrice.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (!onlinePrice.getText().toString().isEmpty()) {
                    office.setOnlinePrice(Double.parseDouble(onlinePrice.getText().toString()));
                } else {
                    office.setOnlinePrice(0.0d);
                }
            }
        });
        meetingPrice = view.findViewById(R.id.editTxtMeetingPriceSignUp3);
        if (office.getMeetingPrice() != 0.0d) {
            meetingPrice.setText(String.valueOf(office.getMeetingPrice()));
        }
        meetingPrice.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (!meetingPrice.getText().toString().isEmpty()) {
                    office.setMeetingPrice(Double.parseDouble(meetingPrice.getText().toString()));
                } else {
                    office.setMeetingPrice(0.0d);
                }
            }
        });
        biography = view.findViewById(R.id.editTxtBiographySignUp3);
        biography.setText(office.getBiography());
        biography.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                office.setBiography((biography.getText().toString()));
            }
        });
        next = view.findViewById(R.id.btnNextSignUp3);
        next.setOnClickListener(v -> {
            office.setAddress(address.getText().toString());
            office.setAddressCode(addressCode.getText().toString());
            office.setPhone(telephone.getText().toString());
            office.setMobile(mobile.getText().toString());
            office.setWorkHours(workHours.getText().toString());

            if (!onlinePrice.getText().toString().isEmpty()) {
                office.setOnlinePrice(parseDouble(onlinePrice.getText().toString()));
            }
            if (!meetingPrice.getText().toString().isEmpty()) {
                office.setMeetingPrice(parseDouble(meetingPrice.getText().toString()));
            }
            office.setBiography(biography.getText().toString());
            ConnectionResult result;
            result = officeController.checkInsertOffice(office);

            if (!result.getResult()) {//invalid inputs show toast error
                Toast.makeText(this.getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
            //create doctor account in database
            if(createDoctor(user,userData,office)){
                //load doctor's primary screen
                if(Present.getInstance().getUserSession().getId()>0 && Present.getInstance().getUserSession().getIsDoctor()) {
                    Intent intent = new Intent(this.getContext(), PrimaryScreenDoctor.class);
                    startActivity(intent);
                }
            }
        });
    }
    private boolean createDoctor(User user, UserData userData,Office office) {
        ConnectionResult result;
        result = userController.insertUser(user);
        if (!result.getResult()) {
            Toast.makeText(this.getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
        result = userController.loginUser(user);
        if (!result.getResult()) {
            Toast.makeText(this.getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
        result = userDataController.insertUserData(userData);
        if (!result.getResult()) {
            Toast.makeText(this.getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
        result=officeController.insertOffice(office);
        if(!result.getResult()){
            Toast.makeText(this.getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}