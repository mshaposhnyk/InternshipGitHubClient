package com.example.internshipgithubclient.ui.workspace.repoIssues

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.internshipgithubclient.R
import com.example.internshipgithubclient.network.repo.IssueNetworkEntity

class ClosedIssuesFragment  : Fragment(), IssuesListAdapter.OnIssueClickListener {
    //Closed,Open and RepoIssuesFragment sharing the same viewModel instance
    private val viewModel:IssuesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.simple_list_tab, container, false)
        val listEmptyText = view.findViewById<TextView>(R.id.listEmptyText)
        val closedList = view.findViewById<RecyclerView>(R.id.itemsList)
        //fragment listens to list item onClick
        val closedListAdapter = IssuesListAdapter(this)
        closedList.adapter = closedListAdapter
        closedList.layoutManager = LinearLayoutManager(context)
        //By default we showing textview that informing about empty issues list
        listEmptyText.text = getString(R.string.no_issues)
        viewModel.isDataLoaded.subscribe({
            //if issues are present then turn off textview and turn on recyclerview
            if(it){
                listEmptyText.visibility = View.GONE
                closedList.visibility = View.VISIBLE
                val closedIssues = viewModel.issuesMap["closed"]
                //if issuesList not null then
                if(closedIssues!=null){
                    //set issuesList to recyclerview adapter
                    closedListAdapter.data = closedIssues
                }
            }
        }, {
            Log.e(ClosedIssuesFragment::class.java.simpleName, "Error occurred" + it.message)
        })
        return view
    }

    override fun onClick(v: View?, item: IssueNetworkEntity) {

    }
}