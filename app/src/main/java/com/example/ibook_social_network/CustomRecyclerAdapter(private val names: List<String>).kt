package com.example.ibook_social_network

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerAdapter(private val names: List<String>, private val messenger: Messenger) :
    RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val largeTextView: TextView = itemView.findViewById(R.id.textViewLarge)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.senders_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.largeTextView.text = names[position].split("/")[1].substring(0, names[position].split("/")[1].length-4)
        holder.largeTextView.setOnClickListener {
            Messenger.nextActivity(messenger, names[position].split("/")[1].substring(0, names[position].split("/")[1].length-4))
        }
    }

    override fun getItemCount(): Int {
        return names.size
    }
}