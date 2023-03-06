package com.emilio.popularmovie.ui.movie

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.emilio.popularmovie.R
import com.emilio.popularmovie.common.OnSelectItem
import com.emilio.popularmovie.common.TypeClick
import com.emilio.popularmovie.model.Movie
import com.squareup.picasso.Picasso

class MovieViewHolder(private val itemVView: View, private val onSelectListener: OnSelectItem?): ViewHolder(itemVView) {
    private val movieName: AppCompatTextView? = itemVView.findViewById(R.id.movieName)
    private val movieImage: AppCompatImageView? = itemVView.findViewById(R.id.movieImage)
    private val favorite: AppCompatImageView? = itemVView.findViewById(R.id.addFavorite)


    fun bindData(movie: Movie) {
        movieName?.text = movie.original_title

        Picasso.get()
            .load("https://image.tmdb.org/t/p/original/${movie.poster_path}")
            .fit()
            .into(movieImage)

        itemVView.setOnClickListener { view ->
            onSelectListener?.onSelect(view, absoluteAdapterPosition, null)
        }

        favorite?.setOnClickListener {
            onSelectListener?.onSelect(it, absoluteAdapterPosition, TypeClick.FAVORITE)
        }
    }
}