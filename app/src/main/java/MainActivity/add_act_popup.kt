package MainActivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chad.R

class add_act_popup : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_act_popup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val mySpinner: Spinner = findViewById(R.id.spinner_act)
        val mySpinner2: Spinner = findViewById(R.id.spinner3)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.activity,
            R.layout.spinner_list
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mySpinner.adapter = adapter

        val adapter2 = ArrayAdapter.createFromResource(
            this,
            R.array.intens,
            R.layout.spinner_list
        )
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mySpinner2.adapter = adapter2
    }
}