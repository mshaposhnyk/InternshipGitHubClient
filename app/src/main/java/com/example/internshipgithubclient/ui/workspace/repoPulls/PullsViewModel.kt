package com.example.internshipgithubclient.ui.workspace.repoPulls

import androidx.lifecycle.ViewModel
import com.example.internshipgithubclient.network.pullRequest.PullNetworkEntity
import io.reactivex.subjects.BehaviorSubject

class PullsViewModel():ViewModel(){
    val isDataLoaded:BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    var pullsMap:HashMap<String,List<PullNetworkEntity>> = HashMap()

    //setting pulls from arguments to viewmodel
    fun setPulls(pulls:HashMap<String,List<PullNetworkEntity>>){
        this.pullsMap = pulls
        //notify subscribed fragments for updates
        isDataLoaded.onNext(true)
    }
}