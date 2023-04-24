package com.example.present.Views.LoginSignup.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.present.Controllers.ConnectionResult;
import com.example.present.Controllers.Connectors.UserController;
import com.example.present.Models.Entities.Office;
import com.example.present.Models.Entities.User;
import com.example.present.Models.Entities.UserData;
import com.example.present.R;
import com.example.present.Views.MainActivity;

public class SignUpStep1Fragment extends Fragment {

    private User user;
    private UserData userData;
    private Office office;
    private EditText email;
    private EditText password;
    private EditText passwordConf;
    private CheckBox isDoctor;
    private Button next;
    private UserController userController = new UserController();

    public SignUpStep1Fragment() {
        // Required empty public constructor
    }
    public SignUpStep1Fragment(User user, UserData userData, Office office){
        this.user=user;
        this.userData=userData;
        this.office=office;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_step1, container, false);
        InitializeViewElements(view);

        return view;
    }

    private void InitializeViewElements(View view){

        email = view.findViewById(R.id.editTxtEmailSignUp1);
        email.setText(user.getEmail());
        email.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                user.setEmail(email.getText().toString());
            }
        });
        password = view.findViewById(R.id.editTxtPassSignUp1);
        password.setText(user.getPassword());
        password.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                user.setPassword(password.getText().toString());
            }
        });
        passwordConf = view.findViewById(R.id.editTxtPassConfirmSignUp1);
        passwordConf.setText(user.getPasswordConfirm());
        passwordConf.setOnFocusChangeListener((v, hasFocus) ->{
            if (!hasFocus) {
                user.setPasswordConfirm(passwordConf.getText().toString());
            }
        });
        isDoctor = view.findViewById(R.id.isDoctorSignUp1);
        isDoctor.setChecked(user.getIsDoctor());
        isDoctor.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                user.setIsDoctor(true);
            } else {
                user.setIsDoctor(false);            }
        });
        next = view.findViewById(R.id.btnNextSignUp1);
        next.setOnClickListener(v -> {
            user.setEmail(email.getText().toString());
            user.setPassword(password.getText().toString());
            user.setPasswordConfirm((passwordConf.getText().toString()));
            user.setIsDoctor(isDoctor.isChecked());
            ConnectionResult result;
            result=userController.checkInsertUser(user);
            if(result.getResult()){
                Fragment signUp2Frag = new SignUpStep2Fragment(user,userData,office);
                ((MainActivity) getActivity()).swapFragment(signUp2Frag);
            }
            else{//invalid inputs show toast error
                Toast.makeText(this.getContext(),result.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


}