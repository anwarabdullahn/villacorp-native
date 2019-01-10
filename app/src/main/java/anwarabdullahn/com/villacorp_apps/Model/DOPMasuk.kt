package anwarabdullahn.com.villacorp_apps.Model

import com.google.gson.annotations.SerializedName

class DOPMasuk(
    @SerializedName("success")
    var success: Boolean,

    @SerializedName("tglDOPIn")
    var TglDOPIn: ArrayList<String>
)

class MaxDOP(
    @SerializedName("success")
    var success: Boolean,

    @SerializedName("tanggal")
    var tanggal: String
)