package com.example.adidata.utils

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.adidata.R

class NavigationView {
    var activity: Activity
    var view: View

    constructor(activity: Activity){
        this.activity = activity
        this.view = activity.window.decorView.rootView
    }

    var navigationBack: ImageView? = null
    var navigationTitle: TextView? = null

    fun setupNavigationWithAction(title: String?, callback: (Any) -> Unit): NavigationView {
        navigationBack = view.findViewById<ImageView>(R.id.navigation_back)?.apply {
            setOnClickListener {
                callback.invoke(it)
            }
        }
        navigationTitle = view.findViewById<TextView>(R.id.navigation_title)?.apply {
            text = title
        }
        return this
    }
}