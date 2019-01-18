package anwarabdullahn.com.villacorp_apps.Model

import com.google.gson.annotations.SerializedName

class LeaveFingers(
    @SerializedName("success")
    var Success: String,

    @SerializedName("totalpage")
    var TotalPage: String,

    @SerializedName("saldocuti")
    var SaldoCuti: Int,

    @SerializedName("leave_finger")
    var LeaveFinger: MutableList<LeaveFinger>
)

class LeaveFinger(
    @SerializedName("id_leave")
    var ID: String,

    @SerializedName("nomor")
    var Nomor: String,

    @SerializedName("type_leave")
    var TypeLeave: String,

    @SerializedName("update_by")
    var UpdateBy: String,

    @SerializedName("input_by")
    var InputBy: String,

    @SerializedName("reason")
    var Reason: String,

    @SerializedName("reject_reason")
    var RejectReason: String,

    @SerializedName("lampiran")
    var Lampiran: String,

    @SerializedName("date_leave")
    var Date: String,

    @SerializedName("status_leave")
    var Status: String
)