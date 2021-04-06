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
import java.util.ArrayList
import javax.inject.Inject

@FragmentScope
class IssuesViewModel @Inject constructor(private val getRepoIssues: GetRepoIssues) : ViewModel() {
    private val _isIssuesFetchingErrorOccurred = MutableStateFlow(false)
    val isIssuesFetchingErrorOccurred: StateFlow<Boolean> = _isIssuesFetchingErrorOccurred

    //list that contains pull requests
    private val _issues: MutableStateFlow<List<Issue>> = MutableStateFlow(ArrayList())
    val issues: StateFlow<List<Issue>> = _issues


    fun fetchIssues(repo: Repo) {
        //getting list of issues
        viewModelScope.launch {
            when(val result = getRepoIssues.invoke(repo)){
                is Result.Success -> _issues.value = result.data.toList()
                else -> _isIssuesFetchingErrorOccurred.value = true
            }
        }
    }
}