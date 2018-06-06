package ir.paad.audiobook.decoration

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import ir.paad.audiobook.utils.Converter

class VerticalThreeColumnGridDecoration(context: Context, dpPaddingLeft: Int, dpPaddingTop: Int, dpPaddingRight: Int, dpPaddingBottom: Int) : RecyclerView.ItemDecoration() {

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
        itemCount ?: return

        outRect.left = paddingLeft
        outRect.right = paddingRight
        outRect.top = paddingTop / 2
        outRect.bottom = paddingBottom / 2

        when {
            itemPosition == 0 || itemPosition == 1 || itemPosition == 2 -> outRect.top = paddingTop
            itemPosition == itemCount.minus(1) ||
                    itemPosition == itemCount.minus(2) ||
                    itemPosition == itemCount.minus(3) -> outRect.bottom = paddingBottom
            itemPosition.plus(1) % 3 == 0 -> {
                outRect.left = paddingLeft / 2
                outRect.right = paddingRight
                return
            }
            itemPosition.plus(1) % 3 == 1 -> {
                outRect.left = paddingLeft
                outRect.right = paddingRight / 2
                return
            }
            itemPosition.plus(1) % 3 == 2 -> {
                outRect.left = paddingLeft / 2
                outRect.right = paddingRight / 2

                return
            }
        }

    }
}