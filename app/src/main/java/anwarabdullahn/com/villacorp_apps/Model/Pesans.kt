package anwarabdullahn.com.villacorp_apps.Model

import com.google.gson.annotations.SerializedName

data class Pesans(

    @SerializedName("success")
    var success: Boolean,

    @SerializedName("jumlah_pesan")
    var jumlah_pesan: String,

    @SerializedName("pesan")
    var pesan: MutableList<Pesan>
)

data class Pesan(
    @SerializedName("id")
    var id: String,

    @SerializedName("judul")
    var judul: String,

    @SerializedName("pesan")
    var pesan: String,

    @SerializedName("approve_by")
    var approve_by: String,

    @SerializedName("waktu")
    var waktu: String,

    @SerializedName("status")
    var status: Boolean

)
