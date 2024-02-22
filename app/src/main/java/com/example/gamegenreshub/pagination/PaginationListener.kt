package com.example.gamegenreshub.pagination

import androidx.recyclerview.widget.LinearLayoutManager

class PaginationListener(
    private val paginationDelegate: PaginationViewModelDelegate,
    layoutManager: LinearLayoutManager
) : PaginationScrollListener(layoutManager = layoutManager) {
    override fun isLastPage(): Boolean {
        return paginationDelegate.allItemsLoaded
    }

    override fun isLoading(): Boolean {
        return paginationDelegate.pageLoading.value ?: false
    }

    override fun loadMoreItems() {
        paginationDelegate.loadMoreItems()
    }
}
