package ir.paad.audiobook.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatTextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import ir.paad.audiobook.MainActivity
import ir.paad.audiobook.R
import kotlinx.android.synthetic.main.login_layout.*


class LoginFragment : Fragment(), View.OnClickListener {

    override fun onClick(p0: View?) {
        when (p0?.id) {

            R.id.act_login -> {

                login()
            }

            R.id.act_login_wg -> {

                Toast.makeText(activity, "act_login_wg", Toast.LENGTH_LONG).show()
            }

            R.id.act_register -> {
                register_alertDialog()
            }
            R.id.act_recovery -> {
                recovery_alertDialog()

            }
            R.id.act_register_alert -> {

                Toast.makeText(activity, "act_register_alert", Toast.LENGTH_LONG).show()
            }
            R.id.act_recovery_alert -> {

                Toast.makeText(activity, "act_recovery_alert", Toast.LENGTH_LONG).show()
            }
        }
    }

    var buffer: ArrayList<String> = ArrayList()

    companion object {
        fun putBundle(bundle: Bundle): LoginFragment {
            val fragment = LoginFragment()
            fragment.arguments = bundle
            return fragment
        }
    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login_layout, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).hideLoadLayout()

        act_recovery.setOnClickListener(this)
        act_register.setOnClickListener(this)
        act_login.setOnClickListener(this)
        act_login_wg.setOnClickListener(this)
        fillBuffer()

    }

    private fun register_alertDialog() {
        val tv_costom_title: TextView
        val et_user: AppCompatEditText
        val et_pass: AppCompatEditText
        val et_alias: AppCompatEditText
        val act_register_alert: AppCompatTextView
        val view1: View

        view1 = LayoutInflater.from(context).inflate(R.layout.register_layout, null, false)

        act_register_alert = view1.findViewById(R.id.act_register_alert)
        act_register_alert.setOnClickListener(this)

        et_user = view1.findViewById(R.id.et_user)
        et_pass = view1.findViewById(R.id.et_pass)
        et_alias = view1.findViewById(R.id.et_alias)

        val view2 = LayoutInflater.from(context).inflate(R.layout.profile_dialog_title, null, false)
        tv_costom_title = view2.findViewById(R.id.tv_customTitle)
        tv_costom_title.setText(buffer.get(0))

        val builder = AlertDialog.Builder(activity!!)
        builder.setCustomTitle(view2)
        builder.setView(view1)
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun recovery_alertDialog() {
        val tv_costom_title: TextView
        val et_user: AppCompatEditText
        val act_recovery_alert: AppCompatTextView
        val view1: View

        view1 = LayoutInflater.from(context).inflate(R.layout.recovery_layout, null, false)

        act_recovery_alert = view1.findViewById(R.id.act_recovery_alert)
        act_recovery_alert.setOnClickListener(this)

        et_user = view1.findViewById(R.id.et_user)

        val view2 = LayoutInflater.from(context).inflate(R.layout.profile_dialog_title, null, false)
        tv_costom_title = view2.findViewById(R.id.tv_customTitle)
        tv_costom_title.setText(buffer.get(1))

        val builder = AlertDialog.Builder(activity!!)
        builder.setCustomTitle(view2)
        builder.setView(view1)
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun fillBuffer() {
        buffer.add("ثبت نام")
        buffer.add("بازیابی")
    }


    private fun login() {

        val i = Intent()
        i.action = "ir.paad.audiobook.fragments.log_pro"
        activity?.sendBroadcast(i)
    }
}
