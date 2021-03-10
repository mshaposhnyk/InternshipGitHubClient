package com.example.internshipgithubclient.ui.workspace.repoWatchers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.internshipgithubclient.R
import com.example.internshipgithubclient.network.user.UserNetworkEntity
import com.example.internshipgithubclient.ui.workspace.repoIssues.IssuesListAdapter

class RepoWatchersAdapter(val listener:OnWatcherClickListener) :
    RecyclerView.Adapter<RepoWatchersAdapter.ViewHolder>() {

    //List of Watchers
    var data = listOf<UserNetworkEntity>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder.from(parent,listener)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount(): Int = data.size

    class ViewHolder(itemView:View, val listener:OnWatcherClickListener):RecyclerView.ViewHolder(itemView), View.OnClickListener {
        lateinit var item:UserNetworkEntity
        val primaryText:TextView = itemView.findViewById(R.id.primaryText)
        val secondaryText:TextView = itemView.findViewById(R.id.secondaryText)
        val repoImage:ImageView = itemView.findViewById(R.id.listIcon)

        fun bind(item:UserNetworkEntity){
            this.item = item
            primaryText.text = item.login
            secondaryText.text = item.name
            Glide.with(repoImage.context)
                .load(item.avatarUrl)
                .circleCrop()
                .into(repoImage)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onClick(v,item)
        }
        companion object{
            fun from(parent: ViewGroup,listener: OnWatcherClickListener) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.list_item_generic,parent,false)
                return ViewHolder(view, listener)
            }
        }
    }

    interface OnWatcherClickListener{
        fun onClick(v: View?, item:UserNetworkEntity)
    }
}