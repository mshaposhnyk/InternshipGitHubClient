package com.example.internshipgithubclient.ui.workspace.repoList

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.internshipgithubclient.network.repo.RepoApiService
import com.example.internshipgithubclient.network.repo.RepoNetworkEntity
import com.example.internshipgithubclient.network.user.UserApiService
import com.example.internshipgithubclient.network.user.UserNetworkEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import retrofit2.Retrofit
import javax.inject.Inject

class RepoListViewModel @Inject constructor() : ViewModel() {
    val isUserDataLoaded: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    val isRepoDataLoaded: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    lateinit var repoList: List<RepoNetworkEntity>
    private var userEntity: UserNetworkEntity? = null
    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var repoApiService: RepoApiService

    @Inject
    lateinit var userApiService: UserApiService

    fun eventGotUser() {
        userApiService.let { apiService ->
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
        //Fetching data with RX
        repoApiService.let { apiService ->
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