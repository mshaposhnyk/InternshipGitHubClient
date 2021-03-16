package com.example.internshipgithubclient.ui.workspace.repoWatchers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.internshipgithubclient.R
import com.example.internshipgithubclient.databinding.SimpleListTabBinding
import com.example.internshipgithubclient.network.repo.RepoNetworkEntity
import com.example.internshipgithubclient.network.user.UserNetworkEntity
import com.example.internshipgithubclient.ui.workspace.repoIssues.ClosedIssuesFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class RepoWatchersFragment : Fragment(), RepoWatchersAdapter.OnWatcherClickListener {
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var binding: SimpleListTabBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SimpleListTabBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this).get(RepoWatchersViewModel::class.java)
        val listEmptyText = binding.listEmptyText
        val watchList = binding.itemsList
        //Fragment listens to click on item
        val watchListAdapter = RepoWatchersAdapter(this)
        //Setting adapter for a recyclerview
        watchList.adapter = watchListAdapter
        watchList.layoutManager = LinearLayoutManager(context)
        //Set default text for textview if nobody watching repositoty
        listEmptyText.text = getString(R.string.no_watchers)
        val repo: RepoNetworkEntity? = arguments?.getParcelable("choosedRepo")
        //if repo is not null then fetch for watchers list
        repo?.let {
            viewModel.fetchWatchers(it)
        }
        compositeDisposable = CompositeDisposable()
        val subscription: Disposable = viewModel.isDataLoaded.subscribe({
            if (it) {
                //turn off textview
                listEmptyText.visibility = View.GONE
                //turn on recyclerview
                watchList.visibility = View.VISIBLE
                val watchers = viewModel.listWatchers
                watchListAdapter.data = watchers
            }
        }, {
            Log.e(RepoWatchersFragment::class.java.simpleName, "Error occurred" + it.message)
        })
        compositeDisposable.add(subscription)
        return view
    }

    override fun onClick(v: View?, item: UserNetworkEntity) {}

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}