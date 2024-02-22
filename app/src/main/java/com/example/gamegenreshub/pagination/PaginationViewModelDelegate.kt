package com.example.gamegenreshub.pagination

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface PaginationViewModelDelegate {
    val allItemsLoaded: Boolean
    val pageLoading: LiveData<Boolean>
    val refreshing: LiveData<Boolean>
    val pageNumber: Int
    val firstPage: Boolean

    fun resetPagination()
    fun setRefreshing(loading: Boolean)
    fun setPageNumber(pageNumber: Int)
    fun setPageLoading(loading: Boolean)
    fun setAllItemsLoaded(allItemsLoaded: Boolean)

    fun loadMoreItems() {
        (pageLoading as MutableLiveData).value = true
    }
}

class PaginationViewModelDelegateImpl : PaginationViewModelDelegate {
    private var allItemsLoadComplete = false
    private var pageIndex = 1

    override val allItemsLoaded: Boolean
        get() = allItemsLoadComplete

    override val pageLoading: LiveData<Boolean> = MutableLiveData()
    override val refreshing: LiveData<Boolean> = MutableLiveData()

    override val pageNumber: Int
        get() = pageIndex

    override val firstPage: Boolean
        get() = pageNumber == 1

    override fun resetPagination() {
        allItemsLoadComplete = false
        pageIndex = 1
        (pageLoading as MutableLiveData).value = false
    }

    override fun setPageNumber(pageNumber: Int) {
        pageIndex = pageNumber
    }

    override fun setPageLoading(loading: Boolean) {
        if (loading) {
            if (firstPage) {
                setRefreshing(true)
            } else {
                (pageLoading as MutableLiveData).value = loading
            }
        } else {
            (pageLoading as MutableLiveData).value = loading
        }
    }

    override fun setRefreshing(loading: Boolean) {
        (refreshing as MutableLiveData).value = loading
    }

    override fun setAllItemsLoaded(allItemsLoaded: Boolean) {
        allItemsLoadComplete = allItemsLoaded
    }
}
