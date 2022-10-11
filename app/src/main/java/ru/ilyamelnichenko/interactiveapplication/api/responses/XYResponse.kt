package ru.ilyamelnichenko.interactiveapplication.api.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class XYResponse(
    @SerializedName("x") val x: Double?,
    @SerializedName("y") val y: Double?
): Parcelable