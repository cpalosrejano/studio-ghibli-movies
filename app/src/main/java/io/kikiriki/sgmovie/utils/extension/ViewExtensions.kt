package io.kikiriki.sgmovie.utils.extension

import android.text.TextUtils
import android.view.ViewTreeObserver
import android.widget.TextView

fun TextView?.setEllipsizeWithDynamicHeight() {
    if (this == null) return
    val textView = this
    textView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            textView.ellipsize = TextUtils.TruncateAt.END
            textView.maxLines = (textView.height / textView.lineHeight)
            textView.viewTreeObserver.removeOnGlobalLayoutListener(this)
        }
    })
}