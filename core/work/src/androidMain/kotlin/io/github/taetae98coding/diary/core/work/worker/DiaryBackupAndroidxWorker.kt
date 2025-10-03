package io.github.taetae98coding.diary.core.work.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import io.github.taetae98coding.diary.core.work.worker.backup.DiaryBackupWorker
import kotlin.uuid.Uuid
import org.koin.android.annotation.KoinWorker

@KoinWorker
internal class DiaryBackupAndroidxWorker(
    private val worker: DiaryBackupWorker,
    appContext: Context,
    params: WorkerParameters,
) : CoroutineWorker(
    appContext,
    params,
) {
    override suspend fun doWork(): Result {
        return try {
            val accountId = inputData.getString(ACCOUNT_ID)?.let(Uuid.Companion::parse) ?: return Result.success()
            val token = inputData.getString(TOKEN) ?: return Result.success()

            worker.doWork(accountId, token)
            Result.success()
        } catch (_: Throwable) {
            Result.failure()
        }
    }

    companion object {
        const val ACCOUNT_ID = "accountId"
        const val TOKEN = "TOKEN"

        fun data(accountId: Uuid, token: String): Data {
            return Data.Builder()
                .putString(ACCOUNT_ID, accountId.toString())
                .putString(TOKEN, token)
                .build()
        }
    }
}
