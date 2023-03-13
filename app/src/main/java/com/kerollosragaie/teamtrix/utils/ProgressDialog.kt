package com.kerollosragaie.teamtrix.utils

import android.app.Dialog
import android.content.Context
import android.widget.TextView
import com.kerollosragaie.teamtrix.R

class ProgressDialog(context: Context,text:String="Loading",isCancelable:Boolean=false) {
    private var mProgressDialog: Dialog = Dialog(context, R.style.TEAMTRIX_ProgressDialog)

    init {
        mProgressDialog.setContentView(R.layout.dialog_progress_layout)
        mProgressDialog.setCancelable(isCancelable)
        val textView = mProgressDialog.findViewById<TextView>(R.id.tv_text_dpl)
        textView.text = "$text..."
    }

    fun showDialog(){
        if(!mProgressDialog.isShowing){
            mProgressDialog.show()
        }
    }

    fun dismissDialog(){
        if(mProgressDialog.isShowing){
            mProgressDialog.dismiss()
        }
    }

}