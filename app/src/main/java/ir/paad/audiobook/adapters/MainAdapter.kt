package ir.paad.audiobook.adapters

import android.animation.ObjectAnimator
import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.squareup.picasso.Picasso
import ir.paad.audiobook.R
import ir.paad.audiobook.interfaces.OnListItemClick
import ir.paad.audiobook.models.BookModel
import ir.paad.audiobook.utils.Converter
import java.util.*

class MainAdapter(val context: Context, val books: ArrayList<BookModel>) : RecyclerView.Adapter<MainAdapter.MainItemHolder>() {

    private var i = -1
    private lateinit var clickDispatcherList: OnListItemClick

    private var thumbnailWidthInPx = Converter.pxFromDp(context, 70f).toInt()
    private var thumbnailHeightInPx = Converter.pxFromDp(context, 90f).toInt()

    fun setOnItemClickListener(i: OnListItemClick) = apply {
        clickDispatcherList = i
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainItemHolder {
        val item = LayoutInflater.from(context).inflate(R.layout.item_main, parent, false)
        return MainItemHolder(item)
    }


    override fun onBindViewHolder(holder: MainItemHolder, position: Int) {

        holder.title.text = books[position].name
        holder.author.text = books[position].authorName
        holder.price.text = books[position].price.toString()

        if (position > i) {
            setAnimation(holder.itemView, true)
        } else {
            setAnimation(holder.itemView, false)
        }
        i = holder.adapterPosition

        Picasso.get()
                .load(books[0].imageUrl)
                .resize(thumbnailWidthInPx, thumbnailHeightInPx)
                .centerCrop()
                .into(holder.thumbnail)

    }


    override fun getItemCount(): Int {
        return books.size
    }


    inner class MainItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val p: LinearLayout = itemView.findViewById(R.id.llll)
        val title: AppCompatTextView = itemView.findViewById(R.id.tv_mainTitle)
        val author: AppCompatTextView = itemView.findViewById(R.id.tv_mainAuthor)
        val price: AppCompatTextView = itemView.findViewById(R.id.tv_mainPrice)
        val thumbnail: AppCompatImageView = itemView.findViewById(R.id.iv_mainThumbnail)


        override fun onClick(v: View?) {
            if (this@MainAdapter::clickDispatcherList.isInitialized) {
                Log.e("Clicked", "$adapterPosition")
                clickDispatcherList.onItemClick(this@MainAdapter::class.java.name, adapterPosition)
            }
        }

        init {
            //itemView.setOnClickListener(this)
            p.setOnClickListener(this)
        }

    }


    private fun setAnimation(viewToAnimate: View, scrollToBottom: Boolean) {
        val random = Random()
        var a = ObjectAnimator.ofFloat(viewToAnimate, "translationY", random.nextInt(200).toFloat(), 0f)
        if (!scrollToBottom) {
            a = ObjectAnimator.ofFloat(viewToAnimate, "translationY", -random.nextInt(200).toFloat(), 0f)
        }
        a.duration = Random().nextInt(150).toLong()
        a.start()
    }

    fun notifyNewItemAdded(newBooks: ArrayList<BookModel>) {
        books.addAll(newBooks)
        notifyItemRangeInserted(itemCount - 1, newBooks.size)
    }

}
