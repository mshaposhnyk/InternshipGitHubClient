package com.example.internshipgithubclient.ui.workspace.repoDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.internshipgithubclient.R
import com.example.internshipgithubclient.network.repo.RepoNetworkEntity
import com.example.internshipgithubclient.ui.loadCircleImage

class RepoDetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_repo_details, container, false)
        val viewModel = ViewModelProvider(this).get(RepoDetailsViewModel::class.java)
        //getting choosed repo from arguments
        val repo:RepoNetworkEntity? = arguments?.getParcelable("choosedRepo")
        //getting views
        val userIcon = view.findViewById<ImageView>(R.id.userIcon)
        val userName = view.findViewById<TextView>(R.id.authorName)
        val repoName = view.findViewById<TextView>(R.id.repoName)
        val repoDescription = view.findViewById<TextView>(R.id.desciptionContent)
        val forksCount = view.findViewById<TextView>(R.id.forksCountText)
        val starsCount = view.findViewById<TextView>(R.id.starsCountText)
        val issuesCount = view.findViewById<TextView>(R.id.issuesCounter)
        val prequestsCount = view.findViewById<TextView>(R.id.prequestsCounter)
        //setting default values for pull requests count
        prequestsCount.text = "0"
        val watchersCount = view.findViewById<TextView>(R.id.watchersCounter)
        val issuesButton = view.findViewById<TextView>(R.id.issuesChoice)
        val watchersButton = view.findViewById<TextView>(R.id.watchersChoice)
        val prequestsButton = view.findViewById<TextView>(R.id.pullRequestChoice)
        //if repo is not null then set data in ui
        repo?.let {
            //load circle avatar of user who owns this repo
            loadCircleImage(view.context,repo.owner.avatarUrl,userIcon)
            userName.text = repo.owner.login
            repoName.text = repo.name
            repoDescription.text = repo.description
            //template strings
            forksCount.text = resources.getString(R.string.forksCount,repo.forks)
            starsCount.text = resources.getString(R.string.starsCount,repo.stargazersCount)
            issuesCount.text = repo.openIssuesCount.toString()
            watchersCount.text = repo.watchersCount.toString()
            //Navigate to issues fragment
            issuesButton.setOnClickListener {
                view.findNavController()
                    .navigate(RepoDetailsFragmentDirections.actionRepoDetailsFragmentToRepoIssuesFragment(repo))
            }
            //Navigate to watchers fragment
            watchersButton.setOnClickListener {
                view.findNavController()
                    .navigate(RepoDetailsFragmentDirections.actionRepoDetailsFragmentToRepoWatchersFragment(repo))
            }
            //Ask view models for pull requests
            viewModel.fetchPulls(it)
            viewModel.isDataLoaded.subscribe({
                //if loading of pulls completed then
                if(it && viewModel.pullsMap.isNotEmpty()){
                    //set count of open pull requests
                    prequestsCount.text = viewModel.pullsMap["open"]?.size.toString()
                    //navigate to Pulls fragment
                    prequestsButton.setOnClickListener {
                        view.findNavController()
                            .navigate(RepoDetailsFragmentDirections.actionRepoDetailsFragmentToRepoPullsFragment(viewModel.pullsMap))
                    }
                }
            }, {
                Log.e(RepoDetailsFragment::class.java.simpleName, "Error occurred" + it.message)
            })
        }
        return view
    }
}