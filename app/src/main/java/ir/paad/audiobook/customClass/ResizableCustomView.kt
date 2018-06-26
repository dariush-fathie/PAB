package ir.paad.audiobook.customClass


import android.support.v7.widget.AppCompatTextView
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView

/**
 * Created by user on 5/2/2016.
 */
object ResizableCustomView {
    fun doResizeTextView(tv: AppCompatTextView, maxLine: Int, expandText: String, viewMore: Boolean) {

        if (tv.tag == null) {
            tv.tag = tv.text
        }
        val vto = tv.viewTreeObserver
        vto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val obs = tv.viewTreeObserver
                obs.removeOnGlobalLayoutListener(this)
                if (maxLine == 0) {
                    val lineEndIndex = tv.layout.getLineEnd(0)
                    val text = tv.text.subSequence(0, lineEndIndex - expandText.length + 1).toString() + " " + expandText
                    tv.text = text
                    tv.movementMethod = LinkMovementMethod.getInstance()
                    tv.setText(
                            addClickablePartTextViewResizable(SpannableStringBuilder(tv.text.toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE)
                } else if (maxLine > 0 && tv.lineCount >= maxLine) {
                    val lineEndIndex = tv.layout.getLineEnd(maxLine - 1)
                    val text = tv.text.subSequence(0, lineEndIndex - expandText.length + 1).toString() + " " + expandText
                    tv.text = text
                    tv.movementMethod = LinkMovementMethod.getInstance()
                    tv.setText(
                            addClickablePartTextViewResizable(SpannableStringBuilder(tv.text.toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE)
                } else {
                    val lineEndIndex = tv.layout.getLineEnd(tv.layout.lineCount - 1)
                    val text = tv.text.subSequence(0, lineEndIndex).toString() + " " + expandText
                    tv.text = text
                    tv.movementMethod = LinkMovementMethod.getInstance()
                    tv.setText(
                            addClickablePartTextViewResizable(SpannableStringBuilder(tv.text.toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE)
                }
            }
        })

    }

    private fun addClickablePartTextViewResizable(strSpanned: Spanned,
                                                  tv: AppCompatTextView,
                                                  maxLine: Int,
                                                  spanableText: String,
                                                  viewMore: Boolean): SpannableStringBuilder {
        val str = strSpanned.toString()
        val ssb = SpannableStringBuilder(strSpanned)
        if (str.contains(spanableText)) {
            ssb.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    if (viewMore) {
                        tv.layoutParams = tv.layoutParams
                        tv.setText(tv.tag.toString(), TextView.BufferType.SPANNABLE)
                        tv.invalidate()
                        doResizeTextView(tv, -1, " کمتر", false)
                    } else {
                        tv.layoutParams = tv.layoutParams
                        tv.setText(tv.tag.toString(), TextView.BufferType.SPANNABLE)
                        tv.invalidate()
                        doResizeTextView(tv, 3, "... بیشتر", true)
                    }

                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length, 0)

        }
        return ssb

    }
}