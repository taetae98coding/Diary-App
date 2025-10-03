package io.github.taetae98coding.diary.domain.buddy.group.usecase

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.buddy.BuddyGroup
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupRepository
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.annotation.Factory

@Factory
public class GetBuddyGroupUseCase internal constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val buddyGroupRepository: BuddyGroupRepository,
) {
    public operator fun invoke(buddyGroupId: Uuid): Flow<Result<BuddyGroup?>> {
        return flow {
            getAccountUseCase().mapLatest { it.getOrThrow() }
                .flatMapLatest { account ->
                    when (account) {
                        is Account.Guest -> flowOf(Result.failure(NotLoginException()))
                        is Account.User -> buddyGroupRepository.get(buddyGroupId)
                            .mapLatest { Result.success(it) }
                    }
                }
                .also { emitAll(it) }
        }.catch { emit(Result.failure(it)) }
    }
}
