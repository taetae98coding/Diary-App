package io.github.taetae98coding.diary.core.work.worker.fetch

import kotlin.uuid.Uuid

internal abstract class EntityFetchWorker<T> {
    suspend fun doWork(token: String, accountId: Uuid) {
        while (true) {
            val lastUpdatedAt = getLastUpdatedAt(accountId) ?: -1L
            val remote = getFetchList(token, lastUpdatedAt)

            if (remote.isEmpty()) {
                break
            }

            fetch(accountId, remote)
        }
    }

    protected abstract suspend fun getLastUpdatedAt(accountId: Uuid): Long?
    protected abstract suspend fun getFetchList(token: String, lastUpdatedAt: Long): List<T>
    protected abstract suspend fun fetch(accountId: Uuid, list: List<T>)
}
