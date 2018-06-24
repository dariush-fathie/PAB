package ir.paad.audiobook.adapters

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.paad.audiobook.DetailActivity
import ir.paad.audiobook.R
import ir.paad.audiobook.models.BookModel
import java.util.*

class MainAdapter(private val context: Context, private val books: ArrayList<BookModel>) : RecyclerView.Adapter<MainAdapter.MainItemHolder>() {

    private var i = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainItemHolder {
        val item = LayoutInflater.from(context).inflate(R.layout.item_main, parent, false)
        return MainItemHolder(item)
    }


    override fun onBindViewHolder(holder: MainItemHolder, position: Int) {
        if (position > i) {
            setAnimation(holder.itemView, true)
        } else {
            setAnimation(holder.itemView, false)
        }
        i = holder.adapterPosition
    }


    override fun getItemCount(): Int {
        return books.size + 100
    }


    inner class MainItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View?) {
            val i = Intent(context, DetailActivity::class.java)
            i.putExtra(context.getString(R.string.productId), books.get(adapterPosition).id)
            context.startActivity(i)
        }

        init {
            itemView.setOnClickListener(this)
        }

    }


    private fun setAnimation(viewToAnimate: View, scrollToBottom: Boolean) {
        val random = Random()
        var a = ObjectAnimator.ofFloat(viewToAnimate, "translationY", random.nextInt(200).toFloat(), 0f)
        if (!scrollToBottom) {
            a = ObjectAnimator.ofFloat(viewToAnimate, "translationY", -random.nextInt(200).toFloat(), 0f)
        }
        a.duration = Random().nextInt(250).toLong()
        a.start()
    }

    fun notifyNewItemAdded(newBooks: ArrayList<BookModel>) {
        books.addAll(newBooks)
        notifyItemRangeInserted(itemCount - 1, newBooks.size)
    }

}
