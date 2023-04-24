package com.example.present.Views.Adapters;

import android.animation.AnimatorInflater;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.present.App.Present;
import com.example.present.Models.Entities.Chat;
import com.example.present.R;

import java.util.List;

public class ChatRecyclerAdapter extends RecyclerView.Adapter<ChatRecyclerAdapter.ViewHolder> {
    private List<Chat> chats;
    private OnItemClickListener listener;
    private Context context;

    public ChatRecyclerAdapter(Context context, List<Chat> data, OnItemClickListener listener) {
        chats = data;
        this.listener = listener;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Chat chat = chats.get(position);
        if (chat.getSenderId() == Present.getInstance().getUserSession().getId()) {
            //set different params for the sender
            holder.linearLayout.setGravity(Gravity.END);
            holder.chatTxt.setBackgroundColor(ContextCompat.getColor(context, R.color.purple_500));
            holder.chatTxt.setTextColor(ContextCompat.getColor(context, R.color.white));
        } else {
            //set different params for the receiver
            holder.linearLayout.setGravity(Gravity.START);
            holder.chatTxt.setBackgroundColor(ContextCompat.getColor(context, R.color.teal_200));
            holder.chatTxt.setTextColor(ContextCompat.getColor(context, R.color.black));
        }
        holder.chatTxt.setText(chat.getMessage());
        holder.itemView.setOnClickListener(view -> listener.onItemClick(chat));
        // Set the StateListAnimator for the item view
        holder.itemView.setStateListAnimator(AnimatorInflater.loadStateListAnimator(holder.itemView.getContext(), R.animator.recycler_view_item_selector));
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView chatTxt;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linearLayoutChatRow);
            chatTxt = itemView.findViewById(R.id.chatTxtView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Chat chat);
    }
}

