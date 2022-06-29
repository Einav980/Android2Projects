package com.example.rently.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.text.NumberFormat
import java.util.*

fun convertImageToBase64(bitmap: Bitmap): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    val encodedImage =
        try {
            Base64.encodeToString(byteArray, Base64.DEFAULT);
        } catch (exception: Exception) {
            ""
        }

    return encodedImage
}

fun getImageBitmap(context: Context, imageUri: Uri): Bitmap {
    return if (Build.VERSION.SDK_INT < 28) {
        MediaStore.Images
            .Media.getBitmap(context.contentResolver, imageUri)

    } else {
        val source = ImageDecoder
            .createSource(context.contentResolver, imageUri)
        ImageDecoder.decodeBitmap(source)
    }
}

fun priceToCurrency(price: Number, currencyCode: String = "ILS"): String {
    val format = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 0
    format.currency = Currency.getInstance(currencyCode)
    return format.format(price)
}