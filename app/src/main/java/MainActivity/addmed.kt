package MainActivity

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chad.R
import com.google.firebase.auth.FirebaseAuth
import java.util.Calendar
import java.util.Locale

class addmed : AppCompatActivity() {
    private lateinit var confirm: Button
    private lateinit var reminder: Button
    private lateinit var drugNameEditText: EditText
    private lateinit var dosageEditText: EditText
    private lateinit var unitSpinner: Spinner
    private lateinit var timeSpinner: Spinner

    private val firestoreHandler = FirestoreHandler()

    @SuppressLint("MissingInflatedId")
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
        unitSpinner = findViewById(R.id.dosage_spin)
        timeSpinner = findViewById(R.id.time_spin)

        val unitAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.dosage,
            R.layout.spinner_list
        )
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        unitSpinner.adapter = unitAdapter

        val timeAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.time,
            R.layout.spinner_list
        )
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        timeSpinner.adapter = timeAdapter

        confirm = findViewById(R.id.confirm)
        confirm.setOnClickListener {
            validateDetails()
        }

        reminder = findViewById(R.id.reminder)
        reminder.setOnClickListener {
            showRemindPop()
        }
    }

    private fun validateDetails() {
        val drugName = drugNameEditText.text.toString().trim()
        val dosageStr = dosageEditText.text.toString().trim()
        val unit = unitSpinner.selectedItem.toString()
        val time = timeSpinner.selectedItem.toString()

        if (TextUtils.isEmpty(drugName)) {
            drugNameEditText.error = "Please enter a drug name"
            return
        }
        if (TextUtils.isEmpty(dosageStr)) {
            dosageEditText.error = "Please enter a dosage"
            return
        }

        val drugNameRegex = Regex("^[a-zA-Z ]+\$")
        if (!drugName.matches(drugNameRegex)) {
            drugNameEditText.error = "Drug name should contain only letters"
            return
        }

        val dosage: Int
        try {
            dosage = dosageStr.toInt()
        } catch (e: NumberFormatException) {
            dosageEditText.error = "Dosage should be a number"
            return
        }

        saveToFirestore(drugName, dosage, unit, time)
    }

    private fun showRemindPop() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.activity_notification_popup)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val hourPicker = dialog.findViewById<NumberPicker>(R.id.hourPicker)
        hourPicker.minValue = 0
        hourPicker.maxValue = 23

        val minutePicker = dialog.findViewById<NumberPicker>(R.id.minutePicker)
        minutePicker.minValue = 0
        minutePicker.maxValue = 59

        val confirmButton = dialog.findViewById<Button>(R.id.confirm_notification)
        confirmButton.setOnClickListener {
            val selectedHour = hourPicker.value
            val selectedMinute = minutePicker.value

            val selectedTime = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute)
            Toast.makeText(this, "Selected Time: $selectedTime", Toast.LENGTH_SHORT).show()

            dialog.dismiss()
        }

        dialog.show()
    }

    private fun saveToFirestore(drugName: String, dosage: Int, unit: String, time: String) {
        val userEmail = FirebaseAuth.getInstance().currentUser?.email

        if (userEmail == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        val medication = Medication(drugName, dosage, unit, time)

        firestoreHandler.addMedication(
            userEmail,
            medication,
            onSuccess = {
                Toast.makeText(this, "Medication added to Firestore", Toast.LENGTH_SHORT).show()
                openMedsMain()
            },
            onFailure = { e ->
                Toast.makeText(this, "Error adding medication to Firestore: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun openMedsMain() {
        val intent = Intent(this, medsMain::class.java)
        startActivity(intent)
    }
}