package com.android.pagingapplication

import androidx.paging.PagingSource
import androidx.paging.PagingState

class ItemPagingSource : PagingSource<Int, String>() {
    private val data = List(100) { "Item #$it" } // Simulated data

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
        val page = params.key ?: 0
        val pageSize = params.loadSize
        val start = page * pageSize
        val end = minOf(start + pageSize, data.size)

        return try {
            LoadResult.Page(
                data = data.subList(start, end),
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (end == data.size) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, String>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}