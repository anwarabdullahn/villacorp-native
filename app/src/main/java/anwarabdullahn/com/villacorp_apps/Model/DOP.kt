package anwarabdullahn.com.villacorp_apps.Model

import com.google.gson.annotations.SerializedName

data class DOP(
    @SerializedName("success")
    var success: String,

    @SerializedName("totalpage")
    var totalpage: Number,

    @SerializedName("dop_finger")
    var DOPFinger: MutableList<DOPFinger>
)

data class DOPFinger(
    @SerializedName("id_dop")
    var ID: String,

    @SerializedName("nomor")
    var Nomor: String,

    @SerializedName("nama")
    var Nama: String,

    @SerializedName("type_dop")
    var Type: String,

    @SerializedName("date_dop")
    var Date: String,

    @SerializedName("status_ambil")
    var StatusAmbil: String,

    @SerializedName("masa_aktif")
    var Expired: String,

    @SerializedName("status_dop")
    var Status: String,

    @SerializedName("reason")
    var Reason: String,

    @SerializedName("reject_reason")
    var RejectReason: String,

    @SerializedName("update_by")
    var UpdateBy: String,

    @SerializedName("input_by")
    var InputBy: String
)