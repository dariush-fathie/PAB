package ir.paad.audiobook.fragments.detailFragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.paad.audiobook.R
import ir.paad.audiobook.adapters.CommentAdapter
import ir.paad.audiobook.decoration.VerticalLinearLayoutDecoration
import ir.paad.audiobook.fragments.MyLibraryFragment
import ir.paad.audiobook.interfaces.OnListItemClick
import kotlinx.android.synthetic.main.fragment_detail_comment.*
import me.zhanghai.android.materialratingbar.MaterialRatingBar


class CommentFragment : Fragment(), View.OnClickListener, OnListItemClick {

    override fun onItemClick(host: String, position: Int) {
        if (position == 0) {
            showCommentDialog()
        } else {
            // todo comment clicked show dialog
        }
    }


    override fun onClick(v: View?) {

    }


    private fun showCommentDialog() {
        val view = LayoutInflater.from(activity as Context).inflate(R.layout.dialog_rating, null)
        val et = view.findViewById<AppCompatEditText>(R.id.et_commentBody)
        et.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                et.setLineSpacing(5f, 1.2f)
            }
        }
        val ratingBar = view.findViewById<MaterialRatingBar>(R.id.mbr_commentDialog)
        val alertDialogBuilder = AlertDialog.Builder(activity as Context)
                .setView(view)
                .setPositiveButton("ارسال دیدگاه") { _, _ ->
                    Log.e("comment body", et.text.toString())
                    Log.e("comment rating", "${ratingBar.rating}")
                }.setNegativeButton("نمیخوام") { _, _ ->

                }.setCancelable(true)
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    companion object {
        fun putBundle(bundle: Bundle): MyLibraryFragment {
            val fragment = MyLibraryFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail_comment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_comments.layoutManager = LinearLayoutManager(activity as Context)
        rv_comments.addItemDecoration(VerticalLinearLayoutDecoration(activity as Context,
                16,
                16,
                8,
                16))
        rv_comments.adapter = CommentAdapter(activity as Context, ArrayList()).setOnItemClickListener(this)
        Log.e("sdfds", "sdfsf")

    }

}