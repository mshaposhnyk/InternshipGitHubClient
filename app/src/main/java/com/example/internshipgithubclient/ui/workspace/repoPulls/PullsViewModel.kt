package com.example.internshipgithubclient.ui.workspace.repoPulls

import androidx.lifecycle.ViewModel
import com.example.internshipgithubclient.di.FragmentScope
import com.example.internshipgithubclient.network.pullRequest.PullNetworkEntity
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

@FragmentScope
class PullsViewModel @Inject constructor() : ViewModel() {
    val isDataLoaded: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    lateinit var pullsList: List<PullNetworkEntity>


    //setting pulls from arguments to viewmodel
    fun setPulls(pulls: List<PullNetworkEntity>) {
        this.pullsList = pulls
        //notify subscribed fragments for updates
        isDataLoaded.onNext(true)
    }
}