package ru.skillbranch.devintensive.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.core.graphics.drawable.toBitmap
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.utils.Utils
import kotlin.math.min

class CircleImageView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet?,
    defStyleAttributes: Int = 0
) : ImageView(context, attributeSet, defStyleAttributes) {

    companion object {
        const val DEF_BORDER_COLOR = Color.WHITE
        const val DEF_BORDER_WIDTH_DP = 2F
    }

    private var borderColor: Int
    private var borderWidth: Int

    private val circleBorderPaint: Paint = Paint()
    private lateinit var bitmapShader: BitmapShader
    private var paintShader: Paint = Paint()

    init {
        borderColor = DEF_BORDER_COLOR
        borderWidth = Utils.convertDpToPx(context, DEF_BORDER_WIDTH_DP)

        attributeSet?.let {
            val typedArray = context.obtainStyledAttributes(
                attributeSet,
                R.styleable.CircleImageView
            )
            borderColor =
                typedArray.getColor(R.styleable.CircleImageView_cv_borderColor, borderColor)
            borderWidth = typedArray.getDimensionPixelSize(
                R.styleable.CircleImageView_cv_borderWidth,
                borderWidth
            )

            typedArray.recycle()
        }
        preparePaints()
    }

    private fun preparePaints() {
        circleBorderPaint.style = Paint.Style.STROKE
        circleBorderPaint.color = borderColor
        circleBorderPaint.strokeWidth = borderWidth.toFloat()
    }

    private fun getBitmapFromResource(width: Int, height: Int): Bitmap? {
        return drawable.toBitmap(width, height)
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.d(javaClass.name, "Width: $width Height: $height")
        /*
        Можно попробовать прогружать битмап в данном callback, но не уверен, что это есть
        хорошая идея.
         */
    }

    /*
    Аллоцировать объекты в onDraw - плохая идея, но сравнив все плюсы и минусы (другие методы
    отрисовки), я пришел к тому, что использование шейдеров - оптимальная по затратам времени и
    ресурсов идея. Тем более у нас немного действий, так что на этот раз прокатит.
    Аллоцируем мы здесь потому, что нам надо промасштабировать битмап к размерам канвы.
    proof me wrong
     */
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)

        Log.d(javaClass.name, "$width $height")

        val halfWidth = width / 2F
        val halfHeight = height / 2F

        val usefulWidth = width - paddingStart - paddingEnd
        val usefulheight = height - paddingTop - paddingBottom

        val min = min(usefulWidth, usefulheight)
        val radius = min / 2F
        val bitmap = getBitmapFromResource(usefulWidth, usefulheight)
        bitmap ?: return

        // this is bad practice (работает - не трожь)
        bitmapShader = BitmapShader(
            bitmap,
            Shader.TileMode.CLAMP,
            Shader.TileMode.CLAMP
        )
        paintShader.shader = bitmapShader


        canvas.drawCircle(
            halfWidth,
            halfHeight,
            radius,
            paintShader
        )

        canvas.drawCircle(
            halfWidth,
            halfHeight,
            radius,
            circleBorderPaint
        )
    }

    @Dimension
    fun getBorderWidth() = Utils.convertPxToDp(context, borderWidth.toFloat())

    fun setBorderWidth(@Dimension dp: Int) {
        borderWidth = Utils.convertDpToPx(context, dp.toFloat())
    }

    fun getBorderColor() = borderColor

    fun setBorderColor(hex: String) {
        borderColor = Color.parseColor(hex)
    }

    fun setBorderColor(@ColorRes colorId: Int) {
        borderColor = context.getColor(colorId)
    }
}