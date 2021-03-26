package com.example.internshipgithubclient.ui.workspace.repoPulls

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.core.domain.Pull
import com.example.core.domain.Repo
import com.example.core.interactors.GetRepoPulls
import com.example.internshipgithubclient.di.FragmentScope
import com.example.internshipgithubclient.ui.workspace.repoDetails.RepoDetailsViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

@FragmentScope
class PullsViewModel @Inject constructor(private val getRepoPulls: GetRepoPulls) : ViewModel() {
    val isDataLoaded: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    lateinit var pulls: List<Pull>
    private var compositeDisposable = CompositeDisposable()

    //fetching pulls for repo
    fun fetchPulls(repo: Repo) {
        //getting list of pull requests
        val disposable = getRepoPulls.invoke(repo)
            .subscribe({
                pulls = it
                if (it.isNotEmpty())
                    isDataLoaded.onNext(true)
            }, {
                Log.e(
                    RepoDetailsViewModel::class.java.simpleName,
                    "Error occurred" + it.message
                )
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}