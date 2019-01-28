package anwarabdullahn.com.villacorp_apps.Adapter.IKS

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import anwarabdullahn.com.villacorp_apps.Model.Security
import anwarabdullahn.com.villacorp_apps.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import org.jetbrains.anko.find


class SecurityAdapter(var listSecurity: MutableList<Security>) : RecyclerView.Adapter<SecurityAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val codeTxt = itemView.find<TextView>(R.id.codeTxt)
        val photoImg = itemView.find<CircleImageView>(R.id.photoImg)

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SecurityAdapter.ViewHolder {
        val contentView = LayoutInflater.from(p0.context).inflate(R.layout.list_security, p0, false)

        return ViewHolder(contentView)
    }

    override fun getItemCount(): Int {
        return listSecurity.size
    }

    override fun onBindViewHolder(p0: SecurityAdapter.ViewHolder, p1: Int) {
        val sec: Security = listSecurity[p1]
        p0.codeTxt.text = sec.Name
        Picasso.get().load(sec.Pic).resize(50, 50).centerCrop().into(p0.photoImg)

//        p0.tanggalTxt.text = iks.Date

    }

    fun addmore(sec: MutableList<Security>){
        for (iO in sec){
            listSecurity.add(iO)
        }
        notifyDataSetChanged()
    }

    fun updateList(sec: MutableList<Security>) {
        listSecurity = ArrayList()
        listSecurity.addAll(sec)
        notifyDataSetChanged()
    }

}
