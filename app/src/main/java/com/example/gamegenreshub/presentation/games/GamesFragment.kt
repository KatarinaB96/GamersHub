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
import com.example.gamegenreshub.domain.models.Games
import com.example.gamegenreshub.presentation.genres.GenresViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GamesFragment : BaseFragment() {
    private lateinit var binding: FragmentGamesBinding
    private var gamesAdapter: GamesAdapter? = null

    private val genresViewModel: GenresViewModel by activityViewModels()
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
    }

    private fun getData() {
        showLoadingIndicator()

        val genreIds: List<Int> = args.genresId.toList()

        genresViewModel.getGamesByGenres(genreIds)
    }

    private fun onSettingsClicked() {
        binding.settingsButton.setOnClickListener {
            val action = GamesFragmentDirections.actionGamesFragmentToSettingsFragment()
            findNavController().navigate(action)
        }
    }

    private fun initAdapter() {
        gamesAdapter = GamesAdapter(requireContext()) { onItemClicked(it) }
        binding.gamesRecycler.layoutManager =
            GridLayoutManager(requireContext(), SPAN_COUNT, GridLayoutManager.VERTICAL, false)
        binding.gamesRecycler.adapter = gamesAdapter
    }

    private fun onItemClicked(game: Games) {
        val action = GamesFragmentDirections.actionGamesFragmentToGameDetailsFragment(game.id)

        findNavController().navigate(action)
    }

    private fun observeData() {
        genresViewModel.gamesObservable.observe(viewLifecycleOwner) {
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