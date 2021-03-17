package com.example.internshipgithubclient.ui.workspace.repoWatchers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.internshipgithubclient.R
import com.example.internshipgithubclient.databinding.ListItemGenericBinding
import com.example.internshipgithubclient.network.user.UserNetworkEntity
import com.example.internshipgithubclient.ui.loadCircleImage
import com.example.internshipgithubclient.ui.workspace.repoIssues.IssuesListAdapter

class RepoWatchersAdapter(private val listener: OnWatcherClickListener) :
    RecyclerView.Adapter<RepoWatchersAdapter.ViewHolder>() {

    //List of Watchers
    var data = listOf<UserNetworkEntity>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent, listener)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount(): Int = data.size

    class ViewHolder(
        val binding: ListItemGenericBinding, private val listener: OnWatcherClickListener
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        lateinit var item: UserNetworkEntity

        fun bind(item: UserNetworkEntity) {
            this.item = item
            binding.primaryText.text = item.login
            binding.secondaryText.text = item.name
            loadCircleImage(itemView.context, item.avatarUrl, binding.listIcon)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onClick(v, item)
        }

        companion object {
            fun from(parent: ViewGroup, listener: OnWatcherClickListener): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemGenericBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, listener)
            }
        }
    }

    interface OnWatcherClickListener {
        fun onClick(v: View?, item: UserNetworkEntity)
    }
}