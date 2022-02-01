package com.example.internshipgithubclient.ui.workspace.repoList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.domain.Repo
import com.example.internshipgithubclient.databinding.ListItemGenericBinding
import com.example.internshipgithubclient.network.repo.RepoNetworkEntity
import com.example.internshipgithubclient.ui.loadCircleImage

class RepoListAdapter(private val listener: OnRepoClickListener) :
    RecyclerView.Adapter<RepoListAdapter.ViewHolder>() {
    //List of Repos
    var data = listOf<Repo>()
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
        return ViewHolder.from(parent, listener)
    }

    class ViewHolder private constructor(
        val binding: ListItemGenericBinding, private val listener: OnRepoClickListener
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        lateinit var item: Repo

        fun bind(item: Repo) {
            this.item = item
            binding.primaryText.text = item.name
            binding.secondaryText.text = item.owner.login
            //Loading circle image to repoImage with Glide
            loadCircleImage(itemView.context, item.owner.avatarUrl, binding.listIcon)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onClick(v, item)
        }

        companion object {
            fun from(parent: ViewGroup, listener: OnRepoClickListener): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemGenericBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, listener)
            }
        }
    }

    interface OnRepoClickListener {
        fun onClick(v: View?, item: Repo)
    }
}