package com.example.internshipgithubclient.ui.workspace.repoPulls

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.core.domain.IssueState
import com.example.core.domain.Pull
import com.example.internshipgithubclient.R
import com.example.internshipgithubclient.databinding.ListItemWcommentBinding

class PullsListAdapter(private val listener: OnPullClickListener) :
    RecyclerView.Adapter<PullsListAdapter.ViewHolder>() {
    //List of Pulls
    var data = listOf<Pull>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent, listener)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount(): Int = data.size


    class ViewHolder private constructor(
        val binding: ListItemWcommentBinding,
        private val listener: OnPullClickListener
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        lateinit var item: Pull

        fun bind(item: Pull) {
            this.item = item
            //Pull request title
            binding.primaryText.text = item.title
            //Pull request number
            binding.secondaryText.text =
                itemView.resources.getString(R.string.repoNumber, item.number)
            val issueImage = when (item.state) {
                IssueState.OPEN -> ResourcesCompat.getDrawable(
                    itemView.resources,
                    R.drawable.ic_pull_request,
                    itemView.context.theme
                )
                IssueState.CLOSED -> ResourcesCompat.getDrawable(
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
                IssueState.OPEN -> Color.GREEN
                IssueState.CLOSED -> Color.RED
                else -> Color.MAGENTA
            }
            binding.listIcon.setImageDrawable(issueImage)
            binding.listIcon.setColorFilter(issueIconTint)
            binding.comments.visibility = View.INVISIBLE
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
        fun onClick(v: View?, item: Pull)
    }
}
