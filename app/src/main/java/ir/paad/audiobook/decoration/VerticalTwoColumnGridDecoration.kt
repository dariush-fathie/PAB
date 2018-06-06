package ir.paad.audiobook.decoration

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import ir.paad.audiobook.utils.Converter

class VerticalTwoColumnGridDecoration(context: Context, dpPaddingLeft: Int, dpPaddingTop: Int, dpPaddingRight: Int, dpPaddingBottom: Int) : RecyclerView.ItemDecoration() {

    private var paddingLeft = dpPaddingLeft
    private var paddingRight = dpPaddingRight
    private var paddingTop = dpPaddingTop
    private var paddingBottom = dpPaddingBottom

    init {
        // initialize [padding] with px value
        paddingLeft = Converter.pxFromDp(context, paddingLeft.toFloat()).toInt()
        paddingRight = Converter.pxFromDp(context, paddingRight.toFloat()).toInt()
        paddingTop = Converter.pxFromDp(context, paddingTop.toFloat()).toInt()
        paddingBottom = Converter.pxFromDp(context, paddingBottom.toFloat()).toInt()
    }


    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        val itemCount = parent?.adapter?.itemCount
        val itemPosition = (view?.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition

        // return if outRect is null
        outRect ?: return

        outRect.left = paddingLeft
        outRect.right = paddingRight
        outRect.top = paddingTop
        outRect.bottom = paddingBottom

        if (itemPosition != 0 || itemPosition != 1) {

        }

        if (itemPosition % 2 == 0) {
            outRect.right = paddingRight / 2
        } else {
            outRect.left = paddingLeft / 2
        }


        if (itemPosition == itemCount?.minus(1)) {
            outRect.bottom = paddingBottom
        }

    }
}