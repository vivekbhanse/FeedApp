package com.example.myvote.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myvote.R
import com.example.myvote.data.dto.PostDetails
import com.example.myvote.presentation.ui.ShowOperation

class CustomAdapter(
    private val mList: MutableList<PostDetails>, val username: String, val param: ShowOperation
) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mList[position]

        holder.postText.text = "Post: ${item.post}"
        holder.date.text = "Post Date: ${item.date}"

        holder.btn_Delete.setOnClickListener {
            param.delateNote(item.id.toString())
            deleteItem(position)
        }
        holder.btn_Update.setOnClickListener {
            param.updateNote(item)

        }

        when {
            username == item.username -> {
                holder.postCreatedBy.text = "Created By: Own"
                holder.btn_Delete.visibility = View.VISIBLE
                holder.btn_Update.visibility = View.VISIBLE

            }

            username == "Admin" -> {
                holder.postCreatedBy.text = "Created By: ${item.username}"
            }

            else -> {
                holder.postCreatedBy.text = "Created By: ${item.username}"
                holder.btn_Delete.visibility = View.GONE
                holder.btn_Update.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun deleteItem(position: Int) {
        if (position < mList.size) {
            mList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, mList.size)
        }
    }


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val postText: TextView = itemView.findViewById(R.id.postText)
        val postCreatedBy: TextView = itemView.findViewById(R.id.postCreated)
        val date: TextView = itemView.findViewById(R.id.postDate)
        val btn_Delete: Button = itemView.findViewById(R.id.btn_createpost)
        val btn_Update: Button = itemView.findViewById(R.id.btn_viewpost)
    }
}