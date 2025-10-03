package io.github.taetae98coding.diary.core.work.worker.backup

import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
internal class DiaryBackupWorker(
    private val memoBackupWorker: MemoBackupWorker,
    private val tagBackupWorker: TagBackupWorker,
    private val memoTagBackupWorker: MemoTagBackupWorker,
) {
    suspend fun doWork(accountId: Uuid, token: String) {
        tagBackupWorker.doWork(accountId, token)
        memoBackupWorker.doWork(accountId, token)
        memoTagBackupWorker.doWork(accountId, token)
    }
}
