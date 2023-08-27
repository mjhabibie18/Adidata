/*
 * Otten Coffee Android Apps
 * Copyright (c) 2019. All rights reserved.
 * Created by Agus Rianto on 12/26/19 9:39 PM
 * Last modified 12/26/19 9:39 PM by agus
 */

package com.example.adidata.utils

import android.content.Context
import android.os.Build
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun Int.dpToPixel(context: Context): Int {
    return (this * context.resources.displayMetrics.density).toInt()
}

fun convertDpToPixel(context: Context, dp: Int): Int {
    return (dp * context.resources.displayMetrics.density).toInt()
}

fun convertPixelsToDp(context: Context, px: Int): Int {
    return (px / context.resources.displayMetrics.density).toInt()
}

fun EditText.addTextWatcher(): Flowable<String> {
    return Flowable.create<String>({ emiter ->
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                emiter.onNext(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }, BackpressureStrategy.LATEST)
}

fun EditText.addTextWatcher(callback: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            callback.invoke(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    })
}

fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
    val spannableString = SpannableString(this.text)
    for (link in links) {
        val clickableSpan = object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                ds.color = ds.linkColor
                ds.isUnderlineText = false
            }

            override fun onClick(view: View) {
                Selection.setSelection((view as TextView).text as Spannable, 0)
                view.invalidate()
                link.second.onClick(view)
            }
        }
        val startIndexOfLink = this.text.toString().indexOf(link.first)
        spannableString.setSpan(
            clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    this.movementMethod = LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
    this.setText(spannableString, TextView.BufferType.SPANNABLE)
}

fun TextView.setTextHtml(value: String?){
    if (value == null) return
    text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(value, Html.FROM_HTML_MODE_LEGACY)
    }else{
        Html.fromHtml(value)
    }
}

fun RecyclerView.setupLinearLayoutManager(@RecyclerView.Orientation orientation: Int){
    layoutManager = LinearLayoutManager(context, orientation, false)
}

fun RecyclerView.setupVerticalLayoutManager(){
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
}

fun RecyclerView.setupHorizontalLayoutManager(){
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
}


fun RecyclerView.setupGridLayoutManager(column: Int){
    layoutManager = GridLayoutManager(context, column)
}
