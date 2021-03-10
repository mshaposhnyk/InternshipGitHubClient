package com.example.internshipgithubclient.ui.workspace.repoList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.internshipgithubclient.R
import com.example.internshipgithubclient.network.repo.RepoNetworkEntity

class RepoListAdapter: RecyclerView.Adapter<RepoListAdapter.ViewHolder>() {

    //List of Repos
    var data =  listOf<RepoNetworkEntity>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        val primaryText: TextView = itemView.findViewById(R.id.primaryText)
        val secondaryText: TextView = itemView.findViewById(R.id.secondaryText)
        val repoImage: ImageView = itemView.findViewById(R.id.listIcon)

        fun bind(item: RepoNetworkEntity) {
            primaryText.text = item.name
            secondaryText.text = item.owner.login
            //Loading circle image to repoImage with Glide
            Glide.with(repoImage.context)
                    .load(item.owner.avatarUrl)
                    .circleCrop()
                    .into(repoImage)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                        .inflate(R.layout.list_item_repo, parent, false)
                return ViewHolder(view)
            }
        }
    }
}