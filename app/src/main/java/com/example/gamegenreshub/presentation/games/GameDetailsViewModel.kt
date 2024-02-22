package com.example.gamegenreshub.presentation.games

import com.example.gamegenreshub.base.BaseViewModel
import com.example.gamegenreshub.domain.models.Games
import com.example.gamegenreshub.domain.repository.GameRepository
import com.example.gamegenreshub.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    private val repository: GameRepository
) : BaseViewModel() {

    val gameDetailsObservable: SingleLiveEvent<Games> = SingleLiveEvent()

    fun loadGameDetails(id: Int) {
        performAction({ repository.getGameDetails(id) }, { it?.let { gameDetailsObservable.postValue(it) } })
    }
}