package com.example.internshipgithubclient.ui.workspace.repoIssues

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.core.domain.Issue
import com.example.core.domain.Repo
import com.example.core.interactors.GetRepoIssues
import com.example.internshipgithubclient.di.FragmentScope
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

@FragmentScope
class IssuesViewModel @Inject constructor(private val getRepoIssues: GetRepoIssues) : ViewModel() {
    val isDataLoaded: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    lateinit var issues: List<Issue>
    private val compositeDisposable = CompositeDisposable()


    fun fetchIssues(repo: Repo) {
        //getting list of issues
        val disposable = getRepoIssues.invoke(repo)
            .subscribe({
                issues = it
                if (it.isNotEmpty())
                    isDataLoaded.onNext(true)
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