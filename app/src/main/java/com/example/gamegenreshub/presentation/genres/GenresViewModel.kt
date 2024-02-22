package com.example.gamegenreshub.presentation.genres

import androidx.lifecycle.viewModelScope
import com.example.gamegenreshub.base.BaseViewModel
import com.example.gamegenreshub.domain.models.Games
import com.example.gamegenreshub.domain.models.GamesByGenres
import com.example.gamegenreshub.domain.models.GenreDetails
import com.example.gamegenreshub.domain.models.Genres
import com.example.gamegenreshub.domain.repository.GameRepository
import com.example.gamegenreshub.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenresViewModel @Inject constructor(
    private val repository: GameRepository
) : BaseViewModel() {

    val genresObservable: SingleLiveEvent<List<GenreDetails>> = SingleLiveEvent()
    val gamesObservable: SingleLiveEvent<List<Games>> = SingleLiveEvent()
    val navigationEvent: SingleLiveEvent<IntArray> = SingleLiveEvent()

    private var currentGenreIds = emptyList<Int>()

    fun getData(shouldDownloadData: Boolean) {
        if (shouldDownloadData) {
            loadGenres()
        } else {
            loadGenresFromDatabase()
        }
    }

    private fun loadGenres() {
        performAction({ repository.getGenres() }, { onGetGenres(it) })
    }

    private fun loadGenresFromDatabase() {
        performAction({ repository.getGenresFromDatabase() }, { it?.let { genresObservable.postValue(it) } })
    }

    fun saveGenres(genres: List<GenreDetails>) {
        if (genres.isNotEmpty()) {
            viewModelScope.launch {
                repository.insertGenresToDatabase(genres)

                val genresToShow = genres.filter { it.isSelected }.map { it.id }.toIntArray()
                navigationEvent.postValue(genresToShow)
            }
        }
    }

    fun getGamesByGenres(genreIds: List<Int>) {
        if (currentGenreIds == genreIds) {
            gamesObservable.postValue(gamesObservable.value)
        } else {
            currentGenreIds = genreIds
            viewModelScope.launch {
                val result = repository.getGamesByGenreId(genreIds)
                onGetGamesByGenres(GamesByGenres(result.next, result.results))
            }
        }
    }

    private fun onGetGenres(genreDetailsScreenModel: Genres?) {
        val result = genreDetailsScreenModel?.results?.map {
            GenreDetails(
                it.id,
                it.name,
                it.slug,
                it.gamesCount,
                it.imageBackground,
                it.description,
                false
            )
        } ?: emptyList()

        genresObservable.postValue(result)
    }

    private fun onGetGamesByGenres(gamesByGenres: GamesByGenres) {
        val result = gamesByGenres.results.map {
            Games(
                it.id,
                it.name,
                it.slug,
                it.description, it.released, it.backgroundImage,
                it.website,
                it.rating
            )
        }

        gamesObservable.postValue(result)
    }
}