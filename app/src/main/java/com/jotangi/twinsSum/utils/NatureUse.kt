package com.jotangi.twinsSum.utils

import android.view.View
import android.widget.EditText
import android.widget.TextView


/**
 * Log
 */
fun String.title() = "${"＊".repeat(15)}  $this  ${"＊".repeat(15)}"

/**
 * View
 */
fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

/**
 * TextView
 */
fun TextView.txt() = this.text.toString()

/**
 * EditText
 */
fun EditText.txt() = this.text.toString()

fun List<EditText>.checkBlank(): Boolean {

    this.forEach { if (it.txt().isBlank()) return true }

    return false
}