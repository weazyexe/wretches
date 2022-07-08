package dev.weazyexe.wretches.utils

import android.widget.CheckBox
import android.widget.EditText

/**
 * Обновление значения в [EditText], если оно изменилось
 */
fun EditText.updateIfNeeds(value: String) {
    if (text.toString() != value) {
        setText(value)
        setSelection(value.length - 1)
    }
}

/**
 * Обновление значения в [CheckBox], если оно изменилось
 */
fun CheckBox.updateIfNeeds(value: Boolean) {
    if (isChecked != value) {
        isChecked = value
    }
}