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

public class UserDataRecyclerAdapter extends RecyclerView.Adapter<UserDataRecyclerAdapter.ViewHolder> {
    private List<UserData> userDatas;
    private OnItemClickListener listener;

    public UserDataRecyclerAdapter(List<UserData> data, OnItemClickListener listener) {
        userDatas = data;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.conversation_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserData userData = userDatas.get(position);
        holder.conversationNameTxtView.setText(userData.getNameSurname()) ;
        holder.itemView.setOnClickListener(view -> listener.onItemClick(userData));

        // Set the StateListAnimator for the item view
        holder.itemView.setStateListAnimator(AnimatorInflater.loadStateListAnimator(holder.itemView.getContext(), R.animator.recycler_view_item_selector));
    }

    @Override
    public int getItemCount() {
        return userDatas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView conversationNameTxtView;

        public ViewHolder(View itemView) {
            super(itemView);
            conversationNameTxtView = itemView.findViewById(R.id.conversationNameTxtView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(UserData userData);
    }
}

