package anwarabdullahn.com.villacorp_apps.Model

import com.google.gson.annotations.SerializedName

data class DOPHoliday(

    @SerializedName("success")
    var success: Boolean,

    @SerializedName("holiday")
    var holiday: ArrayList<String>
)