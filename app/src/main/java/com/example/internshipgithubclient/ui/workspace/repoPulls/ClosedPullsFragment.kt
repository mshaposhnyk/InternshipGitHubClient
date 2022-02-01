package com.example.internshipgithubclient.ui.workspace.repoPulls

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.domain.IssueState
import com.example.core.domain.Pull
import com.example.internshipgithubclient.R
import com.example.internshipgithubclient.databinding.SimpleListTabBinding
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ClosedPullsFragment : DaggerFragment(), PullsListAdapter.OnPullClickListener {
    //Closed,Open and RepoPullsFragment sharing the same viewModel instance
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(PullsViewModel::class.java)
    }
    private lateinit var binding: SimpleListTabBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SimpleListTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val closedList = binding.itemsList
        //fragment listens to list item onClick
        val closedListAdapter = PullsListAdapter(this)
        closedList.adapter = closedListAdapter
        closedList.layoutManager = LinearLayoutManager(context)
        //By default we showing textview that informing about empty pull requests list
        binding.listEmptyText.text = getString(R.string.no_prequests)
        viewModel.pulls
            .onEach {
                val closedPulls = it.filter { pull -> pull.state != IssueState.OPEN }
                //if pullsList not null then
                if (closedPulls.isNotEmpty()) {
                    //set pullsList to recyclerview adapter
                    closedListAdapter.data = closedPulls
                    binding.listEmptyText.visibility = View.GONE
                    closedList.visibility = View.VISIBLE
                }
            }
            .launchIn(lifecycleScope)
    }

    override fun onClick(v: View?, item: Pull) {}
}