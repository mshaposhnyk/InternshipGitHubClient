package com.example.internshipgithubclient.ui.workspace.repoIssues

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.internshipgithubclient.di.FragmentScope
import com.example.internshipgithubclient.network.repo.IssueNetworkEntity
import com.example.internshipgithubclient.network.repo.RepoApiService
import com.example.internshipgithubclient.network.repo.RepoNetworkEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import retrofit2.Retrofit
import javax.inject.Inject

@FragmentScope
class IssuesViewModel @Inject constructor() : ViewModel() {
    val isDataLoaded: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    lateinit var issues: List<IssueNetworkEntity>
    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var service: RepoApiService


    fun fetchIssues(repo: RepoNetworkEntity) {
        //getting list of issues and mapping them by state
        service.let { apiService ->
            val subscription: Disposable =
                apiService.getIssuesForRepo(repo.owner.login, repo.name)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        issues = it
                        if (it.isNotEmpty())
                            isDataLoaded.onNext(true)
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