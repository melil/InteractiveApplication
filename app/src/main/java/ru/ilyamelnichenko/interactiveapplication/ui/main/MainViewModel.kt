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
import ru.ilyamelnichenko.interactiveapplication.domain.cases.RegisterUseCase
import javax.inject.Inject

interface MainMethods {
    fun register(name: String, email: String, phone: String)
}

class MainViewModel(
    private val registerUseCase: RegisterUseCase,
) : ViewModel(), MainMethods {

    companion object {
        const val EMAIL = "email"
        const val PHONE = "phone"
    }

    private val _progress = MutableStateFlow(false)
    val progress: Flow<Boolean> = _progress

    private val _error = MutableSharedFlow<ErrorData>()
    val error: Flow<ErrorData> = _error

    private val _emailError = MutableStateFlow<String>("")
    val emailError: Flow<String> = _emailError

    private val _phoneError = MutableStateFlow<String>("")
    val phoneError: Flow<String> = _phoneError

    val name = MutableStateFlow("")
    val phone =  MutableStateFlow("")

    override fun register(name: String, email: String, phone: String) {

        viewModelScope.launch {

            _progress.emit(true)

            registerUseCase.run(RegisterUseCase.Param(name, email, phone))
                .onSuccess { output ->

                    when(output.output.success) {
                        true -> {

                        }
                        false -> {
                            output.output.errors?.forEach { error ->
                                when(error.field) {
                                    EMAIL -> {
                                        _emailError.emit(error.errorString)
                                    }
                                    PHONE -> {
                                        _phoneError.emit(error.errorString)
                                    }
                                }
                            }
                        }
                    }


                    _progress.emit(false)
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
    private val registerUseCase: RegisterUseCase,
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            registerUseCase
        ) as T
    }
}

