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
import com.example.present.Models.Entities.Office;
import com.example.present.Models.Entities.User;
import com.example.present.Models.Entities.UserData;
import com.example.present.R;
import com.example.present.Views.MainActivity;
import com.example.present.Views.Primary.Activities.PrimaryScreenDoctor;
import com.example.present.Views.Primary.Activities.PrimaryScreenPatient;


public class LoginFragment extends Fragment {

    private final User user = new User();
    private final UserData userData = new UserData();
    private final Office office = new Office();
    private EditText email;
    private EditText pass;
    private Button signUpBtn;
    private Button logInBtn;

    private final UserController userController = new UserController();

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        InitializeViewElements(view);

        return view;
    }

    private void InitializeViewElements(View view) {
        email = view.findViewById(R.id.editTxtEmailLogin);
        pass = view.findViewById(R.id.editTxtPassLogin);

        email.setText("");
        pass.setText("");
        signUpBtn = view.findViewById(R.id.btnSignUpLogin);
        signUpBtn.setOnClickListener(v -> {
            Fragment signUp1Frag = new SignUpStep1Fragment(user, userData, office);
            ((MainActivity) getActivity()).swapFragment(signUp1Frag);
        });
        logInBtn = view.findViewById(R.id.btnLogInLogin);
        logInBtn.setOnClickListener(v -> {
            User userLogin = new User();
            userLogin.setEmail(email.getText().toString());
            userLogin.setPassword(pass.getText().toString());
            ConnectionResult result;
            result = userController.loginUser(userLogin);
            if (!result.getResult()) {
                Toast.makeText(this.getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
            Present.getInstance().getUserSession();
            if (Present.getInstance().getUserSession().getId() > 0 && Present.getInstance().getUserSession().getIsDoctor()) {
                email.setText("");
                pass.setText("");
                Intent intent = new Intent(this.getContext(), PrimaryScreenDoctor.class);
                startActivity(intent);
            } else if (Present.getInstance().getUserSession().getId() > 0 && !Present.getInstance().getUserSession().getIsDoctor()) {
                email.setText("");
                pass.setText("");
                Intent intent = new Intent(this.getContext(), PrimaryScreenPatient.class);
                startActivity(intent);
            }
        });
    }

}