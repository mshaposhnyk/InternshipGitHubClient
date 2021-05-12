package com.example.internshipgithubclient.repositories.fakeDataSources

import com.example.core.data.LocalRepoDataSource
import com.example.core.domain.Repo
import com.example.core.domain.User
import com.example.internshipgithubclient.createTestRepo
import com.example.internshipgithubclient.createTestUser
import com.example.internshipgithubclient.db.fromDomain
import com.example.internshipgithubclient.db.repo.RepoRoomEntity
import com.example.internshipgithubclient.db.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf

class FakeLocalRepoDataSource : LocalRepoDataSource {
    private val listOfRepos = ArrayList<RepoRoomEntity>()
    private val repoWatchers = HashMap<Repo, MutableList<User>>()

    override suspend fun getAll(user: User): Flow<Repo> {
        return listOfRepos.filter { it.ownerId == user.id }.map { it.toDomain(user) }.asFlow()
    }

    override suspend fun get(user: User, repoName: String): Repo {
        var repo = createTestRepo(-1, user.id)
        listOfRepos.forEach {
            if (it.ownerId == user.id) {
                repo = it.toDomain(user)
                return@forEach
            }
        }
        return repo
    }

    override suspend fun getWatchers(repo: Repo): Flow<User> {
        return repoWatchers[repo]?.asFlow() ?: flowOf()
    }

    override suspend fun addRepo(repo: Repo) {
        listOfRepos.add(repo.fromDomain())
    }

    override suspend fun addRepoWatcher(repo: Repo, user: User) {
        if(repoWatchers.containsKey(repo)) {
            repoWatchers[repo]?.add(user)
        } else {
            val watchersList = ArrayList<User>()
            watchersList.add(user)
            repoWatchers[repo] = watchersList
        }
    }

    override suspend fun deleteRepo(repo: Repo) {
        listOfRepos.remove(repo.fromDomain())
        repoWatchers.remove(repo)
    }
}