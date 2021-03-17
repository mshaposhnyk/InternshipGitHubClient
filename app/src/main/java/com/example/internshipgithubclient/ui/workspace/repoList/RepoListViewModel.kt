package com.example.internshipgithubclient.ui.workspace.repoList

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.internshipgithubclient.network.AuthStateHelper
import com.example.internshipgithubclient.network.NetworkClient
import com.example.internshipgithubclient.network.repo.RepoApiService
import com.example.internshipgithubclient.network.repo.RepoNetworkEntity
import com.example.internshipgithubclient.network.user.UserApiService
import com.example.internshipgithubclient.network.user.UserNetworkEntity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class RepoListViewModel : ViewModel() {
    val isUserDataLoaded: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    val isRepoDataLoaded: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    lateinit var repoList: List<RepoNetworkEntity>
    private var userEntity: UserNetworkEntity? = null
    private val compositeDisposable = CompositeDisposable()


    fun eventGotUser() {
        val service = AuthStateHelper.currentAuthState.accessToken?.let {
            NetworkClient.getInstance(it).create(UserApiService::class.java)
        }

        service?.let { apiService ->
            val subscription: Disposable = apiService.getAuthenticatedUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
            compositeDisposable.add(subscription)
        }
    }

    fun fetchUserRepos() {
        val service = AuthStateHelper.currentAuthState.accessToken?.let {
            NetworkClient.getInstance(it).create(RepoApiService::class.java)
        }
        //Fetching data with RX
        service?.let { apiService ->
            userEntity?.let { userNetworkEntity ->
                val subscription: Disposable = apiService.getUserRepos(userNetworkEntity.login)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
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
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}