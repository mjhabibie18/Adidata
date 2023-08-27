package com.example.adidata.ui.custom

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.example.adidata.R

class DialogProgress(private val ctx : Context) : Dialog(ctx){

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_progress)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    fun showDialog() {
        try {
            val context = ctx as Activity
            if (context.isDestroyed || context.isFinishing || context.window == null) return

            if (context.window?.isActive == true && !isShowing) {
                show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun dismissDialog() {
        try {
            val context = ctx as Activity
            if (context.isDestroyed || context.isFinishing || context.window == null) return
            if (context.window?.isActive == true && isShowing) {
                dismiss()
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}