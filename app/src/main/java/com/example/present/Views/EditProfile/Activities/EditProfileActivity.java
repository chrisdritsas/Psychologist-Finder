package com.example.present.Views.EditProfile.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.present.App.Present;
import com.example.present.R;
import com.example.present.Views.EditProfile.Fragments.EditEmailFragment;
import com.example.present.Views.EditProfile.Fragments.EditOfficeFragment;
import com.example.present.Views.EditProfile.Fragments.EditPasswordFragment;
import com.example.present.Views.EditProfile.Fragments.EditUserDataFragment;
import com.example.present.Views.Primary.Fragments.MeetingListFragment;

import java.util.List;

public class EditProfileActivity extends AppCompatActivity {

    private FragmentContainerView fragmentContainerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        InitializeViewElements(this);
    }

    // TODO: Check if swapFragment keeps only max 2 copies on backstack
    // TODO: After frag stack is fixed, make back button close activity
    private void swapFragment(Fragment newFragment, String fragmentTag) {
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
            fragmentManager.beginTransaction().show(existingFragment).commit();
        } else {
            fragmentManager.beginTransaction().add(fragmentContainerView.getId(), newFragment, fragmentTag).commit();
        }
    }

    private void InitializeViewElements(Activity activity) {
        fragmentContainerView = activity.findViewById(R.id.fragmentContainerViewEditProfile);
        swapFragment(new EditEmailFragment(), "editEmailFrag");
        LinearLayout layout = activity.findViewById(R.id.linearLayoutEditProfile);
        Button editEmailBtn = activity.findViewById(R.id.btnEditEmail);
        Button editPassBtn = activity.findViewById(R.id.btnEditPass);
        Button editUserDataBtn = activity.findViewById(R.id.btnEditUserData);
        Button editOfficeBtn = activity.findViewById(R.id.btnEditOffice);
        editEmailBtn.setOnClickListener(v -> {
            Fragment editEmailFrag = new EditEmailFragment();
            swapFragment(editEmailFrag, "editEmailFrag");
        });
        editPassBtn.setOnClickListener(v -> {
            Fragment editPassFrag = new EditPasswordFragment();
            swapFragment(editPassFrag, "editPassFrag");
        });
        editUserDataBtn.setOnClickListener(v -> {
            Fragment editUserDataFrag = new EditUserDataFragment();
            swapFragment(editUserDataFrag, "editUserDataFrag");
        });
        editOfficeBtn.setOnClickListener(v -> {
            Fragment editOfficeFrag = new EditOfficeFragment();
            swapFragment(editOfficeFrag, "editOfficeFrag");
        });

        if (!Present.getInstance().getUserSession().getIsDoctor()) {
            layout.removeView(editOfficeBtn);
        }
    }


}