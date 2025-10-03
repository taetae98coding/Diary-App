package io.github.taetae98coding.diary.core.work.manager

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.work.worker.fetch.DiaryFetchWorker
import kotlin.uuid.Uuid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import org.koin.core.annotation.Single

@Single
internal class DiarySyncWorkManagerImpl(
    private val applicationScope: CoroutineScope,
    private val backupWorkManager: DiaryBackupWorkManager,
    private val worker: DiaryFetchWorker,
) : DiarySyncWorkManager {
    private var lastRequestAccountId: Uuid? = null
    private var currentJob: Deferred<Unit>? = null

    override fun requestSync(account: Account.User) {
        if (lastRequestAccountId == account.id && currentJob?.isActive == true) return

        lastRequestAccountId = account.id

        currentJob?.cancel()
        currentJob = applicationScope.async {
            backupWorkManager.backup(account)
            worker.doWork(account.token, account.id)
        }
    }

    override suspend fun sync(account: Account.User) {
        requestSync(account)
        awaitSyncFinish()
    }

    private suspend fun awaitSyncFinish() {
        currentJob?.await()
    }
}
