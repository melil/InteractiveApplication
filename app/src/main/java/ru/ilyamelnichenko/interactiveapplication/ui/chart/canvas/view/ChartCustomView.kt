package ru.ilyamelnichenko.interactiveapplication.ui.chart.canvas.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.DimenRes
import ru.ilyamelnichenko.interactiveapplication.R
import ru.ilyamelnichenko.interactiveapplication.api.responses.XYResponse


class ChartCustomView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {
    private val lineThickness = getSize(R.dimen.chart_line_thickness)
    private val dotRadius = getSize(R.dimen.chart_dot_radius)
    private var padding = dotRadius
    private var linePaint = Paint().apply {
        strokeWidth = lineThickness
        isAntiAlias = true
        style = Paint.Style.STROKE
    }
    private var circlePaint = Paint().apply {
        strokeWidth = dotRadius
        isAntiAlias = true
        style = Paint.Style.FILL
    }
    var lineDashPaint = Paint().apply {
        color = context.getColor(R.color.chart_line_dash)
        strokeWidth = lineThickness
        isAntiAlias = true
        style = Paint.Style.STROKE
        val dashLineWidth = getSize(R.dimen.chart_dash_line_length)
        val dashEmptyWidth = getSize(R.dimen.chart_dash_line_length)
        val dashRadius = lineThickness / 2
        pathEffect = PathDashPathEffect(
            Path().apply {
                addRoundRect(
                    RectF(0F, 0F, dashLineWidth, lineThickness),
                    dashRadius,
                    dashRadius,
                    Path.Direction.CW
                )
            },
            dashLineWidth + dashEmptyWidth,
            0F,
            PathDashPathEffect.Style.TRANSLATE
        )
    }

    private val lineDashPath = Path()
    private val linePath = Path()

    private var chart: ArrayList<Float> = ArrayList()
    private var startX = 0F
    private var startY = 0F
    private var chartHeight = 0F
    private var xStep = 0F

    fun setChart(dailyChart: List<XYResponse>) {
        if (dailyChart.isEmpty()) return
        val prices = dailyChart.map { it.x!!.toFloat() }
        var min = prices[0]
        var max = prices[0]
        prices.forEach { price ->
            if (price < min) min = price
            if (price > max) max = price
        }
        val yRange = (max - min)
        chart.clear()
        prices.forEach { price ->
            chart.add((price - min) / yRange)
        }
        invalidate()
    }

    fun setColor(color: Int) {
        circlePaint.color = color
        linePaint.color = color
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        startX = padding
        startY = height - padding
        chartHeight = height - padding * 2
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        if (chart.isNotEmpty()) {
            xStep = (width.toFloat() - padding * 2) / chart.lastIndex
            drawDashLine(canvas)
            drawChart(canvas)
        }
    }

    private fun drawChart(canvas: Canvas?) {
        canvas?.drawCircle(getDotX(0), getDotY(chart[0]), dotRadius, circlePaint)
        linePath.rewind()
        linePath.moveTo(getDotX(0), getDotY(chart[0]))
        for (i in 1 until chart.size) {
            linePath.lineTo(getDotX(i), getDotY(chart[i]))
        }
        canvas?.drawPath(linePath, linePaint)
    }

    private fun drawDashLine(canvas: Canvas?) {
        lineDashPath.rewind()
        lineDashPath.moveTo(
            getDotX(0),
            getDotY(chart[0]) - dotRadius + lineThickness / 2
        )
        lineDashPath.lineTo(
            getDotX(chart.lastIndex),
            getDotY(chart[0]) - dotRadius + lineThickness / 2
        )
        canvas?.drawPath(lineDashPath, lineDashPaint)
    }

    private fun getDotX(stepIndex: Int) = startX + xStep * stepIndex

    private fun getDotY(dot: Float) = startY - chartHeight * dot

    private fun getSize(@DimenRes resourceId: Int) = context.resources.getDimension(resourceId)

}