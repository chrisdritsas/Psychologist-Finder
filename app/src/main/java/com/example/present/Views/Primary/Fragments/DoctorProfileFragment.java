package com.example.present.Views.Primary.Fragments;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.present.Controllers.ConnectionResult;
import com.example.present.Controllers.Connectors.OfficeController;
import com.example.present.Controllers.Connectors.UserDataController;
import com.example.present.Models.Entities.UserData;
import com.example.present.R;
import com.example.present.Views.Primary.Activities.PrimaryScreenPatient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class DoctorProfileFragment extends Fragment implements OnMapReadyCallback{//, OnMapsSdkInitializedCallback {
    private final boolean preview;
    private boolean isReceiverRegistered = false;
    private GoogleMap googleMap;
    private MapView mapView;
    private final OfficeController officeController = new OfficeController();
    private final UserDataController userDataController = new UserDataController();
    private UserData selectedDoctor;
    private TextView sessionNameSurname;
    private TextView contact;
    private TextView fullAddress;
    private TextView workHours;
    private TextView meetingPrice;
    private TextView onlineMeetingPrice;
    private TextView biography;
    private ImageView imageView;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        //updates TextView userNameSurname
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.example.Present.OFFICE_UPDATE") || intent.getAction().equals("com.example.Present.DATA_UPDATE")) {
                ConnectionResult result = userDataController.getDoctorList();
                if (result.getResult()) {
                    List<UserData> doctorList = ((List<UserData>) result.getObj());
                    for (UserData doctor : doctorList) {
                        if (doctor.getUserId() == selectedDoctor.getUserId()) {
                            Bitmap bitmap = BitmapFactory.decodeFile(doctor.getProfilePicture());
                            // Set the bitmap to the image button
                            imageView.setImageBitmap(bitmap);
                            sessionNameSurname.setText(doctor.getNameSurname());
                            fullAddress.setText(doctor.getFullAddress());
                            contact.setText(doctor.getContactInfo());
                            workHours.setText(doctor.getOffice().getWorkHours());
                            meetingPrice.setText(doctor.getOffice().getMeetingPrice() +" €");
                            onlineMeetingPrice.setText(doctor.getOffice().getOnlinePrice()+" €");
                            biography.setText(doctor.getOffice().getBiography());
                        }
                    }
                }
            }
        }
    };

    //permission handler
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    if (selectedDoctor.getProfilePicture() != null) {
                        Bitmap bitmap = BitmapFactory.decodeFile(selectedDoctor.getProfilePicture());
                        // Set the bitmap to the image button
                        imageView.setImageBitmap(bitmap);
                    }
                } else {
                    // Permission denied, show a message or disable features
                    Toast.makeText(this.getContext(), "Permission denied, can't load photo.", Toast.LENGTH_SHORT).show();
                }
            });

    public DoctorProfileFragment(UserData selectedDoctor, boolean preview) {
        // Required empty public constructor
        this.selectedDoctor = selectedDoctor;
        this.preview = preview;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_profile, container, false);
        //MapsInitializer.initialize(getContext(), MapsInitializer.Renderer.LATEST, this);
        InitializeViewElements(view, savedInstanceState);
        return view;
    }

    private void InitializeViewElements(View view, Bundle savedInstanceState) {
        if (!preview) {
            //ConnectionResult incViewsRes = officeController.increaseOfficeViewsByOfficeId(selectedDoctor.getOffice().getId());
            officeController.increaseOfficeViewsByOfficeId(selectedDoctor.getOffice().getId()).showInLog();
            ConnectionResult result = officeController.getOfficeViewsByOfficeId(selectedDoctor.getOffice().getId());
            if (result.getResult()) {
                int updatedViews = (int) result.getObj();
                selectedDoctor.getOffice().setViews(updatedViews);
            }
        }

        imageView = view.findViewById(R.id.UserPhotoDoctorProf);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        } else {
            // Permission already granted, do something
            if (selectedDoctor.getProfilePicture() != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(selectedDoctor.getProfilePicture());
                // Set the bitmap to the image button
                imageView.setImageBitmap(bitmap);
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }
        }
        sessionNameSurname = view.findViewById(R.id.txtViewSessionNameSurnameDoctorProf);
        sessionNameSurname.setText(selectedDoctor.getNameSurname());
        TextView viewsTxtView = view.findViewById(R.id.txtViewViewValuesDoctorProfile);
        viewsTxtView.setText(String.valueOf(selectedDoctor.getOffice().getViews()));
        fullAddress = view.findViewById(R.id.txtViewAddressValueDoctorProf);
        fullAddress.setText(selectedDoctor.getFullAddress());
        contact = view.findViewById(R.id.txtViewContactValueDoctorProf);
        contact.setText(selectedDoctor.getContactInfo());
        workHours = view.findViewById(R.id.txtViewWorkHoursValueDoctorProf);
        workHours.setText(selectedDoctor.getOffice().getWorkHours());
        meetingPrice = view.findViewById(R.id.txtViewMeetingValueDoctorProf);
        meetingPrice.setText(selectedDoctor.getOffice().getMeetingPrice() + "€");
        onlineMeetingPrice = view.findViewById(R.id.txtViewOnlineMeetingValueDoctorProf);
        onlineMeetingPrice.setText(selectedDoctor.getOffice().getOnlinePrice() + "€");
        biography = view.findViewById(R.id.txtViewBiographyValueDoctorProf);
        biography.setText(selectedDoctor.getOffice().getBiography());
        Button meetingBtn = view.findViewById(R.id.btnMeetingDoctorProf);
        if (preview) {
            meetingBtn.setEnabled(false);
        }
        meetingBtn.setOnClickListener(v -> {
            CreateMeetingFragment createMeetingFrag = new CreateMeetingFragment(selectedDoctor);
            PrimaryScreenPatient screen = (PrimaryScreenPatient) getActivity();
            screen.swapFragment(createMeetingFrag, "createMeetingFrag" + selectedDoctor.getUserId(), true);
        });
        Button messageBtn = view.findViewById(R.id.btnMessageDoctorProf);
        if (preview) {
            messageBtn.setEnabled(false);
        }
        messageBtn.setOnClickListener(v -> {
            ChatFragment chatFrag = new ChatFragment(selectedDoctor);
            PrimaryScreenPatient screen = (PrimaryScreenPatient) getActivity();
            screen.swapFragment(chatFrag, "createChatFrag" + selectedDoctor.getUserId(), true);
        });

        mapView = view.findViewById(R.id.mapViewDoctorProf);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        //finding latlng from address by google
        new GeocodeTask().execute(selectedDoctor.getFullAddress() + " Greece");
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        if (isReceiverRegistered) {
            requireActivity().unregisterReceiver(receiver);
            isReceiverRegistered = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.Present.OFFICE_UPDATE");
        filter.addAction("com.example.Present.DATA_UPDATE");
        requireActivity().registerReceiver(receiver, filter);
        isReceiverRegistered = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private class GeocodeTask extends AsyncTask<String, Void, LatLng> {

        @Override
        protected LatLng doInBackground(String... strings) {
            String address = strings[0];
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocationName(address, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addresses != null && addresses.size() > 0) {
                Address location = addresses.get(0);
                return new LatLng(location.getLatitude(), location.getLongitude());
            }
            return null;
        }

        @Override
        protected void onPostExecute(LatLng latLng) {
            if (latLng != null) {
                // Add a marker to the map
                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(selectedDoctor.getNameSurname());
                googleMap.addMarker(markerOptions);

                // Animate the camera to the marker position
                CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(16).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        }
    }


}