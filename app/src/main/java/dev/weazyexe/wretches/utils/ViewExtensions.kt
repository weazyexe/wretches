package dev.weazyexe.wretches.utils

import android.widget.CheckBox
import android.widget.TextView

fun TextView.performIfChanged(value: String, block: TextView.() -> Unit) {
    if (text != value) {
        block(this)
    }
}

fun CheckBox.performIfChanged(value: Boolean, block: CheckBox.() -> Unit) {
    if (isChecked != value) {
        block(this)
    }
}