import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
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

        drugNameEditText = findViewById(R.id.entername)
        dosageEditText = findViewById(R.id.enterdosage)

        val mySpinner: Spinner = findViewById(R.id.dosage_spin)
        val mySpinner2: Spinner = findViewById(R.id.time_spin)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.dosage,
            R.layout.spinner_list
        )

        val adapter2 = ArrayAdapter.createFromResource(
            this,
            R.array.time,
            R.layout.spinner_list
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mySpinner.adapter = adapter

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mySpinner2.adapter = adapter2

        confirm = findViewById(R.id.confirm)
        confirm.setOnClickListener {
            validateDetails()
        }
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

        val drugNameRegex = Regex("^[a-zA-Z ]+\$")
        if (!drugName.matches(drugNameRegex)) {
            drugNameEditText.error = "Drug name should contain only letters"
            return
        }

        val dosageRegex = Regex("^\\d+\$")
        if (!dosage.matches(dosageRegex)) {
            dosageEditText.error = "Dosage should contain only digits"
            return
        }

        showNotificationPopup()
    }
    // niedokończone
    private fun showNotificationPopup() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.activity_notification_popup, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)

        val alertDialog = dialogBuilder.create()
        alertDialog.show()

        val okButton: Button = dialogView.findViewById(R.id.ok_button)
        okButton.setOnClickListener {
            alertDialog.dismiss()
        }
    }
}
