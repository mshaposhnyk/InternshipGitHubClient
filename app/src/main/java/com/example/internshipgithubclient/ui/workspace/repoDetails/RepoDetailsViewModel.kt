package com.example.internshipgithubclient.ui.workspace.repoDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.internshipgithubclient.network.AuthStateHelper
import com.example.internshipgithubclient.network.NetworkClient
import com.example.internshipgithubclient.network.pullRequest.PullNetworkEntity
import com.example.internshipgithubclient.network.repo.RepoApiService
import com.example.internshipgithubclient.network.repo.RepoNetworkEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class RepoDetailsViewModel:ViewModel() {
    //BehaviorSubject similar to LiveData in RX Java
    val isDataLoaded: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    //map that contains pull requests
    val pullsMap:HashMap<String,List<PullNetworkEntity>> = HashMap()

    fun fetchPulls(repo:RepoNetworkEntity){
        val service = AuthStateHelper.currentAuthState.accessToken?.let{
            NetworkClient.getInstance(it).create(RepoApiService::class.java)
        }
        //getting list of pull requests and mapping them by state
        service?.getPullsForRepo(repo.owner.login,repo.name)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.map {
                    pulls ->
                val openPulls = ArrayList<PullNetworkEntity>()
                val closedPulls = ArrayList<PullNetworkEntity>()
                pullsMap["open"] = openPulls
                pullsMap["closed"] = closedPulls
                pulls.forEach {
                    when(it.state){
                        "open" -> openPulls.add(it)
                        else -> closedPulls.add(it)
                    }
                }
                return@map pulls
            }
            ?.subscribe({
                if(it.isNotEmpty())
                    isDataLoaded.onNext(true)
            }, {
                Log.e(RepoDetailsViewModel::class.java.simpleName, "Error occurred" + it.message)
            })
    }
}