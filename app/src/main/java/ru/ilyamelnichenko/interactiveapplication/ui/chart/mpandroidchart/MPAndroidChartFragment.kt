package ru.ilyamelnichenko.interactiveapplication.ui.chart.mpandroidchart

import android.content.Context
import androidx.fragment.app.Fragment
import ru.ilyamelnichenko.interactiveapplication.App
import ru.ilyamelnichenko.interactiveapplication.R

class MPAndroidChartFragment: Fragment(R.layout.fragment_mpandroichart) {

    override fun onAttach(context: Context) {
        (activity?.application as App).component.inject(this)
        super.onAttach(context)
    }

}