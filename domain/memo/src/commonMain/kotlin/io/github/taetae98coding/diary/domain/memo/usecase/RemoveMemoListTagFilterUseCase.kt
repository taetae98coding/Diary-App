package io.github.taetae98coding.diary.domain.memo.usecase

import io.github.taetae98coding.diary.domain.memo.repository.MemoListTagFilterRepository
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
public class RemoveMemoListTagFilterUseCase internal constructor(
    private val memoListTagFilterRepository: MemoListTagFilterRepository,
) {
    public suspend operator fun invoke(tagId: Uuid): Result<Unit> {
        return runCatching { memoListTagFilterRepository.upsert(tagId, false) }
    }
}
