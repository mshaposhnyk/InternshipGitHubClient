package com.example.internshipgithubclient.ui.workspace.repoWatchers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.domain.Repo
import com.example.core.domain.User
import com.example.internshipgithubclient.R
import com.example.internshipgithubclient.databinding.SimpleListTabBinding
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class RepoWatchersFragment : DaggerFragment(), RepoWatchersAdapter.OnWatcherClickListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(RepoWatchersViewModel::class.java)
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
        //Fragment listens to click on item
        val watchListAdapter = RepoWatchersAdapter(this)
        //Setting adapter for a recyclerview
        binding.itemsList.adapter = watchListAdapter
        binding.itemsList.layoutManager = LinearLayoutManager(context)
        //Set default text for textview if nobody watching repositoty
        binding.listEmptyText.text = getString(R.string.no_watchers)
        val repo: Repo? = arguments?.getSerializable("choosedRepo") as Repo?
        //if repo is not null then fetch for watchers list
        repo?.let {
            viewModel.fetchWatchers(it)
        }
        viewModel.listWatchers
            .onEach {
                //turn off textview
                binding.listEmptyText.visibility = View.GONE
                //turn on recyclerview
                binding.itemsList.visibility = View.VISIBLE
                watchListAdapter.data = it
            }
            .launchIn(lifecycleScope)
    }

    override fun onClick(v: View?, item: User) {}

}