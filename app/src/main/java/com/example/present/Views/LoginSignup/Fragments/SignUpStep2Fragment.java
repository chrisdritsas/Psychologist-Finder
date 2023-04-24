package com.example.present.Views.LoginSignup.Fragments;

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
import com.example.present.Controllers.Connectors.UserController;
import com.example.present.Controllers.Connectors.UserDataController;
import com.example.present.Models.Entities.Office;
import com.example.present.Models.Entities.User;
import com.example.present.Models.Entities.UserData;
import com.example.present.R;
import com.example.present.Views.MainActivity;
import com.example.present.Views.Primary.Activities.PrimaryScreenPatient;

public class SignUpStep2Fragment extends Fragment {

    private EditText name;
    private EditText surname;
    private EditText address;
    private EditText addressCode;
    private EditText city;
    private EditText telephone;
    private Button next;
    private User user;
    private UserData userData;
    private Office office;
    private UserController userController = new UserController();
    private UserDataController userDataController = new UserDataController();

    public SignUpStep2Fragment() {
        // Required empty public constructor
    }

    public SignUpStep2Fragment(User user, UserData userData, Office office) {
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
        View view = inflater.inflate(R.layout.fragment_sign_up_step2, container, false);
        InitializeViewElements(view);

        return view;
    }

    private void InitializeViewElements(View view) {

        name = view.findViewById(R.id.editTxtNameSignUp2);
        name.setText(userData.getName());
        name.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                userData.setName(name.getText().toString());
            }
        });
        surname = view.findViewById(R.id.editTxtSurnameSignUp2);
        surname.setText(userData.getSurname());
        surname.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                userData.setSurname(surname.getText().toString());
            }
        });
        address = view.findViewById(R.id.editTxtAddressSignUp2);
        address.setText(userData.getAddress());
        address.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                userData.setAddress(address.getText().toString());
            }
        });
        addressCode = view.findViewById(R.id.editTxtAddressCodeSignUp2);
        addressCode.setText(userData.getAddressCode());
        addressCode.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                userData.setAddressCode(addressCode.getText().toString());
            }
        });
        city = view.findViewById(R.id.editTxtCitySignUp2);
        city.setText(userData.getCity());
        name.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                userData.setCity(city.getText().toString());
            }
        });
        telephone = view.findViewById(R.id.editTxtTelephoneSignUp2);
        telephone.setText(userData.getTelephone());
        telephone.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                userData.setTelephone(telephone.getText().toString());
            }
        });
        next = view.findViewById(R.id.btnNextSignUp2);
        if (!user.getIsDoctor()) {
            next.setText(R.string.finish_btn);
        }
        next.setOnClickListener(v -> {
            userData.setName(name.getText().toString());
            userData.setSurname(surname.getText().toString());
            userData.setAddress(address.getText().toString());
            userData.setAddressCode(addressCode.getText().toString());
            userData.setCity(city.getText().toString());
            userData.setTelephone(telephone.getText().toString());

            ConnectionResult result;
            result = userDataController.checkInsertUserData(userData);

            if (!result.getResult()) {//invalid inputs show toast error
                Toast.makeText(this.getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
            if (user.getIsDoctor()) {//if sign up user is a doctor continue to step 3 office frag
                Fragment signUp3Frag = new SignUpStep3Fragment(user, userData, office);
                ((MainActivity) getActivity()).swapFragment(signUp3Frag);
                return;
            }
            //create patient account in database
            if (createPatient(user, userData)) {
                //load patient's primary screen
                if(Present.getInstance().getUserSession().getId()>0 && !Present.getInstance().getUserSession().getIsDoctor()) {
                    Intent intent = new Intent(this.getContext(), PrimaryScreenPatient.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean createPatient(User user, UserData userData) {
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
        return true;
    }
}