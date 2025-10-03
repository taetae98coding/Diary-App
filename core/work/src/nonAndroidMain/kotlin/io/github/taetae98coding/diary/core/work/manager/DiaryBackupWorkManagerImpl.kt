package io.github.taetae98coding.diary.core.work.manager

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.work.worker.backup.DiaryBackupWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single

@Single
internal class DiaryBackupWorkManagerImpl(
    private val applicationScope: CoroutineScope,
    private val worker: DiaryBackupWorker,
) : DiaryBackupWorkManager {
    private var currentJob: Job? = null

    override fun requestBackup(account: Account.User) {
        if (currentJob?.isActive == true) return

        currentJob = applicationScope.launch {
            runCatching { worker.doWork(account.token, account.id) }
        }
    }

    override suspend fun backup(account: Account.User) {
        requestBackup(account)
        awaitBackupFinish()
    }

    private suspend fun awaitBackupFinish() {
        currentJob?.join()
    }
}
