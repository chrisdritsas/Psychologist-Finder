package com.example.present.Views.Primary.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.present.App.Present;
import com.example.present.Controllers.ConnectionResult;
import com.example.present.Controllers.Connectors.ChatController;
import com.example.present.Controllers.Connectors.UserDataController;
import com.example.present.Models.Entities.UserData;
import com.example.present.R;
import com.example.present.Views.Adapters.UserDataRecyclerAdapter;
import com.example.present.Views.Primary.Activities.PrimaryScreenDoctor;
import com.example.present.Views.Primary.Activities.PrimaryScreenPatient;

import java.util.ArrayList;
import java.util.List;

public class ConversationListFragment extends Fragment implements UserDataRecyclerAdapter.OnItemClickListener {

    private final ChatController chatController = new ChatController();
    private final UserDataController userDataController = new UserDataController();

    public ConversationListFragment() {
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
        View view = inflater.inflate(R.layout.fragment_conversation_list, container, false);
        InitializeViewElements(view);
        return view;
    }

    private void InitializeViewElements(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.conversationListRecyclerView);
        recyclerView.setHasFixedSize(true);
        List<Integer> conversationIdsList;
        List<UserData> conversationsUserData=new ArrayList<>();
        ConnectionResult result = chatController.getConversationIdsByUserSession();
        if (result.getResult()) {
            conversationIdsList = (List<Integer>) result.getObj();
            for (Integer id : conversationIdsList) {
                ConnectionResult result1 = userDataController.getUserNameSurnameByUserId(id);
                if (result1.getResult()) {
                    UserData data = new UserData();
                    data.setUserId(id);
                    data.setName(((UserData) result1.getObj()).getName());
                    data.setSurname(((UserData) result1.getObj()).getSurname());
                    conversationsUserData.add(data);

                }
            }
            UserDataRecyclerAdapter adapter = new UserDataRecyclerAdapter(conversationsUserData, this);
            recyclerView.setAdapter(adapter);
        }
    }

    // TODO: fix the fragment screen
    @Override
    public void onItemClick(UserData userData) {
        // get the child fragment manager
        FragmentManager childFragmentManager = getChildFragmentManager();

        // create a new instance of the fragment you want to show
        ChatFragment chatFrag = new ChatFragment(userData);

        // replace the existing fragment in the child fragment container with the new fragment
        if(Present.getInstance().getUserSession().getIsDoctor()){
            PrimaryScreenDoctor screen = (PrimaryScreenDoctor) getActivity();
            screen.swapFragment(chatFrag,"chatFrag"+userData.getUserId(),true);
        }
        else{
            PrimaryScreenPatient screen = (PrimaryScreenPatient) getActivity();
            screen.swapFragment(chatFrag,"chatFrag"+userData.getUserId(),true);
        }

    }
}