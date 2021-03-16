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
import com.example.internshipgithubclient.databinding.SimpleListTabBinding
import com.example.internshipgithubclient.network.STATE_OPEN
import com.example.internshipgithubclient.network.repo.IssueNetworkEntity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class OpenIssuesFragment : Fragment(), IssuesListAdapter.OnIssueClickListener {
    //Closed,Open and RepoIssuesFragment uses the same viewModel instance
    private val viewModel: IssuesViewModel by activityViewModels()
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var binding: SimpleListTabBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SimpleListTabBinding.inflate(inflater, container, false)
        val listEmptytext = binding.listEmptyText
        //By default we showing textview that informing about empty issues list
        listEmptytext.text = getString(R.string.no_issues)
        val openList = binding.itemsList
        //fragment listens to list item onClick
        val openListAdapter = IssuesListAdapter(this)
        openList.adapter = openListAdapter
        openList.layoutManager = LinearLayoutManager(context)
        listEmptytext.text = getString(R.string.no_issues)
        compositeDisposable = CompositeDisposable()
        val subscription: Disposable = viewModel.isDataLoaded.subscribe({
            //if issues are present then turn off textview and turn on recyclerview
            if (it) {
                val openIssues = viewModel.issuesMap[STATE_OPEN]
                //if issuesList not null then
                if (openIssues != null && openIssues.isNotEmpty()) {
                    //set issuesList to recyclerview adapter
                    listEmptytext.visibility = View.GONE
                    openList.visibility = View.VISIBLE
                    openListAdapter.data = openIssues
                }
            }
        }, {
            Log.e(OpenIssuesFragment::class.java.simpleName, "Error occurred" + it.message)
        })
        compositeDisposable.add(subscription)
        return binding.root
    }

    override fun onClick(v: View?, item: IssueNetworkEntity) {

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}