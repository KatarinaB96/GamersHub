package com.example.gamegenreshub.presentation.games

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

import com.example.gamegenreshub.databinding.GameItemBinding
import com.example.gamegenreshub.domain.models.Game

class GamesAdapter(
    private val context: Context,
    val clickListener: (screenModel: Game) -> (Unit)
) :

    ListAdapter<Game, GamesAdapter.GamesViewHolder>(GamesListItemDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder {
        return GamesViewHolder(
            GameItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class GamesViewHolder(private val binding: GameItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setItem(game: Game) {
            binding.apply {
                gameName.text = game.name
                gameRating.rating = game.rating.toFloat()
                Glide.with(context)
                    .load(game.backgroundImage)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(IMAGE_CORNER_RADIUS)))
                    .into(gameImage)

                itemContainer.setOnClickListener {
                    clickListener(game)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) = holder.setItem(getItem(position))

    class GamesListItemDiffUtilCallback : DiffUtil.ItemCallback<Game>() {
        override fun areItemsTheSame(
            oldItem: Game,
            newItem: Game
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Game,
            newItem: Game
        ): Boolean = oldItem == newItem
    }

    companion object {
        private const val IMAGE_CORNER_RADIUS = 28
    }
}