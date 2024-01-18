package com.jotangi.twinsSum.utils

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore

class AppUtil {

    /**
     * Uri to Path
     */
    fun getRealPathFromUri(context: Context, uri: Uri): String? {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(
                context,
                uri
            )
        ) {
            // 如果是 DocumentProvider 类型的 Uri，则通过 DocumentFile 处理
            return handleDocumentUri(context, uri)
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            // MediaStore (使用于大多数的 ContentProvider)
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                // 如果是 KitKat 及以上版本，使用 MediaStore 处理
                getRealPathFromMediaStore(context, uri)
            } else {
                // 如果是 KitKat 以下版本，使用旧的处理方式
                getRealPathFromUriLegacy(context, uri)
            }
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            // 如果是 File 类型的 Uri，直接返回路径
            return uri.path
        }
        return null
    }

    private fun handleDocumentUri(context: Context, uri: Uri): String? {

        val documentId = DocumentsContract.getDocumentId(uri)
        val split = documentId.split(":").toTypedArray()
        val type = split[0]

        if ("com.android.providers.media.documents".equals(uri.authority, ignoreCase = true)) {
            // 处理来自 MediaProvider 的 Uri
            val contentUri = when (type) {
                "image" -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                "video" -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                "audio" -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                else -> return null
            }

            val selection = "_id=?"
            val selectionArgs = arrayOf(split[1])

            return getDataColumn(context, contentUri, selection, selectionArgs)
        } else if ("com.android.providers.downloads.documents".equals(
                uri.authority,
                ignoreCase = true
            )
        ) {
            // 处理来自 DownloadsProvider 的 Uri
            if (type == "raw") {
                return split[1]
            }

            val contentUri = if ("video".equals(type, ignoreCase = true)) {
                Uri.parse("content://downloads/public_videos")
            } else {
                Uri.parse("content://downloads/public_images")
            }

            return getDataColumn(context, contentUri, "_id=?", arrayOf(split[1]))
        }

        return null
    }

    private fun getDataColumn(
        context: Context,
        uri: Uri,
        selection: String?,
        selectionArgs: Array<String>?
    ): String? {

        val column = "_data"
        val projection = arrayOf(column)

        context.contentResolver.query(uri, projection, selection, selectionArgs, null)
            ?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val columnIndex = cursor.getColumnIndexOrThrow(column)
                    return cursor.getString(columnIndex)
                }
            }
        return null
    }

    private fun getRealPathFromMediaStore(context: Context, uri: Uri): String? {

        var filePath: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                filePath = it.getString(columnIndex)
            }
        }
        return filePath
    }

    private fun getRealPathFromUriLegacy(context: Context, uri: Uri): String? {

        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, proj, null, null, null)
        val column_index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()
        val path = cursor?.getString(column_index ?: -1)
        cursor?.close()
        return path
    }
}