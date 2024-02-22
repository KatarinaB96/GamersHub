package com.example.gamegenreshub.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamegenreshub.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel : ViewModel() {

    val errorObservable: SingleLiveEvent<String> = SingleLiveEvent()

    fun <R> performAction(
        action: suspend () -> R,
        doOnSuccess: (responseData: R?) -> Unit = {}
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = action.invoke()

                    doOnSuccess.invoke(response)

                } catch (e: Exception) {
                    errorObservable.postValue(e.message)
                }
            }
        }
    }
}