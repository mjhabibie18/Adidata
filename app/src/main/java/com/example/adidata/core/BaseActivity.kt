package com.example.adidata.core

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.adidata.ui.custom.AdidataToast
import com.example.adidata.ui.custom.DialogProgress

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

    /**
     * This variable is used for showing toast error internet, 500, etc
     */
    protected var adidataToast: AdidataToast? = null

    /**
     * This variable is used for binding the view
     */
    protected lateinit var binding: B

    /**
     * This function is used for set the view layout
     */
    @LayoutRes
    protected abstract fun getResLayoutId(): Int

    private var progressDialog: DialogProgress? = null

    /**
     * This function is used for set the action when the activity was created
     */
    protected abstract fun onActivityCreated(savedInstanceState: Bundle?)

    /**
     * This function is used for init all function when activity is created
     * There're have function for binding the view with data binding
     * There're also have function for listen the connection state change
     * and have function to observe the error state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<B>(this, getResLayoutId())
            .apply {
                lifecycleOwner = this@BaseActivity
            }

        adidataToast = AdidataToast(this)
        progressDialog = DialogProgress(this)

        onActivityCreated(savedInstanceState)

    }

    fun showToastInfo(message: String?) {
        if (message.isNullOrEmpty()) return
        adidataToast?.setDismiss(AdidataToast.LENGTH_SHORT)?.showInfo(message)
    }

    fun showToastInfo(message: String?, callback: AdidataToast.Callback?) {
        if (message.isNullOrEmpty()) return
        adidataToast?.setDismiss(AdidataToast.LENGTH_SHORT, callback)?.showInfo(message)
    }

    fun showToastInfo(message: String?, duration: Long, callback: AdidataToast.Callback?) {
        if (message.isNullOrEmpty()) return
        adidataToast?.setDismiss(duration, callback)?.showInfo(message)
    }

    fun showToastDanger(message: String?) {
        if (message.isNullOrEmpty()) return
        adidataToast?.showDanger(message)
    }

    fun showToastDanger(message: String?, duration: Long, callback: AdidataToast.Callback?) {
        if (message.isNullOrEmpty()) return
        adidataToast?.setDismiss(duration, callback)?.showDanger(message)
    }

    fun hideToast() {
        try {
            adidataToast?.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onSetMessage(info: String? = null, error : String? = null) {
        info?.let {
            showToastInfo(info)
        }
        error?.let {
            showToastDanger(error)
        }

    }

    fun onSetProgress(isShow : Boolean){
        if (isShow) showProgress()
        else hideProgress()
    }

    fun showProgress(): DialogProgress? {
        try {
            progressDialog?.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return progressDialog
    }

    fun hideProgress() {
        try {
            progressDialog?.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * This is used for unregister the listener form network change when activity is destroyed
     */
    override fun onDestroy() {
        try {
            adidataToast?.dismiss()
            progressDialog?.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.onDestroy()
    }

}