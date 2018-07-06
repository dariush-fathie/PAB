package ir.paad.audiobook.adapters

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import ir.paad.audiobook.R
import ir.paad.audiobook.interfaces.OnListItemClick
import ir.paad.audiobook.models.SlideItem
import ir.paad.audiobook.utils.Converter

class SliderAdapter(val context: Context, val slides: Array<SlideItem>) : RecyclerView.Adapter<SliderAdapter.SlideHolder>() {


    private lateinit var clickDispatcherList: OnListItemClick

    private var slideItemWidthInPx = 0
    private var slideItemHeightInPx = 0


    init {
        slideItemHeightInPx = Converter.pxFromDp(context, 180f).toInt()
        slideItemWidthInPx = Converter.getScreenWidthPx(context)
    }

    fun setOnItemClickListener(i: OnListItemClick) = apply {
        clickDispatcherList = i
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideHolder {
        val item = LayoutInflater.from(context).inflate(R.layout.item_main_slider, parent, false)
        return SlideHolder(item)
    }

    override fun onBindViewHolder(holder: SlideHolder, position: Int) {

        Picasso.get()
                .load(slides[position].url)
                .resize(slideItemWidthInPx, slideItemHeightInPx)
                .centerCrop()
                .into(holder.image)

    }


    override fun getItemCount(): Int {
        return slides.size
    }


    inner class SlideHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val card = itemView.findViewById<CardView>(R.id.cv_slide)
        private val width = Converter.getScreenWidthPx(context)

        val title: AppCompatTextView = itemView.findViewById(R.id.tv_sliderTitle)
        val image: AppCompatImageView = itemView.findViewById(R.id.iv_slideImage)

        init {
            val layoutParams = card.layoutParams
            val width = width - .20 * width
            layoutParams.width = width.toInt()
            card.layoutParams = layoutParams

            itemView.setOnClickListener(this)

        }


        override fun onClick(v: View?) {
            if (this@SliderAdapter::clickDispatcherList.isInitialized) {
                clickDispatcherList.onItemClick(this@SliderAdapter::class.java.name, adapterPosition)
            }
        }


    }

}
