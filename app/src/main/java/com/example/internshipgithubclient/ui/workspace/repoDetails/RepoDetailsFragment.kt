package com.example.internshipgithubclient.ui.workspace.repoDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.internshipgithubclient.R
import com.example.internshipgithubclient.databinding.FragmentRepoDetailsBinding
import com.example.internshipgithubclient.network.STATE_OPEN
import com.example.internshipgithubclient.network.repo.RepoNetworkEntity
import com.example.internshipgithubclient.ui.loadCircleImage
import dagger.android.support.DaggerFragment
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
        val repo: RepoNetworkEntity? = arguments?.getParcelable("choosedRepo")
        //if repo is not null then set data in ui
        repo?.let {
            //load circle avatar of user who owns this repo
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
                            viewModel.pulls.toTypedArray()
                        )
                    )
            }
            //Ask view models for pull requests
            viewModel.fetchPulls(it)
            viewModel.isDataLoaded.subscribe({
                //if loading of pulls completed then
                if (it && viewModel.pulls.isNotEmpty()) {
                    //set count of open pull requests
                    binding.prequestsCounter.text =
                        viewModel.pulls.filter { pull -> pull.state == STATE_OPEN }.size.toString()
                    //navigate to Pulls fragment
                }
            }, {
                Log.e(RepoDetailsFragment::class.java.simpleName, "Error occurred" + it.message)
            })
        }
    }
}