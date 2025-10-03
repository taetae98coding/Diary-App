package io.github.taetae98coding.diary.presenter.calendar.colortext

public sealed class CalendarColorTextEffect {
    public data object None : CalendarColorTextEffect()
    public data object FetchError : CalendarColorTextEffect()
    public data object UnknownError : CalendarColorTextEffect()
}
