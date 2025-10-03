package io.github.taetae98coding.diary.feature.more.holiday

internal sealed class GoldHolidayUiState {
    data class FetchProgress(
        val percentage: Float = 0F,
    ) : GoldHolidayUiState()

    data class Result(
        val yearMonthUiState: List<GoldHolidayYearMonthUiState>,
    ) : GoldHolidayUiState()

    data object Error : GoldHolidayUiState()
}
