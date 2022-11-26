package ru.ilyamelnichenko.interactiveapplication.adapter.delegates

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import ru.ilyamelnichenko.interactiveapplication.R
import ru.ilyamelnichenko.interactiveapplication.adapter.AdapterItem
import ru.ilyamelnichenko.interactiveapplication.databinding.ItemTableBinding

object AdapterDelegates {

    fun xyItem() = adapterDelegate<XYAdapterItem, AdapterItem>(
        R.layout.item_table
    ) {
        val binding = ItemTableBinding.bind(itemView)

        bind {
            with(binding) {
                xText.text = item.title
                yText.text = item.value
            }
        }
    }

}