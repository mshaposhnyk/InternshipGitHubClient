package com.example.internshipgithubclient.ui.workspace.repoList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.Repo
import com.example.core.domain.Result
import com.example.core.domain.User
import com.example.core.interactors.GetAllUserRepos
import com.example.core.interactors.GetUser
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepoListViewModel @Inject constructor(
    private val getUser: GetUser,
    private val getAllUserRepos: GetAllUserRepos
) : ViewModel() {
    private val _isUserLoadedState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isUserLoadedState: StateFlow<Boolean> = _isUserLoadedState

    private val _isAuthErrorOccurred: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isAuthErrorOccurred: StateFlow<Boolean> = _isAuthErrorOccurred

    private val _isReposLoadErrorOccurred: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isReposLoadErrorOccurred: StateFlow<Boolean> = _isReposLoadErrorOccurred

    private val _reposState: MutableSharedFlow<List<Repo>> = MutableSharedFlow()
    val reposState: SharedFlow<List<Repo>> = _reposState

    //val isRepoLoadErrorOccurred: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    lateinit var repoList: List<Repo>
    private lateinit var userEntity: User

    fun eventGotUser() {
        viewModelScope.launch {
            when (val userResult = getUser.invoke()) {
                is Result.Success -> {
                    userEntity = userResult.data
                    _isUserLoadedState.value = true
                }
                else -> _isAuthErrorOccurred.value = true
            }
        }
    }

    fun fetchUserRepos() {
        //Fetching data with Kotlin Flow
        viewModelScope.launch {
            when (val reposResult = getAllUserRepos.invoke(userEntity)) {
                is Result.Success -> {
                    repoList = reposResult.data.toList()
                    _reposState.emit(repoList)
                }
                else -> _isReposLoadErrorOccurred.value = true
            }
        }
    }
}