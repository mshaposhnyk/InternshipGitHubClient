package com.example.internshipgithubclient.ui.workspace.repoWatchers

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.internshipgithubclient.network.AuthStateHelper
import com.example.internshipgithubclient.network.NetworkClient
import com.example.internshipgithubclient.network.repo.RepoApiService
import com.example.internshipgithubclient.network.repo.RepoNetworkEntity
import com.example.internshipgithubclient.network.user.UserNetworkEntity
import com.example.internshipgithubclient.ui.workspace.repoIssues.IssuesViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class RepoWatchersViewModel : ViewModel() {
    val isDataLoaded: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    lateinit var listWatchers: List<UserNetworkEntity>
    private val compositeDisposable = CompositeDisposable()

    fun fetchWatchers(repo: RepoNetworkEntity) {
        val service = AuthStateHelper.currentAuthState.accessToken?.let {
            NetworkClient.getInstance(it).create(RepoApiService::class.java)
        }
        //getting list of watchers for a given repo
        service?.let { apiService ->
            val subscription: Disposable =
                apiService.getWatchersForRepo(repo.owner.login, repo.name)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it.isNotEmpty()) {
                            listWatchers = it
                            //notify subscribers about list availability
                            isDataLoaded.onNext(true)
                        }
                    }, {
                        Log.e(IssuesViewModel::class.java.simpleName, "Error occurred" + it.message)
                    })
            compositeDisposable.add(subscription)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}