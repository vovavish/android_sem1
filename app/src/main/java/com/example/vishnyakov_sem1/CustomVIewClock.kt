package com.example.vishnyakov_sem1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.Calendar

class CustomClockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 5f
    }

    private var radius = 0f
    private var centerX = 0f
    private var centerY = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = (Math.min(w, h) / 2 * 0.8).toFloat()
        centerX = w / 2f
        centerY = h / 2f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawClockFace(canvas)
        drawHands(canvas)
        invalidate()
    }

    private fun drawClockFace(canvas: Canvas) {
        paint.color = Color.BLACK
        canvas.drawCircle(centerX, centerY, radius, paint)

        paint.strokeWidth = 5f
        for (i in 0 until 12) {
            val angle = Math.PI / 6 * i
            val startX = centerX + (radius * 0.9 * Math.cos(angle)).toFloat()
            val startY = centerY + (radius * 0.9 * Math.sin(angle)).toFloat()
            val stopX = centerX + (radius * Math.cos(angle)).toFloat()
            val stopY = centerY + (radius * Math.sin(angle)).toFloat()
            canvas.drawLine(startX, startY, stopX, stopY, paint)
        }
    }

    private fun drawHands(canvas: Canvas) {
        val calendar = Calendar.getInstance()
        val hours = calendar.get(Calendar.HOUR)
        val minutes = calendar.get(Calendar.MINUTE)
        val seconds = calendar.get(Calendar.SECOND)

        paint.strokeWidth = 10f
        paint.color = Color.RED
        val hourAngle = Math.PI / 6 * (hours % 12 + minutes / 60f)
        drawHand(canvas, hourAngle, radius * 0.5f)

        paint.strokeWidth = 7f
        paint.color = Color.BLUE
        val minuteAngle = Math.PI / 30 * minutes
        drawHand(canvas, minuteAngle, radius * 0.7f)

        paint.strokeWidth = 3f
        paint.color = Color.GREEN
        val secondAngle = Math.PI / 30 * seconds
        drawHand(canvas, secondAngle, radius * 0.9f)
    }

    private fun drawHand(canvas: Canvas, angle: Double, length: Float) {
        val stopX = centerX + (length * Math.cos(angle - Math.PI / 2)).toFloat()
        val stopY = centerY + (length * Math.sin(angle - Math.PI / 2)).toFloat()
        canvas.drawLine(centerX, centerY, stopX, stopY, paint)
    }
}