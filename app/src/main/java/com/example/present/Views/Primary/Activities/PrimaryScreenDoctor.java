package com.example.present.Views.Primary.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.present.Controllers.ConnectionResult;
import com.example.present.Controllers.Connectors.NotificationController;
import com.example.present.Controllers.Connectors.OfficeController;
import com.example.present.Controllers.Connectors.UserController;
import com.example.present.Controllers.Connectors.UserDataController;
import com.example.present.Models.Contracts.Database.NotificationContract;
import com.example.present.Models.Entities.Notification;
import com.example.present.Models.Entities.Office;
import com.example.present.Models.Entities.UserData;
import com.example.present.R;
import com.example.present.Views.EditProfile.Activities.EditProfileActivity;
import com.example.present.Views.Notifications.NotificationActivity;
import com.example.present.Views.Primary.Fragments.ConversationListFragment;
import com.example.present.Views.Primary.Fragments.DoctorProfileFragment;
import com.example.present.Views.Primary.Fragments.MeetingListFragment;

import java.util.ArrayList;
import java.util.List;

public class PrimaryScreenDoctor extends AppCompatActivity {

    private final String CHANNEL_ID = "Present_Notifications";
    private final NotificationController notificationController = new NotificationController();
    private TextView userNameSurname;
    private ImageButton profBtn;
    private UserData sessionUserData;
    private final UserController userController = new UserController();
    private final UserDataController userDataController = new UserDataController();
    private final OfficeController officeController = new OfficeController();
    private boolean isReceiverRegistered = false;
    private FragmentContainerView fragmentContainerView;
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        //updates TextView userNameSurname
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.example.Present.DATA_UPDATE")) {
                String data = intent.getStringExtra("UserData.nameSurname");
                userNameSurname.setText(data);
            }
            if (intent.getAction().equals("com.example.Present.DATA_UPDATE")) {
                String profPicLink = intent.getStringExtra("UserData.profilePicture");
                Bitmap bitmap = BitmapFactory.decodeFile(profPicLink);
                // Set the bitmap to the image button
                profBtn.setImageBitmap(bitmap);
            }
            //updates the sessionUserData for the Preview profile object transfer
            if (intent.getAction().equals("com.example.Present.DATA_UPDATE") || intent.getAction().equals("com.example.Present.OFFICE_UPDATE")) {
                ConnectionResult userDataResult = userDataController.getUserDataByUserSession();
                if (userDataResult.getResult()) {
                    sessionUserData = (UserData) userDataResult.getObj();
                }
                ConnectionResult officeResult = officeController.getUserSessionOfficeData();
                if (officeResult.getResult()) {
                    sessionUserData.setOffice((Office) officeResult.getObj());
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary_screen_doctor);
        InitializeViewElements(this);
        createNotificationChannel();
        createUnreadNotifications();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isReceiverRegistered) {
            unregisterReceiver(receiver);
            isReceiverRegistered = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.Present.DATA_UPDATE");
        filter.addAction("com.example.Present.OFFICE_UPDATE");
        registerReceiver(receiver, filter);
        isReceiverRegistered = true;
    }

    public void swapFragment(Fragment newFragment, String fragmentTag, boolean addToStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment existingFragment = fragmentManager.findFragmentByTag(fragmentTag);
        List<Fragment> fragments = fragmentManager.getFragments();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragmentTransaction.hide(fragment);
            }
        }
        fragmentTransaction.commit();
        if (existingFragment != null) {
            if (addToStack) {
                fragmentManager.beginTransaction().show(existingFragment).addToBackStack(null).commit();
            } else {
                fragmentManager.beginTransaction().show(existingFragment).commit();
            }
        } else {
            if (addToStack) {
                fragmentManager.beginTransaction().add(fragmentContainerView.getId(), newFragment, fragmentTag).addToBackStack(null).commit();
                Log.d("TAG", "swapFragment: ");
            } else {
                fragmentManager.beginTransaction().add(fragmentContainerView.getId(), newFragment, fragmentTag).commit();
                Log.d("TAG", "swapFragment: ");
            }
        }
    }

    private void emptyBackStack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.popBackStackImmediate()) ;
    }

    private void InitializeViewElements(Activity activity) {
        fragmentContainerView = activity.findViewById(R.id.fragmentContainerViewDoctorPS);
        swapFragment(new MeetingListFragment(), "meetingListFrag", false);
        userNameSurname = activity.findViewById(R.id.txtViewSessionNameSurnameDoctorPS);
        profBtn = activity.findViewById(R.id.UserProfileBtnDoctorPS);
        profBtn.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        Button previewProfileBtn = activity.findViewById(R.id.btnPreviewProfileDoctorPS);
        Button calendarBtn = activity.findViewById(R.id.btnCalendarDoctorPS);
        Button messagesBtn = activity.findViewById(R.id.btnMessagesDoctorPS);
        ConnectionResult result;
        result = userDataController.getUserDataByUserSession();
        if (result.getResult()) {
            sessionUserData = (UserData) result.getObj();
            String imagePath = ((UserData) result.getObj()).getProfilePicture();
            if (imagePath != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                // Set the bitmap to the image button
                profBtn.setImageBitmap(bitmap);

            }
        }
        ConnectionResult result1 = officeController.getUserSessionOfficeData();
        if (result.getResult()) {
            sessionUserData.setOffice((Office) result1.getObj());
        }
        userNameSurname.setText(sessionUserData.getNameSurname());


        profBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditProfileActivity.class);
            startActivity(intent);
        });
        previewProfileBtn.setOnClickListener(v -> {
            emptyBackStack();
            if (sessionUserData.getOffice() != null) {
                Fragment doctorProfileFrag = new DoctorProfileFragment(sessionUserData, true);
                swapFragment(doctorProfileFrag, "doctorProfileFrag", false);
            }
        });
        calendarBtn.setOnClickListener(v -> {
            emptyBackStack();
            Fragment meetingListFrag = new MeetingListFragment();
            swapFragment(meetingListFrag, "meetingListFrag", false);

        });
        messagesBtn.setOnClickListener(v -> {
            emptyBackStack();
            Fragment conversationListFrag = new ConversationListFragment();
            swapFragment(conversationListFrag, "conversationListFrag", false);
        });
    }

    //fix it the same way as PrimaryScreenPatient.onBackPressed() to keep screen order on back button
    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            //if there is a stacked frag pop it
            fragmentManager.popBackStack();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            List<Fragment> fragments = fragmentManager.getFragments();
            //makes the frag behind the popped frag visible
            transaction.show(fragments.get(fragments.size() - 2));
            //commits the change
            transaction.commit();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.logout_title))
                    .setMessage(getResources().getString(R.string.logout_msg))
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ConnectionResult logoutResult = userController.logOutUser();
                            if (logoutResult.getResult()) {
                                // Get an instance of the NotificationManagerCompat
                                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                                // Cancel all notifications issued by your app
                                notificationManager.cancelAll();
                                finish();
                            }
                        }
                    })
                    .setNegativeButton(android.R.string.no, (dialog, which) -> {
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    protected void createUnreadNotifications() {
        List<Notification> notificationList = new ArrayList<>();
        ConnectionResult getNotificationListResult = notificationController.getSessionUserUnreadNotificationList();
        if (getNotificationListResult.getResult()) {
            notificationList = (List<Notification>) getNotificationListResult.getObj();
        }
        if (notificationList.size() > 0) {
            for (Notification notification : notificationList) {
                Intent intent = new Intent(this, NotificationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.setAction(notification.getAction());
                if (notification.getAction().equals(NotificationContract.Controller.ACTION_MEETING)) {
                    intent.putExtra(NotificationContract.Controller.MEETING_ID, notification.getMeetingId());
                } else if (notification.getAction().equals(NotificationContract.Controller.ACTION_CHAT)) {
                    intent.putExtra(NotificationContract.Controller.CHAT_USER_ID, notification.getChatSenderId());
                }
                PendingIntent pendingIntent = PendingIntent.getActivity(this, notification.getRequestCode(), intent, PendingIntent.FLAG_MUTABLE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle(notification.getContentTitle())
                        .setContentText(notification.getContentText())
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    Log.d("NotiPerm", "no access ");
                    Toast.makeText(this, "Permission required to show notifications!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("NotiPerm", "has access");
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                    notificationManager.notify(notification.getId(), builder.build());
                }
            }
        }
    }

}