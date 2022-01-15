package com.example.core.fakeDataSources

import com.example.core.createTestRepo
import com.example.core.createTestUser
import com.example.core.data.LocalRepoDataSource
import com.example.core.domain.Repo
import com.example.core.domain.User
import io.reactivex.Completable
import io.reactivex.Single

class FakeLocalRepoDataSource : LocalRepoDataSource {
    private val listOfRepos = ArrayList<Repo>()
    private val repoWatchers = HashMap<Repo, MutableList<User>>()

    override fun getAll(user: User): Single<List<Repo>> {
        return Single.just(listOfRepos)
    }

    override fun get(user: User, repoName: String): Single<Repo> {
        var repo = createTestRepo(-1, user.id, repoName = repoName)
        listOfRepos.forEach {
            if (it.owner.id == user.id) {
                repo = it
                return@forEach
            }
        }
        return Single.just(repo)
    }

    override fun getWatchers(repo: Repo): Single<List<User>> {
        return Single.create {
            it.onSuccess(repoWatchers[repo]?: listOf())
        }
    }

    override fun addRepo(repo: Repo): Completable {
        listOfRepos.add(repo)
        return Completable.complete()
    }

    override fun addRepoWatcher(repo: Repo, user: User): Completable {
        if(repoWatchers.containsKey(repo)) {
            repoWatchers[repo]?.add(user)
        } else {
            val watchersList = ArrayList<User>()
            watchersList.add(user)
            repoWatchers[repo] = watchersList
        }
        return Completable.complete()
    }

    override fun addRepoWatcher(repo: Repo): Completable {
        return addRepoWatcher(repo,createTestUser(2))
    }

    override fun deleteRepo(repo: Repo): Completable {
        listOfRepos.remove(repo)
        repoWatchers.remove(repo)
        return Completable.complete()
    }
}