package ir.paad.audiobook.adapters

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.paad.audiobook.R
import ir.paad.audiobook.interfaces.OnListItemClick
import ir.paad.audiobook.models.GenerItem

class GenersAdapter(val context: Context,  val geners: Array<GenerItem>) : RecyclerView.Adapter<GenersAdapter.GenerItemHolder>() {


    private lateinit var clickDispatcherList: OnListItemClick

    fun setOnItemClickListener(i: OnListItemClick) =apply{
        clickDispatcherList = i
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenerItemHolder {
        val item = LayoutInflater.from(context).inflate(R.layout.item_geners, parent, false)
        return GenerItemHolder(item)
    }

    override fun onBindViewHolder(holder: GenerItemHolder, position: Int) {
        holder.title.text = geners.get(position).title
    }


    override fun getItemCount(): Int {
        return geners.size
    }


    inner class GenerItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View?) {

            if (this@GenersAdapter::clickDispatcherList.isInitialized) {
                clickDispatcherList.onItemClick(this@GenersAdapter::class.java.name,adapterPosition)
            }
        }

        val title: AppCompatTextView = itemView.findViewById(R.id.tv_gener)

        init {
            itemView.setOnClickListener(this)
        }
    }

}
