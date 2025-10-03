package io.github.taetae98coding.diary.core.work.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import io.github.taetae98coding.diary.core.work.worker.fetch.DiaryFetchWorker
import kotlin.uuid.Uuid
import org.koin.android.annotation.KoinWorker

@KoinWorker
internal class DiaryFetchAndroidxWorker(
    private val worker: DiaryFetchWorker,
    appContext: Context,
    params: WorkerParameters,
) : CoroutineWorker(
    appContext,
    params,
) {
    override suspend fun doWork(): Result {
        return try {
            val token = inputData.getString(TOKEN) ?: return Result.success()
            val accountId = inputData.getString(ACCOUNT_ID)?.let(Uuid.Companion::parse) ?: return Result.success()

            worker.doWork(token, accountId)
            Result.success()
        } catch (_: Throwable) {
            Result.failure()
        }
    }

    companion object {
        const val TOKEN = "TOKEN"
        const val ACCOUNT_ID = "accountId"

        fun data(token: String, accountId: Uuid): Data {
            return Data.Builder()
                .putString(TOKEN, token)
                .putString(ACCOUNT_ID, accountId.toString())
                .build()
        }
    }
}
