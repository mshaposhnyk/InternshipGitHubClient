package com.example.internshipgithubclient.ui.workspace.repoDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.internshipgithubclient.network.AuthStateHelper
import com.example.internshipgithubclient.network.NetworkClient
import com.example.internshipgithubclient.network.STATE_CLOSED
import com.example.internshipgithubclient.network.STATE_OPEN
import com.example.internshipgithubclient.network.pullRequest.PullNetworkEntity
import com.example.internshipgithubclient.network.repo.RepoApiService
import com.example.internshipgithubclient.network.repo.RepoNetworkEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class RepoDetailsViewModel : ViewModel() {
    //BehaviorSubject similar to LiveData in RX Java
    val isDataLoaded: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)

    //map that contains pull requests
    val pullsMap: HashMap<String, List<PullNetworkEntity>> = HashMap()
    private val compositeDisposable = CompositeDisposable()

    fun fetchPulls(repo: RepoNetworkEntity) {
        val service = AuthStateHelper.currentAuthState.accessToken?.let {
            NetworkClient.getInstance(it).create(RepoApiService::class.java)
        }
        //getting list of pull requests and mapping them by state
        service?.let { apiService ->
            val subscription: Disposable =
                apiService.getPullsForRepo(repo.owner.login, repo.name)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map { pulls ->
                        val openPulls = pulls.filter { pull -> pull.state == STATE_OPEN }
                        val closedPulls = pulls.filterNot { pull -> pull.state != STATE_OPEN }
                        pullsMap[STATE_OPEN] = openPulls
                        pullsMap[STATE_CLOSED] = closedPulls
                        pulls
                    }
                    .subscribe({
                        if (it.isNotEmpty())
                            isDataLoaded.onNext(true)
                    }, {
                        Log.e(
                            RepoDetailsViewModel::class.java.simpleName,
                            "Error occurred" + it.message
                        )
                    })
            compositeDisposable.add(subscription)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}