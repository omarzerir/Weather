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

class ProviderDialog(context: Activity, private val onProviderDialogDone: OnProviderDialogDone) : Dialog(context) {

    interface OnProviderDialogDone {
        fun onEnableGps()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.view_provider_dialog)
        setCancelable(false)

        val enableTv = findViewById<TextView>(R.id.enable_textView)
        enableTv.setOnClickListener {
            onProviderDialogDone.onEnableGps()
            dismiss()
        }

        val cancelTv = findViewById<TextView>(R.id.cancel_textView)
        cancelTv.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        val dialog = this
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

}