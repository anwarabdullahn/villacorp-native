package anwarabdullahn.com.villacorp_apps.Model

import com.google.gson.annotations.SerializedName

data class Info(

    @SerializedName("succes")
    var succes: String,

    @SerializedName("jmlsisacuti")
    var jmlsisacuti: Int,

    @SerializedName("periode")
    var periode: String,

    @SerializedName("sisaHariKerja")
    var sisaHariKerja: String,

    @SerializedName("hakdop")
    var hakdop: Int,

    @SerializedName("haritelatkerja")
    var haritelatkerja: HariTelatKerja,

    @SerializedName("jmlharikerja")
    var jmlharikerja: String,

    @SerializedName("telatkerja")
    var telatkerja: TelatKerja,

    @SerializedName("jmlabsen")
    var jmlabsen: Int,

    @SerializedName("jmlizin")
    var jmlizin: Int,

    @SerializedName("jmlsakit")
    var jmlsakit: Int,

    @SerializedName("jmlcutikhusus")
    var jmlcutikhusus: Int,

    @SerializedName("jmlcutitahunan")
    var jmlcutitahunan: Int,

    @SerializedName("jmldopmasuk")
    var jmldopmasuk: Int,

    @SerializedName("jmldoplibur")
    var jmldoplibur: Int

)

data class HariTelatKerja(
    @SerializedName("lambat")
    var lambat: Int,

    @SerializedName("cepat")
    var cepat: Int
)

data class TelatKerja(
    @SerializedName("totalLate")
    var totalLate: String,

    @SerializedName("totalEarly")
    var totalEarly: String
)