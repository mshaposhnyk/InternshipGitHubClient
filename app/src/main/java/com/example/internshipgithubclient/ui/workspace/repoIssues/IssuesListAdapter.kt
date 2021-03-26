package com.example.internshipgithubclient.ui.workspace.repoIssues

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.core.domain.Issue
import com.example.core.domain.IssueState
import com.example.internshipgithubclient.R
import com.example.internshipgithubclient.databinding.ListItemWcommentBinding


class IssuesListAdapter(private val listener: OnIssueClickListener) :
    RecyclerView.Adapter<IssuesListAdapter.ViewHolder>() {
    //List of Issues
    var data = listOf<Issue>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent, listener)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount(): Int = data.size

    class ViewHolder private constructor(
        val binding: ListItemWcommentBinding, private val listener: OnIssueClickListener
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        lateinit var item: Issue

        fun bind(item: Issue) {
            this.item = item
            binding.primaryText.text = item.title
            binding.secondaryText.text = item.body
            val issueImage = when (item.state) {
                IssueState.OPEN -> ResourcesCompat.getDrawable(
                    itemView.resources,
                    R.drawable.ic_issue_opened,
                    itemView.context.theme
                )
                IssueState.CLOSED -> ResourcesCompat.getDrawable(
                    itemView.resources,
                    R.drawable.ic_issue_closed,
                    itemView.context.theme
                )
                else -> ResourcesCompat.getDrawable(
                    itemView.resources,
                    R.drawable.ic_issue_reopened,
                    itemView.context.theme
                )
            }
            val issueIconTint = when (item.state) {
                IssueState.OPEN -> Color.GREEN
                IssueState.CLOSED -> Color.RED
                else -> Color.RED + Color.YELLOW
            }
            binding.listIcon.setImageDrawable(issueImage)
            binding.listIcon.setColorFilter(issueIconTint)
            binding.comments.text = item.commentsCount.toString()
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onClick(v, item)
        }

        companion object {
            fun from(parent: ViewGroup, listener: OnIssueClickListener): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemWcommentBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, listener)
            }
        }
    }

    interface OnIssueClickListener {
        fun onClick(v: View?, item: Issue)
    }
}