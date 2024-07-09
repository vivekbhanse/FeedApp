package com.example.myvote.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myvote.R
import com.example.myvote.data.PostDetails
import com.example.myvote.presentation.ui.ShowOperation

class CustomAdapter(
    private val mList: List<PostDetails>,
    val username: String,
    val param: ShowOperation
) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        holder.postText.text = "Post  :".plus(ItemsViewModel.post)
        ItemsViewModel.let {
            if (username.equals(ItemsViewModel.username)){
                holder.postCreatedBy.text="Created By : Own"
                holder.btn_createpost.visibility=View.VISIBLE
                holder.btn_viewpost.visibility=View.VISIBLE
            }else if (username.equals("Admin")){
                holder.postCreatedBy.text="Created By : ${ItemsViewModel.username}"

            }

            else{
                holder.postCreatedBy.text="Created By : ${ItemsViewModel.username}"
                holder.btn_createpost.visibility=View.GONE
                holder.btn_viewpost.visibility=View.GONE
            }

        }
        holder.date.text = "Post Date  :".plus(ItemsViewModel.date)

        holder.btn_createpost.setOnClickListener{
            param.delateNote(ItemsViewModel.id.toString())
            notifyItemRemoved(position)
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val postText: TextView = itemView.findViewById(R.id.postText)
        val postCreatedBy: TextView = itemView.findViewById(R.id.postCreated)
        val date: TextView = itemView.findViewById(R.id.postDate)
        val btn_createpost: Button = itemView.findViewById(R.id.btn_createpost)
        val btn_viewpost: Button = itemView.findViewById(R.id.btn_viewpost)
    }
}