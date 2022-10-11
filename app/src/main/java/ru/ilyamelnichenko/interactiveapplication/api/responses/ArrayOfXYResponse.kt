package ru.ilyamelnichenko.interactiveapplication.api.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArrayOfXYResponse(
    @SerializedName("points")
    val points: List<XYResponse>?
): Parcelable