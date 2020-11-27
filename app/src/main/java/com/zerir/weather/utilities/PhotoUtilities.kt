package com.zerir.weather.utilities

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.Log
import androidx.fragment.app.Fragment
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

const val TAKE_PHOTO_TAG = 101
const val PHOTO_DATE_FORMAT = "yyMMdd_HHmmss"

fun openCamera(context: Fragment) {
    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    try {
        context.startActivityForResult(takePictureIntent, TAKE_PHOTO_TAG)
    } catch (e: ActivityNotFoundException) {
        Log.e("open Camera", "${e.message}")
    }
}

fun drawMultilineTextToBitmap(
    context: Fragment,
    src: Bitmap?,
    text: String
): String? {
    if (src == null) return null
    var bitmap = src
    var bitmapConfig = bitmap.config
    if (bitmapConfig == null) {
        bitmapConfig = Bitmap.Config.ARGB_8888
    }
    bitmap = bitmap.copy(bitmapConfig, true)
    val canvas = Canvas(bitmap)
    val paint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    paint.color = Color.WHITE
    paint.textSize = 12f
    paint.setShadowLayer(1f, 0f, 1f, Color.BLACK)
    val textWidth = canvas.width - (16).toInt()
    val textLayout =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            StaticLayout.Builder.obtain(text, 0, text.length, paint, textWidth)
                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                .setLineSpacing(0.0f, 1.0f)
                .setIncludePad(false)
                .build()
        } else {
            @Suppress("DEPRECATION")
            StaticLayout(
                text, paint, textWidth, Layout.Alignment.ALIGN_CENTER,
                1.0f, 0.0f, false
            )
        }
    val textHeight = textLayout.height
    val x = ((bitmap.width - textWidth) / 2).toFloat()
    val y = ((bitmap.height - textHeight) / 2).toFloat()
    canvas.save()
    canvas.translate(x, y)
    textLayout.draw(canvas)
    canvas.restore()
    return savePhoto(context, bitmap)
}

private fun savePhoto(context: Fragment, photo: Bitmap?): String? {
    if (photo == null) return null
    return try {
        val file = createImageFile(context)
        val outStream = FileOutputStream(file)
        photo.compress(
            Bitmap.CompressFormat.JPEG,
            100,
            outStream
        )
        file.path
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        null
    }
}

fun shareImage(context: Fragment, path: String?) {
    @Suppress("DEPRECATION")
    val newPath =
        MediaStore.Images.Media.insertImage(
            context.requireActivity().contentResolver,
            path, "title", "des"
        )
    val uri: Uri = Uri.parse(newPath)
    val shareIntent = Intent()
    shareIntent.action = Intent.ACTION_SEND
    shareIntent.type = "image/png"
    shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
    context.startActivity(
        Intent.createChooser(
            shareIntent,
            "Share"
        )
    )
}

@SuppressLint("SimpleDateFormat")
private fun createImageFile(context: Fragment): File {
    val timeStamp: String = SimpleDateFormat(PHOTO_DATE_FORMAT).format(Date())
    val storageDir: File? =
        context.requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "JPEG_${timeStamp}_",
        ".jpg",
        storageDir
    )
}