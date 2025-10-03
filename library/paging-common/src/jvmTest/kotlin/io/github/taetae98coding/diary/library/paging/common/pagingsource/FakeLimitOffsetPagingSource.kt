package io.github.taetae98coding.diary.library.paging.common.pagingsource

class FakeLimitOffsetPagingSource(
    private val totalCount: Int,
    initialOffset: Int = 0,
) : LimitOffsetPagingSource<Int>(initialOffset) {
    override suspend fun paging(limit: Int, offset: Int): Page<Int> {
        val start = offset
        val endInclusive = (offset + limit).coerceAtMost(totalCount)

        return Page(
            count = totalCount,
            data = (start..endInclusive).toList(),
        )
    }
}
