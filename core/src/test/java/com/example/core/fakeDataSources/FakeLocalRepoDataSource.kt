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
        var repo = createTestRepo(-1, user.id)
        listOfRepos.forEach {
            if (it.owner.id == user.id) {
                repo = it
                return@forEach
            }
        }
        return Single.just(repo)
    }

    override fun getWatchers(repo: Repo): Single<List<User>> {
        return Single.just(repoWatchers[repo])
    }

    override fun addRepo(repo: Repo): Completable {
        listOfRepos.add(repo)
        return Completable.complete()
    }

    override fun addRepoWatcher(repo: Repo): Completable {
        if(repoWatchers.containsKey(repo)) {
            repoWatchers[repo]?.add(createTestUser(2))
        } else {
            val watchersList = ArrayList<User>()
            watchersList.add(createTestUser(2))
            repoWatchers[repo] = watchersList
        }
        return Completable.complete()
    }

    override fun deleteRepo(repo: Repo): Completable {
        listOfRepos.remove(repo)
        repoWatchers.remove(repo)
        return Completable.complete()
    }
}