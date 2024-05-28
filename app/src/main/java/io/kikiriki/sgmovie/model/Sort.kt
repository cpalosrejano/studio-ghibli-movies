package io.kikiriki.sgmovie.model

import androidx.annotation.StringRes
import io.kikiriki.sgmovie.R

class Sort private constructor(
    val type: Type,
    @StringRes val title: Int
) {

    enum class Type {
        FAVOURITE, NAME, SCORE, DIRECTOR, YEAR
    }
        
    companion object {
        val FAVOURITES = Sort(Type.FAVOURITE, R.string.dialog_sort_by_lbl_favourites)
        val NAME = Sort(Type.NAME, R.string.dialog_sort_by_lbl_name)
        val SCORE = Sort(Type.SCORE, R.string.dialog_sort_by_lbl_score)
        val DIRECTOR = Sort(Type.DIRECTOR, R.string.dialog_sort_by_lbl_director)
        val YEAR = Sort(Type.YEAR, R.string.dialog_sort_by_lbl_year)

        fun getAll() : List<Sort> {
            return listOf(NAME, FAVOURITES, SCORE, DIRECTOR, YEAR)
        }

    }

}