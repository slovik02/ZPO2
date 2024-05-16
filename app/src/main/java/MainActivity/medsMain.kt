package MainActivity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chad.R

class medsMain : AppCompatActivity() {
    private lateinit var home: ImageButton
    private lateinit var addmeds: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_meds_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //home
        home = findViewById(R.id.home)
        home.setOnClickListener {
            homeMain()
        }

        //home
        addmeds = findViewById(R.id.addmed)
        addmeds.setOnClickListener {
            addmed()
        }
    }

    private fun homeMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun addmed(){
        val intent = Intent(this, addmed::class.java)
        startActivity(intent)
    }
}