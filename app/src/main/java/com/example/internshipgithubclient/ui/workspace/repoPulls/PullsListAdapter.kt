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
import com.example.internshipgithubclient.network.pullRequest.PullNetworkEntity

class PullsListAdapter(val listener: OnPullClickListener):
    RecyclerView.Adapter<PullsListAdapter.ViewHolder>() {
    //List of Pulls
    var data = listOf<PullNetworkEntity>()
        set(value){
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder.from(parent,listener)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount(): Int = data.size


    class ViewHolder private constructor(itemView: View, val listener: OnPullClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        lateinit var item: PullNetworkEntity
        val primaryText: TextView = itemView.findViewById(R.id.primaryText)
        val secondaryText: TextView = itemView.findViewById(R.id.secondaryText)
        val pullIcon: ImageView = itemView.findViewById(R.id.listIcon)
        val commentsCount: TextView = itemView.findViewById(R.id.comments)

        fun bind(item:PullNetworkEntity){
            this.item = item
            //Pull request title
            primaryText.text = item.title
            //Pull request number
            secondaryText.text = """#${item.number}"""
            val issueImage = when(item.state){
                "open" -> ResourcesCompat.getDrawable(itemView.resources,R.drawable.ic_pull_request,itemView.context.theme)
                "closed" -> ResourcesCompat.getDrawable(itemView.resources,R.drawable.ic_pull_request,itemView.context.theme)
                else -> ResourcesCompat.getDrawable(itemView.resources,R.drawable.ic_merge,itemView.context.theme)
            }
            val issueIconTint = when(item.state){
                "open" -> Color.GREEN
                "closed" -> Color.RED
                else -> Color.MAGENTA
            }
            pullIcon.setImageDrawable(issueImage)
            pullIcon.setColorFilter(issueIconTint)
            commentsCount.visibility = View.INVISIBLE
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

        }

        companion object {
            fun from(
                parent: ViewGroup,
                listener: OnPullClickListener
            ): PullsListAdapter.ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.list_item_wcomment, parent, false)
                return PullsListAdapter.ViewHolder(view, listener)
            }
        }
    }

    interface OnPullClickListener {
        fun onClick(v: View?, item: PullNetworkEntity)
    }
}
