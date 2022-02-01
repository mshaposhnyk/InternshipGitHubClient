package com.example.internshipgithubclient.ui.workspace.repoDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.core.domain.IssueState
import com.example.core.domain.Repo
import com.example.internshipgithubclient.R
import com.example.internshipgithubclient.databinding.FragmentRepoDetailsBinding
import com.example.internshipgithubclient.ui.loadCircleImage
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class RepoDetailsFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(RepoDetailsViewModel::class.java)
    }
    private lateinit var binding: FragmentRepoDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepoDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //getting choosed repo from arguments
        val repo: Repo? = arguments?.getSerializable("choosedRepo") as Repo?
        //load circle avatar of user who owns this repo
        repo?.let {
            loadCircleImage(binding.root.context, repo.owner.avatarUrl, binding.userIcon)
            binding.authorName.text = repo.owner.login
            binding.repoName.text = repo.name
            binding.desciptionContent.text = repo.description
            //template strings
            binding.forksCountText.text = resources.getString(R.string.forksCount, repo.forks)
            binding.starsCountText.text =
                resources.getString(R.string.starsCount, repo.stargazersCount)
            binding.issuesCounter.text = repo.openIssuesCount.toString()
            binding.watchersCounter.text = repo.watchersCount.toString()
            //setting default value for pull requests count
            binding.prequestsCounter.text = "0"
            //Navigate to issues fragment
            binding.issuesChoice.setOnClickListener {
                binding.root.findNavController()
                    .navigate(
                        RepoDetailsFragmentDirections.actionRepoDetailsFragmentToRepoIssuesFragment(
                            repo
                        )
                    )
            }
            //Navigate to watchers fragment
            binding.watchersChoice.setOnClickListener {
                binding.root.findNavController()
                    .navigate(
                        RepoDetailsFragmentDirections.actionRepoDetailsFragmentToRepoWatchersFragment(
                            repo
                        )
                    )
            }
            //navigate to Pulls fragment
            binding.pullRequestChoice.setOnClickListener {
                binding.root.findNavController()
                    .navigate(
                        RepoDetailsFragmentDirections.actionRepoDetailsFragmentToRepoPullsFragment(
                            repo
                        )
                    )
            }
            //Ask view models for pull requests
            viewModel.fetchPulls(repo)
            viewModel.pulls
                .onEach {
                    binding.prequestsCounter.text =
                        it.filter { pull -> pull.state == IssueState.OPEN }.size.toString()
                }
                .launchIn(lifecycleScope)
        }

    }
}