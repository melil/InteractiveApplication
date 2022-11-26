package ru.ilyamelnichenko.interactiveapplication.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import ru.ilyamelnichenko.interactiveapplication.App
import ru.ilyamelnichenko.interactiveapplication.R
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

    private var name = ""
    private var phone = ""
    private var email = ""

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
            applyButton.setOnClickListener {
                viewModel.register(
                    binding.nameEditText.text.toString(),
                    binding.emailEditText.text.toString(),
                    binding.phoneEditText.text.toString()
                )

            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.error
                .collect {

                }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.progress
                .collect {
                    binding.applyButton.isEnabled = it.not()
                }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.emailError
                .collect {
                    if(it.isNotBlank()) {
                        binding.emailTextInputLayout.error = it
                    }
                }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.phoneError
                .collect {
                    if(it.isNotBlank()) {
                        binding.phoneTextInputLayout.error = it
                    }
                }
        }

        phone
    }

}