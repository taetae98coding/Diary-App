package io.github.taetae98coding.diary.domain.account.usecase

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.domain.account.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.annotation.Factory

@Factory
public class GetAccountUseCase internal constructor(
    private val sessionRepository: SessionRepository,
) {
    public operator fun invoke(): Flow<Result<Account>> {
        return flow {
            sessionRepository.get()
                .mapLatest { session ->
                    if (session == null) {
                        Account.Guest
                    } else {
                        Account.User(
                            token = session.token,
                            id = session.id,
                            email = session.email,
                            profileImage = session.profileImage,
                        )
                    }
                }.also {
                    emitAll(it)
                }
        }.mapLatest {
            Result.success(it)
        }.catch {
            emit(Result.failure(it))
        }
    }
}
