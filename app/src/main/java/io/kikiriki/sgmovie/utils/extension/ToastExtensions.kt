package io.kikiriki.sgmovie.utils.extension

import android.app.Activity
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

fun Activity?.toast(@StringRes stringRes: Int?) {
    if (stringRes == null || this == null) return
    Toast.makeText(this, stringRes, Toast.LENGTH_LONG).show()
}

fun Activity?.shortToast(@StringRes stringRes: Int?) {
    if (stringRes == null || this == null) return
    Toast.makeText(this, stringRes, Toast.LENGTH_SHORT).show()
}