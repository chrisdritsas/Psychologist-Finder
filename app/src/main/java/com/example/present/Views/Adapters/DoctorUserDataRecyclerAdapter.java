package com.example.present.Views.Adapters;

import android.animation.AnimatorInflater;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.present.Models.Entities.UserData;
import com.example.present.R;

import java.util.List;

public class DoctorUserDataRecyclerAdapter extends RecyclerView.Adapter<DoctorUserDataRecyclerAdapter.ViewHolder> {
    private List<UserData> doctorData;
    private OnItemClickListener listener;
    private Context context;

    public DoctorUserDataRecyclerAdapter(Context context, List<UserData> data, OnItemClickListener listener) {
        doctorData = data;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doctor_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserData userData = doctorData.get(position);
        holder.nameSurnameTxtView.setText(userData.getNameSurname());
        holder.fullAddressTxtView.setText(userData.getFullAddress());
        holder.viewsTxtView.setText(context.getString(R.string.doctor_views_txtView) + " " + userData.getOffice().getViews());//ADD VIEW VALUE
        holder.onlinePriceTxtView.setText(context.getString(R.string.doctor_online_price) + " " + userData.getOffice().getOnlinePrice() + "€");
        holder.meetingPriceTxtView.setText(context.getString(R.string.doctor_meeting_price) + " " + userData.getOffice().getMeetingPrice() + "€");
        holder.itemView.setOnClickListener(view -> listener.onItemClick(userData));
        // Set the StateListAnimator for the item view
        holder.itemView.setStateListAnimator(AnimatorInflater.loadStateListAnimator(holder.itemView.getContext(), R.animator.recycler_view_item_selector));
    }

    @Override
    public int getItemCount() {
        return doctorData.size();
    }

    public void setList(List<UserData> filteredList) {
        doctorData = filteredList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameSurnameTxtView;
        public TextView fullAddressTxtView;
        public TextView onlinePriceTxtView;
        public TextView meetingPriceTxtView;
        public TextView viewsTxtView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameSurnameTxtView = itemView.findViewById(R.id.doctorNameSurnameTxtView);
            fullAddressTxtView = itemView.findViewById(R.id.doctorOfficeFullAddressTxtView);
            onlinePriceTxtView = itemView.findViewById(R.id.doctorOnlinePriceTxtView);
            meetingPriceTxtView = itemView.findViewById(R.id.doctorMeetingPriceTxtView);
            viewsTxtView = itemView.findViewById(R.id.doctorViewsTxtView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(UserData userData);
    }
}

