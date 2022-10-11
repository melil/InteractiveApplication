package ru.ilyamelnichenko.interactiveapplication.ui.chart.canvas


import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.ilyamelnichenko.interactiveapplication.R

class CanvasFragment: Fragment(R.layout.fragment_canvas) {

    companion object {
        fun newInstance() = CanvasFragment()
    }

    private lateinit var viewModel: CanvasViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}