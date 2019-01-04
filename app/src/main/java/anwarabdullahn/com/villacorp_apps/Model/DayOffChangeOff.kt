package anwarabdullahn.com.villacorp_apps.Model

import com.google.gson.annotations.SerializedName
import java.lang.reflect.Array

data class DayOffChangeOff(
    @SerializedName("success")
    var success: Boolean,

    @SerializedName("offday")
    var offday: Array
)