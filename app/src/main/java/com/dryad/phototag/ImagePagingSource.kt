package com.dryad.phototag

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.delay
import kotlin.math.max

private const val STARTING_KEY = 0

class ImagePagingSource(private val dao: DataBaseDao): PagingSource<Int, ItemData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemData> {
        val page = params.key ?: STARTING_KEY
        val range = page.until(page + params.loadSize)

        return try {
            val entities = dao.getPagedItem(params.loadSize, page * params.loadSize)

            // simulate page loading
            if (page != 0) delay(1000)

            LoadResult.Page(
                data = entities,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (entities.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ItemData>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val item = state.closestItemToPosition(anchorPosition) ?: return null
        return ensureValidKey(key = item.id - (state.config.pageSize / 2))
    }

    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)
}