package com.example.internshipgithubclient.ui.workspace.repoPulls

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.core.domain.Repo
import com.example.internshipgithubclient.R
import com.example.internshipgithubclient.databinding.FragmentWtabsGenericBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class RepoPullsFragment : DaggerFragment() {
    //ViewPager adapter for instantiating right fragments in viewpager
    private lateinit var viewPagerAdapter: PullsPagerAdapter

    //Closed,Open and RepoPullsFragment sharing the same viewModel instance
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(PullsViewModel::class.java)
    }
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
        viewPagerAdapter = PullsPagerAdapter(childFragmentManager, lifecycle)
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
        val repo = arguments?.getSerializable("choosedRepo") as Repo?
        //if repo is not null then set fetch pulls
        repo?.let {
            viewModel.fetchPulls(it)
        }
    }
}