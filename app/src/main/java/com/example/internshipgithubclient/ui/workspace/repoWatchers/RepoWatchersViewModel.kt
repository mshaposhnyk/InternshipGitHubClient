package com.example.internshipgithubclient.ui.workspace.repoWatchers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.Repo
import com.example.core.domain.Result
import com.example.core.domain.User
import com.example.core.interactors.GetWatchersRepo
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepoWatchersViewModel @Inject constructor(private val watchersRepo: GetWatchersRepo) :
    ViewModel() {
    private val _isFetchingWatchersFailed: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFetchingWatchersFailed: StateFlow<Boolean> = _isFetchingWatchersFailed

    private val _listWatchers: MutableStateFlow<List<User>> = MutableStateFlow(ArrayList())
    val listWatchers: StateFlow<List<User>> = _listWatchers

    fun fetchWatchers(repo: Repo) {
        viewModelScope.launch {
            //getting list of watchers for a given repo
            when (val result = watchersRepo.invoke(repo)) {
                is Result.Success -> _listWatchers.value = result.data.toList()
                else -> _isFetchingWatchersFailed.value = true
            }
        }
    }
}