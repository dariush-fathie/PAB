package ir.paad.audiobook.adapters

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.paad.audiobook.R
import ir.paad.audiobook.utils.Converter

class SliderAdapter(ctx: Context) : RecyclerView.Adapter<SliderAdapter.SlideHolder>() {

    private val context = ctx

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideHolder {
        val item = LayoutInflater.from(context).inflate(R.layout.item_main_slider, parent, false)
        return SlideHolder(item)
    }

    override fun onBindViewHolder(holder: SlideHolder, position: Int) {

    }


    override fun getItemCount(): Int {
        return 5
    }


    inner class SlideHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val card = itemView.findViewById<CardView>(R.id.cv_slide)
        private val width = Converter.getScreenWidthPx(context)

        init {
            val layoutParams = card.layoutParams
            val width = width - .20 * width
            layoutParams.width = width.toInt()
            card.layoutParams = layoutParams
        }

    }

}
