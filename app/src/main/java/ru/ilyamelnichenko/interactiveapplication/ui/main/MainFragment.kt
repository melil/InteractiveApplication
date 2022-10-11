package ru.ilyamelnichenko.interactiveapplication.ui.main

import android.app.AlertDialog
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.flow.collect
import ru.ilyamelnichenko.interactiveapplication.App
import ru.ilyamelnichenko.interactiveapplication.R
import ru.ilyamelnichenko.interactiveapplication.api.responses.ArrayOfXYResponse
import ru.ilyamelnichenko.interactiveapplication.auxiliary.viewBinding
import ru.ilyamelnichenko.interactiveapplication.databinding.FragmentMainBinding
import javax.inject.Inject

class MainFragment : Fragment() {

    private val binding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)

    @Inject
    lateinit var vmFactory: MainViewModelFactory

    private val viewModel by viewModels<MainViewModel> {
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
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            goButton.setOnClickListener {
                openDialog()
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.error
                .collect{

                }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.progress
                .collect {

                }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.successPointsCanvas
                .collect {
                    goToCanvasFragment(it)
                }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.successPointsMp
                .collect {
                    goToCanvasFragment(it)
                }
        }
    }

    private fun openDialog() {
        AlertDialog.Builder(activity)
            .setTitle(resources.getString(R.string.dialog_title))
            .setMessage(resources.getString(R.string.dialog_description))
            .setPositiveButton(R.string.canvas) { d,_ ->
                viewModel.getPoints(DrawType.CANVAS, binding.pointsEdittext.text.toString().toInt())


                d.dismiss()
            }
            .setNegativeButton(R.string.MPAndroidChart) { d,_ ->
                viewModel.getPoints(DrawType.MPANDROIDCHART, binding.pointsEdittext.text.toString().toInt())


                d.dismiss()
            }
            .show()
    }

    private fun goToCanvasFragment(points: ArrayOfXYResponse) {
        (activity as MainActivity).goToCanvasFragment(points)
    }
}