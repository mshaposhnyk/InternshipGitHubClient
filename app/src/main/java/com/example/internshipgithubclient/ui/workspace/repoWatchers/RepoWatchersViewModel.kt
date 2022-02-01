package com.example.internshipgithubclient.ui.workspace.repoWatchers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.Repo
import com.example.core.domain.Result
import com.example.core.domain.User
import com.example.core.interactors.GetWatchersRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class RepoWatchersViewModel @Inject constructor(private val watchersRepo: GetWatchersRepo) :
    ViewModel() {
    var compositeDisposable = CompositeDisposable()

    private val _isFetchingWatchersFailed: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFetchingWatchersFailed: StateFlow<Boolean> = _isFetchingWatchersFailed

    private val _listWatchers: MutableStateFlow<List<User>> = MutableStateFlow(ArrayList())
    val listWatchers: StateFlow<List<User>> = _listWatchers

    fun fetchWatchers(repo: Repo) {
        compositeDisposable.add(watchersRepo.invoke(repo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                when(it){
                    is Result.Success -> {
                        _listWatchers.value = it.data
                    }
                    else -> _isFetchingWatchersFailed.value = true
                }
            })
        }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}