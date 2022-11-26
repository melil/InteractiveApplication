package ru.ilyamelnichenko.interactiveapplication.ui.chart.canvas

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.ilyamelnichenko.interactiveapplication.adapter.AdapterItem
import ru.ilyamelnichenko.interactiveapplication.adapter.delegates.XYAdapterItem
import ru.ilyamelnichenko.interactiveapplication.api.responses.ArrayOfXYResponse
import ru.ilyamelnichenko.interactiveapplication.domain.cases.GetPointsUseCase
import ru.ilyamelnichenko.interactiveapplication.ui.chart.BaseChartViewModel

class CanvasViewModel(
    private val pointsUseCase: GetPointsUseCase,
    ) : BaseChartViewModel() {

    fun setPoints(arrayOfXYResponse: ArrayOfXYResponse?) {
        arrayOfXYResponse?.let { points ->
            viewModelScope.launch {
                pointsUseCase.run(GetPointsUseCase.Param(10))
                _text.emit("Canvas chart")
                _chart.emit(points)

                val adapterItems = mutableListOf<AdapterItem>()
                points.points?.forEach {
                    adapterItems.add(XYAdapterItem(
                        title = "x:${it.x}",
                        value = "y:${it.y}",
                        uniqueTag = "points"
                    ))
                }
                _adapterItems.emit(adapterItems)
            }
        }
    }

}
