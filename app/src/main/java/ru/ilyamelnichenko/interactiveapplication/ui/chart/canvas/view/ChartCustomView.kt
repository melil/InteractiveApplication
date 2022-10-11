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
        color = resources.getColor(R.color.green)
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
        val yDots = dailyChart.map { it.y!!.toFloat() }
        var min = yDots[0]
        var max = yDots.last()
        yDots.forEach { dot ->
            if (dot < min) min = dot
            if (dot > max) max = dot
        }
        val yRange = (max - min)
        chart.clear()
        yDots.forEach { dot ->
            chart.add((dot - min) / yRange)
        }
        invalidate()
    }

    fun setColor(color: Int) {
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
            getDotY(chart[0])
        )
        lineDashPath.lineTo(
            getDotX(chart.lastIndex),
            getDotY(chart[0])
        )
        canvas?.drawPath(lineDashPath, lineDashPaint)
    }

    private fun getDotX(stepIndex: Int) = startX + xStep * stepIndex

    private fun getDotY(dot: Float) = startY - chartHeight * dot

    private fun getSize(@DimenRes resourceId: Int) = context.resources.getDimension(resourceId)

}