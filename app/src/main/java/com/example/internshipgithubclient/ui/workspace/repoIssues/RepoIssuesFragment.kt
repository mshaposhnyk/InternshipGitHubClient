package com.example.internshipgithubclient.ui.workspace.repoIssues

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.internshipgithubclient.R
import com.example.internshipgithubclient.network.repo.RepoNetworkEntity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class RepoIssuesFragment : Fragment() {
    //ViewPager adapter for instantiating right fragments in viewpager
    private lateinit var viewPagerAdapter: IssuesPagerAdapter
    //ViewPager for switching tabs with slide gesture
    private lateinit var viewPager: ViewPager2
    //Closed,Open and RepoIssuesFragment uses the same viewModel instance
    private val viewModel:IssuesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_wtabs_generic, container, false)
        viewPagerAdapter = IssuesPagerAdapter(parentFragmentManager,lifecycle)
        viewPager = view.findViewById(R.id.issuesPages)
        //setting adapter for viewPager
        viewPager.adapter = viewPagerAdapter
        val tabLayout = view.findViewById<TabLayout>(R.id.issuesTabs)
        //Attaching tabs to viewPager
        TabLayoutMediator(tabLayout,viewPager) { tab, position ->
            tab.text = "placeholder"
            when(position){
                0 -> tab.text = getString(R.string.open)
                1 -> tab.text = getString(R.string.closed)
            }
        }.attach()
        val repo: RepoNetworkEntity? = arguments?.getParcelable("choosedRepo")
        //If repo is not null then ask viewmodel for fetching pull issues map
        repo?.let{
            viewModel.fetchIssues(it)
        }
        return view
    }
}