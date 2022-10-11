package ru.ilyamelnichenko.interactiveapplication.ui.main

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
import ru.ilyamelnichenko.interactiveapplication.auxiliary.viewBinding
import ru.ilyamelnichenko.interactiveapplication.databinding.FragmentMainBinding
import ru.ilyamelnichenko.interactiveapplication.ui.main.di.MainModule
import javax.inject.Inject

class MainFragment : Fragment() {

    private val binding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)

    @Inject
    lateinit var vmFactory: MainViewModelFactory

    private val viewModel by viewModels<MainViewModel> {
        vmFactory
    }

    override fun onAttach(context: Context) {
        (activity?.application as App).component.inject(this)//(MainModule(this)).inject(this)
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
            message.setText("asd see")

            canvasButton.setOnClickListener {
                viewModel.getPoints(1, 10)
            }

            mpandroidchartButton.setOnClickListener {
                viewModel.getPoints(2, 10)
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
    }
}