package io.github.taetae98coding.diary.domain.memo.usecase

import io.github.taetae98coding.diary.domain.memo.repository.ListMemoTagFilterTagRepository
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
public class RemoveListMemoTagFilterUseCase internal constructor(
    private val listMemoTagFilterTagRepository: ListMemoTagFilterTagRepository,
) {
    public suspend operator fun invoke(tagId: Uuid): Result<Unit> {
        return runCatching { listMemoTagFilterTagRepository.upsert(tagId, false) }
    }
}
