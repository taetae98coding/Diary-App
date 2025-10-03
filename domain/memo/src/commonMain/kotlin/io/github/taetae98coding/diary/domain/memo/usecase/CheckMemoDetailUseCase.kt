package io.github.taetae98coding.diary.domain.memo.usecase

import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
import io.github.taetae98coding.diary.domain.memo.exception.MemoTitleBlankException
import org.koin.core.annotation.Factory

@Factory
public class CheckMemoDetailUseCase internal constructor() {
    public suspend operator fun invoke(detail: MemoDetail): Result<Unit> {
        return runCatching {
            if (detail.title.isBlank()) throw MemoTitleBlankException()
        }
    }
}
