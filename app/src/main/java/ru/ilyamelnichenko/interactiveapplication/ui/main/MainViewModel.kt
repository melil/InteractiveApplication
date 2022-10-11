package ru.ilyamelnichenko.interactiveapplication.ui.main

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.ilyamelnichenko.interactiveapplication.R
import ru.ilyamelnichenko.interactiveapplication.api.responses.ArrayOfXYResponse
import ru.ilyamelnichenko.interactiveapplication.domain.ApiError
import ru.ilyamelnichenko.interactiveapplication.domain.UnknownAppError
import ru.ilyamelnichenko.interactiveapplication.domain.cases.GetPointsUseCase
import javax.inject.Inject

class MainViewModel(
    private val pointsUseCase: GetPointsUseCase,
) : ViewModel() {

    private val _progress = MutableStateFlow(false)
    val progress: Flow<Boolean> = _progress

    private val _error = MutableSharedFlow<ErrorData>()
    val error: Flow<ErrorData> = _error

    private val _successPointsCanvas = MutableSharedFlow<ArrayOfXYResponse>()
    val successPointsCanvas = _successPointsCanvas

    private val _successPointsMp = MutableSharedFlow<ArrayOfXYResponse>()
    val successPointsMp = _successPointsMp

    fun getPoints(variant: DrawType, count: Int) {

        viewModelScope.launch {

            _progress.emit(true)

            pointsUseCase.run(GetPointsUseCase.Param(count))
                .onSuccess {
                    when(variant) {
                        DrawType.CANVAS -> {
                            successPointsCanvas.emit(it.points)
                        }
                        DrawType.MPANDROIDCHART -> {
                            successPointsMp.emit(it.points)
                        }
                    }
                }
                .onFailure { appError ->
                    when (appError) {
                        ApiError -> {
                            _error.emit(ErrorData.apiError)

                        }
                        UnknownAppError -> {
                            _error.emit(ErrorData.unknownError)

                        }
                        else -> {
                            _error.emit(ErrorData.default)
                        }
                    }
                    _progress.emit(false)

                }
        }

    }


    data class ErrorData(@StringRes val title: Int, @StringRes val message: Int) {
        companion object {
            val default: ErrorData = ErrorData(
                R.string.error_title,
                R.string.default_error_description
            )
            val apiError: ErrorData = ErrorData(
                R.string.error_title,
                R.string.api_error_description
            )
            val unknownError: ErrorData = ErrorData(
                R.string.error_title,
                R.string.unknown_error_description
            )
        }
    }
}


@Suppress("UNCHECKED_CAST")
class MainViewModelFactory @Inject constructor(
    private val getPointsUseCase: GetPointsUseCase,
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            getPointsUseCase
        ) as T
    }
}

enum class DrawType {
    CANVAS, MPANDROIDCHART
}
