package MainActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chad.R

class MainActivity : AppCompatActivity() {
    private lateinit var contactsButton: LinearLayout
    private lateinit var medsButton: LinearLayout
    private lateinit var calendarButton: LinearLayout
    private lateinit var addButton: ImageButton

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // przycisk kontaktu
        contactsButton = findViewById(R.id.layoutContacts)
        contactsButton.setOnClickListener {
            opencontactsMain()
        }

        //przycisk leki
        medsButton = findViewById(R.id.layoutMedication)
        medsButton.setOnClickListener {
            openmedsMain()
        }

        //przycisk kalendarz
        calendarButton = findViewById(R.id.layoutCalendar)
        calendarButton.setOnClickListener {
            opencalendarMain()
        }

        //przycisk dodaj
        addButton = findViewById(R.id.plus)
        addButton.setOnClickListener {
            addMain()
        }
    }

    // Metoda do otwierania drugiej aktywności
    private fun opencontactsMain(){
        val intent = Intent(this, contactsMain::class.java)
        startActivity(intent)
    }

    private fun openmedsMain(){
        val intent = Intent(this, medsMain::class.java)
        startActivity(intent)
    }

    private fun opencalendarMain(){
        val intent = Intent(this, calendarMain::class.java)
        startActivity(intent)
    }

    private fun addMain(){
        val intent = Intent(this, addMain::class.java)
        startActivity(intent)
    }
}