package com.example.present.Views.EditProfile.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.present.Controllers.ConnectionResult;
import com.example.present.Controllers.Connectors.UserDataController;
import com.example.present.Models.Entities.UserData;
import com.example.present.R;

public class EditUserDataFragment extends Fragment {

    final UserDataController userDataController = new UserDataController();
    ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getData() != null) {
                        Uri imageUri = data.getData();
                        newProfilePicPath = getPathFromUri(imageUri);
                    }
                }
            });
    private String newProfilePicPath = null;
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    mGetContent.launch(intent);
                }
                else{
                    Toast.makeText(this.getContext(), "Permission denied, can't open device files.", Toast.LENGTH_SHORT).show();
                }
            });

    public EditUserDataFragment() {
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
        View view = inflater.inflate(R.layout.fragment_edit_user_data, container, false);
        InitializeViewElements(view);
        return view;
    }

    private void InitializeViewElements(View view) {
        EditText newName = view.findViewById(R.id.editTxtNameUserDataEdit);
        EditText newSurname = view.findViewById(R.id.editTxtSurnameUserDataEdit);
        EditText newAddress = view.findViewById(R.id.editTxtAddressUserDataEdit);
        EditText newAddressCode = view.findViewById(R.id.editTxtAddressCodeUserDataEdit);
        EditText newCity = view.findViewById(R.id.editTxtCityUserDataEdit);
        EditText newTelephone = view.findViewById(R.id.editTxtTelephoneUserDataEdit);
        UserData oldUserData;
        ConnectionResult result1 = userDataController.getUserDataByUserSession();
        if (result1.getResult()) {
            oldUserData = (UserData) result1.getObj();
            newName.setText(oldUserData.getName());
            newSurname.setText(oldUserData.getSurname());
            newAddress.setText(oldUserData.getAddress());
            newAddressCode.setText(oldUserData.getAddressCode());
            newCity.setText(oldUserData.getCity());
            newTelephone.setText(oldUserData.getTelephone());
            newProfilePicPath = oldUserData.getProfilePicture();
        } else {
            Toast.makeText(this.getContext(), result1.getMessage(), Toast.LENGTH_SHORT).show();
        }
        Button updateBtn = view.findViewById(R.id.btnUpdateUserData);
        updateBtn.setOnClickListener(v -> {
            UserData userData = new UserData();
            userData.setName(newName.getText().toString());
            userData.setSurname(newSurname.getText().toString());
            userData.setAddress(newAddress.getText().toString());
            userData.setAddressCode(newAddressCode.getText().toString());
            userData.setCity(newCity.getText().toString());
            userData.setTelephone(newTelephone.getText().toString());
            userData.setProfilePicture(newProfilePicPath);
            ConnectionResult result2 = userDataController.updateUserData(userData);
            Toast.makeText(this.getContext(), result2.getMessage(), Toast.LENGTH_SHORT).show();
            if (result2.getResult()) {
                Intent intent = new Intent("com.example.Present.DATA_UPDATE");
                intent.putExtra("UserData.nameSurname", userData.getNameSurname());
                intent.putExtra("UserData.profilePicture", userData.getProfilePicture());
                intent.putExtra("UserData.city",userData.getCity());
                requireActivity().sendBroadcast(intent);
            }
            CloseSoftKeyboard();
        });
        Button setProfImageBtn = view.findViewById(R.id.btnSetProfImageUserDataEdit);
        setProfImageBtn.setOnClickListener(view1 -> {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted, request it
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            } else {
                // Permission already granted, do something
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                mGetContent.launch(intent);
            }
        });
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

    private String getPathFromUri(Uri uri) {
        String path = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getContext().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index);
            cursor.close();
        }
        return path;
    }
}