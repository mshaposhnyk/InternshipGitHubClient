package com.example.internshipgithubclient.ui.workspace.repoIssues

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.internshipgithubclient.R
import com.example.internshipgithubclient.databinding.SimpleListTabBinding
import com.example.internshipgithubclient.network.STATE_OPEN
import com.example.internshipgithubclient.network.repo.IssueNetworkEntity
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class ClosedIssuesFragment : DaggerFragment(), IssuesListAdapter.OnIssueClickListener {
    //Closed,Open and RepoIssuesFragment sharing the same viewModel instance
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(IssuesViewModel::class.java)
    }
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var binding: SimpleListTabBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SimpleListTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onClick(v: View?, item: IssueNetworkEntity) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //fragment listens to list item onClick
        val closedListAdapter = IssuesListAdapter(this)
        binding.itemsList.adapter = closedListAdapter
        binding.itemsList.layoutManager = LinearLayoutManager(context)
        //By default we showing textview that informing about empty issues list
        binding.listEmptyText.text = getString(R.string.no_issues)
        val subscription: Disposable = viewModel.isDataLoaded.subscribe({
            //if issues are present then turn off textview and turn on recyclerview
            if (it) {
                val closedIssues = viewModel.issues.filter { issue -> issue.state != STATE_OPEN }
                //if issuesList not empty then
                if (closedIssues.isNotEmpty()) {
                    //set issuesList to recyclerview adapter
                    closedListAdapter.data = closedIssues
                    binding.listEmptyText.visibility = View.GONE
                    binding.itemsList.visibility = View.VISIBLE
                }
            }
        }, {
            Log.e(ClosedIssuesFragment::class.java.simpleName, "Error occurred" + it.message)
        })
        compositeDisposable.add(subscription)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}