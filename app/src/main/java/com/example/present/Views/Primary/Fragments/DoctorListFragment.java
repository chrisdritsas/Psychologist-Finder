package com.example.present.Views.Primary.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.present.Controllers.ConnectionResult;
import com.example.present.Controllers.Connectors.UserDataController;
import com.example.present.Models.Entities.UserData;
import com.example.present.R;
import com.example.present.Views.Adapters.DoctorUserDataRecyclerAdapter;
import com.example.present.Views.Primary.Activities.PrimaryScreenPatient;

import java.util.ArrayList;
import java.util.List;

public class DoctorListFragment extends Fragment implements DoctorUserDataRecyclerAdapter.OnItemClickListener {

    private final UserDataController userDataController = new UserDataController();
    private DoctorUserDataRecyclerAdapter adapter;
    List<UserData> doctorsList = new ArrayList<>();
    List<UserData> filteredDoctorsList = new ArrayList<>();

    public DoctorListFragment() {
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
        View view = inflater.inflate(R.layout.fragment_doctor_list, container, false);
        InitializeViewElements(view);
        return view;
    }

    private void InitializeViewElements(View view) {
        SearchView searchView = view.findViewById(R.id.searchViewDoctorList);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.doctorListRecyclerView);
        //recyclerView.setHasFixedSize(true);
        ConnectionResult result = userDataController.getDoctorList();
        if (result.getResult()) {
            doctorsList = (List<UserData>) result.getObj();
        }
        adapter = new DoctorUserDataRecyclerAdapter(getContext(), doctorsList, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onResume(){
        super.onResume();

    }
    @Override
    public void onItemClick(UserData userData) {
        //update the new view locally on the list
        for(UserData doctor : doctorsList){
            if(doctor.getUserId()== userData.getUserId()){
                doctor.getOffice().setViews(userData.getOffice().getViews()+1);
                adapter.notifyDataSetChanged();
                break;
            }
        }

        // get the child fragment manager
        FragmentManager childFragmentManager = getChildFragmentManager();
        // create a new instance of the fragment you want to show
        DoctorProfileFragment doctorProfileFrag = new DoctorProfileFragment(userData,false);
        // replace the existing fragment in the child fragment container with the new fragment
        PrimaryScreenPatient screen = (PrimaryScreenPatient) getActivity();
        screen.swapFragment(doctorProfileFrag,"doctorProfileFrag"+userData.getUserId(),true);
    }

    private void oldFilter(String query) {
        filteredDoctorsList.clear();
        if (query.isEmpty()) {
            filteredDoctorsList.addAll(doctorsList);
        } else {
            for (UserData doctor : doctorsList) {
                if (
                        doctor.getName().toLowerCase().contains(query.toLowerCase()) ||
                        doctor.getSurname().toLowerCase().toLowerCase().contains(query.toLowerCase()) ||
                        doctor.getOffice().getAddress().toLowerCase().contains(query.toLowerCase()) ||
                        doctor.getOffice().getAddressCode().toLowerCase().contains(query.toLowerCase()) ||
                        doctor.getFullAddress().toLowerCase().contains(query.toLowerCase())
                ) {
                    filteredDoctorsList.add(doctor);
                }
            }
        }
        adapter.setList(filteredDoctorsList);
        adapter.notifyDataSetChanged();
    }
    private void filter(String query) {
        filteredDoctorsList.clear();
        if (query.isEmpty()) {
            filteredDoctorsList.addAll(doctorsList);
        } else {
            for (UserData doctor : doctorsList) {
                // Check if the item matches the regular search criteria
                if (doctor.getName().toLowerCase().contains(query.toLowerCase()) ||
                        doctor.getSurname().toLowerCase().toLowerCase().contains(query.toLowerCase()) ||
                        doctor.getOffice().getAddress().toLowerCase().contains(query.toLowerCase()) ||
                        doctor.getOffice().getAddressCode().toLowerCase().contains(query.toLowerCase()) ||
                        doctor.getFullAddress().toLowerCase().contains(query.toLowerCase())) {
                    filteredDoctorsList.add(doctor);
                } else {
                    // Parse the search query to extract the search criteria
                    String[] parts = query.split(" ");
                    if (parts.length == 3 && isNumeric(parts[2])) {
                        String field = parts[0].toLowerCase();
                        String operator = parts[1];
                        int value = Integer.parseInt(parts[2]);

                        // Check if the item matches the search criteria
                        if (field.equals("online") && operator.equals(">") && doctor.getOffice().getOnlinePrice() > value) {
                            filteredDoctorsList.add(doctor);
                        } else if (field.equals("online") && operator.equals("<") && doctor.getOffice().getOnlinePrice() < value) {
                            filteredDoctorsList.add(doctor);
                        } else if (field.equals("physical") && operator.equals(">") && doctor.getOffice().getMeetingPrice() > value) {
                            filteredDoctorsList.add(doctor);
                        } else if (field.equals("physical") && operator.equals("<") && doctor.getOffice().getMeetingPrice() < value) {
                            filteredDoctorsList.add(doctor);
                        }
                    }
                }
            }
        }
        adapter.setList(filteredDoctorsList);
        adapter.notifyDataSetChanged();
    }

    // Helper function to check if a string is numeric
    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}