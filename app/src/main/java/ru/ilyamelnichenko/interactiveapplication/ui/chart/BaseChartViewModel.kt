package ru.ilyamelnichenko.interactiveapplication.ui.chart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.ilyamelnichenko.interactiveapplication.adapter.AdapterItem
import ru.ilyamelnichenko.interactiveapplication.api.responses.ArrayOfXYResponse
import ru.ilyamelnichenko.interactiveapplication.domain.cases.GetPointsUseCase
import ru.ilyamelnichenko.interactiveapplication.ui.chart.canvas.CanvasViewModel
import javax.inject.Inject

abstract class BaseChartViewModel: ViewModel() {

    protected val _chart = MutableSharedFlow<ArrayOfXYResponse>()
    val chart: Flow<ArrayOfXYResponse> = _chart

    protected val _text = MutableSharedFlow<String>()
    val text: Flow<String> = _text

    protected val _adapterItems = MutableStateFlow(listOf<AdapterItem>())
    val adapterItems: Flow<List<AdapterItem>> = _adapterItems

}


@Suppress("UNCHECKED_CAST")
class BaseChartViewModelFactory @Inject constructor(
    private val getPointsUseCase: GetPointsUseCase,
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CanvasViewModel(
            getPointsUseCase
        ) as T
    }
}