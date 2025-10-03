package io.github.taetae98coding.diary.feature.buddy.group.search

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import io.github.taetae98coding.diary.core.coroutines.ext.MainImmediateScope
import io.github.taetae98coding.diary.core.entity.buddy.Buddy
import io.github.taetae98coding.diary.domain.buddy.usecase.SearchBuddyUseCase
import io.github.taetae98coding.diary.library.kotlinx.coroutines.core.flow.mapCollectionLatest
import io.github.taetae98coding.diary.library.paging.common.loading
import io.github.taetae98coding.diary.library.paging.common.notLoading
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

@Factory
internal class BuddySearchStateHolderImpl(
    private val searchBuddyUseCase: SearchBuddyUseCase,
    private val coroutineScope: CoroutineScope = MainImmediateScope(),
) : BuddySearchStateHolder {
    private val searchQuery = MutableStateFlow("")

    val selectedBuddySet = MutableStateFlow<Set<Buddy>>(emptySet())

    override val buddySearchPagingData = searchQuery.flatMapLatest { query ->
        if (query.isBlank()) {
            selectedBuddySet.mapCollectionLatest {
                BuddySearchUiState(
                    buddy = it,
                    isChecked = true,
                )
            }.mapLatest {
                PagingData.notLoading(it)
            }
        } else {
            val searchPagingData = searchBuddyUseCase(query).mapLatest { it.getOrNull() ?: PagingData.loading() }
                .cachedIn(coroutineScope)

            flow {
                emit(PagingData.loading())
                delay(200.milliseconds)
                combine(searchPagingData, selectedBuddySet) { pagingData, buddySet ->
                    val buddyIdSet = buddySet.map(Buddy::id).toSet()

                    pagingData.map {
                        BuddySearchUiState(
                            buddy = it,
                            isChecked = it.id in buddyIdSet,
                        )
                    }
                }.also {
                    emitAll(it)
                }
            }
        }
    }.cachedIn(coroutineScope)

    override fun search(query: String) {
        coroutineScope.launch {
            searchQuery.emit(query)
        }
    }

    override fun selectBuddy(buddy: Buddy) {
        coroutineScope.launch {
            selectedBuddySet.emit(selectedBuddySet.value + buddy)
        }
    }

    override fun unselectBuddy(buddy: Buddy) {
        coroutineScope.launch {
            selectedBuddySet.emit(selectedBuddySet.value - buddy)
        }
    }

    fun clear() {
        coroutineScope.cancel()
    }
}
