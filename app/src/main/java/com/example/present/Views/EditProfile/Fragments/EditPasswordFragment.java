package com.example.present.Views.EditProfile.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.present.Controllers.ConnectionResult;
import com.example.present.Controllers.Connectors.UserController;
import com.example.present.Models.Entities.User;
import com.example.present.R;

public class EditPasswordFragment extends Fragment {

    private final UserController userController = new UserController();
    public EditPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_password, container, false);
        InitializeViewElements(view);
        return view;
    }

    private void InitializeViewElements(View view) {
        EditText newPass = view.findViewById(R.id.editTxtPassPassEdit);
        EditText newPassConfirm = view.findViewById(R.id.editTxtPassConfirmPassEdit);
        Button updateBtn = view.findViewById(R.id.btnUpdatePass);
        updateBtn.setOnClickListener(v -> {
            User user = new User();
            user.setPassword(newPass.getText().toString());
            user.setPasswordConfirm(newPassConfirm.getText().toString());
            ConnectionResult result = userController.updateUserPasswordById(user);
            Toast.makeText(this.getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
            if (result.getResult()) {
                newPass.setText("");
                newPassConfirm.setText("");
            }
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