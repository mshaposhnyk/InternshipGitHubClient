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
import com.example.internshipgithubclient.network.repo.RepoNetworkEntity
import com.example.internshipgithubclient.network.user.UserNetworkEntity
import com.example.internshipgithubclient.ui.workspace.repoIssues.ClosedIssuesFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class RepoWatchersFragment : Fragment(),RepoWatchersAdapter.OnWatcherClickListener {
    private lateinit var compositeDisposable: CompositeDisposable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.simple_list_tab, container, false)
        val viewModel = ViewModelProvider(this).get(RepoWatchersViewModel::class.java)
        val listEmptyText = view.findViewById<TextView>(R.id.listEmptyText)
        val watchList = view.findViewById<RecyclerView>(R.id.itemsList)
        //Fragment listens to click on item
        val watchListAdapter = RepoWatchersAdapter(this)
        //Setting adapter for a recyclerview
        watchList.adapter = watchListAdapter
        watchList.layoutManager = LinearLayoutManager(context)
        //Set default text for textview if nobody watching repositoty
        listEmptyText.text = getString(R.string.no_watchers)
        val repo: RepoNetworkEntity? = arguments?.getParcelable("choosedRepo")
        //if repo is not null then fetch for watchers list
        repo?.let{
            viewModel.fetchWatchers(it)
        }
        compositeDisposable = CompositeDisposable()
        val subscription:Disposable = viewModel.isDataLoaded.subscribe({
            if(it){
                //turn off textview
                listEmptyText.visibility = View.GONE
                //turn on recyclerview
                watchList.visibility = View.VISIBLE
                val watchers = viewModel.listWatchers
                if(watchers!=null){
                    //if list of watchers is not null then update adapter
                    watchListAdapter.data = watchers
                }
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