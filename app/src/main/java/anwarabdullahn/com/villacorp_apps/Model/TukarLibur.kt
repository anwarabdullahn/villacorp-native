package anwarabdullahn.com.villacorp_apps.Model

import com.google.gson.annotations.SerializedName

data class TukarLibur(
    @SerializedName("success")
    var success: String,

    @SerializedName("totalpage")
    var totalpage: Number,

    @SerializedName("jadwal_libur")
    var jadwal_libur: MutableList<JadwalLibur>,

    @SerializedName("change_off")
    var Pengajuan: MutableList<Pengajuan>
)

data class JadwalLibur(
    @SerializedName("id")
    var ID: String,

    @SerializedName("jdid")
    var JDID: String,

    @SerializedName("aname")
    var Kantor: String,

    @SerializedName("date_off")
    var DateOff: String
)

data class Pengajuan(
    @SerializedName("id_change_off")
    var IdChangeOff: String,

    @SerializedName("nomor")
    var Nomor: String,

    @SerializedName("date_old")
    var DateOld: String,

    @SerializedName("date_new")
    var DateNew: String,

    @SerializedName("input_date")
    var InputDate: String,

    @SerializedName("update_by")
    var UpdateBy: String,

    @SerializedName("reject_reason")
    var RejectReason: String,

    @SerializedName("status")
    var Status: String,

    @SerializedName("alasan")
    var Alasan: String,

    @SerializedName("input_by")
    var InputBy: String
)

