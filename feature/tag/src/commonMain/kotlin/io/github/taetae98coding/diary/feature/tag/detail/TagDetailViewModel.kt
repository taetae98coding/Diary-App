package io.github.taetae98coding.diary.feature.tag.detail

import androidx.lifecycle.ViewModel
import io.github.taetae98coding.diary.presenter.tag.detail.TagDetailStateHolder
import io.github.taetae98coding.diary.presenter.tag.detail.TagDetailStateHolderTemplate
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class TagDetailViewModel(
    private val strategy: AccountTagDetailStrategy,
    private val stateHolder: TagDetailStateHolderTemplate = TagDetailStateHolderTemplate(strategy = strategy),
) : ViewModel(stateHolder),
    TagDetailStateHolder by stateHolder
