package com.example.internshipgithubclient.ui.workspace.repoDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.internshipgithubclient.network.pullRequest.PullNetworkEntity
import com.example.internshipgithubclient.network.repo.RepoApiService
import com.example.internshipgithubclient.network.repo.RepoNetworkEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import retrofit2.Retrofit
import javax.inject.Inject

class RepoDetailsViewModel @Inject constructor() : ViewModel() {
    //BehaviorSubject similar to LiveData in RX Java
    val isDataLoaded: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)

    //list that contains pull requests
    lateinit var pulls: List<PullNetworkEntity>
    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var service: RepoApiService

    fun fetchPulls(repo: RepoNetworkEntity) {
        //getting list of pull requests and mapping them by state
        service.let { apiService ->
            val subscription: Disposable =
                apiService.getPullsForRepo(repo.owner.login, repo.name)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        pulls = it
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