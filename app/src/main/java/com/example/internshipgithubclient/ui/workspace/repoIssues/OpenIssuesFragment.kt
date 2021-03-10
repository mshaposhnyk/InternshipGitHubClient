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

class OpenIssuesFragment : Fragment(), IssuesListAdapter.OnIssueClickListener {
    //Closed,Open and RepoIssuesFragment uses the same viewModel instance
    private val viewModel:IssuesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.simple_list_tab, container, false)
        val listEmptytext = view.findViewById<TextView>(R.id.listEmptyText)
        //By default we showing textview that informing about empty issues list
        listEmptytext.text = getString(R.string.no_issues)
        val openList = view.findViewById<RecyclerView>(R.id.itemsList)
        //fragment listens to list item onClick
        val openListAdapter = IssuesListAdapter(this)
        openList.adapter = openListAdapter
        openList.layoutManager = LinearLayoutManager(context)
        listEmptytext.text = getString(R.string.no_issues)
        viewModel.isDataLoaded.subscribe({
            //if issues are present then turn off textview and turn on recyclerview
            if(it){
                listEmptytext.visibility = View.GONE
                openList.visibility = View.VISIBLE
                val openIssues = viewModel.issuesMap["open"]
                //if issuesList not null then
                if(openIssues!=null){
                    //set issuesList to recyclerview adapter
                    openListAdapter.data = openIssues
                }
            }
        }, {
            Log.e(OpenIssuesFragment::class.java.simpleName, "Error occurred" + it.message)
        })
        return view
    }

    override fun onClick(v: View?, item: IssueNetworkEntity) {
        //
    }
}