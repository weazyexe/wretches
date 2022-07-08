package dev.weazyexe.wretches.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

/**
 * Проверка на присутствие разрешения для чтения файлов
 */
fun Context.hasReadExternalStoragePermission(): Boolean =
    checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

/**
 * Проверка на присутствие разрешения для записи файлов.
 * Для Android с версией 10 и выше оно неактуально и было удалено
 */
fun Context.hasWriteExternalStoragePermission(): Boolean =
    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ||
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q