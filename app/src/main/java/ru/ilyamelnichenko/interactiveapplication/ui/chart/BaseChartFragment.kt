package ru.ilyamelnichenko.interactiveapplication.ui.chart

import androidx.fragment.app.Fragment
import ru.ilyamelnichenko.interactiveapplication.adapter.DiffAdapter
import ru.ilyamelnichenko.interactiveapplication.adapter.delegates.AdapterDelegates

abstract class BaseChartFragment : Fragment() {

    val adapter = DiffAdapter(
        AdapterDelegates.xyItem()
    )

}