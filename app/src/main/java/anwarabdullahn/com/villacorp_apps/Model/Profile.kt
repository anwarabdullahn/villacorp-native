package anwarabdullahn.com.villacorp_apps.Model

import com.google.gson.annotations.SerializedName

data class Profile(
    @SerializedName("name")
    var name: String,

    @SerializedName("photo")
    var photo: String,

    @SerializedName("phone")
    var phone: String,

    @SerializedName("religion")
    var religion: String,

    @SerializedName("schedule")
    var schedule: String,

    @SerializedName("sex")
    var sex: String,

    @SerializedName("office")
    var office: String,

    @SerializedName("department")
    var department: String,

    @SerializedName("brand")
    var brand: String,

    @SerializedName("jabatan")
    var jabatan: String,

    @SerializedName("posisi")
    var posisi: String,

    @SerializedName("birth_place")
    var birth_place: String,

    @SerializedName("birth_date")
    var birth_date: String,

    @SerializedName("address")
    var address: String

)