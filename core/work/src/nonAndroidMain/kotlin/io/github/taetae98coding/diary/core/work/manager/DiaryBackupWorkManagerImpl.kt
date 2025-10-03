package io.github.taetae98coding.diary.core.work.manager

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.work.worker.backup.DiaryBackupWorker
import kotlin.uuid.Uuid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import org.koin.core.annotation.Single

@Single
internal class DiaryBackupWorkManagerImpl(
    private val applicationScope: CoroutineScope,
    private val worker: DiaryBackupWorker,
) : DiaryBackupWorkManager {
    private var lastRequestAccountId: Uuid? = null
    private var currentJob: Deferred<Unit>? = null

    override fun requestBackup(account: Account.User) {
        if (lastRequestAccountId == account.id && currentJob?.isActive == true) return

        lastRequestAccountId = account.id

        currentJob?.cancel()
        currentJob = applicationScope.async {
            worker.doWork(account.id, account.token)
        }
    }

    override suspend fun backup(account: Account.User) {
        requestBackup(account)
        awaitBackupFinish()
    }

    private suspend fun awaitBackupFinish() {
        currentJob?.await()
    }
}
