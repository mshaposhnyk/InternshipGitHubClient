package com.example.internshipgithubclient.ui.workspace.repoList

import androidx.lifecycle.ViewModel
import com.example.core.domain.Repo
import com.example.core.domain.Result
import com.example.core.domain.User
import com.example.core.interactors.GetAllUserRepos
import com.example.core.interactors.GetUser
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class RepoListViewModel @Inject constructor(
    private val getUser: GetUser,
    private val getAllUserRepos: GetAllUserRepos
) : ViewModel() {
    var compositeDisposable = CompositeDisposable()

    private val _isUserLoadedState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isUserLoadedState: StateFlow<Boolean> = _isUserLoadedState

    private val _isAuthErrorOccurred: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isAuthErrorOccurred: StateFlow<Boolean> = _isAuthErrorOccurred

    private val _isReposLoadErrorOccurred: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isReposLoadErrorOccurred: StateFlow<Boolean> = _isReposLoadErrorOccurred

    private val _reposState: MutableSharedFlow<List<Repo>> = MutableSharedFlow()
    val reposState: SharedFlow<List<Repo>> = _reposState

    lateinit var repoList: List<Repo>
    private lateinit var _userEntity: User
    val userEntity: User
        get() = _userEntity

    fun eventGotUser() {
        compositeDisposable.add(getUser.invoke()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                when(it){
                    is Result.Success -> {
                        _userEntity = it.data
                        _isUserLoadedState.value = true
                    }
                    else -> _isAuthErrorOccurred.value = true
                }
            })
    }

    fun fetchUserRepos(user: User) {
        compositeDisposable.add(getAllUserRepos.invoke(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                when(it){
                    is Result.Success -> {
                        repoList = it.data
                        runBlocking {
                            _reposState.emit(repoList)
                        }
                    }
                    else -> _isReposLoadErrorOccurred.value = true
                }
            })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}