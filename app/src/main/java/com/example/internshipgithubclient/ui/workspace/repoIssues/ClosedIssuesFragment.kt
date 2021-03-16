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
import com.example.internshipgithubclient.network.STATE_CLOSED
import com.example.internshipgithubclient.network.repo.IssueNetworkEntity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class ClosedIssuesFragment : Fragment(), IssuesListAdapter.OnIssueClickListener {
    //Closed,Open and RepoIssuesFragment sharing the same viewModel instance
    private val viewModel: IssuesViewModel by activityViewModels()
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var binding: SimpleListTabBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SimpleListTabBinding.inflate(inflater, container, false)
        val listEmptyText = binding.listEmptyText
        val closedList = binding.itemsList
        //fragment listens to list item onClick
        val closedListAdapter = IssuesListAdapter(this)
        closedList.adapter = closedListAdapter
        closedList.layoutManager = LinearLayoutManager(context)
        //By default we showing textview that informing about empty issues list
        listEmptyText.text = getString(R.string.no_issues)
        compositeDisposable = CompositeDisposable()
        val subscription: Disposable = viewModel.isDataLoaded.subscribe({
            //if issues are present then turn off textview and turn on recyclerview
            if (it) {
                val closedIssues = viewModel.issuesMap[STATE_CLOSED]
                //if issuesList not null then
                if (closedIssues != null && closedIssues.isNotEmpty()) {
                    //set issuesList to recyclerview adapter
                    closedListAdapter.data = closedIssues
                    listEmptyText.visibility = View.GONE
                    closedList.visibility = View.VISIBLE
                }
            }
        }, {
            Log.e(ClosedIssuesFragment::class.java.simpleName, "Error occurred" + it.message)
        })
        compositeDisposable.add(subscription)
        return binding.root
    }

    override fun onClick(v: View?, item: IssueNetworkEntity) {}

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}