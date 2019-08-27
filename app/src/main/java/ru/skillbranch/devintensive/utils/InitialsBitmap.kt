package ru.skillbranch.devintensive.utils

import android.graphics.*
import android.text.TextPaint
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.annotation.Dimension.PX

class InitialsBitmap(
    @Dimension(unit = PX) var width: Int,
    @Dimension(unit = PX) var height: Int
) {
    private val bitmap: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    private var textSize: Float = 0F
    private var initials: String = ""
    private var textColor: Int = Color.WHITE
    private var backgroundColor: Int = Color.BLACK

    fun setTextSize(@Dimension(unit = PX) textSize: Float) = apply { this.textSize = textSize }
    fun setInitials(initials: String?) = apply { this.initials = initials ?: "" }
    fun setTextColor(@ColorInt textColor: Int) = apply { this.textColor = textColor }
    fun setBackgroundColor(@ColorInt backgroundColor: Int) =
        apply { this.backgroundColor = backgroundColor }


    fun buildBitmap(): Bitmap {
        val canvas = Canvas(bitmap)
        canvas.drawColor(backgroundColor)

        val textPaint = TextPaint().also {
            it.textSize = textSize
            it.textAlign = Paint.Align.CENTER
            it.color = textColor
        }

        val textRect = Rect().also {
            textPaint.getTextBounds(initials, 0, initials.length, it)
        }

        val canvasRect = Rect(0, 0, width, height)


        canvas.drawText(
            initials,
            canvasRect.exactCenterX(),
            canvasRect.exactCenterY() - textRect.exactCenterY(),
            textPaint
        )
        return bitmap
    }
}