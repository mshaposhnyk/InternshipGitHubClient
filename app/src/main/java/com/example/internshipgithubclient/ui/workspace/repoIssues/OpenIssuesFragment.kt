package com.example.internshipgithubclient.ui.workspace.repoIssues

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.domain.Issue
import com.example.core.domain.IssueState
import com.example.internshipgithubclient.R
import com.example.internshipgithubclient.databinding.SimpleListTabBinding
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class OpenIssuesFragment : DaggerFragment(), IssuesListAdapter.OnIssueClickListener {
    //Closed,Open and RepoIssuesFragment uses the same viewModel instance
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(IssuesViewModel::class.java)
    }
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
        viewModel.issues
            .onEach {
                val openIssues = it.filter { issue -> issue.state == IssueState.OPEN }
                //if issuesList not empty then
                if (openIssues.isNotEmpty()) {
                    //set issuesList to recyclerview adapter
                    listEmptytext.visibility = View.GONE
                    openList.visibility = View.VISIBLE
                    openListAdapter.data = openIssues
                }
            }
            .launchIn(lifecycleScope)
        return binding.root
    }

    override fun onClick(v: View?, item: Issue) {

    }
}