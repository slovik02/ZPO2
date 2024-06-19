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

class add : AppCompatActivity() {

    private lateinit var confirm: Button
    private lateinit var nameEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var phoneEditText: EditText // Declare phoneEditText here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        nameEditText = findViewById(R.id.contact_name) // Initialize EditText for name
        surnameEditText = findViewById(R.id.contact_surname) // Initialize EditText for surname
        phoneEditText = findViewById(R.id.editTextText4) // Initialize EditText for phone number

        val mySpinner: Spinner = findViewById(R.id.spinner)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.tags,
            R.layout.spinner_list
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mySpinner.adapter = adapter

        confirm = findViewById(R.id.confirm)
        confirm.setOnClickListener {
            validateDetails()
        }
    }

    private fun validateDetails() {
        val name = nameEditText.text.toString().trim()
        val surname = surnameEditText.text.toString().trim()
        val phoneNumber = phoneEditText.text.toString().trim()

        if (TextUtils.isEmpty(name)) {
            nameEditText.error = "Please enter a name"
            return
        }
        if (TextUtils.isEmpty(surname)) {
            surnameEditText.error = "Please enter a surname"
            return
        }
        if (TextUtils.isEmpty(phoneNumber)) {
            phoneEditText.error = "Please enter a phone number"
            return
        }

        // Regular expression to check if the name and surname contain only letters
        val nameSurnameRegex = Regex("^[a-zA-Z ]+\$") // Allows letters and spaces

        if (!name.matches(nameSurnameRegex)) {
            nameEditText.error = "Name should contain only letters"
            return
        }
        if (!surname.matches(nameSurnameRegex)) {
            surnameEditText.error = "Surname should contain only letters"
            return
        }

        // Regular expression to check if the phone number contains exactly 9 digits
        val phoneRegex = Regex("^\\d{9}\$")

        if (!phoneNumber.matches(phoneRegex)) {
            phoneEditText.error = "Phone number should contain exactly 9 digits"
            return
        }

        // Continue with your logic here (e.g., saving data, navigating to next screen)
        opencontactsMain()
    }

    private fun opencontactsMain() {
        val intent = Intent(this, contactsMain::class.java)
        startActivity(intent)
    }
}
