package ir.paad.audiobook.decoration

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import ir.paad.audiobook.utils.Converter


class CenterItemDecoration(context: Context, dpPaddingLeft: Int, dpPaddingTop: Int, dpPaddingRight: Int, dpPaddingBottom: Int) : RecyclerView.ItemDecoration() {

    private var paddingLeft = dpPaddingLeft
    private var paddingRight = dpPaddingRight
    private var paddingTop = dpPaddingTop
    private var paddingBottom = dpPaddingBottom
    private var parentWidth = Converter.getScreenWidthPx(context)

    init {
        Log.e("w", "$parentWidth")
        // initialize [padding] with px value
        paddingLeft = Converter.pxFromDp(context, paddingLeft.toFloat()).toInt()
        paddingRight = Converter.pxFromDp(context, paddingRight.toFloat()).toInt()
        paddingTop = Converter.pxFromDp(context, paddingTop.toFloat()).toInt()
        paddingBottom = Converter.pxFromDp(context, paddingBottom.toFloat()).toInt()
    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {

        val itemPosition = (view?.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition
        val itemCount = parent?.adapter?.itemCount

        val viewWidth = view.layoutParams.width
        Log.e("viewWidth", "$viewWidth")

        outRect?.top = paddingTop
        outRect?.bottom = paddingBottom
        outRect?.left = paddingLeft / 2
        outRect?.right = paddingRight / 2

        // first item
        if (itemPosition == 0) {
            outRect?.left = parentWidth/2 - viewWidth/2
        }

        // last item
        if (itemPosition == itemCount?.minus(1)) {
            outRect?.right = parentWidth/2 - viewWidth/2
        }

    }
}
