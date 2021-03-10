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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.internshipgithubclient.R
import com.example.internshipgithubclient.network.repo.RepoNetworkEntity
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

import io.reactivex.observers.DisposableObserver




class RepoListFragment: Fragment() {

    private lateinit var viewModel:RepoListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_repo_list, container, false)
        val reposList = view.findViewById<RecyclerView>(R.id.repoList)
        val repoListAdapter = RepoListAdapter()
        reposList.layoutManager = LinearLayoutManager(context)
        reposList.adapter = repoListAdapter
        viewModel = ViewModelProvider(this).get(RepoListViewModel::class.java)
        //Observing eventUserUpdated flag. If the user data is loaded, then start loading list of repos
        viewModel.eventGotUser()
                .flatMap { userLoaded-> if(userLoaded)
                                        return@flatMap viewModel.fetchUserRepos()
                                    else
                                        return@flatMap Single.just(ArrayList<RepoNetworkEntity>()) }
                .subscribe(object:SingleObserver<List<RepoNetworkEntity>>{
                    override fun onSubscribe(d: Disposable) {
                        Log.d(RepoListFragment::class.java.simpleName,"Subscribed to single list repos observable")
                    }

                    override fun onSuccess(t: List<RepoNetworkEntity>) {
                        repoListAdapter.data = t
                    }

                    override fun onError(e: Throwable) {
                        Log.e(RepoListFragment::class.java.simpleName,"Error occurred"+e.message)
                    }

                })
        /*viewModel.eventUserUpdated.observe(viewLifecycleOwner){
            if(it == true){
                viewModel.fetchUserRepos()
                        ?.subscribe(object:SingleObserver<List<RepoNetworkEntity>>{
                            override fun onSubscribe(d: Disposable) {
                                Log.d(RepoListFragment::class.java.simpleName,"Subscribed to single list repos observable")
                            }

                            override fun onSuccess(t: List<RepoNetworkEntity>) {
                                repoListAdapter.data = t
                            }

                            override fun onError(e: Throwable) {
                                Log.e(RepoListFragment::class.java.simpleName,"Error occurred"+e.message)
                            }

                        })

            }
        }*/
        return view
    }
}
