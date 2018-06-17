package ir.paad.audiobook.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.paad.audiobook.ListActivity
import ir.paad.audiobook.R
import ir.paad.audiobook.models.GenerItem

class GenersAdapter(private val context: Context, private val geners: Array<GenerItem>) : RecyclerView.Adapter<GenersAdapter.GenerItemHolder>() {


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
            val i = Intent(context, ListActivity::class.java)
            i.putExtra(context.getString(R.string.queryType), geners.get(adapterPosition).queryId)
            i.putExtra(context.getString(R.string.title), geners.get(adapterPosition).title)
            context.startActivity(i)
        }

        val title: AppCompatTextView = itemView.findViewById(R.id.tv_gener)

        init {
            itemView.setOnClickListener(this)
        }
    }

}
