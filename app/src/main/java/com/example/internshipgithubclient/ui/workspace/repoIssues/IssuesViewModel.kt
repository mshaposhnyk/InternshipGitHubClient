package com.example.internshipgithubclient.ui.workspace.repoIssues

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.internshipgithubclient.network.AuthStateHelper
import com.example.internshipgithubclient.network.NetworkClient
import com.example.internshipgithubclient.network.repo.IssueNetworkEntity
import com.example.internshipgithubclient.network.repo.RepoApiService
import com.example.internshipgithubclient.network.repo.RepoNetworkEntity

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class IssuesViewModel() : ViewModel() {
    val isDataLoaded:BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    val issuesMap: HashMap<String,List<IssueNetworkEntity>> = HashMap()

    fun fetchIssues(repo: RepoNetworkEntity){
        val service = AuthStateHelper.currentAuthState.accessToken?.let{
            NetworkClient.getInstance(it).create(RepoApiService::class.java)
        }
        //getting list of issues and mapping them by state
        service?.getIssuesForRepo(repo.owner.login,repo.name)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.map {
                issues ->
                val openIssues = ArrayList<IssueNetworkEntity>()
                val closedIssues = ArrayList<IssueNetworkEntity>()
                issuesMap["open"] = openIssues
                issuesMap["closed"] = closedIssues
                issues.forEach {
                    when(it.state){
                        "open" -> openIssues.add(it)
                        else -> closedIssues.add(it)
                    }
                }
                return@map issues
            }
            ?.subscribe({
                if(it.isNotEmpty())
                    isDataLoaded.onNext(true)
            }, {
                Log.e(IssuesViewModel::class.java.simpleName, "Error occurred" + it.message)
            })

    }
}