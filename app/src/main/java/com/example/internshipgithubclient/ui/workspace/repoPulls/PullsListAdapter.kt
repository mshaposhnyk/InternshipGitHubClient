package com.example.internshipgithubclient.ui.workspace.repoPulls

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.internshipgithubclient.R
import com.example.internshipgithubclient.databinding.ListItemWcommentBinding
import com.example.internshipgithubclient.network.STATE_CLOSED
import com.example.internshipgithubclient.network.STATE_OPEN
import com.example.internshipgithubclient.network.pullRequest.PullNetworkEntity

class PullsListAdapter(private val listener: OnPullClickListener) :
    RecyclerView.Adapter<PullsListAdapter.ViewHolder>() {
    //List of Pulls
    var data = listOf<PullNetworkEntity>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent, listener)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount(): Int = data.size


    class ViewHolder private constructor(
        binding: ListItemWcommentBinding,
        private val listener: OnPullClickListener
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        lateinit var item: PullNetworkEntity
        private val primaryText: TextView = binding.primaryText
        private val secondaryText: TextView = binding.secondaryText
        private val pullIcon: ImageView = binding.listIcon
        private val commentsCount: TextView = binding.comments

        fun bind(item: PullNetworkEntity) {
            this.item = item
            //Pull request title
            primaryText.text = item.title
            //Pull request number
            secondaryText.text = itemView.resources.getString(R.string.repoNumber, item.number)
            val issueImage = when (item.state) {
                STATE_OPEN -> ResourcesCompat.getDrawable(
                    itemView.resources,
                    R.drawable.ic_pull_request,
                    itemView.context.theme
                )
                STATE_CLOSED -> ResourcesCompat.getDrawable(
                    itemView.resources,
                    R.drawable.ic_pull_request,
                    itemView.context.theme
                )
                else -> ResourcesCompat.getDrawable(
                    itemView.resources,
                    R.drawable.ic_merge,
                    itemView.context.theme
                )
            }
            val issueIconTint = when (item.state) {
                STATE_OPEN -> Color.GREEN
                STATE_CLOSED -> Color.RED
                else -> Color.MAGENTA
            }
            pullIcon.setImageDrawable(issueImage)
            pullIcon.setColorFilter(issueIconTint)
            commentsCount.visibility = View.INVISIBLE
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onClick(v, item)
        }

        companion object {
            fun from(
                parent: ViewGroup,
                listener: OnPullClickListener
            ): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemWcommentBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, listener)
            }
        }
    }

    interface OnPullClickListener {
        fun onClick(v: View?, item: PullNetworkEntity)
    }
}
