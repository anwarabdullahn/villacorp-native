package anwarabdullahn.com.villacorp_apps.Adapter.TukarLiburAdapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import anwarabdullahn.com.villacorp_apps.Model.Pengajuan
import anwarabdullahn.com.villacorp_apps.R
import org.jetbrains.anko.find

class TukarLiburPengajuanAdapter(val pengajuanList: MutableList<Pengajuan>) : RecyclerView.Adapter <TukarLiburPengajuanAdapter.ViewHolder>(){


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val contentView = LayoutInflater.from(p0.context).inflate(R.layout.list_tukar_libur_pengajuan, p0, false)

        return ViewHolder(contentView)
    }

    override fun getItemCount(): Int {
        return pengajuanList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val pengajuan = pengajuanList[p1]
        p0.codeTxt.text = pengajuan.Nomor
        p0.jadwalBaruTxt.text = pengajuan.DateNew
        if (pengajuan.Status == "2"){
            p0.statusTxt.text = "Rejected"
            p0.statusTxt.background =  p0.redColor
        } else if (pengajuan.Status == "1"){
            p0.statusTxt.text = "Approved"
            p0.statusTxt.background =  p0.greenColor
        } else{
            p0.statusTxt.text = "On Process"
            p0.statusTxt.background =  p0.yellowColor
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val codeTxt = itemView.find<TextView>(R.id.codeTxt)
        val jadwalBaruTxt = itemView.find<TextView>(R.id.tanggalBaruTxt)
        val statusTxt = itemView.find<TextView>(R.id.statusTxt)
        val redColor = itemView.resources.getDrawable(R.drawable.circle_menu_red)!!
        val yellowColor = itemView.resources.getDrawable(R.drawable.circle_menu_yellow)!!
        val greenColor = itemView.resources.getDrawable(R.drawable.circle_menu_green)!!
    }

    fun addmore(pengajuan: MutableList<Pengajuan>){
        for (pg in pengajuan){
            pengajuanList.add(pg)
        }
    }
}