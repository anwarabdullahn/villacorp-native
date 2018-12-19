package anwarabdullahn.com.villacorp_apps.Adapter

import android.graphics.Color
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import anwarabdullahn.com.villacorp_apps.Model.Agenda
import anwarabdullahn.com.villacorp_apps.R
import org.jetbrains.anko.find
import android.graphics.drawable.GradientDrawable
import java.util.*


class AgendaRecyclerAdapter(val agendaList: MutableList<Agenda>) : RecyclerView.Adapter<AgendaRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val contentView = LayoutInflater.from(p0.context).inflate(R.layout.list_agenda,null)

        return ViewHolder(contentView)
    }

    override fun getItemCount(): Int {
        return agendaList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val agenda: Agenda = agendaList[p1]

        p0.remarkTxt.text = agenda.remark
        p0.ruanganTxt.text = agenda.ruangan
        p0.hariTxt.text = agenda.hari+", "
        p0.dateTxt.text = agenda.tanggal
        p0.mulaiTxt.text = agenda.jam_mulai
        p0.selesaiTxt.text = " - " + agenda.jam_selesai
        p0.depTxt.text = "Dept, "+agenda.department

        val r = Random()
        val red = r.nextInt(255 - 0 + 1) + 0
        val green = r.nextInt(255 - 0 + 1) + 0
        val blue = r.nextInt(255 - 0 + 1) + 0

        val color = Color.rgb(red, green, blue)
        p0.remarkTxt.setTextColor(color)
        p0.sideView.setBackgroundColor(color)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val remarkTxt = itemView.find<TextView>(R.id.remarkTxt)
        val ruanganTxt = itemView.find<TextView>(R.id.ruangTxt)
        val hariTxt = itemView.find<TextView>(R.id.hariTxt)
        val dateTxt = itemView.find<TextView>(R.id.dateTxt)
        val mulaiTxt = itemView.find<TextView>(R.id.mulaiTxt)
        val selesaiTxt = itemView.find<TextView>(R.id.selesaiTxt)
        val depTxt = itemView.find<TextView>(R.id.depTxt)
        val sideView = itemView.find<View>(R.id.sideView)
    }

}