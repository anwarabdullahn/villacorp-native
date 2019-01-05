package anwarabdullahn.com.villacorp_apps.Adapter.DOP

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import anwarabdullahn.com.villacorp_apps.Model.DOPFinger
import anwarabdullahn.com.villacorp_apps.R
import org.jetbrains.anko.find

class DOPAdapter(val dopList: MutableList<DOPFinger>) : RecyclerView.Adapter <DOPAdapter.ViewHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val contentView = LayoutInflater.from(p0.context).inflate(R.layout.list_dop, p0, false)

        return ViewHolder(contentView)
    }

    override fun getItemCount(): Int {
        return dopList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val codeTxt = itemView.find<TextView>(R.id.codeTxt)
        val tanggalTxt= itemView.find<TextView>(R.id.tanggalTxt)
        val statusTxt= itemView.find<TextView>(R.id.statusTxt)
        val iconKeluar = itemView.find<ImageView>(R.id.iconKeluarImg)
        val iconMasuk = itemView.find<ImageView>(R.id.iconMasukImg)

        val redColor = itemView.resources.getDrawable(R.drawable.circle_menu_red)!!
        val yellowColor = itemView.resources.getDrawable(R.drawable.circle_menu_yellow)!!
        val greenColor = itemView.resources.getDrawable(R.drawable.circle_menu_green)!!
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val dop: DOPFinger= dopList[p1]
        p0.codeTxt.text = dop.Nomor
        p0.tanggalTxt.text = dop.Date

        when {
            dop.Status == "2" -> {
                p0.statusTxt.text = "Rejected"
                p0.statusTxt.background =  p0.redColor
            }
            dop.Status == "1" -> {
                p0.statusTxt.text = "Approved"
                p0.statusTxt.background =  p0.greenColor
            }
            else -> {
                p0.statusTxt.text = "On Process"
                p0.statusTxt.background =  p0.yellowColor
            }
        }

        when {
            dop.Type == "1" -> {
                p0.iconKeluar.visibility = View.GONE
                p0.iconMasuk.visibility = View.VISIBLE
            }
            else -> {
                p0.iconKeluar.visibility = View.VISIBLE
                p0.iconMasuk.visibility = View.GONE
            }
        }

        p0.statusTxt.setOnClickListener {
        }
    }

    fun addmore(dop: MutableList<DOPFinger>){
        for (iO in dop){
            dopList.add(iO)
        }
        notifyDataSetChanged()
    }


}