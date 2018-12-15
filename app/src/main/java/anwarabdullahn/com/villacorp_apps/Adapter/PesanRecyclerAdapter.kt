package anwarabdullahn.com.villacorp_apps.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import anwarabdullahn.com.villacorp_apps.Model.Pesan
import anwarabdullahn.com.villacorp_apps.R

class PesanRecyclerAdapter(val pesanList: MutableList<Pesan>) : RecyclerView.Adapter<PesanRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val contentView = LayoutInflater.from(p0?.context).inflate(R.layout.list_pesan, p0, false)

        return ViewHolder(contentView)
    }

    override fun getItemCount(): Int {
        return pesanList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val pesan: Pesan = pesanList[p1]

        p0.pesanJudul.text = pesan.judul
        p0.pesanWaktu.text = pesan.waktu
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val pesanJudul = itemView.findViewById(R.id.pesanJudulTxt) as TextView
        val pesanWaktu = itemView.findViewById(R.id.pesanWaktuTxt) as TextView
    }

    fun addmore(pesan: MutableList<Pesan>) {
        for (pm in pesan) {
            pesanList.add(pm)
        }
        notifyDataSetChanged()
    }
}