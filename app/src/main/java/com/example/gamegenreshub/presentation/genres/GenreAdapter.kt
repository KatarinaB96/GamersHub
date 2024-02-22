package com.example.gamegenreshub.presentation.genres

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.gamegenreshub.R
import com.example.gamegenreshub.databinding.GenreItemBinding
import com.example.gamegenreshub.domain.models.GenreDetails

class GenreAdapter(
    private val context: Context,
    val clickListener: (genre: GenreDetails) -> Unit,
) : ListAdapter<GenreDetails, GenreAdapter.GenreViewHolder>(GenresListItemDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(
            GenreItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class GenreViewHolder(private val binding: GenreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setItem(genre: GenreDetails) {
            binding.genreName.text = genre.name

            Glide.with(context)
                .load(genre.imageBackground)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(28)))
                .into(binding.genreImage)

            binding.genreName.background =
                ContextCompat.getDrawable(context, if (genre.isSelected) R.drawable.rounded_corners_text_picked else R.drawable.rounded_corners_text)
            binding.genreName.setTextColor(if (genre.isSelected) Color.BLACK else Color.WHITE)

            binding.itemContainer.setOnClickListener { clickListener.invoke(genre) }
        }
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.setItem(getItem(position))
    }

    class GenresListItemDiffUtilCallback : DiffUtil.ItemCallback<GenreDetails>() {
        override fun areItemsTheSame(
            oldItem: GenreDetails,
            newItem: GenreDetails
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: GenreDetails,
            newItem: GenreDetails
        ): Boolean = oldItem == newItem
    }
}