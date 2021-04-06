package com.example.internshipgithubclient.ui.workspace.repoList

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.core.domain.Repo
import com.example.core.domain.User
import com.example.core.interactors.GetAllUserRepos
import com.example.core.interactors.GetUser
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class RepoListViewModel @Inject constructor(
    private val getUser: GetUser,
    private val getAllUserRepos: GetAllUserRepos
) : ViewModel() {
    val isUserDataLoaded: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    val isRepoDataLoaded: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    lateinit var repoList: List<Repo>
    private lateinit var userEntity: User
    private val compositeDisposable = CompositeDisposable()

    fun eventGotUser() {
        val disposable = getUser.invoke()
            .subscribe(
                {
                    if (it != null)
                        userEntity = it
                    isUserDataLoaded.onNext(true)
                }, {
                    Log.e(
                        RepoListViewModel::class.java.simpleName,
                        "Error occurred" + it.message
                    )
                })
        compositeDisposable.add(disposable)
    }

    fun fetchUserRepos() {
        //Fetching data with RX
        val subscription = getAllUserRepos.invoke(userEntity)
            .subscribe(
                {
                    if (it != null)
                        repoList = it
                    isRepoDataLoaded.onNext(true)
                }, {
                    Log.e(
                        RepoListViewModel::class.java.simpleName,
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