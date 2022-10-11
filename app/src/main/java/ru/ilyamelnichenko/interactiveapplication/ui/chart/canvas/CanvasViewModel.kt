package ru.ilyamelnichenko.interactiveapplication.ui.chart.canvas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import ru.ilyamelnichenko.interactiveapplication.api.responses.ArrayOfXYResponse
import ru.ilyamelnichenko.interactiveapplication.api.responses.XYResponse
import ru.ilyamelnichenko.interactiveapplication.domain.cases.GetPointsUseCase
import javax.inject.Inject

class CanvasViewModel(
    private val pointsUseCase: GetPointsUseCase,
    ) : ViewModel() {

    private val _chart = MutableSharedFlow<ArrayOfXYResponse>()
    val chart: Flow<ArrayOfXYResponse> = _chart

    private val _text = MutableSharedFlow<String>()
    val text: Flow<String> = _text

    fun setPoints(arrayOfXYResponse: ArrayOfXYResponse?) {
        arrayOfXYResponse?.let { points ->
            viewModelScope.launch {
                pointsUseCase.run(GetPointsUseCase.Param(10))


                _text.emit("Canvas chart")
                _chart.emit(points)
            }
        }
    }

}

@Suppress("UNCHECKED_CAST")
class CanvasViewModelFactory @Inject constructor(
    private val getPointsUseCase: GetPointsUseCase,
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CanvasViewModel(
            getPointsUseCase
        ) as T
    }
}