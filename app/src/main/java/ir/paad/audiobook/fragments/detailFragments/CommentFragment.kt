package ir.paad.audiobook.fragments.detailFragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.paad.audiobook.R
import ir.paad.audiobook.adapters.GridAdapter
import ir.paad.audiobook.fragments.MyLibraryFragment
import kotlinx.android.synthetic.main.fragment_comment.*


class CommentFragment : Fragment() {


    companion object {
        fun putBundle(bundle: Bundle): MyLibraryFragment {
            val fragment = MyLibraryFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_comment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_comments.layoutManager = LinearLayoutManager(activity as Context)
        rv_comments.adapter = GridAdapter(activity as Context)
        Log.e("sdfds", "sdfsf")

    }

}