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
import com.example.internshipgithubclient.databinding.SimpleListTabBinding
import com.example.internshipgithubclient.network.pullRequest.PullNetworkEntity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class OpenPullsFragment : Fragment(), PullsListAdapter.OnPullClickListener {
    //Closed,Open and RepoPullsFragment sharing the same viewModel instance
    private val viewModel: PullsViewModel by activityViewModels()
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var binding: SimpleListTabBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SimpleListTabBinding.inflate(inflater, container, false)
        val listEmptytext = binding.listEmptyText
        val openList = binding.itemsList
        //fragment listens to list item onClick
        val openListAdapter = PullsListAdapter(this)
        openList.adapter = openListAdapter
        openList.layoutManager = LinearLayoutManager(context)
        //By default we showing textview that informing about empty pull requests list
        listEmptytext.text = getString(R.string.no_prequests)
        compositeDisposable = CompositeDisposable()
        val subscription: Disposable = viewModel.isDataLoaded.subscribe({
            //if pulls are present then turn off textview and turn on recyclerview
            if (it) {
                val openPulls = viewModel.pullsMap["open"]
                //if pullsList not null then
                if (openPulls != null && openPulls.isNotEmpty()) {
                    //set pullsList to recyclerview adapter
                    openListAdapter.data = openPulls
                    listEmptytext.visibility = View.GONE
                    openList.visibility = View.VISIBLE
                }
            }
        }, {
            Log.e(OpenPullsFragment::class.java.simpleName, "Error occured" + it.message)
        })
        compositeDisposable.add(subscription)
        return binding.root
    }

    override fun onClick(v: View?, item: PullNetworkEntity) {}

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}