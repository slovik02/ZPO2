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

class contactsMain : AppCompatActivity() {
    private lateinit var addButton: LinearLayout
    private lateinit var home: ImageButton
    private lateinit var list: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contacts_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // dodawanie leków
        addButton = findViewById(R.id.linearLayout2)
        addButton.setOnClickListener {
            openaddMain()
        }

        // home
        home = findViewById(R.id.imageButton)
        home.setOnClickListener {
            homeMain()
        }

        // lista kontaktów
        list = findViewById(R.id.scrollconts)
        list.setOnClickListener {
            scrollCont()
        }
    }

    // Metoda do otwierania drugiej aktywności
    private fun openaddMain(){
        val intent = Intent(this, add::class.java)
        startActivity(intent)
    }

    private fun homeMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun scrollCont(){
        val intent = Intent(this, scroll::class.java)
        startActivity(intent)
    }


}