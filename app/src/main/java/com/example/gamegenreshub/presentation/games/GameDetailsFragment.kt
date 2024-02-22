package com.example.gamegenreshub.presentation.games

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.gamegenreshub.R
import com.example.gamegenreshub.base.BaseFragment
import com.example.gamegenreshub.databinding.FragmentGameDetailsBinding

class GameDetailsFragment : BaseFragment() {
    private lateinit var binding: FragmentGameDetailsBinding
    private val args: GameDetailsFragmentArgs by navArgs()
    private val gameDetailsViewModel: GameDetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gameId = args.gameId
        gameDetailsViewModel.loadGameDetails(gameId)
        observeData()
    }

    private fun observeData() {
        binding.apply {
            gameDetailsViewModel.gameDetailsObservable.observe(viewLifecycleOwner) { game ->
                Glide.with(requireParentFragment())
                    .load(game.backgroundImage)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(28)))
                    .into(gameImage)

                val htmlString = getString(R.string.game_website_html, game.website, game.website)

                gameName.text = game.name
                gameDescription.text = HtmlCompat.fromHtml(game.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
                gameRelease.text = getString(R.string.game_was_released, game.released)
                gameWebsite.movementMethod = LinkMovementMethod()
                gameWebsite.text = HtmlCompat.fromHtml(htmlString, HtmlCompat.FROM_HTML_MODE_LEGACY)
            }
        }
    }
}