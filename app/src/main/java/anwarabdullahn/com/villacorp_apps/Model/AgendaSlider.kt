package anwarabdullahn.com.villacorp_apps.Model

import com.google.gson.annotations.SerializedName

data class AgendaSlider(
    @SerializedName("success")
    var success: Boolean,

    @SerializedName("slider")
    var slider: MutableList<Slider>
)

class Slider (
    @SerializedName("name")
    var name: String,

    @SerializedName("file")
    var file: String
)