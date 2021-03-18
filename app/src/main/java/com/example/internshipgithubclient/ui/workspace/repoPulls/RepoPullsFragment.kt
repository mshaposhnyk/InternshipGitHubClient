package com.example.internshipgithubclient.ui.workspace.repoPulls

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.internshipgithubclient.R
import com.example.internshipgithubclient.databinding.FragmentWtabsGenericBinding
import com.example.internshipgithubclient.network.pullRequest.PullNetworkEntity
import com.google.android.material.tabs.TabLayoutMediator

class RepoPullsFragment : Fragment() {
    //ViewPager adapter for instantiating right fragments in viewpager
    private lateinit var viewPagerAdapter: PullsPagerAdapter

    //Closed,Open and RepoPullsFragment sharing the same viewModel instance
    private val viewModel: PullsViewModel by activityViewModels()
    private lateinit var binding: FragmentWtabsGenericBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWtabsGenericBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPagerAdapter = PullsPagerAdapter(parentFragmentManager, lifecycle)
        //setting adapter for viewPager
        binding.issuesPages.adapter = viewPagerAdapter
        //Attaching tabs to viewPager
        TabLayoutMediator(binding.issuesTabs, binding.issuesPages) { tab, position ->
            tab.text = "placeholder"
            when (position) {
                0 -> tab.text = getString(R.string.open)
                1 -> tab.text = getString(R.string.closed)
            }
        }.attach()
        val pullsList =
            (arguments?.getParcelableArray("repoPulls") as Array<PullNetworkEntity>).toList()
        //if map of pull requests is not null then set it in viewmodel
        pullsList.let {
            viewModel.setPulls(it)
        }
    }
}