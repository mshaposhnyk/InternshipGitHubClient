package com.example.internshipgithubclient.ui.workspace.repoList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.domain.Repo
import com.example.internshipgithubclient.R
import com.example.internshipgithubclient.databinding.FragmentRepoListBinding
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class RepoListFragment : DaggerFragment(), RepoListAdapter.OnRepoClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(RepoListViewModel::class.java)
    }
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
        //Observing isUserLoadedFlag flag. If the user data is loaded, then start loading list of repos
        viewModel.eventGotUser()
        viewModel.isUserLoadedState
            .onEach { if (it) viewModel.fetchUserRepos() }
            .launchIn(lifecycleScope)
        //Observing state of repoList
        viewModel.reposState
            .filterNotNull()
            .onEach {
                adapter.data = it
                binding.root.isRefreshing = false
            }
            .launchIn(lifecycleScope)
        //Show snack if authorization failed
        viewModel.isAuthErrorOccurred
            .onEach {
                if (it) showSnackbar(view, getString(R.string.auth_error_snack))
            }
            .launchIn(lifecycleScope)
        //Show snack if fetching repos operation failed
        viewModel.isReposLoadErrorOccurred
            .onEach {
                if (it) showSnackbar(view, getString(R.string.repo_load_error_snack))
            }
            .launchIn(lifecycleScope)
        binding.root.setOnRefreshListener {
            viewModel.fetchUserRepos()
        }
    }

    override fun onClick(v: View?, item: Repo) {
        view?.findNavController()
            ?.navigate(RepoListFragmentDirections.actionRepoListFragmentToRepoDetailsFragment(item))
    }

    private fun DaggerFragment.showSnackbar(view: View, msg: String) {
        Snackbar.make(
            view,
            msg,
            Snackbar.LENGTH_LONG
        ).show()
    }

}
