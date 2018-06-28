package ir.paad.audiobook.customClass

import android.content.Context
import android.support.design.widget.TabLayout
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.LayoutInflater
import ir.paad.audiobook.R
import ir.paad.audiobook.utils.Colors

class CustomCardTab : TabLayout {

    private var flag = true

    private val mContext: Context

    private var pDark: Int = 0
    private var transparent = 0
    private var semiWhite = 0


    private fun init(context: Context) {
        pDark = Colors(context).colorPrimaryDark
        transparent = Colors(context).transparent
        semiWhite = Colors(context).background2Transparent
    }

    constructor(context: Context) : super(context) {
        mContext = context
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mContext = context
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mContext = context
        init(context)
    }


    override fun addTab(tab: Tab, position: Int, selected: Boolean) {
        val view = LayoutInflater.from(mContext).inflate(R.layout.custom_tab_item_2, this, false)
        val textView = view.findViewById<AppCompatTextView>(R.id.tv_customTabTitle)
        val cardView = view.findViewById<CardView>(R.id.cv_customTabContainer)
        textView.text = tab.text
        tab.customView = view

        if (selected) {
            flag = false
            textView.setTextColor(pDark)
            textView.textSize = 14f
            textView.setBackgroundResource(R.drawable.empty)
        } else {
            textView.textSize = 13f
            tab.customView?.scaleY = 0.9f
            tab.customView?.scaleX = 0.9f
            cardView.cardElevation = 0f
            cardView.setCardBackgroundColor(transparent)
            textView.setTextColor(semiWhite)
            textView.setBackgroundResource(R.drawable.dot_rect_back)
        }

        super.addTab(tab, position, selected)
    }


}