package io.github.taetae98coding.diary.domain.tag.usecase

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeKotlinBuilder
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.core.entity.tag.TagDetail
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.sync.usecase.RequestDiaryBackupUseCase
import io.github.taetae98coding.diary.domain.tag.exception.TagNotFoundException
import io.github.taetae98coding.diary.domain.tag.repository.AccountTagRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.flowOf

class UpdateTagUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val requestDiaryBackupUseCase = mockk<RequestDiaryBackupUseCase>(relaxed = true, relaxUnitFun = true)
        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val getTagUseCase = mockk<GetTagUseCase>(relaxed = true, relaxUnitFun = true)
        val accountTagRepository = mockk<AccountTagRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = UpdateTagUseCase(
            requestDiaryBackupUseCase = requestDiaryBackupUseCase,
            getAccountUseCase = getAccountUseCase,
            getTagUseCase = getTagUseCase,
            accountTagRepository = accountTagRepository,
        )

        Given("태그가 존재하지 않는 경우") {
            val account = fixture.giveMeOne<Account>()
            val tagId = fixture.giveMeOne<Uuid>()
            val detail = fixture.giveMeOne<TagDetail>()

            every { getAccountUseCase() } returns flowOf(Result.success(account))
            every { getTagUseCase(tagId) } returns flowOf(Result.success(null))

            When("유즈케이스 실행하면") {
                val result = useCase(tagId, detail)

                Then("실패한다.") {
                    result.shouldBeFailure<TagNotFoundException>()
                }
            }
        }

        Given("태그가 존재하는 경우") {
            val account = fixture.giveMeOne<Account>()
            val tagId = fixture.giveMeOne<Uuid>()
            val tag = fixture.giveMeKotlinBuilder<Tag>()
                .set(Tag::id, tagId)
                .sample()

            every { getAccountUseCase() } returns flowOf(Result.success(account))
            every { getTagUseCase(tagId) } returns flowOf(Result.success(tag))

            And("업데이트 하려는 제목이 공백인 경우") {
                val detail = fixture.giveMeKotlinBuilder<TagDetail>()
                    .set(TagDetail::title, "")
                    .sample()

                When("유즈케이스 실행하면") {
                    val result = useCase(tagId, detail)

                    Then("성공한다.") {
                        result.shouldBeSuccess()
                    }

                    Then("기존 제목으로 업데이트한다.") {
                        coVerify(exactly = 1) {
                            accountTagRepository.update(
                                account = account,
                                tagId = tagId,
                                detail = detail.copy(title = tag.detail.title),
                            )
                        }
                    }
                }
            }

            And("업데이트 하려는 제목이 공백이 아닌 경우") {
                val detail = fixture.giveMeKotlinBuilder<TagDetail>()
                    .set(TagDetail::title, "title")
                    .sample()

                When("유즈케이스 실행하면") {
                    val result = useCase(tagId, detail)

                    Then("성공한다.") {
                        result.shouldBeSuccess()
                    }

                    Then("업데이트한다.") {
                        coVerify(exactly = 1) {
                            accountTagRepository.update(
                                account = account,
                                tagId = tagId,
                                detail = detail,
                            )
                        }
                    }
                }
            }
        }
    }
}
