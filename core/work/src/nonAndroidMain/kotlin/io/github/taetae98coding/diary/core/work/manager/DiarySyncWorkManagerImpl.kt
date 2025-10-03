package io.github.taetae98coding.diary.core.work.manager

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.work.worker.fetch.DiaryFetchWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single

@Single
internal class DiarySyncWorkManagerImpl(
    private val applicationScope: CoroutineScope,
    private val backupWorkManager: DiaryBackupWorkManager,
    private val worker: DiaryFetchWorker,
) : DiarySyncWorkManager {
    private var currentJob: Job? = null
    override fun requestSync(account: Account.User) {
        if (currentJob?.isActive == true) return

        currentJob = applicationScope.launch {
            runCatching {
                backupWorkManager.backup(account)
                worker.doWork(account.token, account.id)
            }
        }
    }

    override suspend fun sync(account: Account.User) {
        requestSync(account)
        awaitSyncFinish()
    }

    private suspend fun awaitSyncFinish() {
        currentJob?.join()
    }
}
