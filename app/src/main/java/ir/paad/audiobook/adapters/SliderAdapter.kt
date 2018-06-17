package ir.paad.audiobook.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ir.paad.audiobook.DetailActivity
import ir.paad.audiobook.R
import ir.paad.audiobook.models.SlideItem
import ir.paad.audiobook.utils.Converter

class SliderAdapter(private val context: Context, private val slides: Array<SlideItem>) : RecyclerView.Adapter<SliderAdapter.SlideHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideHolder {
        val item = LayoutInflater.from(context).inflate(R.layout.item_main_slider, parent, false)
        return SlideHolder(item)
    }

    override fun onBindViewHolder(holder: SlideHolder, position: Int) {

        Glide.with(context)
                .load(slides[position].url)
                .apply(RequestOptions()
                        .fitCenter()).into(holder.image)

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
            val i = Intent(context, DetailActivity::class.java)
            i.putExtra(context.resources.getString(R.string.productId), slides.get(adapterPosition).id)
            context.startActivity(i)
        }


    }

}
