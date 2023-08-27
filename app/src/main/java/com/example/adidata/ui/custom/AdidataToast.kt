package com.example.adidata.ui.custom

import android.app.Activity
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.adidata.R
import com.example.adidata.utils.convertDpToPixel
import com.example.adidata.utils.gone
import com.example.adidata.utils.setTextHtml
import com.example.adidata.utils.visible

class AdidataToast(private val context: Activity?) {
    private val popupWindow: PopupWindow?
    private val tvMessage: TextView
    private val ivClose: ImageView
    private val toastLayout: RelativeLayout
    private val toolbar: View?

    init {
        val popupView: View =
            LayoutInflater.from(context).inflate(R.layout.custom_adidata_toast, null)
        tvMessage = popupView.findViewById(R.id.tv_toast_message)
        ivClose = popupView.findViewById(R.id.iv_toast_button)
        toastLayout = popupView.findViewById(R.id.toast_layout)
        toolbar = context?.window?.decorView?.rootView?.findViewById(R.id.nav_bar_layout)

        ivClose.setOnClickListener {
            dismiss()
        }
        popupWindow = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            true
        )
        popupWindow.isFocusable = false
        popupWindow.isOutsideTouchable = false

    }

    companion object {

        const val LENGTH_SHORT = 3000L
        const val LENGTH_LONG = 5000L

        fun make(activity: Activity, message: String, duration: Long) : AdidataToast{
            return AdidataToast(activity).setMessage(message).setDismiss(duration)
        }
    }


    fun showToastInfo(message: String?){
        if (message == null) return
        setDismiss(LENGTH_SHORT)
        showInfo(message)
    }

    fun showToastInfo(message: String?, callback: Callback?){
        if (message == null) return
        setDismiss(LENGTH_SHORT, callback).showInfo(message)
    }

    fun showToastInfo(message: String?, duration :Long, callback: Callback? = null){
        if (message == null) return
        setDismiss(duration, callback).showInfo(message)
    }

    fun showToastDanger(message: String?){
        if (message == null) return
        showDanger(message)
    }

    fun showToastDanger(message: String?, duration :Long, callback: Callback? = null){
        if (message == null) return
        setDismiss(duration, callback).showDanger(message)
    }

    fun showDanger(message: String?) {
        message?.let {
            setDismiss(0)
            tvMessage.setTextHtml(message)
            ivClose.visible()
            toastLayout.setBackgroundResource(R.drawable.toast_danger)
            context?.runOnUiThread {
                show()
            }
        }
    }

    fun showInfo(message: String?) {
        message?.let {
            tvMessage.text = message
            ivClose.gone()
            toastLayout.setBackgroundResource(R.drawable.toast_coal)
            context?.runOnUiThread {
                show()
            }
        }
    }

    fun setDismiss(duration: Long) : AdidataToast{
        setDismiss(duration, null)
        return this
    }

    val handler = Handler()
    fun setDismiss(duration: Long, callback: Callback?): AdidataToast {
        if (duration > 0) {
            handler.removeCallbacksAndMessages(null)
            handler.postDelayed({
                dismiss()
                callback?.onCompleted()
            }, duration)
        }
        return this
    }

    fun setMessage(message: String): AdidataToast {
        tvMessage.text = message
        return this
    }

    fun show() {
        try {
            val context = context ?: return
            if (context.isDestroyed || context.isFinishing || context.window == null || context.windowManager == null) return
            if (context.window?.isActive == true && popupWindow != null) {
                if (popupWindow.isShowing) {
                    dismiss()
                }
                if (toolbar != null) {
                    popupWindow.showAsDropDown(toolbar, 0, 0)
                } else {
                    popupWindow.showAtLocation(
                        context.window.decorView.rootView, Gravity.TOP, 0,
                        convertDpToPixel(
                            context,
                            76
                        )
                    )
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun dismiss() {
        try {
            if (popupWindow != null) {
                if (popupWindow.isShowing) {
                    popupWindow.dismiss()
                }
            }
        }catch (ignored: Exception){
        }
    }

    interface Callback {
        fun onCompleted()
    }
}

