package io.kikiriki.sgmovie.model

import androidx.annotation.StringRes
import io.kikiriki.sgmovie.R

class Sort private constructor(
    val type: Type,
    @StringRes val title: Int
) {

    enum class Type {
        LIKE, NAME, SCORE, DIRECTOR, YEAR, LIKE_COUNT
    }
        
    companion object {
        val LIKE = Sort(Type.LIKE, R.string.dialog_sort_by_lbl_like)
        val NAME = Sort(Type.NAME, R.string.dialog_sort_by_lbl_name)
        val SCORE = Sort(Type.SCORE, R.string.dialog_sort_by_lbl_score)
        val DIRECTOR = Sort(Type.DIRECTOR, R.string.dialog_sort_by_lbl_director)
        val YEAR = Sort(Type.YEAR, R.string.dialog_sort_by_lbl_year)
        val LIKE_COUNT = Sort(Type.LIKE_COUNT, R.string.dialog_sort_by_lbl_total_likes)

        fun getAll() : List<Sort> {
            return listOf(NAME, LIKE, SCORE, DIRECTOR, YEAR, LIKE_COUNT)
        }

    }

}