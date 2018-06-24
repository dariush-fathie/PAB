package ir.paad.audiobook.adapters

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.paad.audiobook.R
import ir.paad.audiobook.interfaces.OnItemClick


class SimpleAdapter(private val context: Context, private val titles: ArrayList<String>) : RecyclerView.Adapter<SimpleAdapter.SimpleItemHolder>() {

    var clickDispatcher:OnItemClick? = null

    fun setOnItemClickListener(clickDispatcher:OnItemClick){
        this.clickDispatcher = clickDispatcher
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleItemHolder {
        val item = LayoutInflater.from(context).inflate(R.layout.simple_text_item, parent, false)
        return SimpleItemHolder(item)
    }

    override fun onBindViewHolder(holder: SimpleItemHolder, position: Int) {
        holder.title.text = titles[position]
    }


    override fun getItemCount(): Int {
        return titles.size
    }


    inner class SimpleItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View?) {
            clickDispatcher?.onClick(adapterPosition)
        }
        val title: AppCompatTextView = itemView.findViewById(R.id.tv_simple)
        init {
            itemView.setOnClickListener(this)
        }


    }

}
