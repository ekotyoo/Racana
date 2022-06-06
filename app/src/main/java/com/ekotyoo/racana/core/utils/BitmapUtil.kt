package com.ekotyoo.racana.core.utils

import android.content.Context
import android.graphics.*
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

object BitmapUtil {
    fun markerBitmapDescriptor(
        context: Context,
        vectorResId: Int,
        number: Int?,
    ): BitmapDescriptor? {
        val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)

        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        val textPaint = Paint().apply {
            textSize = 50f
            typeface = Typeface.DEFAULT_BOLD
            color = Color.BLACK
        }

        val canvas = Canvas(bitmap)
        drawable.draw(canvas)
        number?.let { canvas.drawText(number.toString(), 36f, 66f, textPaint) }

        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}