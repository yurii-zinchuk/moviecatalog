package com.zinchuk.data

import androidx.paging.PagingSource
import androidx.paging.PagingState

object TestPagingSource {
    fun <T : Any> from(items: List<T>): PagingSource<Int, T> {
        return object : PagingSource<Int, T>() {
            override fun getRefreshKey(state: PagingState<Int, T>): Int? = null
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
                return LoadResult.Page(items, null, null)
            }
        }
    }
}
