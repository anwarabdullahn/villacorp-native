package anwarabdullahn.com.villacorp_apps.Model


import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("succes")
    var succes: String,

    @SerializedName("jmlsisacuti")
    var jmlsisacuti: Int,

    @SerializedName("sisaHariKerja")
    var sisaHariKerja: String,

    @SerializedName("hakdop")
    var hakdop: Int

)