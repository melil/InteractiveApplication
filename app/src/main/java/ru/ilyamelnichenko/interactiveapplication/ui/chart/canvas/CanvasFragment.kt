package ru.ilyamelnichenko.interactiveapplication.ui.chart.canvas

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.flow.collect
import ru.ilyamelnichenko.interactiveapplication.App
import ru.ilyamelnichenko.interactiveapplication.R
import ru.ilyamelnichenko.interactiveapplication.api.responses.ArrayOfXYResponse
import ru.ilyamelnichenko.interactiveapplication.auxiliary.viewBinding
import ru.ilyamelnichenko.interactiveapplication.databinding.FragmentCanvasBinding
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