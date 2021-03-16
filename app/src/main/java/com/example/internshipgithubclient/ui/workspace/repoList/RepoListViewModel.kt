package com.example.internshipgithubclient.ui.workspace.repoList

import androidx.lifecycle.ViewModel
import com.example.internshipgithubclient.network.AuthStateHelper
import com.example.internshipgithubclient.network.NetworkClient
import com.example.internshipgithubclient.network.repo.RepoApiService
import com.example.internshipgithubclient.network.repo.RepoNetworkEntity
import com.example.internshipgithubclient.network.user.UserApiService
import com.example.internshipgithubclient.network.user.UserNetworkEntity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

class RepoListViewModel : ViewModel() {
    private var userEntity: UserNetworkEntity? = null


    fun eventGotUser(): Single<Boolean> {
        val service = AuthStateHelper.currentAuthState.accessToken?.let {
            NetworkClient.getInstance(it).create(UserApiService::class.java)
        }
        var observable: Single<Boolean> = Single.just(false)

        if (service != null) {
            //Get single observable if got user
            observable = service.getAuthenticatedUser()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map { t ->
                        userEntity = t
                        userEntity != null
                    }
        }
        return observable
    }

    fun fetchUserRepos(): Single<List<RepoNetworkEntity>> {
        val service = AuthStateHelper.currentAuthState.accessToken?.let {
            NetworkClient.getInstance(it).create(RepoApiService::class.java)
        }
        //Fetching data with RX
        var observable: Single<List<RepoNetworkEntity>> = Single.just(ArrayList())
        if (userEntity != null && service != null) {
            observable = userEntity?.let {
                service.getUserRepos(it.login).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
            } as Single<List<RepoNetworkEntity>>
        }
        return observable
    }
}