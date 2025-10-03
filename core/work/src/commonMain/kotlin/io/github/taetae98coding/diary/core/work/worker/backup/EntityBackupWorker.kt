package io.github.taetae98coding.diary.core.work.worker.backup

import kotlin.uuid.Uuid

internal abstract class EntityBackupWorker<T> {
    suspend fun doWork(accountId: Uuid, token: String) {
        while (true) {
            val local = getBackupList(accountId)
            if (local.isEmpty()) {
                break
            }

            backup(accountId, token, local)
            removeBackupList(accountId, local)
        }
    }

    protected abstract suspend fun getBackupList(accountId: Uuid): List<T>
    protected abstract suspend fun backup(accountId: Uuid, token: String, list: List<T>)
    protected abstract suspend fun removeBackupList(accountId: Uuid, list: List<T>)
}
