package com.example.internshipgithubclient.ui.workspace.repoWatchers

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.core.domain.Repo
import com.example.core.domain.User
import com.example.core.interactors.GetWatchersRepo
import com.example.internshipgithubclient.ui.workspace.repoIssues.IssuesViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class RepoWatchersViewModel @Inject constructor(private val watchersRepo: GetWatchersRepo) : ViewModel() {
    val isDataLoaded: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    lateinit var listWatchers: List<User>
    private val compositeDisposable = CompositeDisposable()

    fun fetchWatchers(repo: Repo) {
        //getting list of watchers for a given repo
        val disposable = watchersRepo.invoke(repo)
            .subscribe({
                if (it.isNotEmpty()) {
                    listWatchers = it
                    //notify subscribers about list availability
                    isDataLoaded.onNext(true)
                }
            }, {
                Log.e(IssuesViewModel::class.java.simpleName, "Error occurred" + it.message)
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}