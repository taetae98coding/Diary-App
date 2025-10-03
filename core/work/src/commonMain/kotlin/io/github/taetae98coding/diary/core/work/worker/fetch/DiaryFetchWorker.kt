package io.github.taetae98coding.diary.core.work.worker.fetch

import kotlin.uuid.Uuid
import kotlinx.coroutines.coroutineScope
import org.koin.core.annotation.Factory

@Factory
internal class DiaryFetchWorker(
    private val tagFetchWorker: TagFetchWorker,
    private val memoFetchWorker: MemoFetchWorker,
    private val memoTagFetchWorker: MemoTagFetchWorker,
) {
    suspend fun doWork(token: String, accountId: Uuid) {
        coroutineScope {
            tagFetchWorker.doWork(token, accountId)
            memoFetchWorker.doWork(token, accountId)
            memoTagFetchWorker.doWork(token, accountId)
        }
    }
}
