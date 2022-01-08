package com.example.internshipgithubclient.ui.workspace.repoDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.Pull
import com.example.core.domain.Repo
import com.example.core.domain.Result
import com.example.core.interactors.GetRepoPulls
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import java.util.ArrayList
import javax.inject.Inject

class RepoDetailsViewModel @Inject constructor(private val getRepoPulls: GetRepoPulls) : ViewModel() {
    private val _isPullsFetchingErrorOccurred = MutableStateFlow(false)
    val isPullsFetchingErrorOccurred: StateFlow<Boolean> = _isPullsFetchingErrorOccurred

    //list that contains pull requests
    private val _pulls: MutableStateFlow<List<Pull>> = MutableStateFlow(ArrayList())
    val pulls: StateFlow<List<Pull>> = _pulls

    fun fetchPulls(repo: Repo) {
        //getting list of pull requests
        viewModelScope.launch {
            when(val result = getRepoPulls.invoke(repo)){
                is Result.Success -> {
                    result.data.subscribe { it ->
                        _pulls.value = it
                    }
                }
                else -> _isPullsFetchingErrorOccurred.value = true
            }
        }
    }
}