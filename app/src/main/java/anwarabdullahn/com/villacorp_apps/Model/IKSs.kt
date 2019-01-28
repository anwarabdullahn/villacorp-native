package anwarabdullahn.com.villacorp_apps.Model

import com.google.gson.annotations.SerializedName

class IKSs(
    @SerializedName("success")
    var Success: String,

    @SerializedName("totalpage")
    var TotalPage: String,

    @SerializedName("ikks_finger")
    var IKS: MutableList<IKS>
)

class IKS (

    @SerializedName("id_ikks")
    var ID: String,

    @SerializedName("nomor")
    var Nomor: String,

    @SerializedName("tanggal")
    var Date: String,

    @SerializedName("jam_awal")
    var FirstTime: String,

    @SerializedName("jam_akhir")
    var SecondTime: String,

    @SerializedName("status")
    var Status: String

)

