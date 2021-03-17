package com.example.internshipgithubclient.ui.workspace.repoList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.internshipgithubclient.databinding.FragmentRepoListBinding
import com.example.internshipgithubclient.network.repo.RepoNetworkEntity
import io.reactivex.disposables.CompositeDisposable

class RepoListFragment : Fragment(), RepoListAdapter.OnRepoClickListener {

    private lateinit var viewModel: RepoListViewModel
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var adapter: RepoListAdapter
    private lateinit var binding: FragmentRepoListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RepoListAdapter(this)
        binding.repoList.layoutManager = LinearLayoutManager(context)
        binding.repoList.adapter = adapter
        viewModel = ViewModelProvider(this).get(RepoListViewModel::class.java)
        //Observing eventUserUpdated flag. If the user data is loaded, then start loading list of repos
        viewModel.eventGotUser()
        val subscrUser = viewModel.isUserDataLoaded.subscribe({
            if (it) {
                viewModel.fetchUserRepos()
                viewModel.isUserDataLoaded.onNext(false)
            }
        }, {
            Log.e(RepoListFragment::class.java.simpleName, "Error occurred" + it.message)
        })
        val subscrRepo = viewModel.isRepoDataLoaded.subscribe({
            if (it) {
                adapter.data = viewModel.repoList
                viewModel.isRepoDataLoaded.onNext(false)
                binding.root.isRefreshing = false
            }
        }, {
            Log.e(RepoListFragment::class.java.simpleName, "Error occurred" + it.message)
        })
        binding.root.setOnRefreshListener {
            viewModel.fetchUserRepos()
        }
        compositeDisposable.add(subscrUser)
        compositeDisposable.add(subscrRepo)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    override fun onClick(v: View?, item: RepoNetworkEntity) {
        view?.findNavController()
            ?.navigate(RepoListFragmentDirections.actionRepoListFragmentToRepoDetailsFragment(item))
    }

}
