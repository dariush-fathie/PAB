package ir.paad.audiobook.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.paad.audiobook.R
import ir.paad.audiobook.interfaces.OnListItemClick
import ir.paad.audiobook.models.Comment
import java.util.*

class CommentAdapter(val context: Context, val comments: ArrayList<Comment>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var i = -1
    private lateinit var clickDispatcherList: OnListItemClick

    fun setOnItemClickListener(i: OnListItemClick) = apply {
        clickDispatcherList = i
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var item: View
        item = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false)
        if (viewType == 0) {
            item = LayoutInflater.from(context).inflate(R.layout.simple_button_layout, parent, false)
        }
        return CommentItemHolder(item)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CommentItemHolder) {

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            0
        } else {
            1
        }
    }

    // one more for btn
    override fun getItemCount(): Int {
        return comments.size + 10 + 1
    }

    inner class CommentItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View?) {
            if (this@CommentAdapter::clickDispatcherList.isInitialized) {
                Log.e("Clicked", "$adapterPosition")
                clickDispatcherList.onItemClick(this@CommentAdapter::class.java.name, adapterPosition)
            }

        }

        init {
            itemView.setOnClickListener(this)
        }

    }


    inner class ButtonItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View?) {
            if (this@CommentAdapter::clickDispatcherList.isInitialized) {
                Log.e("Clicked", "$adapterPosition")
                clickDispatcherList.onItemClick(this@CommentAdapter::class.java.name, adapterPosition)
            }

        }

        init {
            itemView.setOnClickListener(this)
        }

    }


    fun notifyNewItemAdded(newBooks: ArrayList<Comment>) {
        comments.addAll(newBooks)
        notifyItemRangeInserted(itemCount - 1, newBooks.size)
    }

}
