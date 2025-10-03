package io.github.taetae98coding.diary.domain.memo.usecase

import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.domain.memo.repository.MemoRepository
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.annotation.Factory

@Factory
public class GetMemoUseCase internal constructor(
    private val memoRepository: MemoRepository,
) {
    public operator fun invoke(memoId: Uuid): Flow<Result<Memo?>> {
        return flow { emitAll(memoRepository.get(memoId)) }
            .mapLatest { Result.success(it) }
            .catch { emit(Result.failure(it)) }
    }
}
