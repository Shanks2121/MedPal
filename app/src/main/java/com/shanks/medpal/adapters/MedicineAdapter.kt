package com.shanks.medpal.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shanks.medpal.R
import com.shanks.medpal.models.Medicine

class MedicineAdapter : RecyclerView.Adapter<MedicineAdapter.MedicineHolder>()  {
    private var list = emptyList<Medicine>()


    class MedicineHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val medicineName = itemView.findViewById<TextView>(R.id.tvMedicineName)
        val medicineType = itemView.findViewById<TextView>(R.id.tvmedicineType)
        val medicineSchedule = itemView.findViewById<TextView>(R.id.tvMedicineSchedule)

    }

    fun setData(newDataList : List<Medicine>){
        list = newDataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_medicine, parent, false)
        return MedicineHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun onBindViewHolder(holder: MedicineHolder, position: Int) {
        val medicine = list[position]

        holder.medicineName.text = medicine.medicineName
        holder.medicineType.text = medicine.medicineType
        holder.medicineSchedule.text = medicine.dayFormat

    }
}