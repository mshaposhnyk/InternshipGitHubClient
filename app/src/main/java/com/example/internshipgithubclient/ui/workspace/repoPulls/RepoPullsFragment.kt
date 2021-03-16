package com.example.internshipgithubclient.ui.workspace.repoPulls

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.internshipgithubclient.R
import com.example.internshipgithubclient.databinding.FragmentWtabsGenericBinding
import com.example.internshipgithubclient.network.pullRequest.PullNetworkEntity
import com.example.internshipgithubclient.ui.workspace.repoIssues.IssuesPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class RepoPullsFragment : Fragment() {
    //ViewPager adapter for instantiating right fragments in viewpager
    private lateinit var viewPagerAdapter: PullsPagerAdapter

    //ViewPager for switching tabs with slide gesture
    private lateinit var viewPager: ViewPager2

    //Closed,Open and RepoPullsFragment sharing the same viewModel instance
    private val viewModel: PullsViewModel by activityViewModels()
    private lateinit var binding: FragmentWtabsGenericBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWtabsGenericBinding.inflate(inflater, container, false)
        viewPagerAdapter = PullsPagerAdapter(parentFragmentManager, lifecycle)
        viewPager = binding.issuesPages
        val tabLayout = binding.issuesTabs
        //setting adapter for viewPager
        viewPager.adapter = viewPagerAdapter
        //Attaching tabs to viewPager
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "placeholder"
            when (position) {
                0 -> tab.text = getString(R.string.open)
                1 -> tab.text = getString(R.string.closed)
            }
        }.attach()
        val pulls: HashMap<String, List<PullNetworkEntity>>? =
            arguments?.getSerializable("pullsRepo") as HashMap<String, List<PullNetworkEntity>>?
        //if map of pull requests is not null then set it in viewmodel
        pulls?.let {
            viewModel.setPulls(it)
        }
        return binding.root
    }
}