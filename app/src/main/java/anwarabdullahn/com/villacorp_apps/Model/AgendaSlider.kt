package anwarabdullahn.com.villacorp_apps.Model

import com.google.gson.annotations.SerializedName

data class AgendaSlider(
    @SerializedName("success")
    var success: Boolean,

    @SerializedName("slider")
    var slider: MutableList<Slider>,

    @SerializedName("agenda")
    var agenda: MutableList<Agenda>
)

class Slider (
    @SerializedName("name")
    var name: String,

    @SerializedName("file")
    var file: String
)

class Agenda (
    @SerializedName("tanggal")
    var tanggal: String,

    @SerializedName("jam_mulai")
    var jam_mulai: String,

    @SerializedName("jam_selesai")
    var jam_selesai: String,

    @SerializedName("remark")
    var remark: String,

    @SerializedName("hari")
    var hari: String,

    @SerializedName("ruangan")
    var ruangan: String,

    @SerializedName("department")
    var department: String
)