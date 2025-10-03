package io.github.taetae98coding.diary.domain.tag.usecase

import io.github.taetae98coding.diary.core.entity.tag.TagDetail
import io.github.taetae98coding.diary.domain.tag.exception.TagTitleBlankException
import org.koin.core.annotation.Factory

@Factory
public class CheckTagDetailUseCase internal constructor() {
    public suspend operator fun invoke(detail: TagDetail): Result<Unit> {
        return runCatching {
            if (detail.title.isBlank()) throw TagTitleBlankException()
        }
    }
}
