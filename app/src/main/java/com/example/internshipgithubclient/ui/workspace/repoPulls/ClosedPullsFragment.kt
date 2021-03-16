package com.example.internshipgithubclient.ui.workspace.repoPulls

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
import com.example.internshipgithubclient.network.pullRequest.PullNetworkEntity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class ClosedPullsFragment:Fragment(), PullsListAdapter.OnPullClickListener{
    //Closed,Open and RepoPullsFragment sharing the same viewModel instance
    private val viewModel:PullsViewModel by activityViewModels()
    private lateinit var compositeDisposable: CompositeDisposable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.simple_list_tab, container, false)
        val listEmptytext = view.findViewById<TextView>(R.id.listEmptyText)
        val closedList = view.findViewById<RecyclerView>(R.id.itemsList)
        //fragment listens to list item onClick
        val closedListAdapter = PullsListAdapter(this)
        closedList.adapter = closedListAdapter
        closedList.layoutManager = LinearLayoutManager(context)
        //By default we showing textview that informing about empty pull requests list
        listEmptytext.text = getString(R.string.no_prequests)
        compositeDisposable = CompositeDisposable()
        val subscription:Disposable = viewModel.isDataLoaded.subscribe({
            //if pulls are present then turn off textview and turn on recyclerview
            if(it){
                listEmptytext.visibility = View.GONE
                closedList.visibility = View.VISIBLE
                val closedPulls = viewModel.pullsMap["closed"]
                //if pullsList not null then
                if(closedPulls!=null){
                    //set pullsList to recyclerview adapter
                    closedListAdapter.data = closedPulls
                }
            }
        }, {
            Log.e(ClosedPullsFragment::class.java.simpleName,"Error occured"+it.message)
        })
        compositeDisposable.add(subscription)
        return view
    }

    override fun onClick(v: View?, item: PullNetworkEntity) {}

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}