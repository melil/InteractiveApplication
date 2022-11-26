package ru.ilyamelnichenko.interactiveapplication.ui.chart.canvas

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import ru.ilyamelnichenko.interactiveapplication.App
import ru.ilyamelnichenko.interactiveapplication.R
import ru.ilyamelnichenko.interactiveapplication.adapter.DiffAdapter
import ru.ilyamelnichenko.interactiveapplication.adapter.delegates.AdapterDelegates
import ru.ilyamelnichenko.interactiveapplication.api.responses.ArrayOfXYResponse
import ru.ilyamelnichenko.interactiveapplication.auxiliary.viewBinding
import ru.ilyamelnichenko.interactiveapplication.databinding.FragmentChartCanvasBinding
import ru.ilyamelnichenko.interactiveapplication.ui.chart.BaseChartFragment
import ru.ilyamelnichenko.interactiveapplication.ui.chart.BaseChartViewModelFactory
import javax.inject.Inject


class CanvasFragment : BaseChartFragment() {

    companion object {
        private const val ARG_POINTS = "POINTS"

        fun newInstance(points: ArrayOfXYResponse?): CanvasFragment =
            CanvasFragment().apply {
                arguments = bundleOf(
                    ARG_POINTS to points
                )
            }

    }

    val binding: FragmentChartCanvasBinding by viewBinding(FragmentChartCanvasBinding::bind)

    @Inject
    lateinit var vmFactory: BaseChartViewModelFactory

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
        return inflater.inflate(R.layout.fragment_chart_canvas, container, false)
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

        with(binding.rvItems) {
            adapter = this@CanvasFragment.adapter
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.text.collect {
                binding.textText.text = it

            }
        }


        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.adapterItems
                .collect {
                    adapter.items = it
                }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.chart
                .collect {
                    binding.chart.setChart(it.points!!)
                }
        }

    }
}