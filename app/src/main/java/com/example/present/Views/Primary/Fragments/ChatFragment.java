package com.example.present.Views.Primary.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.present.App.Present;
import com.example.present.Controllers.ConnectionResult;
import com.example.present.Controllers.Connectors.ChatController;
import com.example.present.Controllers.Connectors.NotificationController;
import com.example.present.Controllers.Connectors.UserDataController;
import com.example.present.Models.Contracts.Database.NotificationContract;
import com.example.present.Models.Entities.Chat;
import com.example.present.Models.Entities.Notification;
import com.example.present.Models.Entities.UserData;
import com.example.present.R;
import com.example.present.Views.Adapters.ChatRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment implements ChatRecyclerAdapter.OnItemClickListener {

    private final String notificationAction = NotificationContract.Controller.ACTION_CHAT;
    private final String contentTitle ="New message from ";
    private final UserData selectedUserData;
    private EditText chatMsg;
    private UserData userSession = new UserData();
    private List<Chat> chatsList;
    private ChatRecyclerAdapter adapter;
    private final ChatController chatController = new ChatController();
    private final UserDataController userDataController = new UserDataController();
    private final NotificationController notificationController = new NotificationController();

    public ChatFragment(UserData selectedUserData) {
        // Required empty public constructor
        this.selectedUserData = selectedUserData;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConnectionResult result = userDataController.getUserNameSurnameByUserId(Present.getInstance().getUserSession().getId());
        if(result.getResult()){
            userSession = (UserData)result.getObj();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        InitializeViewElements(view);
        return view;
    }

    private void InitializeViewElements(View view) {

        TextView receiverNameSurname = view.findViewById(R.id.receiverChatNameTxtView);
        receiverNameSurname.append(" " + selectedUserData.getNameSurname());
        RecyclerView recyclerView = view.findViewById(R.id.chatListRecyclerView);
        //recyclerView.setHasFixedSize(true);

        ConnectionResult result = chatController.getConversationChatsByUserSessionAndConvId(selectedUserData.getUserId());

        if (result.getResult()) {
            chatsList = (List<Chat>) result.getObj();
        } else {
            chatsList = new ArrayList<>();
        }
        adapter = new ChatRecyclerAdapter(getContext(), chatsList, this);
        recyclerView.setAdapter(adapter);
        // Create a new LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        // Set the orientation of the layout manager to vertical
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // Set the layout manager for the RecyclerView
        recyclerView.setLayoutManager(layoutManager);
        // Set the initial position of the list to the end
        int position = chatsList.size() - 1;
        layoutManager.scrollToPosition(position);


        chatMsg = view.findViewById(R.id.editTxtChat);
        Button sendBtn = view.findViewById(R.id.btnChatSend);
        sendBtn.setOnClickListener(view1 -> {
            Chat chat = new Chat();
            chat.setMessage(chatMsg.getText().toString());
            chat.setReceiverId(selectedUserData.getUserId());
            ConnectionResult result1 = chatController.insertChat(chat);
            if (result1.getResult()) {
                createNotification();
                chatMsg.setText("");
                chatsList.add(chat);
                adapter.notifyItemInserted(chatsList.size() - 1);
                LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
                // Set the orientation of the layout manager to vertical
                layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
                // Set the layout manager for the RecyclerView
                recyclerView.setLayoutManager(layoutManager1);
                // Set the initial position of the list to the end
                int position1 = chatsList.size() - 1;
                layoutManager1.scrollToPosition(position1);
                CloseSoftKeyboard();

            } else {
                Toast.makeText(this.getContext(), result1.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void createNotification(){
        Notification notification = new Notification();
        notification.setReceiverId(selectedUserData.getUserId());
        notification.setAction(notificationAction);
        notification.setChatSenderId(Present.getInstance().getUserSession().getId());
        notification.setContentTitle(contentTitle+userSession.getNameSurname());
        notification.setContentText(chatMsg.getText().toString());
        notificationController.insertNotification(notification).showInLog();

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

    @Override
    public void onItemClick(Chat chat) {

    }
}