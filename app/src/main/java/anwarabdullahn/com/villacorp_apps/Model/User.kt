package anwarabdullahn.com.villacorp_apps.Model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("name")
    var name: String,

    @SerializedName("lokasi_kerja")
    var lokasi_kerja: String,

    @SerializedName("cabang")
    var cabang: String,

    @SerializedName("brand")
    var brand: String,

    @SerializedName("jabatan")
    var jabatan: String,

    @SerializedName("posisi")
    var posisi: String,

    @SerializedName("key")
    var key: String
)