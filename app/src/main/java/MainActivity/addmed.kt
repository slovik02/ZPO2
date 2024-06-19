package MainActivity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chad.R

class addmed : AppCompatActivity() {
    private lateinit var confirm: Button
    private lateinit var drugNameEditText: EditText
    private lateinit var dosageEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_addmed)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        drugNameEditText = findViewById(R.id.entername) // Initialize EditText for drug name
        dosageEditText = findViewById(R.id.enterdosage) // Initialize EditText for dosage

        val mySpinner: Spinner = findViewById(R.id.dosage_spin)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.dosage,
            R.layout.spinner_list
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mySpinner.adapter = adapter

        // dodawanie leków
        confirm = findViewById(R.id.confirm)
        confirm.setOnClickListener {
            validateDetails()
        }
    }

    // Metoda do otwierania drugiej aktywności
    private fun openMedsMain(){
        val intent = Intent(this, medsMain::class.java)
        startActivity(intent)
    }

    private fun validateDetails() {
        val drugName = drugNameEditText.text.toString().trim()
        val dosage = dosageEditText.text.toString().trim()

        if (TextUtils.isEmpty(drugName)) {
            drugNameEditText.error = "Please enter a drug name"
            return
        }
        if (TextUtils.isEmpty(dosage)) {
            dosageEditText.error = "Please enter a dosage"
            return
        }

        // Regular expression to check if the drug name contains only letters
        val drugNameRegex = Regex("^[a-zA-Z ]+\$") // Allows letters and spaces

        if (!drugName.matches(drugNameRegex)) {
            drugNameEditText.error = "Drug name should contain only letters"
            return
        }

        // Regular expression to check if the dosage contains only digits
        val dosageRegex = Regex("^\\d+\$") // Allows only digits

        if (!dosage.matches(dosageRegex)) {
            dosageEditText.error = "Dosage should contain only digits"
            return
        }

        // Continue with your logic here (e.g., saving data, navigating to next screen)
        openMedsMain()
    }
}
