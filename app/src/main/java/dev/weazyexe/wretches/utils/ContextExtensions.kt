package dev.weazyexe.wretches.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

/**
 * Проверка на присутствие разрешения [permission]
 */
fun Context.handlePermission(
    permission: String,
    onPermissionGranted: (permission: String) -> Unit,
    onPermissionDenied: (permission: String) -> Unit
) {
    val hasScopedStorage = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
    val isWritePermission = permission == Manifest.permission.WRITE_EXTERNAL_STORAGE

    when {
        isWritePermission && hasScopedStorage ->
            onPermissionGranted(permission)
        checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED ->
            onPermissionGranted(permission)
        else -> onPermissionDenied(permission)
    }
}