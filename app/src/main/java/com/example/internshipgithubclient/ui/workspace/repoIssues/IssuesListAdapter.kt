package com.example.internshipgithubclient.ui.workspace.repoIssues

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.internshipgithubclient.R
import com.example.internshipgithubclient.network.repo.IssueNetworkEntity

class IssuesListAdapter(val listener: OnIssueClickListener) :
    RecyclerView.Adapter<IssuesListAdapter.ViewHolder>() {
    //List of Issues
    var data = listOf<IssueNetworkEntity>()
        set(value){
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder.from(parent,listener)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount(): Int = data.size

    class ViewHolder private constructor(itemView:View, val listener:OnIssueClickListener
                                        ) : RecyclerView.ViewHolder(itemView),View.OnClickListener{
        lateinit var item:IssueNetworkEntity
        val primaryText: TextView = itemView.findViewById(R.id.primaryText)
        val secondaryText: TextView = itemView.findViewById(R.id.secondaryText)
        val issueIcon: ImageView = itemView.findViewById(R.id.listIcon)
        val commentsCount: TextView = itemView.findViewById(R.id.comments)

        fun bind(item:IssueNetworkEntity){
            this.item = item
            primaryText.text = item.title
            secondaryText.text = item.body
            val issueImage = when(item.state){
                "open" -> ResourcesCompat.getDrawable(itemView.resources,R.drawable.ic_issue_opened,itemView.context.theme)
                "closed" -> ResourcesCompat.getDrawable(itemView.resources,R.drawable.ic_issue_closed,itemView.context.theme)
                else -> ResourcesCompat.getDrawable(itemView.resources,R.drawable.ic_issue_reopened,itemView.context.theme)
            }
            val issueIconTint = when(item.state){
                "open" -> Color.GREEN
                "closed" -> Color.RED
                else -> Color.RED + Color.YELLOW
            }
            issueIcon.setImageDrawable(issueImage)
            issueIcon.setColorFilter(issueIconTint)
            commentsCount.text = item.commentsCount.toString()
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            listener.onClick(v,item)
        }
        companion object{
            fun from(parent: ViewGroup,listener: OnIssueClickListener) : ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.list_item_wcomment,parent,false)
                return ViewHolder(view,listener)
            }
        }
    }

    interface OnIssueClickListener{
        fun onClick(v: View?, item:IssueNetworkEntity)
    }
}