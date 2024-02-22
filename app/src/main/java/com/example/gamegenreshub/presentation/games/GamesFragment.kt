package com.example.gamegenreshub.presentation.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gamegenreshub.base.BaseFragment
import com.example.gamegenreshub.databinding.FragmentGamesBinding
import com.example.gamegenreshub.domain.models.Game
import com.example.gamegenreshub.pagination.PaginationListener
import com.example.gamegenreshub.presentation.genres.GenresAndGamesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GamesFragment : BaseFragment() {

    private lateinit var binding: FragmentGamesBinding
    private var gamesAdapter: GamesAdapter? = null

    private val genresAndGamesViewModel: GenresAndGamesViewModel by activityViewModels()
    private val args: GamesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGamesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        observeData()
        onSettingsClicked()
        getData()
        setViewModel(genresAndGamesViewModel)
    }

    private fun getData() {
        showLoadingIndicator()

        val genreIds: List<Int> = args.genresId.toList()

        genresAndGamesViewModel.getGamesByGenres(genreIds)
    }

    private fun onSettingsClicked() {
        binding.settingsButton.setOnClickListener {
            val action = GamesFragmentDirections.actionGamesFragmentToSettingsFragment()
            findNavController().navigate(action)
        }
    }

    private fun initAdapter() {
        val layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT, GridLayoutManager.VERTICAL, false)
        gamesAdapter = GamesAdapter(requireContext()) { onItemClicked(it) }
        binding.gamesRecycler.layoutManager = layoutManager
        binding.gamesRecycler.adapter = gamesAdapter
        binding.gamesRecycler.addOnScrollListener(PaginationListener(genresAndGamesViewModel, layoutManager))
    }

    private fun onItemClicked(game: Game) {
        val action = GamesFragmentDirections.actionGamesFragmentToGameDetailsFragment(game.id)

        findNavController().navigate(action)
    }

    private fun observeData() {
        genresAndGamesViewModel.gameObservable.observe(viewLifecycleOwner) {
            hideLoadingIndicator()
            gamesAdapter?.submitList(it)
        }
    }

    private fun showLoadingIndicator() {
        binding.loadingIndicator.visibility = View.VISIBLE
    }

    private fun hideLoadingIndicator() {
        binding.loadingIndicator.visibility = View.GONE
    }

    companion object {
        private const val SPAN_COUNT = 2
    }
}