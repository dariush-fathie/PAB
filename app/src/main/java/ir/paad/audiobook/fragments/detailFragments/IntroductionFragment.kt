package ir.paad.audiobook.fragments.detailFragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.paad.audiobook.R
import ir.paad.audiobook.customClass.ResizableCustomView
import ir.paad.audiobook.fragments.MyLibraryFragment
import kotlinx.android.synthetic.main.fragment_detail_introduction.*


class IntroductionFragment : Fragment() {


    companion object {
        fun putBundle(bundle: Bundle): MyLibraryFragment {
            val fragment = MyLibraryFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail_introduction, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ResizableCustomView.doResizeTextView(tv_expandable, 3, "... بیشتر", true)

    }

}