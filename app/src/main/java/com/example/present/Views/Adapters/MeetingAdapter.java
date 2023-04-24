package com.example.present.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.present.Models.Entities.Meeting;
import com.example.present.R;

import java.util.List;

public class MeetingAdapter extends ArrayAdapter<Meeting> {

    public MeetingAdapter(Context context, List<Meeting> meetingsList) {
        super(context, 0, meetingsList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Meeting meeting = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.meeting_row, parent, false);
        }

        // Lookup view for data population
        TextView dateTime = convertView.findViewById(R.id.meetingDateTimeTxtView);
        TextView state = convertView.findViewById(R.id.meetingStateTxtView);

        // Populate the data into the template view using the data object
        dateTime.setText(meeting.getDateTime());
        state.setText(meeting.getStateToString());

        // Return the completed view to render on screen
        return convertView;
    }
}