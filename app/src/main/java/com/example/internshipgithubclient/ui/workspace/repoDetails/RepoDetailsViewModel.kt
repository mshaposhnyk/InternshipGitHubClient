package com.example.internshipgithubclient.ui.workspace.repoDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.core.domain.Pull
import com.example.core.domain.Repo
import com.example.core.interactors.GetRepoPulls
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

class RepoDetailsViewModel @Inject constructor(private val getRepoPulls: GetRepoPulls) : ViewModel() {
    //BehaviorSubject similar to LiveData in RX Java
    val isDataLoaded: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)

    //list that contains pull requests
    lateinit var pulls: List<Pull>
    private val compositeDisposable = CompositeDisposable()

    fun fetchPulls(repo: Repo) {
        //getting list of pull requests
        val subscription: Disposable = getRepoPulls.invoke(repo)
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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}