package com.example.internshipgithubclient.ui.workspace.repoList

import android.accounts.NetworkErrorException
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.internshipgithubclient.R
import com.example.internshipgithubclient.network.repo.RepoNetworkEntity
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

import io.reactivex.observers.DisposableObserver

class RepoListFragment : Fragment(),RepoListAdapter.OnRepoClickListener {

    private lateinit var viewModel: RepoListViewModel
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var adapter:RepoListAdapter
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_repo_list, container, false)
        val reposList = view.findViewById<RecyclerView>(R.id.repoList)
        adapter = RepoListAdapter(this)
        reposList.layoutManager = LinearLayoutManager(context)
        reposList.adapter = adapter
        viewModel = ViewModelProvider(this).get(RepoListViewModel::class.java)
        //Observing eventUserUpdated flag. If the user data is loaded, then start loading list of repos
        compositeDisposable = CompositeDisposable()
        val disposable = viewModel.eventGotUser()
                .flatMap { userLoaded ->
                    if (userLoaded)
                        return@flatMap viewModel.fetchUserRepos()
                    else
                        return@flatMap Single.just(ArrayList<RepoNetworkEntity>())
                }
                .subscribe({
                    adapter.data = it
                }, {
                    Log.e(RepoListFragment::class.java.simpleName, "Error occurred" + it.message)
                })
        compositeDisposable.add(disposable)
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    override fun onClick(v: View?, item: RepoNetworkEntity) {
        view?.findNavController()?.navigate(RepoListFragmentDirections.actionRepoListFragmentToRepoDetailsFragment(item))
    }

}
