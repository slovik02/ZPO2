package MainActivity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chad.R
import com.example.chad.models.Contact
import com.google.firebase.auth.FirebaseAuth

class add : AppCompatActivity() {

    private lateinit var confirm: Button
    private lateinit var nameEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var tagSpinner: Spinner

    private val firestoreHandler: FirestoreInterface = FirestoreHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        nameEditText = findViewById(R.id.contact_name)
        surnameEditText = findViewById(R.id.contact_surname)
        phoneEditText = findViewById(R.id.editTextText4)
        addressEditText = findViewById(R.id.editTextText3)
        tagSpinner = findViewById(R.id.spinner)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.tags,
            R.layout.spinner_list
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tagSpinner.adapter = adapter

        confirm = findViewById(R.id.confirm)
        confirm.setOnClickListener {
            validateDetails()
        }
    }

    private fun validateDetails() {
        val name = nameEditText.text.toString().trim()
        val surname = surnameEditText.text.toString().trim()
        val phoneNumber = phoneEditText.text.toString().trim()
        val address = addressEditText.text.toString().trim()
        val tag = tagSpinner.selectedItem.toString()

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
        if (TextUtils.isEmpty(address)) {
            addressEditText.error = "Please enter an address"
            return
        }

        val nameSurnameRegex = Regex("^[a-zA-Z ]+\$") // Allows letters and spaces

        if (!name.matches(nameSurnameRegex)) {
            nameEditText.error = "Name should contain only letters"
            return
        }
        if (!surname.matches(nameSurnameRegex)) {
            surnameEditText.error = "Surname should contain only letters"
            return
        }

        val phoneRegex = Regex("^\\d{9}\$")

        if (!phoneNumber.matches(phoneRegex)) {
            phoneEditText.error = "Phone number should contain exactly 9 digits"
            return
        }

        saveToFirestore(name, surname, phoneNumber.toInt(), address, tag)
    }

    private fun saveToFirestore(name: String, surname: String, phoneNumber: Int, address: String, tag: String) {
        val userEmail = FirebaseAuth.getInstance().currentUser?.email

        if (userEmail == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        val contact = Contact(name, surname, phoneNumber, address, tag)

        firestoreHandler.addContact(
            userEmail,
            contact,
            onSuccess = {
                Toast.makeText(this, "Contact added to Firestore", Toast.LENGTH_SHORT).show()
                openContactsMain()
            },
            onFailure = { e ->
                Toast.makeText(this, "Error adding contact to Firestore: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun openContactsMain() {
        val intent = Intent(this, contactsMain::class.java)
        startActivity(intent)
    }
}