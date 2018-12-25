package anwarabdullahn.com.villacorp_apps.Model

import com.google.gson.annotations.SerializedName

data class InOuts(
    @SerializedName("success")
    var Success: String,

    @SerializedName("totalpage")
    var TotalPage: String,

    @SerializedName("in_out")
    var InOut: MutableList<InOut>
)

data class InOut(

    @SerializedName("id_inout")
    var IdInOut: String,

    @SerializedName("nomor")
    var Nomor: String,

    @SerializedName("type_inout")
    var TypeInOut: String,

    @SerializedName("date_inout")
    var DateInOut: String,

    @SerializedName("nama")
    var Nama: String,

    @SerializedName("alasan")
    var Reason: String,

    @SerializedName("update_by")
    var UpdateBy: String,

    @SerializedName("input_by")
    var InputBy: String,

    @SerializedName("reject_reason")
    var RejectReason: String,

    @SerializedName("lampiran")
    var Lampiran: String,

    @SerializedName("status_inout")
    var StatusInOut: String

)