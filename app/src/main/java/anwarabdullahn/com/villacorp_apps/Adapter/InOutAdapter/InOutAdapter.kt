package anwarabdullahn.com.villacorp_apps.Adapter.InOutAdapter

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import anwarabdullahn.com.villacorp_apps.Activity.TukarLibur.DoTukarLiburActivity
import anwarabdullahn.com.villacorp_apps.Model.InOut
import anwarabdullahn.com.villacorp_apps.Model.JadwalLibur
import anwarabdullahn.com.villacorp_apps.R
import org.jetbrains.anko.find

class InOutAdapter(val inOutList: MutableList<InOut>) : RecyclerView.Adapter <InOutAdapter.ViewHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val contentView = LayoutInflater.from(p0.context).inflate(R.layout.list_in_out, p0, false)

        return ViewHolder(contentView)
    }

    override fun getItemCount(): Int {
        return inOutList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val codeTxt = itemView.find<TextView>(R.id.codeInOutTxt)
        val tanggalTxt= itemView.find<TextView>(R.id.tanggalInOutTxt)
        val statusTxt= itemView.find<TextView>(R.id.statusInOutTxt)

        val redColor = itemView.resources.getDrawable(R.drawable.circle_menu_red)!!
        val yellowColor = itemView.resources.getDrawable(R.drawable.circle_menu_yellow)!!
        val greenColor = itemView.resources.getDrawable(R.drawable.circle_menu_green)!!
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val inOut: InOut = inOutList[p1]
        p0.codeTxt.text = inOut.Nomor
        p0.tanggalTxt.text = inOut.DateInOut

        when {
            inOut.StatusInOut == "2" -> {
                p0.statusTxt.text = "Rejected"
                p0.statusTxt.background =  p0.redColor
            }
            inOut.StatusInOut == "1" -> {
                p0.statusTxt.text = "Approved"
                p0.statusTxt.background =  p0.greenColor
            }
            else -> {
                p0.statusTxt.text = "On Process"
                p0.statusTxt.background =  p0.yellowColor
            }
        }
    }

    fun addmore(inOut: MutableList<InOut>){
        for (iO in inOut){
            inOutList.add(iO)
        }
        notifyDataSetChanged()
    }


}