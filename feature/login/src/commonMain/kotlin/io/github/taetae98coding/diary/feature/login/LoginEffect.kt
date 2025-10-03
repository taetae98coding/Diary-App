package io.github.taetae98coding.diary.feature.login

internal sealed class LoginEffect {
    data object None : LoginEffect()

    data object Error : LoginEffect()
}
