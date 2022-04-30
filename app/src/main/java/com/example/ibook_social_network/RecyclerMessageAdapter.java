package com.example.ibook_social_network;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerMessageAdapter extends RecyclerView.Adapter<RecyclerMessageAdapter.messageViewHolder>{

    private final ArrayList <String> myMessages;
    private static int viewHolderCount;


    public RecyclerMessageAdapter(ArrayList<String> messages){
            myMessages = messages;
            viewHolderCount = myMessages.size();
        }

    public void refreshData(String add) {
            myMessages.add(add);
            viewHolderCount = myMessages.size();
            notifyDataSetChanged();
    }

    @NonNull
    @Override
    public messageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListMessage = R.layout.background_messenge;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListMessage, parent, false);
        return new messageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull messageViewHolder holder, int position) {
        String text = myMessages.get(position);
        if(text.charAt(0)=='@'){
            text=text.substring(1);
            holder.viewHolderMessage.setVisibility(View.GONE);
            holder.viewHolderIndex.setVisibility(View.VISIBLE);
        }
        else{
            holder.viewHolderIndex.setVisibility(View.GONE);
            holder.viewHolderMessage.setVisibility(View.VISIBLE);
        }
        holder.viewHolderIndex.setText(text);
        holder.viewHolderMessage.setText(text);
    }

    @Override
    public int getItemCount() {
        return viewHolderCount;
    }

    static class messageViewHolder extends RecyclerView.ViewHolder {
        TextView viewHolderIndex;
        TextView viewHolderMessage;

        public messageViewHolder(@NonNull View itemView) {
            super(itemView);
            viewHolderIndex = itemView.findViewById(R.id.tv_view_holder_number);
            viewHolderMessage = itemView.findViewById(R.id.tv_message);
        }
    }
}
