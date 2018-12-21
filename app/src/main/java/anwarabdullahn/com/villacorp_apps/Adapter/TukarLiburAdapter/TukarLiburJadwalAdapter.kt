package anwarabdullahn.com.villacorp_apps.Adapter.TukarLiburAdapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import anwarabdullahn.com.villacorp_apps.Model.JadwalLibur
import anwarabdullahn.com.villacorp_apps.R
import org.jetbrains.anko.find

class TukarLiburJadwalAdapter(val jadwalList: MutableList<JadwalLibur>) : RecyclerView.Adapter <TukarLiburJadwalAdapter.ViewHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val contentView = LayoutInflater.from(p0.context).inflate(R.layout.list_tukar_libur_jadwal, p0, false)

        return ViewHolder(
            contentView
        )
    }

    override fun getItemCount(): Int {
        return jadwalList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val jadwalLibur = itemView.find<TextView>(R.id.tanggalLibur)
        val tempatLibur = itemView.find<TextView>(R.id.tempatTxt)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val jadwal: JadwalLibur = jadwalList[p1]

        p0.jadwalLibur.text = jadwal.DateOff
        p0.tempatLibur.text = jadwal.Kantor
    }

    fun addmore(jadwal: MutableList<JadwalLibur>){
        for (jd in jadwal){
            jadwalList.add(jd)
        }
    }


}