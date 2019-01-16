package anwarabdullahn.com.villacorp_apps.Model

import com.google.gson.annotations.SerializedName

class IKSs(
    @SerializedName("success")
    var Success: String,

    @SerializedName("totalpage")
    var TotalPage: String,

    @SerializedName("iks_finger")
    var IKS: MutableList<IKS>
)

class IKS (

    @SerializedName("id_leave")
    var ID: String,

    @SerializedName("nomor")
    var Nomor: String,

    @SerializedName("type_leave")
    var Type: String,

    @SerializedName("date_leave")
    var Date: String,

    @SerializedName("ststus_leave")
    var Status: String

)

