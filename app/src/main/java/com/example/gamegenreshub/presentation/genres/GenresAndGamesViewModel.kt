package com.example.gamegenreshub.presentation.genres

import androidx.lifecycle.viewModelScope
import com.example.gamegenreshub.base.BaseViewModel
import com.example.gamegenreshub.domain.models.Game
import com.example.gamegenreshub.domain.models.GamesByGenres
import com.example.gamegenreshub.domain.models.GenreDetails
import com.example.gamegenreshub.domain.models.Genres
import com.example.gamegenreshub.domain.repository.GameRepository
import com.example.gamegenreshub.pagination.PaginationViewModelDelegate
import com.example.gamegenreshub.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GenresAndGamesViewModel @Inject constructor(
    private val repository: GameRepository,
    private val paginationDelegate: PaginationViewModelDelegate
) : BaseViewModel(), PaginationViewModelDelegate by paginationDelegate {

    val genresObservable: SingleLiveEvent<List<GenreDetails>> = SingleLiveEvent()
    val gameObservable: SingleLiveEvent<List<Game>> = SingleLiveEvent()
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

            performAction({ repository.insertGenresToDatabase(genres) }, {
                val genresToShow = genres.filter { it.isSelected }.map { it.id }.toIntArray()
                navigationEvent.postValue(genresToShow)
            })
        }
    }

    fun getGamesByGenres(genreIds: List<Int>) {
        if (currentGenreIds.isEmpty()){
            getGames(genreIds)
        } else if (currentGenreIds == genreIds) {
            gameObservable.postValue(gameObservable.value)
        } else {
            resetPagination()
            getGames(genreIds)
        }
    }

    private fun getGames(genreIds: List<Int>){
        currentGenreIds = genreIds

        performAction({ repository.getGamesByGenreId(genreIds, pageNumber) }, { it?.let { onGetGamesByGenres(GamesByGenres(it.nextPage, it.games)) } })
    }

    override fun loadMoreItems() {
        super.loadMoreItems()

        setPageLoading(true)
        getGames(currentGenreIds)
    }

    private fun updateGamesPage(gamesByGenres: GamesByGenres) {
        if (firstPage) {
            updateUi(gamesByGenres.games)
        } else {
            val updatedItems = gameObservable.value.orEmpty() + gamesByGenres.games
            updateUi(updatedItems)
        }


        setAllItemsLoaded(gamesByGenres.games.size < ITEMS_PER_PAGE)
        setPageNumber(pageNumber + 1)
    }

    private fun updateUi(games: List<Game>) {
        gameObservable.postValue(games)
    }

    private fun onGetGenres(genreDetailsScreenModel: Genres?) {
        genresObservable.postValue(genreDetailsScreenModel?.results.orEmpty())
    }

    private fun onGetGamesByGenres(gamesByGenres: GamesByGenres) {
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                setPageLoading(false)
                setRefreshing(false)
            }
        }

        updateGamesPage(gamesByGenres)
    }

    companion object {
        private const val ITEMS_PER_PAGE = 40
    }
}