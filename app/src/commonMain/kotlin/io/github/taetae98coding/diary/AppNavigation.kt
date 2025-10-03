package io.github.taetae98coding.diary

import androidx.compose.foundation.focusable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.SAVED_STATE_REGISTRY_OWNER_KEY
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation3.runtime.NavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.navEntryDecorator
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.scene.rememberSceneSetupNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.compose.LocalSavedStateRegistryOwner
import io.github.taetae98coding.diary.feature.buddy.group.buddyGroupEntryProvider
import io.github.taetae98coding.diary.feature.calendar.calendarEntryProvider
import io.github.taetae98coding.diary.feature.login.loginEntryProvider
import io.github.taetae98coding.diary.feature.memo.memoEntryProvider
import io.github.taetae98coding.diary.feature.more.moreEntryProvider
import io.github.taetae98coding.diary.feature.tag.tagEntryProvider
import io.github.taetae98coding.diary.library.compose.ui.isPlatformMetaPressed
import io.github.taetae98coding.diary.library.compose.ui.onPreviewKeyUp
import io.github.taetae98coding.diary.navigation.TopLevelDestination
import io.github.taetae98coding.diary.navigation.platformPopTransitionSpec
import io.github.taetae98coding.diary.navigation.platformPredictivePopTransitionSpec
import io.github.taetae98coding.diary.navigation.platformTransitionSpec

@Composable
internal fun AppNavigation(
    state: AppState,
    modifier: Modifier = Modifier,
) {
    NavDisplay(
        backStack = state.backStack,
        modifier = modifier.navigationShortcut(state),
        entryDecorators = listOf(
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator(),
            // TODO replace with default
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            memoEntryProvider(
                backStack = state.backStack,
                scrollState = state.memoScrollState,
            )
            tagEntryProvider(
                backStack = state.backStack,
                scrollState = state.tagScrollState,
            )
            calendarEntryProvider(
                backStack = state.backStack,
                scrollState = state.calendarScrollState,
            )
            buddyGroupEntryProvider(
                backStack = state.backStack,
                scrollState = state.buddyGroupScrollState,
            )
            moreEntryProvider(backStack = state.backStack)
            loginEntryProvider(backStack = state.backStack)
        },
        transitionSpec = platformTransitionSpec(),
        popTransitionSpec = platformPopTransitionSpec(),
        predictivePopTransitionSpec = platformPredictivePopTransitionSpec(),
    )
}

private fun Modifier.navigationShortcut(
    state: AppState,
): Modifier {
    return focusable()
        .onPreviewKeyUp { keyEvent ->
            if (!state.isNavigationVisible) {
                return@onPreviewKeyUp false
            }

            when {
                keyEvent.isPlatformMetaPressed() && keyEvent.key == Key.One -> {
                    if (state.currentTopLevelDestination == TopLevelDestination.Memo) {
                        state.memoScrollState.requestScrollToTop()
                    } else {
                        state.navigate(TopLevelDestination.Memo)
                    }
                    true
                }

                keyEvent.isPlatformMetaPressed() && keyEvent.key == Key.Two -> {
                    if (state.currentTopLevelDestination == TopLevelDestination.Tag) {
                        state.tagScrollState.requestScrollToTop()
                    } else {
                        state.navigate(TopLevelDestination.Tag)
                    }
                    true
                }

                keyEvent.isPlatformMetaPressed() && keyEvent.key == Key.Three -> {
                    if (state.currentTopLevelDestination == TopLevelDestination.Calendar) {
                        state.calendarScrollState.requestScrollToTop()
                    } else {
                        state.navigate(TopLevelDestination.Calendar)
                    }
                    true
                }

                keyEvent.isPlatformMetaPressed() && keyEvent.key == Key.Four -> {
                    if (state.currentTopLevelDestination == TopLevelDestination.BuddyGroup) {
                        state.buddyGroupScrollState.requestScrollToTop()
                    } else {
                        state.navigate(TopLevelDestination.BuddyGroup)
                    }
                    true
                }

                keyEvent.isPlatformMetaPressed() && keyEvent.key == Key.Five -> {
                    state.navigate(TopLevelDestination.More)
                    true
                }

                else -> false
            }
        }
}

@Composable
public fun <T : Any> rememberViewModelStoreNavEntryDecorator(
    viewModelStoreOwner: ViewModelStoreOwner = requireNotNull(LocalViewModelStoreOwner.current),
): NavEntryDecorator<T> = remember {
    val viewModelStore = viewModelStoreOwner.viewModelStore

    navEntryDecorator(
        onPop = { key -> viewModelStore.getEntryViewModel().clearViewModelStoreOwnerForKey(key) },
        decorator = { entry ->
            val viewModelStore = viewModelStore.getEntryViewModel().viewModelStoreForKey(entry.contentKey)
            val savedStateRegistryOwner = LocalSavedStateRegistryOwner.current
            val childViewModelStoreOwner = remember {
                object :
                    ViewModelStoreOwner,
                    SavedStateRegistryOwner by savedStateRegistryOwner,
                    HasDefaultViewModelProviderFactory {
                    override val viewModelStore: ViewModelStore
                        get() = viewModelStore

                    override val defaultViewModelProviderFactory: ViewModelProvider.Factory
                        get() = SavedStateViewModelFactory()

                    override val defaultViewModelCreationExtras: CreationExtras
                        get() =
                            MutableCreationExtras().also {
                                it[SAVED_STATE_REGISTRY_OWNER_KEY] = this
                                it[VIEW_MODEL_STORE_OWNER_KEY] = this
                            }

                    init {
                        require(this.lifecycle.currentState == Lifecycle.State.INITIALIZED) {
                            "The Lifecycle state is already beyond INITIALIZED. The " +
                                "ViewModelStoreNavEntryDecorator requires adding the " +
                                "SavedStateNavEntryDecorator to ensure support for " +
                                "SavedStateHandles."
                        }
                    }
                }
            }
            CompositionLocalProvider(LocalViewModelStoreOwner provides childViewModelStoreOwner) {
                entry.Content()
            }
        },
    )
}

private class EntryViewModel : ViewModel() {
    private val owners = mutableMapOf<Any, ViewModelStore>()

    fun viewModelStoreForKey(key: Any): ViewModelStore = owners.getOrPut(key) { ViewModelStore() }

    fun clearViewModelStoreOwnerForKey(key: Any) {
        owners.remove(key)?.clear()
    }

    override fun onCleared() {
        owners.forEach { (_, store) -> store.clear() }
    }
}

private fun ViewModelStore.getEntryViewModel(): EntryViewModel {
    val provider =
        ViewModelProvider.create(
            store = this,
            factory = viewModelFactory { initializer { EntryViewModel() } },
        )
    return provider[EntryViewModel::class]
}
