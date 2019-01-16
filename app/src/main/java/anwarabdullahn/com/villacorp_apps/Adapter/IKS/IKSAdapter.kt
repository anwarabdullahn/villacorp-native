package anwarabdullahn.com.villacorp_apps.Adapter.IKS

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import anwarabdullahn.com.villacorp_apps.Model.IKS
import anwarabdullahn.com.villacorp_apps.R
import org.jetbrains.anko.find

class IKSAdapter(val IKSList: MutableList<IKS>) : RecyclerView.Adapter<IKSAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val codeTxt = itemView.find<TextView>(R.id.codeTxt)
        val tanggalTxt = itemView.find<TextView>(R.id.tanggalTxt)
        val statusTxt = itemView.find<TextView>(R.id.statusTxt)


        val redColor = itemView.resources.getDrawable(R.drawable.circle_menu_red)!!
        val yellowColor = itemView.resources.getDrawable(R.drawable.circle_menu_yellow)!!
        val greenColor = itemView.resources.getDrawable(R.drawable.circle_menu_green)!!

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): IKSAdapter.ViewHolder {
        val contentView = LayoutInflater.from(p0.context).inflate(R.layout.list_iks, p0, false)

        return ViewHolder(contentView)
    }

    override fun getItemCount(): Int {
       return IKSList.size
    }

    override fun onBindViewHolder(p0: IKSAdapter.ViewHolder, p1: Int) {
       val iks: IKS = IKSList[p1]
        p0.codeTxt.text = iks.Nomor
        p0.tanggalTxt.text = iks.Date

        when {
            iks.Status == "2" -> {
                p0.statusTxt.text = "Rejected"
                p0.statusTxt.background =  p0.redColor
            }
            iks.Status == "1" -> {
                p0.statusTxt.text = "Approved"
                p0.statusTxt.background =  p0.greenColor
            }
            else -> {
                p0.statusTxt.text = "On Process"
                p0.statusTxt.background =  p0.yellowColor
            }
        }
    }

    fun addmore(iks: MutableList<IKS>){
        for (iO in iks){
            IKSList.add(iO)
        }
        notifyDataSetChanged()
    }

}
