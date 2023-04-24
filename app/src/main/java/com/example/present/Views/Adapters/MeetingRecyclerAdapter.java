package com.example.present.Views.Adapters;

import android.animation.AnimatorInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.present.Models.Entities.Meeting;
import com.example.present.Models.Entities.UserData;
import com.example.present.R;

import java.util.List;

public class MeetingRecyclerAdapter extends RecyclerView.Adapter<MeetingRecyclerAdapter.ViewHolder> {
    private List<Meeting> meetings;
    private OnItemClickListener listener;

    public MeetingRecyclerAdapter(List<Meeting> data, OnItemClickListener listener) {
        meetings = data;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meeting_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Meeting meeting = meetings.get(position);
        holder.meetingDateTxtView.setText(meeting.getDateTime());
        holder.meetingStateTxtView.setText(meeting.getStateToString());
        holder.itemView.setOnClickListener(view -> listener.onItemClick(meeting));

        // Set the StateListAnimator for the item view
        holder.itemView.setStateListAnimator(AnimatorInflater.loadStateListAnimator(holder.itemView.getContext(), R.animator.recycler_view_item_selector));
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

    public void setList(List<Meeting> newList) {
        meetings = newList;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView meetingDateTxtView;
        public TextView meetingStateTxtView;

        public ViewHolder(View itemView) {
            super(itemView);
            meetingDateTxtView = itemView.findViewById(R.id.meetingDateTimeTxtView);
            meetingStateTxtView = itemView.findViewById(R.id.meetingStateTxtView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Meeting meeting);
    }
}

