package com.example.internshipgithubclient.ui.workspace.repoIssues

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.Issue
import com.example.core.domain.Pull
import com.example.core.domain.Repo
import com.example.core.domain.Result
import com.example.core.interactors.GetRepoIssues
import com.example.internshipgithubclient.di.FragmentScope
import com.example.internshipgithubclient.network.repo.IssueNetworkEntity
import com.example.internshipgithubclient.network.repo.RepoApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.ArrayList
import javax.inject.Inject

@FragmentScope
class IssuesViewModel @Inject constructor(private val getRepoIssues: GetRepoIssues) : ViewModel() {
    var compositeDisposable = CompositeDisposable()

    private val _isIssuesFetchingErrorOccurred = MutableStateFlow(false)
    val isIssuesFetchingErrorOccurred: StateFlow<Boolean> = _isIssuesFetchingErrorOccurred

    //list that contains pull requests
    private val _issues: MutableStateFlow<List<Issue>> = MutableStateFlow(ArrayList())
    val issues: StateFlow<List<Issue>> = _issues


    fun fetchIssues(repo: Repo) {
        compositeDisposable.add(getRepoIssues.invoke(repo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                when(it){
                    is Result.Success -> {
                        _issues.value = it.data
                    }
                    else -> _isIssuesFetchingErrorOccurred.value = true
                }
            })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}