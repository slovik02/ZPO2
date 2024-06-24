package MainActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chad.R

class MedicationAdapter(private val medicationsList: List<Medication>) :
    RecyclerView.Adapter<MedicationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.med_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val medication = medicationsList[position]
        holder.bind(medication)
    }

    override fun getItemCount(): Int {
        return medicationsList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val drugNameTextView: TextView = itemView.findViewById(R.id.NAME)
        private val dosageTextView: TextView = itemView.findViewById(R.id.dosageint)
        private val unitTextView: TextView = itemView.findViewById(R.id.unitint)
        private val timeTextView: TextView = itemView.findViewById(R.id.timeint)

        fun bind(medication: Medication) {
            drugNameTextView.text = medication.drugName
            dosageTextView.text = "Dosage: ${medication.dosage}"
            unitTextView.text = "Unit: ${medication.unit}"
            timeTextView.text = "Time: ${medication.time}"
        }
    }
}
