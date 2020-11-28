package com.zerir.weather.utilities.dialogs

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import com.zerir.weather.R

class ErrorDialog(context: Activity, private val onErrorDialogDone: OnErrorDialogDone) : Dialog(context) {

    interface OnErrorDialogDone {
        fun onErrorDone()
    }

    private lateinit var messageTv: TextView
    private lateinit var cancelTv: TextView

    private var message = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.view_error_dialog)
        setCancelable(false)

        messageTv = findViewById(R.id.message_tv)
        messageTv.text = message

        cancelTv = findViewById(R.id.cancel_textView)
        cancelTv.setOnClickListener {
            onErrorDialogDone.onErrorDone()
            dismiss()
        }
    }

    fun show(message: String?) {
        message?.let {
            this.message = it
        }
        super.show()
    }

    override fun dismiss() {
        message = ""
        super.dismiss()
    }

    override fun onStart() {
        super.onStart()
        val dialog = this
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

}