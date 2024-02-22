package com.example.gamegenreshub.presentation.genres

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gamegenreshub.base.BaseFragment
import com.example.gamegenreshub.databinding.FragmentGenresBinding
import com.example.gamegenreshub.domain.models.GenreDetails
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenresFragment : BaseFragment() {

    private lateinit var binding: FragmentGenresBinding
    private val args: GenresFragmentArgs by navArgs()

    private var genresAdapter: GenreAdapter? = null

    private val genresViewModel: GenresViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGenresBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        observeData()
        setClickListeners()
        getData()
    }

    private fun getData() {
        showLoadingIndicator()
        val shouldDownloadData = args.isFromSettings
        genresViewModel.getData(shouldDownloadData)
    }

    private fun setClickListeners() {
        binding.navigateToGamesButton.setOnClickListener {
            val currentList = genresAdapter?.currentList ?: emptyList()
            genresViewModel.saveGenres(currentList)
        }
    }

    private fun showGames(genresToShow: IntArray) {
        val action = if (args.isFromSettings) {
            GenresFragmentDirections.actionGenresFragmentToGamesFragmentFromSettings(genresToShow, true)
        } else {
            GenresFragmentDirections.actionGenresFragmentToGamesFragment(genresToShow, true)
        }
        val navController = findNavController()

        navController.navigate(action)
    }

    private fun initAdapter() {
        genresAdapter = GenreAdapter(requireContext()) { onItemClicked(it) }

        binding.genresRecycler.layoutManager =
            GridLayoutManager(requireContext(), SPAN_COUNT, GridLayoutManager.VERTICAL, false)
        binding.genresRecycler.adapter = genresAdapter
    }

    private fun onItemClicked(genre: GenreDetails) {
        val updatedList = genresAdapter?.currentList?.map { item ->
            if (item.id == genre.id) {
                item.copy(isSelected = item.isSelected.not())
            } else {
                item
            }
        } ?: emptyList()

        binding.navigateToGamesButton.isEnabled = updatedList.any { it.isSelected }

        genresAdapter?.submitList(updatedList)
    }

    private fun observeData() {
        genresViewModel.genresObservable.observe(viewLifecycleOwner) { genres ->
            hideLoadingIndicator()
            if (genres.any { it.isSelected }) {
                val genresToShow = genres.map { it.id }.toIntArray()
                showGames(genresToShow)
            } else {
                genresAdapter?.submitList(genres)
            }
        }

        genresViewModel.navigationEvent.observe(viewLifecycleOwner) {
            showGames(it)
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