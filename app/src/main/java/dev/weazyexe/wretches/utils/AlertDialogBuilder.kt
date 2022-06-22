package dev.weazyexe.wretches.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dev.weazyexe.wretches.R
import dev.weazyexe.wretches.entity.Theme

object AlertDialogBuilder {

    fun themePicker(
        context: Context,
        value: Theme,
        onSuccess: (Theme) -> Unit
    ): AlertDialog {
        val values = Theme.values().map { context.getString(it.stringRes) }.toTypedArray()
        var selected = value
        val selectedPosition = Theme.values().indexOf(value)

        return MaterialAlertDialogBuilder(context)
            .setIcon(R.drawable.ic_contrast)
            .setSingleChoiceItems(values, selectedPosition) { _, which ->
                selected = Theme.values()[which]
            }
            .setPositiveButton(R.string.theme_picker_save_text) { dialog, _ ->
                onSuccess(selected)
                dialog.dismiss()
            }
            .setNegativeButton(R.string.theme_picker_cancel_text) { dialog, _ ->
                dialog.dismiss()
            }
            .setTitle(R.string.theme_picker_title_text)
            .create()
    }

}