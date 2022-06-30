package dev.weazyexe.wretches.utils

import android.widget.CheckBox
import android.widget.EditText

fun EditText.updateIfNeeds(value: String) {
    if (text.toString() != value) {
        setText(value)
        setSelection(value.length - 1)
    }
}

fun CheckBox.updateIfNeeds(value: Boolean) {
    if (isChecked != value) {
        isChecked = value
    }
}