package io.github.taetae98coding.diary.domain.buddy.group.usecase.tag

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeKotlinBuilder
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.core.entity.tag.TagDetail
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupTagRepository
import io.github.taetae98coding.diary.domain.tag.usecase.GetTagUseCase
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.flowOf

class UpdateBuddyGroupTagUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val getTagUseCase = mockk<GetTagUseCase>(relaxed = true, relaxUnitFun = true)
        val buddyGroupTagRepository = mockk<BuddyGroupTagRepository>(relaxed = true, relaxUnitFun = true)

        val useCase = UpdateBuddyGroupTagUseCase(
            getAccountUseCase = getAccountUseCase,
            getTagUseCase = getTagUseCase,
            buddyGroupTagRepository = buddyGroupTagRepository,
        )

        Given("로그인된 계정으로 태그를 수정하는 경우") {
            val account = fixture.giveMeOne<Account.User>()
            val buddyGroupId = fixture.giveMeOne<Uuid>()
            val tagId = fixture.giveMeOne<Uuid>()
            val tag = fixture.giveMeOne<Tag>()
            val detail = fixture.giveMeKotlinBuilder<TagDetail>()
                .set(TagDetail::title, "제목")
                .sample()

            every { getAccountUseCase() } returns flowOf(Result.success(account))
            every { getTagUseCase(tagId) } returns flowOf(Result.success(tag))

            When("유즈케이스를 실행하면") {
                val result = useCase(buddyGroupId, tagId, detail)

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
                Then("Repository를 호출한다") {
                    coVerify(exactly = 1) {
                        buddyGroupTagRepository.update(
                            account = account,
                            buddyGroupId = buddyGroupId,
                            tagId = tagId,
                            detail = detail,
                        )
                    }
                }

                clearAllMocks()
            }
        }

        Given("로그인하지 않은 경우") {
            val buddyGroupId = fixture.giveMeOne<Uuid>()
            val tagId = fixture.giveMeOne<Uuid>()
            val detail = fixture.giveMeKotlinBuilder<TagDetail>()
                .set(TagDetail::title, "제목")
                .sample()

            every { getAccountUseCase() } returns flowOf(Result.success(Account.Guest))

            When("유즈케이스를 실행하면") {
                val result = useCase(buddyGroupId, tagId, detail)

                Then("실패한다") {
                    result.shouldBeFailure<NotLoginException>()
                }
                Then("Repository를 호출하지 않는다") {
                    coVerify(exactly = 0) {
                        buddyGroupTagRepository.update(any(), any(), any(), any())
                    }
                }
            }
        }
    }
}
