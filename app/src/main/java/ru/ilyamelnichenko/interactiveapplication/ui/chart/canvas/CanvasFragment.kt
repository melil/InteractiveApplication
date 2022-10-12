package ru.ilyamelnichenko.interactiveapplication.ui.chart.canvas

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import ru.ilyamelnichenko.interactiveapplication.App
import ru.ilyamelnichenko.interactiveapplication.BuildConfig
import ru.ilyamelnichenko.interactiveapplication.R
import ru.ilyamelnichenko.interactiveapplication.api.responses.ArrayOfXYResponse
import ru.ilyamelnichenko.interactiveapplication.auxiliary.viewBinding
import ru.ilyamelnichenko.interactiveapplication.databinding.FragmentCanvasBinding
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject


class CanvasFragment : Fragment() {

    companion object {
        private const val ARG_POINTS = "POINTS"

        fun newInstance(points: ArrayOfXYResponse?): CanvasFragment =
            CanvasFragment().apply {
                arguments = bundleOf(
                    ARG_POINTS to points
                )
            }

    }

    private val binding: FragmentCanvasBinding by viewBinding(FragmentCanvasBinding::bind)

    @Inject
    lateinit var vmFactory: CanvasViewModelFactory

    private val viewModel by viewModels<CanvasViewModel> {
        vmFactory
    }

    private lateinit var imagePath: File

    override fun onAttach(context: Context) {
        (activity?.application as App).component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_canvas, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            val points: ArrayOfXYResponse? = arguments?.getParcelable(ARG_POINTS)
            viewModel.setPoints(points)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            textText.setOnClickListener {

            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.text.collect {
                binding.textText.text = it

            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.chart
                .collect {
                    Log.d("SMAS", "asdasd")
                    it
                    binding.chart.setChart(it.points!!)
                }
        }
    }
}