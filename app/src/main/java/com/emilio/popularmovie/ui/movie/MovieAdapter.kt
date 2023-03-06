package com.emilio.popularmovie.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emilio.popularmovie.R
import com.emilio.popularmovie.common.OnSelectItem
import com.emilio.popularmovie.model.Movie

class MovieAdapter(private val listMovies: MutableList<Movie>): RecyclerView.Adapter<MovieViewHolder>() {
    private var onSelectListener: OnSelectItem? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view, onSelectListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindData(listMovies[position])
    }

    override fun getItemCount(): Int = listMovies.size

     fun setOnSelectListener(onSelectListener: OnSelectItem?) {
          this.onSelectListener = onSelectListener
     }
}