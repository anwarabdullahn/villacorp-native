package anwarabdullahn.com.villacorp_apps.Model

import com.google.gson.annotations.SerializedName

data class DayOffChangeOff(
    @SerializedName("success")
    var success: Boolean,

    @SerializedName("offday")
    var offday: ArrayList<String>
)