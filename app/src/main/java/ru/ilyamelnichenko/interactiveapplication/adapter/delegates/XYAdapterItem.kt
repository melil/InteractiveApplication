package ru.ilyamelnichenko.interactiveapplication.adapter.delegates

import ru.ilyamelnichenko.interactiveapplication.adapter.AdapterItem

data class XYAdapterItem(
    override val uniqueTag: String,
    val title: String,
    val value: String,
) : AdapterItem