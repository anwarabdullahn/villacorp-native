package anwarabdullahn.com.villacorp_apps.Model

import com.google.gson.annotations.SerializedName

class Securitys(
    @SerializedName("success")
    var Success: String,

    @SerializedName("totalpage")
    var TotalPage: String,

    @SerializedName("sekuriti")
    var Security: MutableList<Security>
)

class Security (

    @SerializedName("id")
    var ID: String,

    @SerializedName("nama")
    var Name: String,

    @SerializedName("foto")
    var Pic: String

)

