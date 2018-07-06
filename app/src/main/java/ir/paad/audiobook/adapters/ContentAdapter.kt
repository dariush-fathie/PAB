package ir.paad.audiobook.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.paad.audiobook.R
import ir.paad.audiobook.customClass.MusicIndicator
import ir.paad.audiobook.interfaces.OnListItemClick
import ir.paad.audiobook.models.TrackModel
import ir.paad.audiobook.services.PlayerService
import java.util.*

class ContentAdapter(val context: Context, val tracks: ArrayList<TrackModel>) : RecyclerView.Adapter<ContentAdapter.ContentItemHolder>() {

    private lateinit var clickDispatcherList: OnListItemClick


    private val titles = arrayListOf("نمونه کتاب ", "فصل اول : نبرده پنج سپاه", "فصل دوم : بازگشت پادشاه", "فصل سوم : نابودی اسماک", "فصل چهارم : سفری غیر منتظره", "فصل پنجم : قلمرو ریوندل")

    fun setOnItemClickListener(i: OnListItemClick) = apply {
        clickDispatcherList = i
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentAdapter.ContentItemHolder {
        val item: View = LayoutInflater.from(context).inflate(R.layout.item_track, parent, false)
        return ContentItemHolder(item)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ContentAdapter.ContentItemHolder, position: Int) {
        holder.trackSizeDuration.text = context.resources.getString(R.string.trackSizeDuration, "23MB", "40:25")
        if (position == 0) {
            holder.trackIndicator.setAutoStart(true)
            holder.trackDownload.visibility = View.GONE
        } else {
            holder.trackIndicator.setAutoStart(false)
            holder.trackDownload.visibility = View.VISIBLE
        }
        holder.trackTitle.text = "$position -   ${titles[position]} "
        holder.trackTitle.typeface = ResourcesCompat.getFont(context, R.font.yekan)
    }

    override fun getItemCount(): Int {
        return tracks.size + titles.size
    }

    inner class ContentItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val trackSizeDuration: AppCompatTextView = itemView.findViewById(R.id.tv_trackSizeDuration)
        val trackTitle: AppCompatTextView = itemView.findViewById(R.id.tv_trackTitle)
        val trackDownload: AppCompatImageView = itemView.findViewById(R.id.iv_trackDownload)
        val trackIndicator: MusicIndicator = itemView.findViewById(R.id.iv_trackPlayIndicator)

        override fun onClick(v: View?) {
            /*if (this@ContentAdapter::clickDispatcherList.isInitialized) {
                Log.e("Clicked", "$adapterPosition")
                clickDispatcherList.onItemClick(this@ContentAdapter::class.java.name, adapterPosition)
            }*/


        }

        init {
            itemView.setOnClickListener(this)
        }

    }

    private fun playRequest() {
        val i = Intent(context, PlayerService::class.java)
        i.putExtra("play",true)
        i.putExtra("id", 0)
        i.putExtra("track", 1)
        context.startService(i)
    }

    private fun pauseRequest() {
        val i = Intent(context, PlayerService::class.java)
        i.putExtra("play",false)
        context.startService(i)
    }

    private fun downloadRequest() {

    }


}
