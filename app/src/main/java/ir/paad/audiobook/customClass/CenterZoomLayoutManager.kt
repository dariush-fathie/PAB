package ir.paad.audiobook.customClass

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView


class CenterZoomLayoutManager : LinearLayoutManager {

    private var mShrinkAmount = 0.3f
    private var mShrinkDistance = 0.9f

    constructor(context: Context) : super(context) {}

    constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout) {}

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        val orientation = orientation
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
            scaleDownItem()
            return scrolled
        } else {
            return 0
        }

    }

    fun setShrinkAmount(i: Float) {
        mShrinkAmount = i
    }

    fun setShrinkDistance(i: Float) {
        mShrinkDistance = i
    }

    private fun scaleDownItem() {
        val midpoint = width / 2f
        val d0 = 0f
        val d1 = mShrinkDistance * midpoint
        val s0 = 1f
        val s1 = 1f - mShrinkAmount
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val left = getDecoratedLeft(child)
            val right = getDecoratedRight(child)
            /*
            if (left == 0) {
                left = getDecoratedRight(child)
            }
            if (right == 0) {
                right = getDecoratedLeft(child)
            }*/

            val childMidpoint = (left + right) / 2f
            val d = Math.min(d1, Math.abs(midpoint - childMidpoint))
            val scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)
            child.scaleX = scale
            child.scaleY = scale
            child.alpha = scale
        }
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        scaleDownItem()
    }

}