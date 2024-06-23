package MainActivity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chad.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Evening : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MedicationAdapter
    private val medicationsList = mutableListOf<Medication>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evening)

        recyclerView = findViewById(R.id.recyclerViewE)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MedicationAdapter(medicationsList)
        recyclerView.adapter = adapter

        loadMedications()
    }

    private fun loadMedications() {
        val userEmail = FirebaseAuth.getInstance().currentUser?.email

        if (userEmail == null) {
            // Handle case where user is not logged in
            return
        }

        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(userEmail)
            .collection("medications")
            .whereEqualTo("time", "evening")
            .get()
            .addOnSuccessListener { querySnapshot ->
                medicationsList.clear()
                for (document in querySnapshot.documents) {
                    val drugName = document.getString("name") ?: ""
                    val dosage = document.getDouble("dosage") ?: 0.0
                    val unit = document.getString("unit") ?: ""
                    val time = document.getString("time") ?: ""

                    val medication = Medication(drugName, dosage, unit, time)
                    medicationsList.add(medication)
                }
                // Notify adapter of data change
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                // Handle failure to fetch medications
            }
    }
}