package com.example.present.Views.Primary.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.present.App.Present;
import com.example.present.Controllers.ConnectionResult;
import com.example.present.Controllers.Connectors.MeetingController;
import com.example.present.Models.Entities.Meeting;
import com.example.present.R;
import com.example.present.Views.Adapters.MeetingRecyclerAdapter;
import com.example.present.Views.Primary.Activities.PrimaryScreenDoctor;
import com.example.present.Views.Primary.Activities.PrimaryScreenPatient;

import java.util.List;

public class MeetingListFragment extends Fragment implements MeetingRecyclerAdapter.OnItemClickListener {

    private final MeetingController meetingController = new MeetingController();
    private RecyclerView recyclerView;
    private MeetingRecyclerAdapter adapter;
    public MeetingListFragment() {
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
        View view = inflater.inflate(R.layout.fragment_meeting_list, container, false);
        InitializeViewElements(view);
        return view;
    }

    private void InitializeViewElements(View view) {

        recyclerView = view.findViewById(R.id.meetingListRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<Meeting> meetingsList;
        ConnectionResult result = meetingController.getUserMeetingList();
        if (result.getResult()) {
            meetingsList = (List<Meeting>) result.getObj();
            adapter = new MeetingRecyclerAdapter(meetingsList, this);
            recyclerView.setAdapter(adapter);
        }
    }

    public void onDataChanged(){
        List<Meeting> meetingsList;
        ConnectionResult result = meetingController.getUserMeetingList();
        if (result.getResult()) {
            meetingsList = (List<Meeting>) result.getObj();
            adapter.setList(meetingsList);
            adapter.notifyDataSetChanged();
        }
    }


    // TODO: fix the fragment screen
    @Override
    public void onItemClick(Meeting meeting) {
        // get the child fragment manager
        FragmentManager childFragmentManager = getChildFragmentManager();

        // create a new instance of the fragment you want to show
        UpdateMeetingFragment updateMeetingFrag = new UpdateMeetingFragment(meeting);
        // replace the existing fragment in the child fragment container with the new fragment

        if (Present.getInstance().getUserSession().getIsDoctor()) {
            PrimaryScreenDoctor screen = (PrimaryScreenDoctor) getActivity();
            screen.swapFragment(updateMeetingFrag, "updateMeetingFrag" + meeting.getId(), true);

        } else {
            PrimaryScreenPatient screen = (PrimaryScreenPatient) getActivity();
            screen.swapFragment(updateMeetingFrag, "updateMeetingFrag" + meeting.getId(), true);

        }
    }

}