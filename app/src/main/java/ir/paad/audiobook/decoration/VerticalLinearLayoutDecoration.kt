package ir.paad.audiobook.decoration

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import ir.paad.audiobook.utils.Converter


class VerticalLinearLayoutDecoration(context: Context, dpPaddingLeft: Int, dpPaddingTop: Int, dpPaddingRight: Int, dpPaddingBottom: Int) : RecyclerView.ItemDecoration() {

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
        val itemPosition = (view?.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition
        val itemCount = parent?.adapter?.itemCount

        outRect?.top = paddingTop / 2
        outRect?.bottom = paddingBottom / 2
        outRect?.left = paddingLeft
        outRect?.right = paddingRight

        // first item
        if (itemPosition == 0) {
            outRect?.top = paddingTop
        }

        // last item
        if (itemPosition == itemCount?.minus(1)) {
            outRect?.bottom = paddingBottom
        }

    }
}
