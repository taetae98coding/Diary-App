package io.github.taetae98coding.diary.core.work.worker.backup

import kotlin.uuid.Uuid
import kotlinx.coroutines.coroutineScope
import org.koin.core.annotation.Factory

@Factory
internal class DiaryBackupWorker(
    private val memoBackupWorker: MemoBackupWorker,
    private val tagBackupWorker: TagBackupWorker,
    private val memoTagBackupWorker: MemoTagBackupWorker,
) {
    suspend fun doWork(token: String, accountId: Uuid) {
        coroutineScope {
            tagBackupWorker.doWork(token, accountId)
            memoBackupWorker.doWork(token, accountId)

            memoTagBackupWorker.doWork(token, accountId)
        }
    }
}
