package anwarabdullahn.com.villacorp_apps.Adapter

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import anwarabdullahn.com.villacorp_apps.Activity.PesanDetailActivity
import anwarabdullahn.com.villacorp_apps.Model.Pesan
import anwarabdullahn.com.villacorp_apps.R

class PesanRecyclerAdapter(val pesanList: MutableList<Pesan>) : RecyclerView.Adapter<PesanRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val contentView = LayoutInflater.from(p0.context).inflate(R.layout.list_pesan, p0, false)

        return ViewHolder(contentView)
    }

    override fun getItemCount(): Int {
        return pesanList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val pesan: Pesan = pesanList[p1]

        if(pesanList[p1].status == "1"){
            p0.pesanJudul.setTextColor((0xffaeaeae).toInt())
            p0.pesanImg.setColorFilter((0xffaeaeae).toInt())
        } else if(pesanList[p1].status == "0") {
            p0.pesanJudul.setTextColor((0xff000000).toInt())
            p0.pesanImg.setColorFilter((0xff000000).toInt())
        }

        p0.pesanJudul.text = pesan.judul
        p0.pesanWaktu.text = pesan.waktu

        p0.itemView.setOnClickListener{
            val intent = Intent(p0.itemView.context, PesanDetailActivity::class.java)
            intent.putExtra("judul", pesan.judul)
            intent.putExtra("waktu", pesan.waktu)
            intent.putExtra("approve", pesan.approve_by)
            intent.putExtra("id", pesan.id)
            intent.putExtra("pesan", pesan.pesan)
            startActivity(p0.itemView.context,intent,null)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val pesanJudul = itemView.findViewById(R.id.tanggalLibur) as TextView
        val pesanWaktu = itemView.findViewById(R.id.pesanWaktuTxt) as TextView
        val bgView = itemView.findViewById(R.id.bgView) as View
        val pesanImg = itemView.findViewById(R.id.jadwalImg) as ImageView
    }

    fun addmore(pesan: MutableList<Pesan>) {
        for (pm in pesan) {
            pesanList.add(pm)
        }
        notifyDataSetChanged()
    }
}