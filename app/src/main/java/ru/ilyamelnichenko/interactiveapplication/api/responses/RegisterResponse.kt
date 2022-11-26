package ru.ilyamelnichenko.interactiveapplication.api.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("errors") val errors: List<RegisterErrors>?
): Parcelable

@Parcelize
data class RegisterErrors(
    @SerializedName("field") val field: String,
    @SerializedName("error_string") val errorString: String
): Parcelable