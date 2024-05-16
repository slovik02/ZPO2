package MainActivity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chad.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class addMain : AppCompatActivity() {
    private lateinit var homeButton: ImageButton
    private lateinit var addSleep: Button
    private lateinit var addActivity: Button


    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // przycisk kontaktu
        homeButton = findViewById(R.id.imageButton)
        homeButton.setOnClickListener {
            goMain()
        }

        addSleep = findViewById(R.id.sleep_add)
        addSleep.setOnClickListener {
            showSleepPop()
        }

        addActivity = findViewById(R.id.activity_add)
        addActivity.setOnClickListener {
            showActivityPop()
        }

        // Get reference to the TextView
        val dateTextView = findViewById<TextView>(com.example.chad.R.id.dateTextView)

        // Get the current date
        val currentDate: String = getCurrentDate()

        // Set the current date to the TextView
        dateTextView.text = "Today: $currentDate"
    }

    // Metoda do otwierania drugiej aktywności
    private fun goMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    //do poprawy
    private fun showSleepPop(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.add_sleep_popup)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val confirm = dialog.findViewById<Button>(R.id.confirmpop)
        confirm.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showActivityPop(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.add_activity_popup)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val confirmA = dialog.findViewById<Button>(R.id.confirmpopA)
        confirmA.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun getCurrentDate(): String {
        val monthFormat = SimpleDateFormat("MMMM", Locale.getDefault())
        val calendar: Calendar = Calendar.getInstance()
        val dayOfMonth: Int = calendar.get(Calendar.DAY_OF_MONTH)
        val daySuffix: String = getDayOfMonthSuffix(dayOfMonth)
        val monthName: String = monthFormat.format(calendar.time)
        return "$dayOfMonth$daySuffix $monthName"
    }

    // Method to get the suffix for the day of month (e.g., 1st, 2nd, 3rd)
    private fun getDayOfMonthSuffix(n: Int): String {
        return if (n in 11..13) {
            "th"
        } else when (n % 10) {
            1 -> "st"
            2 -> "nd"
            3 -> "rd"
            else -> "th"
        }
    }
}