package io.github.taetae98coding.diary.domain.memo.repository

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.Memo
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

public interface MemoRepository {
    public fun get(memoId: Uuid): Flow<Memo?>
}
