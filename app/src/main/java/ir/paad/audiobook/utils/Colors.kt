package ir.paad.audiobook.utils

import android.content.Context
import android.support.v4.content.ContextCompat
import ir.paad.audiobook.R

class Colors(context: Context) {
    val colorAccent = ContextCompat.getColor(context , R.color.colorAccent)
    val colorPrimaryDark = ContextCompat.getColor(context , R.color.colorPrimaryDark)
    val colorPrimary = ContextCompat.getColor(context , R.color.colorPrimary)
    val white = ContextCompat.getColor(context , R.color.white)
    val transparent = ContextCompat.getColor(context , R.color.transparent)
}